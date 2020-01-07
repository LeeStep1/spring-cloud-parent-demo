package com.nacosDemo.listener;

import com.nacosDemo.until.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

/**
 * @description:
 * @author: liyang
 * @date: 2020-01-06
 **/
@WebListener
public class ServletListener implements ServletContextListener{

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String uuid = UUID.randomUUID().toString();
//        cacheUtil.set("servletStart" + uuid,"2234");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
