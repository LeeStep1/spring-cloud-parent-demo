package com.nacosDemo.commonEnum;

import java.text.MessageFormat;


public enum RedisKey {

    /**
     * 小程序公众端
     * {0}:接入端
     * {1}:UUID
     */
    WX_LOGIN_TOKEN("token:{0}:{1}"),

    /**
     * 公众号token
     * {0}:接入端
     * {1}:UUID
     */
    PUB_WZLOGIN_TOKEN("wxtoken:{0}"),

    /**
     * 字典缓存
     */
    DICT_CACHE("sys:dict:{0}"),

    /**
     * 延迟上报
     */
    DELAY_INFORMATION("information:{0}");

    /**
     * 操作信息
     */
    private String key;

    /**
     * @param key  状态信息
     */
    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public static String getRediseKey( RedisKey rdy,String[] params){
      return  MessageFormat.format( rdy.getKey(),params);
    }
}
