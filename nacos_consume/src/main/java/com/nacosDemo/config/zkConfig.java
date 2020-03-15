package com.nacosDemo.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


public class zkConfig {

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.sessionTimeout}")
    private int sessionTimeout;

    @Autowired
    InitWatcher initWatcher;

    public ZooKeeper connZk(){
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(address,sessionTimeout,initWatcher);
            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }


}
