package com.nacosDemo.controller;


import com.nacosDemo.config.InitWatcher;
import com.nacosDemo.until.ZkUtil;
import org.apache.zookeeper.ZKUtil;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static sun.misc.PostVMInitHook.run;

@RestController
@RequestMapping("/zkDemo")
public class ZookeeperDemoController {

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.sessionTimeout}")
    private int sessionTimeout;

    @Autowired
    InitWatcher initWatcher;

    @GetMapping("/lock")
    public void lock() throws Exception{

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {

            new Thread(){
                @Override
                public void run() {
                    ZooKeeper zooKeeper = null;
                    try {
                        zooKeeper = new ZooKeeper(address,sessionTimeout,initWatcher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ZkUtil zkUtil = new ZkUtil(zooKeeper);
                    //获取锁
                    zkUtil.tryLock();

                    //做自己的业务逻辑
                    System.out.println("业务逻辑.........");

                    //释放锁
                    zkUtil.unLock();
                }
            }.start();

        }
        long end = System.currentTimeMillis();
        System.out.println("10个线程开启完毕用时" + (end - start));
    }
}
