package com.bit.module.manager.vo;

import com.bit.base.vo.BasePageVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
@Data
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

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

}
