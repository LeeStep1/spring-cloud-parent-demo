package rocketMqDemo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;


public class Consume1 {
    @Test
    public void demo1() throws Exception{

        //声明消费者 这是一个主动去broker拉取的消费者
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("leeTest");

        //设置nameAddr
        defaultMQPushConsumer.setNamesrvAddr("192.168.150.50:9876");

        //订阅broker 指明要消费哪个broker，第二个条件声明过滤，* 表示所有的都不用拦截
        defaultMQPushConsumer.subscribe("MyTopic", "*");

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt ext : msgs){
                    System.out.println("收到的消息是..." + new String(ext.getBody()));
                }
                System.out.println("context" + context);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();

    }
}
