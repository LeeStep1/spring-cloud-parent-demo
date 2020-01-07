package com.bit.common.consts;

import java.text.MessageFormat;

/**
 * @Description:  静态类
 * @Author: mifei
 **/
public enum RedisKey {

    /**
     * 短信注册总和
     */
    SMS_REGISTER_TOTAL("register:total:{0}:{1}"),
    /**
     * 短信注册验证码
     */
    SMS_REGISTER_VERIFICATION_CODE("register:verification:{0}:{1}"),
    /**
     * 密码找回总和
     */
    SMS_BACKPWD_TOTAL("retrieve:total:1{0}:{1}"),
    /**
     * 密码找回验证码
     */
    SMS_BACKPWD_VERIFICATION_CODE("retrieve:verification:{0}:{1}"),
    /**
     * 系统所有端登陆
     * {0}:接入端
     * {1}:UUID
     */
    LOGIN_TOKEN("token:{0}:{1}");



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
