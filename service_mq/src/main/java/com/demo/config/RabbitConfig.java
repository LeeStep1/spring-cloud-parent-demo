package com.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
     * 一个directExchange
     * @return
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("directExchange");
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


}
