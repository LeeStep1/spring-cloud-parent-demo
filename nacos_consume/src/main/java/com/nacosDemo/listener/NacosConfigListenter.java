package com.nacosDemo.listener;


import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosIgnore;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liyang
 * @date: 2019-12-13
 **/
@Component
public class NacosConfigListenter{

    @NacosConfigListener(dataId = "nacos_consume.yml",type = ConfigType.YAML)
    public void aa(){
        System.out.println("aaaaaaa");
    }
}
