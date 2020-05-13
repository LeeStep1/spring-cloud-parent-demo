package com.lee.user.comsumer;

import com.lee.user.config.ConsumerConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer implements InitializingBean {

//    @Autowired
//    private DefaultMQPushConsumer defaultMQPushConsumer;

    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("Lee");
        defaultMQPushConsumer.setNamesrvAddr("192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876");

        ConsumerConfig c = new ConsumerConfig();
        c.test();

        defaultMQPushConsumer.subscribe("chainUpdate","*");
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("我收到消息了...");
                msgs.forEach(x -> {
                    String s = new String(x.getBody());
                    System.out.println("收到的消息是: " + s);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        defaultMQPushConsumer.start();

        System.out.println("消费者启动了.......");
    }
}
