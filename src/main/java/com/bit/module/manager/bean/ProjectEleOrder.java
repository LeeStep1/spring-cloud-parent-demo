package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: 项目下的订单表
 * @date:
 **/
@Data
@TableName("t_project_ele_order")
public class ProjectEleOrder {

    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 电梯类型id
     */
    private Long elevatorTypeId;

    /**
     * 电梯类型名称
     */
    private String elevatorTypeName;

    /**
     * 总价
     */
    private String totalPrice;

    /**
     * 电梯单价
     */
    private String unitPrice;
    /**
     * 安装单价
     */
    private String installPrice;
    /**
     * 单台总价
     */
    private String singleTotalPrice;

    /**
     * 运输费用
     */
    private String transportPrice;

    /**
     * 采购数量
     */
    private String num;

    /**
     * 对应t_projec_price报价历史表中的id
     */
    private Long versionId;

    /**
     * 下浮率
     */
    private Double rate;
    /**
     * 是否标准，1：标准，0非标
     */
    private Integer standard;

    /**
     * 是否标准，1：标准，0非标
     */
    private String standardName;



}
