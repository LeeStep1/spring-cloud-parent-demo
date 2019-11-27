package com.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 延迟队列消费
 * @author: liyang
 * @date: 2019-11-22
 **/
@Component
@RabbitListener(queues = "directQueueProcess")
public class DemoDelayMessage {

    @RabbitHandler
    private void process(String message){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        System.out.println("directQueueProcess收到消息...."+ date + "   " + message);
    }
}
