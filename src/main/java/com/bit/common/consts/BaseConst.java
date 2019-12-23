package com.bit.common.consts;

/**
 * @Description:  静态类
 * @Author: mifei
 **/
public class BaseConst {


    /** 限流过期秒数**/
    public static final int LIMIT_EXPIRE_SECOND = 1;

    /** 限流key**/
    public static final String LIMIT_REDIS_KEY_PREFIX = "limit:rate";

    /** 限流请求个数**/
    public static final int LIMIT_RATE = 5;





}
