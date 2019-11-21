package com.nacosDemo.feign.impl;

import com.nacosDemo.feign.ConsumeFeign;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-19
 **/
@Component
public class ConsumeFeignImpl implements ConsumeFeign {
    @Override
    public String firstDemo(String string) {
        return "稍等一下再试试.......";
    }
}
