package com.lee.user.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConsumerConfig implements InitializingBean {

    private String consumerGroup;

    private String nameSrvAddr;

    public ConsumerConfig(){
//        this.consumerGroup = consumerGroup;
//        this.nameSrvAddr = nameSrvAddr;
    }

//    public DefaultMQPushConsumer init(){
//        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("Lee");
//        defaultMQPushConsumer.setNamesrvAddr("192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876");
//        return defaultMQPushConsumer;
//    }

    public void test(){
        System.out.println("consumerConfig 打印了......");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
