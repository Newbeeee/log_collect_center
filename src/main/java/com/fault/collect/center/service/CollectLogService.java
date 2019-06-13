package com.fault.collect.center.service;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.entity.FaultLog;

public interface CollectLogService {
    Response startCollect(String keywords);

    Response addNewFaultLog(FaultLog faultLog);
}
