package com.nacosDemo.controller;

import com.nacosDemo.bean.DirectMessage;
import com.nacosDemo.service.SpringDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-28
 **/
@RestController
@RequestMapping("/springDemo")
public class SpringDemo {

    @Autowired
    SpringDemoService springDemoService;

    @RequestMapping("/simple")
    public void AopSimpleDemo(){
        System.out.println("最簡單的aopdemo");
    }

    @RequestMapping("/simpleService")
    public String AopSimleServiceDemo(@RequestBody DirectMessage directMessage){
        System.out.println("这次拦截service....");
        return springDemoService.doSomeThing();
    }
}
