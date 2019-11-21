package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-20
 **/
@SpringBootApplication
@EnableHystrixDashboard
@RefreshScope
public class DashBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashBoardApplication.class,args);
    }
}
