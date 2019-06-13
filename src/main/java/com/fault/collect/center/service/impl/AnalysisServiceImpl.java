package com.fault.collect.center.service.impl;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.common.ResponseStatusEnum;
import com.fault.collect.center.dao.FaultLogDao;
import com.fault.collect.center.dao.MatchDao;
import com.fault.collect.center.dao.MissionDao;
import com.fault.collect.center.dao.TemplateDao;
import com.fault.collect.center.entity.FaultLog;
import com.fault.collect.center.entity.Match;
import com.fault.collect.center.entity.MatchDetail;
import com.fault.collect.center.entity.Template;
import com.fault.collect.center.service.AnalysisService;
import com.fault.collect.center.service.ProcessLogService;
import com.fault.collect.center.service.RedisService;
import com.fault.collect.center.service.response.FaultLogsResponse;
import com.fault.collect.center.service.response.MatchDetailResponse;
import com.fault.collect.center.util.FileReaderUtil;
import com.fault.collect.center.util.TokenUtil;
import com.fault.collect.center.util.analysis.tfidf.Sim;
import com.fault.collect.center.util.analysis.tfidf.SplitUtil;
import com.fault.collect.center.util.analysis.tfidf.TfIdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    ProcessLogService processLogService;

    @Autowired
    RedisService redisService;

    @Autowired
    MissionDao missionDao;

    @Autowired
    FaultLogDao faultLogDao;

    @Autowired
    TemplateDao templateDao;

    @Autowired
    MatchDao matchDao;

    @Override
    public Response clean(boolean directMatch) {
        //读取本地日志文件内容
        processLogService.insertProcessLog("读取本地日志文件内容中……");

        //根据数据库的日志记录取出日志收集中心节点的本地日志文件
        //读取文件内容
        String missionId = missionDao.getRunningMission().getMissionId();
        //所有的日志
        List<FaultLog> faultLogs = faultLogDao.getFaultLogsByMissionId(missionId);
        //不为空的日志
        List<FaultLog> notNullLogs = new ArrayList<>();
        for (FaultLog faultLog : faultLogs) {
            String storagePath = faultLog.getStoragePath();
            String content = "";
            try {
                content = FileReaderUtil.readFile(storagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //如果文件内容不为空
            if (!content.isEmpty()) {
                //把日志记录设置为非空
                faultLogDao.updateFaultLogNotEmpty(faultLog);
                //加入非空列表
                notNullLogs.add(faultLog);
                //对日志进行清洗
                //TODO: 去除时间和长ID

                //存入redis
                String redisId = TokenUtil.getUUID();
                boolean toRedis = redisService.set(redisId, content);
                if (toRedis) {
                    //存入redis成功，在数据库日志记录中添加redisId
                    //以后可以根据redisId找到日志记录对应的实际日志内容
                    processLogService.insertProcessLog(
                            String.format("缓存日志内容成功，日志id:%s", faultLog.getId() + ""));
                    faultLog.setRedisId(redisId);
                    faultLogDao.updateFaultLogWithRedisId(faultLog);
                } else {
                    //存入redis失败
                    processLogService.insertProcessLog(
                            String.format("缓存日志内容失败，日志id:%s", faultLog.getId() + ""));
                }
            }
        }

        //开始匹配
        processLogService.insertProcessLog("清洗和缓存日志完毕");
        //如果清洗后直接匹配，则直接调用match方法
        if (directMatch) {
            return match(notNullLogs);
        }
        //否则，返回清洗成功
        return new Response().success();
    }

    @Override
    public Response match(List<FaultLog> faultLogs) {
        List<FaultLog> logsToMatch;
        //如果传入为空，则表明不是清洗后直接匹配
        //不是直接匹配的话，要从数据库中取日志
        String missionId = missionDao.getRunningMission().getMissionId();
        if (faultLogs == null) {
            logsToMatch = faultLogDao.getFaultLogsByMissionId(missionId);
        } else {
            //传入不为空，直接赋值
            logsToMatch = faultLogs;
        }

        //开始匹配
        processLogService.insertProcessLog("开始进行故障匹配");
        //此变量记录故障模板为0的次数
        int templatesNull = 0;
        //要匹配的日志数量
        int faultLogsSize = logsToMatch.size();

        //1.根据logs里每条log的service，查询数据库中的故障模板
        //2.把故障模板存入 TfIdf 工具的documents里
        //3.对log进行匹配，返回匹配列表
        TfIdfUtil tfIdfUtil = new TfIdfUtil();

        for (FaultLog faultLog : logsToMatch) {
            //先清除上一次的数据
            tfIdfUtil.clean();
            //从redis中取出日志内容
            String content = redisService.get(faultLog.getRedisId());
            faultLog.setContent(content);
            //取出日志的服务类型
            //根据服务类型从数据库中取出对应的故障模板列表
            String service = faultLog.getService();
            List<Template> templates = templateDao.getTemplatesByService(service);
            //如果列表为空
            //故障模板为0次数加1
            //记录日志为未匹配成功
            if (templates.size() == 0) {
                templatesNull += 1;
                faultLog.setMatch(false);
            } else {
                faultLog.setMatch(true);
                for (Template template : templates) {
                    //把模板id和内容以键值对形式存入工具
                    tfIdfUtil.addDocument(String.valueOf(template.getId()),
                            SplitUtil.split(template.getContent()));
                }
                //日志内容和故障模板列表的内容进行文本相似度匹配
                //返回一个相似列表
                //sim 为故障模板id 和 匹配度
                List<Sim> sims = tfIdfUtil.similarities(SplitUtil.split(faultLog.getContent()));
                System.out.println(sims);
                //算出相似度后需要根据相似度排除掉过低的数据
                //并且根据模板的采纳次数重新排序
                //但这里尚未实现 TODO

                //相似列表中，每条相似可生成一个匹配记录
                //生成匹配记录对象，并存入数据库
                for (Sim sim : sims) {
                    Match match = new Match();
                    match.setLog_id(faultLog.getId());
                    match.setTemplate_id(Integer.parseInt(sim.getDocName()));
                    match.setRate(sim.getScore());
                    match.setMission_id(missionId);
                    matchDao.insertMatch(match);
                }

                //此条日志匹配完成
                processLogService.insertProcessLog(
                        String.format("日志匹配完成，日志id: %s", faultLog.getId() + ""));
            }
        }

        processLogService.insertProcessLog("全部日志匹配完成");

        if (templatesNull == faultLogsSize) {
            processLogService.insertProcessLog("未找到符合的故障模板");
            return new Response().failure(
                    ResponseStatusEnum.NOT_FOUND, "未找到符合的故障模板");
        }

        //返回匹配后的日志列表
        FaultLogsResponse response = new FaultLogsResponse();
        response.setResults(logsToMatch);
        //打印看一下
        System.out.println(logsToMatch);
        processLogService.insertProcessLog("返回日志列表");
        return new Response().success(response);
    }

    @Override
    public Response getMatchDetail(int logId) {
        //MatchDetail的列表
        List<MatchDetail> matchDetails = new ArrayList<>();

        //Match的列表
        //其实就是 MatchDetail 需要 Match 的内容，并且还需要包一个故障模板 Template
        //TODO: 有些别扭，想想怎么优化
        List<Match> matches = matchDao.getMatchsByLogId(logId);

        for (Match match : matches) {
            Template template = templateDao.getTemplateById(match.getTemplate_id());
            MatchDetail matchDetail = new MatchDetail();
            matchDetail.setLogId(logId);
            matchDetail.setTemplate(template);
            matchDetail.setRate(match.getRate());
            matchDetails.add(matchDetail);
        }

        MatchDetailResponse matchDetailResponse = new MatchDetailResponse();
        matchDetailResponse.setMatchDetails(matchDetails);

        return new Response().success(matchDetailResponse);
    }
}
