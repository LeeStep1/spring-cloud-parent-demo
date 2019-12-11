package com.nacosDemo.listener;

import com.nacosDemo.until.DistributedLock;
import com.nacosDemo.until.RedissionUntil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description: redis中消息失效监听
 * @author: liyang
 * @date: 2019-11-28
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    /**
     * redis 分布式锁工具类
     */
    @Autowired
    private DistributedLock distributedLock;

    /**
     * redission 分布式锁
     */
    @Autowired
    private RedissionUntil redissionUntil;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        System.out.println("进来取到key........." + expiredKey);
        String informationKey = expiredKey.substring(0,expiredKey.indexOf(":"));

        //只有固定业务的rediskey才执行
        if(informationKey.equals("information")){

            redissionUntil.lock("demo:1",20);
            System.out.println("上锁.......");

        }else {
            System.out.println("不是匹配的键....");
        }

        //只有固定业务的rediskey才执行
//        if(informationKey.equals("information")){
//            //获取分布式锁(简易)
//            String uuid = UUID.randomUUID().toString();
//            boolean b = distributedLock.setLock("demo:1",uuid,600000);
//            if(b){
//                // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String date = simpleDateFormat.format(new Date());
//
//                System.out.println(date + " +++++++++++++++8082 消费   失效的key是+++++++++++++" + expiredKey);
//
//                boolean result = distributedLock.releaseLock("demo:1",uuid);
//                System.out.println(" +++++++++++++++8082 释放锁..........." + result);
//            }else {
//                System.out.println(" +++++++++++++++8082 没取到锁..........." );
//            }
//        }
        System.out.println("执行结束.......");

    }

}
