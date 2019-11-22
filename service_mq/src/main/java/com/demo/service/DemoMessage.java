package com.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-22
 **/
@Component
@RabbitListener(queues = "directMessage")
public class DemoMessage {

    @RabbitHandler
    private void process(String message){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date());
        System.out.println("收到消息...."+ date + "   " + message);
    }
}
