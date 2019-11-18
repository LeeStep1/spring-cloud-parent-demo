package com.nacosDemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-07
 **/
@RestController
@RequestMapping("/nacosConsumeDemo")
@RefreshScope
public class DemoController {

    @Value("${refresh.name}")
    private String refreshName;

    @GetMapping("/firstDemo/{string}")
    public String firstDemo(@PathVariable(value = "string") String string){
        System.out.println("refreshName====================" + refreshName);
        return string;
    }
}
