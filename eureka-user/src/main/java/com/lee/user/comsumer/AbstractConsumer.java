package com.lee.user.comsumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.InitializingBean;

public class AbstractConsumer implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化消费者模板类.....");
    }

    /**
     * 消费者初始化方法
     * @param consumerGroup
     * @param nameSrvAddr
     * @return
     */
    public DefaultMQPushConsumer init(String consumerGroup,
                                      String nameSrvAddr,
                                      String topic,
                                      String subExpression) throws Exception{
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(nameSrvAddr);
        defaultMQPushConsumer.subscribe(topic,subExpression);
        return defaultMQPushConsumer;
    }
}
