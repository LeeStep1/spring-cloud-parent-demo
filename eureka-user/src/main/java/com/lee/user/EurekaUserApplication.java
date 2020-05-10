package com.lee.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan("com.lee.user.dao")
public class EurekaUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaUserApplication.class);
    }
}
