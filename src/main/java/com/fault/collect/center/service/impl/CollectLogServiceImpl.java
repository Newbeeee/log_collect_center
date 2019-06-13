package com.fault.collect.center.service.impl;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.dao.FaultLogDao;
import com.fault.collect.center.dao.MissionDao;
import com.fault.collect.center.entity.FaultLog;
import com.fault.collect.center.entity.Mission;
import com.fault.collect.center.sender.StartCollectSender;
import com.fault.collect.center.service.CollectLogService;
import com.fault.collect.center.service.ProcessLogService;
import com.fault.collect.center.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CollectLogServiceImpl implements CollectLogService {

    @Autowired
    ProcessLogService processLogService;

    @Autowired
    StartCollectSender startCollectSender;

    @Autowired
    MissionDao missionDao;

    @Autowired
    FaultLogDao faultLogDao;

    @Override
    public Response startCollect(String keywords) {
        //开始收集故障日志
        processLogService.insertProcessLog("开始收集故障日志");

        //每次收集看作一次任务
        //每次开始任务，生成一个任务的mission_id，存储在数据库中
        //之后每条故障日志都对应这个mission_id
        Mission runningMission = missionDao.getRunningMission();
        //如果有正在运行的任务
        //将其状态标识为已完成
        //系统中有且只有一个正在运行的任务
        if (runningMission != null) {
            runningMission.setStatus("done");
            missionDao.setMissionStatus(runningMission);
            processLogService.insertProcessLog(
                    String.format("有任务正在运行，任务id: %s, 标记任务已完成", runningMission.getMissionId()));
        }
        //新建一个任务，标识为正在运行
        //给任务生成一个唯一标识，即mission_id
        String missionToken = TokenUtil.getUUID();
        Mission mission = new Mission();
        mission.setStatus("running");
        mission.setMissionId(missionToken);
        missionDao.insertMission(mission);
        processLogService.insertProcessLog(
                String.format("新建任务，任务id: %s", missionToken));

        //异步调用消息发送器，向agent发送开始收集日志消息
        new Thread() {
            @Override
            public void run() {
                startCollectSender.startCollect(keywords);
            }
        }.start();
        return new Response().success();
    }

    @Override
    public Response addNewFaultLog(FaultLog faultLog) {
        //从数据库中取出正在运行的mission
        //得到mission_id，添加进故障日志记录中
        Mission mission = missionDao.getRunningMission();
        if (mission == null) {
            // TODO: 完善失败的响应设计
            return new Response().failure();
        } else {
            String missionId = mission.getMissionId();
            faultLog.setMissionId(missionId);
        }

        //判断故障日志的本地存储文件是否存在
        //如果存在，将这条故障日志插入数据库
        String storagePath = faultLog.getStoragePath();
        File file = new File(storagePath);
        //不存在，返回失败
        if (!file.exists()) {
            // TODO: 完善失败的响应设计
            return new Response().failure();
        }

        //存在，插入数据库
        faultLogDao.insertFaultLog(faultLog);
        processLogService.insertProcessLog(
                String.format("收集到一条故障日志，日志节点: %s, 日志节点类型: %s，日志服务类型：%s",
                        faultLog.getHost(), faultLog.getNode(), faultLog.getService()));

        return new Response().success();
    }
}
