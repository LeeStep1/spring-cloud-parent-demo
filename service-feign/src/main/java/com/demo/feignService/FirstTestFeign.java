package com.demo.feignService;

import com.demo.feignService.Impl.FirstTestFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description:
 * @author: liyang
 * @date: 2019-09-25
 **/
@FeignClient(value = "service-consume",fallback = FirstTestFeignImpl.class)
public interface FirstTestFeign {

    @RequestMapping(value = "/firstTest/sayHello",method = RequestMethod.GET)
    String sayHello();
}
