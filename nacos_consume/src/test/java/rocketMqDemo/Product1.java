package rocketMqDemo;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

public class Product1 {

    @Test
    public void sendMessage(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("leeTest");

        //设置nameAddr
        defaultMQProducer.setNamesrvAddr("192.168.150.50:9876");
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        //设置消息的内容和Topic
        Message message = new Message("MyTopic","sendMessage simple demo".getBytes());

        try {
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功......");
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("消息发送失败...失败原因是 " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
