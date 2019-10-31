package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-25
 **/
@RestController
@RequestMapping("/firstTest")
public class FirstTestController {

    @Value("${spring.application.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hi,gitHub test" + name + " : " + port;
    }
}
