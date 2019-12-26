package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
@TableName(value = "t_project_price")
public class ProjectPrice {

    private Long id;

    private Long projectId;

    private String totalPrice;

    private Date createTime;

    private Long createUserId;

    private Integer version;

    private Integer standard;

    private String standardName;

    private Integer stage;

    private String stageName;

}
