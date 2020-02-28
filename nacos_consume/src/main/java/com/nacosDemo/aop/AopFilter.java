package com.nacosDemo.aop;

import org.apache.http.HttpRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-28
 **/
@Component
@Aspect
public class AopFilter {

    @Pointcut("execution(* com.nacosDemo.controller.*.*(..))")
    public void pointControllerCut(){

    }

    @Pointcut("execution(* com.nacosDemo.service.*.*(..))")
    public void pointServiceCut(){

    }


    @Before("pointControllerCut()")
    public void adviceController(){
        System.out.println("Controller切點插入.........................");
    }

    @Around("pointServiceCut()")
    public void adviceService(ProceedingJoinPoint point){
        RequestAttributes request =  RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes ab = (ServletRequestAttributes) request;
        HttpServletRequest rq = ab.getRequest();
        System.out.println("method : "+rq.getMethod());
        System.out.println("url : "+rq.getRequestURI());
        System.out.println(rq.getParameter("name"));
        System.out.println(rq.getQueryString());
        System.out.println("Service切點插入.........................") ;
    }
}
