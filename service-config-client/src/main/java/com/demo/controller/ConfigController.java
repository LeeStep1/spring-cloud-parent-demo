package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-27
 **/
@RestController
@RequestMapping("/configClient")
@RefreshScope
public class ConfigController {

    @Value("${version}")
    private String version;

    @GetMapping("/firstDemo")
    public String firstDemo(){
        return version;
    }
}
