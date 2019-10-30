package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-25
 **/
@RestController
@RequestMapping("/firstTest")
public class FirstTestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbonSayHello")
    public String ribbonSayHello(){
        return restTemplate.getForObject("http://service-consume/firstTest/sayHello",String.class);
    }
}
