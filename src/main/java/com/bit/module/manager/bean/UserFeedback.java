package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
@Data
@TableName(value = "t_user_feedback")
public class UserFeedback {


    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String content;
    /**
     * 创建者ID
     */
    private Long createUserId;
    /**
     * 创建者姓名
     */
    private String  createRealName;
    /**
     * 创建时间
     */
    private Date createTime;

}
