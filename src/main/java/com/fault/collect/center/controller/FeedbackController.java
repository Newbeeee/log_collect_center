package com.fault.collect.center.controller;

import com.fault.collect.center.common.Response;
import com.fault.collect.center.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @ResponseBody
    @RequestMapping(value = "/setValid", method = RequestMethod.GET)
    public Response setTemplateValid(@RequestParam("id") String id) {
        int template_id = Integer.parseInt(id);
        return feedbackService.setTemplateValid(template_id) ;
    }
}
