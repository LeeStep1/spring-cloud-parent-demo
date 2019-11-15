package com.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-08
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class NacosZullApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosZullApplication.class,args);
    }
}
