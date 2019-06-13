package com.fault.collect.center.sender.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConf {

    @Bean(name = "start_collect_message")
    public Queue startCollectMessage() {
        return new Queue("start_collect");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchange(@Qualifier("start_collect_message") Queue message,
                            FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(message).to(fanoutExchange);
    }
}
