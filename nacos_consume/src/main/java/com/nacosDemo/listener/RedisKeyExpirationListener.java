package com.nacosDemo.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: redis中消息失效监听
 * @author: liyang
 * @date: 2019-11-28
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

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
        String informationKey = expiredKey.substring(0,expiredKey.indexOf(":"));

        //只有固定业务的rediskey才执行
        if(informationKey.equals("information")){

            // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());

            System.out.println(date + " +++++++++++++++   失效的key是+++++++++++++" + expiredKey);
        }

    }
}
