package com.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
     * 队列路由绑定
     * @param directMessage
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue directMessage,DirectExchange directExchange){
        return BindingBuilder.bind(directMessage).to(directExchange).with("direct.demo");
    }

    /**
     * 队列路由绑定
     * @param directMessage2
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage2(Queue directMessage2,DirectExchange directExchange){
        return BindingBuilder.bind(directMessage2).to(directExchange).with("direct.demo2");
    }

    @Bean
    Binding bindingFanoutExchangeMessage(Queue fanoutMessage,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingFanoutExchangeMessage2(Queue fanoutMessage2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutMessage2).to(fanoutExchange);
    }

    @Bean
    Binding bindingTopiceExchangeMessage(Queue topicMessage,TopicExchange topicExchange){
        return BindingBuilder.bind(topicMessage).to(topicExchange).with("*.topic.*");
    }

    @Bean
    Binding bindingTopiceExchangeMessage2(Queue topicMessage2,TopicExchange topicExchange){
        return BindingBuilder.bind(topicMessage2).to(topicExchange).with("demo.topic.*");
    }


}
