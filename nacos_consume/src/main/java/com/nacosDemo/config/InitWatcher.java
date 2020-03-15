package com.nacosDemo.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Component;

@Component
public class InitWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        System.out.println("event   "  +event );

        switch (event.getType()) {

            //什么都没发生
            case None:
                System.out.println("init watcher .....什么都没发生....");
                break;
            //节点被创建
            case NodeCreated:
                System.out.println("init watcher .....节点被创建....");
                break;
            //节点被删除
            case NodeDeleted:
                System.out.println("init watcher .....节点被删除....");
                break;
            //节点日期发生改变
            case NodeDataChanged:
                System.out.println("init watcher .....节点日期发生改变....");
                break;
            //子节点被改变
            case NodeChildrenChanged:
                System.out.println("init watcher .....子节点被改变....");
                break;
        }
    }
}
