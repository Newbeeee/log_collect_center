package com.fault.collect.center.service;


import com.fault.collect.center.common.Response;

public interface FeedbackService {
    //标记某条故障模板有效
    Response setTemplateValid(int id);
}
