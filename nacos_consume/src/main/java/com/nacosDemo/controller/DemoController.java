package com.nacosDemo.controller;

import com.alibaba.fastjson.JSON;
import com.nacosDemo.bean.DirectMessage;
import com.nacosDemo.commonEnum.RedisKey;
import com.nacosDemo.until.CacheUtil;
import com.nacosDemo.until.RedisKeyUntil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.nacosDemo.commonEnum.RedisKey.DELAY_INFORMATION;

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

    /**
     * redis 工具类
     */
    @Autowired
    private CacheUtil cacheUtil;

    @GetMapping("/firstDemo/{string}")
    public String firstDemo(@PathVariable(value = "string") String string){
        System.out.println("refreshName====================" + refreshName);
        String ss = "请求的端口..........." + service;
        return ss;
    }

    /**
     * dircet队列消费
     * @author liyang
     * @date 2019-11-26
     * @param string :
     * @return : null
    */
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

    /**
     * fanout队列消费
     * @author liyang
     * @date 2019-11-26
     * @param string :
     * @return : null
    */
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

    /**
     * topic队列消费
     * @author liyang
     * @date 2019-11-26
     * @param routing : 队列的routing
     * @return : null
    */
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

    /**
     * 发送延迟队列(针对消息过期)
     * @author liyang
     * @date 2019-11-26
     * @param routing : routing
     * @return : null
    */
    @GetMapping("/sendToDelayMessage/{routing}")
    public String sendToDelayMessage(@PathVariable(value = "routing") String routing){
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        direct.setContent(date + "  发送延迟消息");
        direct.setTitle("routing是==============" + routing);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("delayTilExchange",routing,directStr,message -> {
            message.getMessageProperties().setExpiration("20000");
            return message;
        });

        return direct.getContent();
    }

    /**
     * 发送延迟队列(针对队列过期)
     * @author liyang
     * @date 2019-11-26
     * @param routing : routing
     * @return : null
     */
    @GetMapping("/sendToDelayQueueMessage/{routing}")
    public String sendToDelayQueueMessage(@PathVariable(value = "routing") String routing){
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        direct.setContent(date + "  发送延迟消息");
        direct.setTitle("routing是==============" + routing);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("delayTilQueueExchange",routing,directStr);

        return direct.getContent();
    }

    /**
     * 延迟队列插件发送
     * @author liyang
     * @date 2019-11-27
     * @param routing : routing
     * @return : String
    */
    @GetMapping("/sendToDelayPlusMessage/{routing}")
    public String sendToDelayPlusMessage(@PathVariable(value = "routing") String routing){
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        direct.setContent(date + "  发送延迟消息");
        direct.setTitle("routing是==============" + routing);

        String directStr = JSON.toJSONString(direct);

        rabbitTemplate.convertAndSend("directDelayPlusExchange", routing, directStr, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(20000);
                return message;
            }
        });

        return direct.getContent();
    }

    /**
     * 发送redis
     * @author liyang
     * @date 2019-11-28
     * @return : null
    */
    @GetMapping("/sendToRedis")
    public String redisDelayDemo(){
        DirectMessage direct = new DirectMessage();
        direct.setId(1L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        direct.setContent(date + "  发送延迟消息");

        String directStr = JSON.toJSONString(direct);

        cacheUtil.set(RedisKeyUntil.getRedisKey(RedisKey.DELAY_INFORMATION,"1"),directStr,20000);

        System.out.println(date + "  发送至redis");

        return date;
    }
}
