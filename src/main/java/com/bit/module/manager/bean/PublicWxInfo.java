package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @description: 公众号相关信息
 * @author: liyang
 * @date: 2019-11-11
 **/
@Data
public class PublicWxInfo {

    /**
     * openId
     */
    private String openId;

    /**
     * unionId
     */
    private String unionid;

    /**
     * accessToken
     */
    private String access_token;

    /**
     * 有效时长
     */
    private String expires_in;

    /**
     * 事件类型
     */
    public String event;

}
