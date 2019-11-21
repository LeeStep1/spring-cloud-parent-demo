package com.nacosDemo.feign;

import com.nacosDemo.feign.impl.ConsumeFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-19
 **/
@FeignClient(value = "nacosConsume",fallback = ConsumeFeignImpl.class)
public interface ConsumeFeign {

    @RequestMapping(value = "/nacosConsumeDemo/firstDemo/{string}",method = RequestMethod.GET)
    String firstDemo(@PathVariable(value = "string") String string);
}
