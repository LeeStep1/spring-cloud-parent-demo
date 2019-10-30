package com.demo.feignService.Impl;

import com.demo.feignService.FirstTestFeign;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-25
 **/
@Component
public class FirstTestFeignImpl implements FirstTestFeign {
    @Override
    public String sayHello() {
        return "等会试试..........";
    }
}
