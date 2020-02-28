package com.nacosDemo.service.imp;

import com.nacosDemo.service.SpringDemoService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-28
 **/
@Service
public class SpringDemoServiceImpl implements SpringDemoService {
    @Override
    public String doSomeThing() {
        String name = "开始执行逻辑..........";
        System.out.println(name);
        return name;
    }
}
