package com.nacosDemo.until;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ZkUtil implements AsyncCallback.StringCallback , AsyncCallback.Children2Callback,Watcher {

    ZooKeeper zooKeeper;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    String lockName;

    public ZkUtil(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 尝试上锁
     */
    public void tryLock() {
        //添加文件,成功后回调
        zooKeeper.create("/lock",Thread.currentThread().getName().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, this,"create");
        //等待上锁成功
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建成功后的回调
     * @param rc 调用后的返回结果
     * @param path 调用的路径
     * @param ctx CTX
     * @param name 成功后的名称
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        //先判断是否成功
        if(rc == KeeperException.Code.OK.intValue()){
            //  要去掉前面的  /   /lock00000001
            lockName = name.substring(1);
            //获取所有的子集
            zooKeeper.getChildren("/",false,this,ctx);

        }
    }

    /**
     * 获取子集的  callback
     * @param rc 获取结果编码
     * @param path 路径
     * @param ctx ctx
     * @param children 获取的子接口的集合
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        if(rc == KeeperException.Code.OK.intValue()){

            //排序
            Collections.sort(children);

            //查询自己是否是第一个
            if(lockName.equals(children.get(0))){
                //如果是第一个说明创建成功，上锁
                System.out.println(Thread.currentThread().getName() + "   上锁成功...创建的文件是....."+ lockName);
                countDownLatch.countDown();

            }else {
                //如果不是第一个,说明上锁失败，watch 前一个文件，等待文件失效后回调
                try {
                    System.out.println(Thread.currentThread().getName() + "   不是第一个创建  " + lockName);
                    zooKeeper.exists("/"+children.get(children.indexOf(lockName)-1),this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




        }
    }

    //监控是否有文件
    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                System.out.println(event.getPath() + "   节点被删除，我上锁......");
                zooKeeper.getChildren("/",false,this,"");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }
    }

    /**
     * 释放锁.
     */
    public void unLock() {
        if(zooKeeper != null){
            try {
                System.out.println("删除的文件是  "  + "/" + lockName);
                zooKeeper.delete("/" + lockName,-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
