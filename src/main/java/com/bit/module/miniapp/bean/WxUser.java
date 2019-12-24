package com.bit.module.miniapp.bean;

import lombok.Data;

/**
 * @Description:  小程序端登陆实体
 * @Author: liyujun
 * @Date: 2019-12-19
 **/
@Data
public class WxUser {


    /***账号**/
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 微信code
     */
    private String code;

    /**
     * 微信返回结果
     */
    private String rawData;

    /**
     * 签名
     */
    private String signature;

    /**
     * 加密数据
     */
    private String encryptedData;


    private String iv;

    /**
     * 接入端
     */
    private Integer terminalId;
}
