package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @description:  前后端用户登录
 * @author: chenduo
 * @create: 2019-05-06 15:39
 */
@Data
public class UserLogin {


    /***账号**/
    private String userName;

    /***密码**/
    private String passWord;
    /**
     * 接入端
     */
    private Integer tid;

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


    private String encrypteData;


    private String iv;


}
