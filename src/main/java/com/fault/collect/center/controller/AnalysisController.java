package com.fault.collect.center.controller;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    @ResponseBody
    @RequestMapping("/cleanAndMatch")
    public Response cleanAndMatch() {
        return analysisService.clean(true);
    }

    @ResponseBody
    @RequestMapping("/clean")
    public Response clean() {
        return analysisService.clean(false);
    }

    @ResponseBody
    @RequestMapping("/match")
    public Response match() {
        return analysisService.match(null);
    }

    @ResponseBody
    @RequestMapping(value = "/match_detail", method = RequestMethod.GET)
    public Response getMatchDetail(@RequestParam("log_id") String logId) {
        System.out.println("logId" + logId);
        return analysisService.getMatchDetail(Integer.parseInt(logId));
    }
}
