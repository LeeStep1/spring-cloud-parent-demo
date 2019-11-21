package com.demo.controller;

import com.demo.feignService.FirstTestFeign;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FirstTestFeign firstTestFeign;

    @GetMapping("/feignSayHello")
    public String feignSayHello(){
        return firstTestFeign.sayHello();
    }
}
