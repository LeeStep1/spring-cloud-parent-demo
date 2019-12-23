package com.bit.module.manager.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description: 用户表
 * @author: liyang
 * @date: 2019-06-11
 **/
@Data
public class User {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户的实际名称
     */
    private String realName;

    /**
     * 用户微信昵称
     */
    //private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 增加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date insertTime ;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 0已删除 1可用
     */
    private Integer status;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 登陆票据
     */
    private String token;

}
