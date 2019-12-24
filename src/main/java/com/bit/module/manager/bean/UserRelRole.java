package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-20
 **/
@Data
@TableName("t_user_role")
public class UserRelRole {


    private Long id;


    private Long userId;


    private Integer roleId;

    /**
     * 登陆票据
     */
    private String token;
}
