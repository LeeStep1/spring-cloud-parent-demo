package com.bit.module.manager.vo;

import com.bit.base.vo.BasePageVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
public class UserFeedbackPageVO  extends BasePageVo implements Serializable {


    /**
     * id
     */
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
