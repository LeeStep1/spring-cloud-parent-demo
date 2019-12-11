package com.nacosDemo.until;

import com.nacosDemo.commonEnum.RedisKey;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-28
 **/
public class RedisKeyUntil {
    /**
     * 生成枚举类中指定格式的key
     * 使用例子: RedisKeyUtil.getRedisKey(RedisKey.SMS_REGISTER_TOTAL, "aaa", "bbb")
     * @param redisKey
     * @param args
     * @return
     */
    public static String getRedisKey(RedisKey redisKey, String... args) {

        String key = redisKey.getKey();
        int len = 0;
        char[] cha = key.toCharArray();
        for (int i = 0; i < cha.length; i++) {
            if (cha[i] == '{') {
                len++;
            }
        }
        if (len != args.length) {
            throw new RuntimeException("redis key 生成出错,占位符和参数不一致");
        }

        String keysReturn = MessageFormat.format(key, args);
        return keysReturn;
    }
}
