package com.fault.collect.center.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCollectSender {
    @Autowired
    AmqpTemplate template;

    public void startCollect(String keywords) {
        template.convertAndSend("fanoutExchange", "", keywords);
    }
}
