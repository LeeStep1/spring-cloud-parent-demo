package com.nacosDemo.listener;


import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosIgnore;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2019-12-13
 **/
public class NacosConfigListenter {

    @NacosConfigListener(dataId = "nacos_consume.yml")
    public void onMessage(){
        System.out.println("配置文件变化");
    }
}
