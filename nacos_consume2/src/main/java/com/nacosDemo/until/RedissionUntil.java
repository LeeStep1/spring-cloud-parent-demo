package com.nacosDemo.until;

import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: liyang
 * @date: 2019-12-03
 **/
@Component
public class RedissionUntil {

    @Autowired
    private RedissonClient redission;

    /**
     * 加锁
     * @param key 加锁的键
     * @param timeOut 锁时间(秒)
     */
    public void lock(String key,int timeOut){
//        redission.getBuckets();
        RLock lock = redission.getLock(key);
        lock.lock(timeOut, TimeUnit.SECONDS);
    }

    public void unLock(String key){
        RLock lock = redission.getLock(key);
        lock.unlock();
    }

    public RLock getLock(String key){
        RLock lock = redission.getLock(key);
        return lock;
    }

}
