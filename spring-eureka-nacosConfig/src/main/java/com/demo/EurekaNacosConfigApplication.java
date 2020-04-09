package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: liyang
 * @date: 2020-04-09
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaNacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaNacosConfigApplication.class);
    }
}
