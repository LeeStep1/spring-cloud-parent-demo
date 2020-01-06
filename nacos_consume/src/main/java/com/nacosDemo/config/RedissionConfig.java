package com.nacosDemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: liyang
 * @date: 2019-12-03
 **/
@Configuration
public class RedissionConfig {

    /**
     * redission 地址
     */
    @Value("${redission.address}")
    private String redissionAddress;

    /**
     * redis 密码
     */
    @Value("${redis.password}")
    private String password;

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(redissionAddress).setPassword(password);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
