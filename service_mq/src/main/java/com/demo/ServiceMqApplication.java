package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-22
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMqApplication.class,args);
    }
}

