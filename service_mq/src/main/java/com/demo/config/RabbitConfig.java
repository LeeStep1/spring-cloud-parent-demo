package com.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-22
 **/
@Configuration
public class RabbitConfig {

    /**
     * 一个directMessage
     * @return
     */
    @Bean
    public Queue directMessage(){
        return new Queue("directMessage");
    }

    /**
     * 另一个directMessage
     * @return
     */
    @Bean
    public Queue directMessage2(){
        return new Queue("directMessage2");
    }

    /**
     * 一个fanoutMessage
     * @return
     */
    @Bean
    public Queue fanoutMessage(){
        return new Queue("fanoutMessage");
    }

    /**
     * 另一个directMessage
     * @return
     */
    @Bean
    public Queue fanoutMessage2(){
        return new Queue("fanoutMessage2");
    }

    /**
     * 一个TopicMessage
     * @return
     */
    @Bean
    public Queue topicMessage(){
        return new Queue("topicMessage");
    }

    /**
     * 另一个TopicMessage
     * @return
     */
    @Bean
    public Queue topicMessage2(){
        return new Queue("topicMessage2");
    }

    /**
     * 延迟队列接收队列(针对消息过期)
     * @return
     */
    @Bean
    public Queue delayTilMessage(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","directExchangeProcess");
        arguments.put("x-dead-letter-routing-key","delay.delayProcess");
        return new Queue("delayTilMessage",true,false,false,arguments);
    }

    /**
     * 延迟队列接收队列(针对队列过期)
     * @return
     */
    @Bean
    public Queue delayTilMessageQueue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","directExchangeProcess");
        arguments.put("x-dead-letter-routing-key","delay.delayProcess");
        arguments.put("x-message-ttl",20000);
        return new Queue("delayTilMessageQueue",true,false,false,arguments);
    }

    /**
     * 延迟队列消费队列
     * @return
     */
    @Bean
    public Queue directQueueProcess(){
        return new Queue("directQueueProcess");
    }

    /**
     * 一个directExchange
     * @return
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    /**
     * 一个fanoutExchange
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 一个topicExchange
     * @return
     */
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    /**
     * 延迟消息接收交换机（针对消息过期）
     * @return
     */
    @Bean
    DirectExchange delayTilExchange(){
        return new DirectExchange("delayTilExchange");
    }

    /**
     * 延迟消息接收交换机（针对队列过期）
     * @return
     */
    @Bean
    DirectExchange delayTilQueueExchange(){
        return new DirectExchange("delayTilQueueExchange");
    }

    /**
     * 延迟队列消费交换机
     * @return
     */
    @Bean
    DirectExchange directExchangeProcess(){
        return new DirectExchange("directExchangeProcess");
    }

    /**
     * direct队列路由绑定
     * @param directMessage
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue directMessage,DirectExchange directExchange){
        return BindingBuilder.bind(directMessage).to(directExchange).with("direct.demo");
    }

    /**
     * direct队列路由绑定
     * @param directMessage2
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage2(Queue directMessage2,DirectExchange directExchange){
        return BindingBuilder.bind(directMessage2).to(directExchange).with("direct.demo2");
    }

    /**
     * fanout队列路由绑定
     * @param fanoutMessage :
     * @param fanoutExchange :
     * @return :
    */
    @Bean
    Binding bindingFanoutExchangeMessage(Queue fanoutMessage,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutMessage).to(fanoutExchange);
    }

    /**
     * fanout队列路由绑定
     * @param fanoutMessage2
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding bindingFanoutExchangeMessage2(Queue fanoutMessage2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutMessage2).to(fanoutExchange);
    }

    /**
     * Topice消息绑定
     * @param topicMessage
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingTopiceExchangeMessage(Queue topicMessage,TopicExchange topicExchange){
        return BindingBuilder.bind(topicMessage).to(topicExchange).with("*.topic.*");
    }

    /**
     * Topice消息绑定
     * @param topicMessage2
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingTopiceExchangeMessage2(Queue topicMessage2,TopicExchange topicExchange){
        return BindingBuilder.bind(topicMessage2).to(topicExchange).with("demo.topic.*");
    }

    /**
     * 延迟接收队列绑定（针对消息过期）
     * @param delayTilMessage
     * @param delayTilExchange
     * @return
     */
    @Bean
    Binding bindingDelayExchangeMessage(Queue delayTilMessage,DirectExchange delayTilExchange){
        return BindingBuilder.bind(delayTilMessage).to(delayTilExchange).with("delay.delayTilMessage");
    }

    /**
     * 延迟队列消费绑定
     * @param directQueueProcess
     * @param directExchangeProcess
     * @return
     */
    @Bean
    Binding bindingDelayExchangeProcessMessage(Queue directQueueProcess,DirectExchange directExchangeProcess){
        return BindingBuilder.bind(directQueueProcess).to(directExchangeProcess).with("delay.delayProcess");
    }

    /**
     * 延迟接收队列绑定（针对队列过期）
     * @param delayTilMessageQueue
     * @param delayTilQueueExchange
     * @return
     */
    @Bean
    Binding bindingDelayQueueExchangeMessage(Queue delayTilMessageQueue,DirectExchange delayTilQueueExchange){
        return BindingBuilder.bind(delayTilMessageQueue).to(delayTilQueueExchange).with("delay.delayTilQueue");
    }

}
