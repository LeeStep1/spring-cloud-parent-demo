package com.nacosDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-07
 **/
@SpringBootApplication(scanBasePackages = "com.nacosDemo")
@EnableDiscoveryClient
@EnableCircuitBreaker
@RefreshScope
@MapperScan("com.nacosDemo.dao")
public class NacosConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumeApplication.class,args);
    }
}
