package com.fault.collect.center.service;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.entity.FaultLog;

import java.util.List;

public interface AnalysisService {
    //清洗故障日志，将清洗后的数据存在redis里
    Response clean(boolean directMatch);

    //将故障日志和数据库中的故障模板进行匹配
    Response match(List<FaultLog> faultLogs);

    //根据日志id从匹配表中找到匹配后的故障模板
    Response getMatchDetail(int logId);
}
