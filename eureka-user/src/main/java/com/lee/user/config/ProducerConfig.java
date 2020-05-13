package com.lee.user.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public DefaultMQProducer producer(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("Lee");
        defaultMQProducer.setNamesrvAddr("192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876");

        return defaultMQProducer;
    }

}
