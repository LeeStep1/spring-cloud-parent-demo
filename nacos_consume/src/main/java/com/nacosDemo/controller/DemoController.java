package com.nacosDemo.controller;

import com.alibaba.fastjson.JSON;
import com.nacosDemo.bean.DirectMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-07
 **/
@RestController
@RequestMapping("/nacosConsumeDemo")
@RefreshScope
public class DemoController {

    @Value("${refresh.name}")
    private String refreshName;

    @Value("${server.port}")
    private String port;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.host}")
    private String service;

    @GetMapping("/firstDemo/{string}")
    public String firstDemo(@PathVariable(value = "string") String string){
        System.out.println("refreshName====================" + refreshName);
        String ss = "请求的端口..........." + service;
        return ss;
    }

    @GetMapping("/sendToDirectMessage/{string}")
    public String sendToDirectMessage(@PathVariable(value = "string") String string){
        System.out.println("host++++++++++++++++++++" + service);
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        direct.setContent("这是一个directMessage");
        direct.setTitle(string);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("directExchange","direct.demo2",directStr);

        return "成功";
    }

    @GetMapping("/sendToFanoutMessage/{string}")
    public String sendToFanoutMessage(@PathVariable(value = "string") String string){
        System.out.println("host++++++++++++++++++++" + service);
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        direct.setContent("这是一个fanoutMessage");
        direct.setTitle(string);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("fanoutExchange","",directStr);

        return "成功";
    }

    @GetMapping("/sendToTopicMessage/{string}")
    public String sendToTopicMessage(@PathVariable(value = "string") String routing){
        System.out.println("host++++++++++++++++++++" + service);
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        direct.setContent("这是一个topicMessage");
        direct.setTitle("routing是==============" + routing);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("topicExchange",routing,directStr);

        return "成功";
    }

}
