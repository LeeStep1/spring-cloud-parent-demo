package com.nacosDemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: liyang
 * @date: 2019-12-03
 **/
@Configuration
public class RedissionConfig {

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.129.103.212:6379").setPassword("LY395722278jay");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
