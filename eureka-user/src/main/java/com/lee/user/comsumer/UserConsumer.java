package com.lee.user.comsumer;

import com.alibaba.fastjson.JSON;
import com.lee.user.bean.Order;
import com.lee.user.bean.User;
import com.lee.user.service.imp.UserServiceImpl;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.rocketmq.common.consumer.ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

@Component
public class UserConsumer extends AbstractConsumer implements InitializingBean {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer consumer = init("LeeTransaction",
                "192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876",
                "updateUserByTransaction","*");
        consumer.setConsumeFromWhere(CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("user---LeeTransaction  收到消息了...");
                msgs.forEach(x -> {
                    String s = new String(x.getBody());
                    System.out.println("user---LeeTransaction  收到的消息是: " + s);
                    User user = JSON.parseObject(x.getBody(), User.class);
                    boolean b = userService.save(user);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.println("user---LeeTransaction  消费者启动了.......");
    }
}
