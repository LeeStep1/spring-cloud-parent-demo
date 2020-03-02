/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bit.module.equation.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
@TableName("t_equation")
public class Equation implements Serializable {

    private static final long serialVersionUID = -6099289237019187959L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;
    /**
     * 第一个公式
     */
    private String equation;
    /**
     * 输出
     */
    private String output;
    /**
     * 类别
     */
    private String category;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 0：无关联 1：电梯类型 2：可选项
     */
    private Integer relevanceType;


}
