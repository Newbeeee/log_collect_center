package com.fault.collect.center.service.impl;

import com.fault.collect.center.dao.ProcessLogDao;
import com.fault.collect.center.entity.ProcessLog;
import com.fault.collect.center.service.ProcessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProcessLogServiceImpl implements ProcessLogService {

    @Autowired
    ProcessLogDao processLogDao;

    @Override
    public void insertProcessLog(String message) {
        ProcessLog processLog = new ProcessLog();
        processLog.setDate(new Date());
        processLog.setContent(message);
        processLogDao.insertProcessLog(processLog);
        System.out.println(message);
    }
}
