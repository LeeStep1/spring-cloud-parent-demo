package com.bit.utils;

import com.bit.common.consts.RedisKey;

import java.text.MessageFormat;

/**
 * 根据规则动态组成redisKey
 *
 * @author Liy
 */
public class RedisKeyUtil {

    /**
     * 按照redis规则组成短信验证码key
     *
     * @param Level1 key主目录
     * @param Level2 key二级目录
     * @param Level3 key三级目录
     * @param Level4 key四级目录
     * @return Level1:Level2:Level3:Level4
     * @author Liy
     */
    public String getRedisSmsKey(String Level1, String Level2, String Level3, String Level4) {

        //获取数组长度
        String[] keys = {Level1, Level2, Level3, Level4};
        int keyLength = keys.length;

        //根据数组长度组成format规则
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < keyLength; i++) {
            s.append("{" + i + "}");
            if (i != (keyLength - 1)) {
                s.append(":");
            }
        }

        String keysReturn = MessageFormat.format(s.toString(), keys);

        return keysReturn;
    }

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
