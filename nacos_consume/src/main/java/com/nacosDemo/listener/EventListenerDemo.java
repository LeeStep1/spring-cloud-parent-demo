package com.nacosDemo.listener;

import com.nacosDemo.until.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description: 微信acctoken 监听器
 * @author: liyang
 * @date: 2019-11-13
 **/
@Component
public class EventListenerDemo {

    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);


    /**
     * 缓存工具
     */
    @Autowired
    private CacheUtil cacheUtil;

    /**
     * acctoken 监听器
     * @author liyang
     * @date 2019-11-13
    */
    @EventListener
    public void contextInitialized(ApplicationReadyEvent event) {

        String uuid = UUID.randomUUID().toString();
//        cacheUtil.set("start" + uuid,123);

    }

}
