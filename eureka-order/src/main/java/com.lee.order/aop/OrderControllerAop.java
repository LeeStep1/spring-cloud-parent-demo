package com.lee.order.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderControllerAop {

    @Before("execution(* com.lee.order.controller.OrderController.*(..))")
    private void pointCut(){
        System.out.println("收到请求..........");
    }
}
