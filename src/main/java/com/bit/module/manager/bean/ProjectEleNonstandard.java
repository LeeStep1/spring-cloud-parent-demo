package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:  订单非标项
 * @Author: liyujun
 * @Date: 2020-01-04
 **/
@Data
@TableName(value = "t_project_ele_nonstandard")
public class ProjectEleNonstandard {

    /**
     * id
     **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 电梯订单的id
     **/
    private Long orderId;

    /**
     * 非标描述内容
     **/
    private String content;

    /**
     * 创建标识
     **/
    private Integer sysType;

    /**
     * 单价格（目前无用，仅为后期扩展）
     **/
    private String  signalPrice;

    /**
     * 单价格（目前无用，仅为后期扩展）
     **/
    private String  totalPrice;


}
