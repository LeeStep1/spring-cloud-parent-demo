package com.demo.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-22
 **/
@Component
@RabbitListener(queues = "fanoutMessage2")
public class DemoFanoutMessage2 {

    @RabbitHandler
    private void process(String message){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date());
        System.out.println("fanoutMessage2收到消息...."+ date + "   " + message);
    }
}
