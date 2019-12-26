package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 关联的项目ID
     */
    private Long projectId;
    /**
     * 总价格
     */
    private String totalPrice;

    private Date createTime;

    private Long createUserId;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 是否标准，1：标准，0非标
     */
    private Integer standard;
    /**
     * 标准名称1：标准，0非标
     */
    private String standardName;
    /**
     * 1：常规 0实施
     */
    private Integer stage;
    /**
     * 常规，实施
     */
    private String stageName;

}
