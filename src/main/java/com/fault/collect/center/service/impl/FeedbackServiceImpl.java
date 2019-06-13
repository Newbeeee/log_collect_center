package com.fault.collect.center.service.impl;



import com.fault.collect.center.common.Response;
import com.fault.collect.center.dao.TemplateDao;
import com.fault.collect.center.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    TemplateDao templateDao;

    @Override
    public Response setTemplateValid(int id) {
        templateDao.setTemplateValidById(id);
        return new Response().success();
    }
}
