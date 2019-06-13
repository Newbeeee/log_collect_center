package com.fault.collect.center.controller;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.entity.FaultLog;
import com.fault.collect.center.service.CollectLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/collect")
public class CollectLogController {

    @Autowired
    CollectLogService collectLogService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public Response startCollectLogs(@RequestParam("keywords") String keywords) {
        // TODO：如果传来的关键词不是 xx-xx-xx形式，报错
        System.out.println(keywords);
        //return new Response().success();
        return collectLogService.startCollect(keywords);
    }

    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public Response insertFaultLog(@RequestParam("node") String node,
                                   @RequestParam("service") String service,
                                   @RequestParam("log_path") String logPath,
                                   @RequestParam("storage_path") String storagePath,
                                   @RequestParam("host") String host) {
        FaultLog faultLog = new FaultLog();
        faultLog.setNode(node);
        faultLog.setService(service);
        faultLog.setLogPath(logPath);
        faultLog.setStoragePath(storagePath);
        faultLog.setHost(host);
        faultLog.setNotEmpty(0);
        return collectLogService.addNewFaultLog(faultLog);
    }
}
