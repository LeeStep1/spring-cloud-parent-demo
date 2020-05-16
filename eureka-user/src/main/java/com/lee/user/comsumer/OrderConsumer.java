package com.lee.user.comsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lee.user.bean.Order;
import com.lee.user.feign.OrderFeign;
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
public class OrderConsumer extends AbstractConsumer implements InitializingBean{

    @Autowired
    private OrderFeign orderFeign;


    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = init("Lee",
                "192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876",
                "chainUpdate","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("Order---Lee    消费者收到消息了...");
                msgs.forEach(x -> {
                    String s = new String(x.getBody());
                    System.out.println("Order---Lee    收到的消息是: " + s);
                    Order order = JSON.parseObject(x.getBody(),Order.class);
                    orderFeign.updateOrderById(order);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.println("Order---Lee    消费者启动了.......");
    }

}
