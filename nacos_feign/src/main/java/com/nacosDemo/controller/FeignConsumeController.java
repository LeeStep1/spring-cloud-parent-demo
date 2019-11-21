package com.nacosDemo.controller;

import com.nacosDemo.feign.ConsumeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-19
 **/
@RestController
@RequestMapping("/nacosFeign")
public class FeignConsumeController {

    /**
     * 消费者feign
     */
    @Autowired
    private ConsumeFeign consumeFeign;

    /**
     * 获取消费者端口号
     * @author liyang
     * @date 2019-11-19
     * @return : String
    */
    @GetMapping("/consumePort")
    public String consumePort(){
        return consumeFeign.firstDemo("Lee");
    }
}
