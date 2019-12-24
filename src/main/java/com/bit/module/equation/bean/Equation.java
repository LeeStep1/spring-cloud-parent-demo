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

/**
 */
@Data
@TableName("t_equation")
public class Equation {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;
    /**
     * 第一个公式
     */
    private String equation;
    private String coefficient;
    private String equation3;
    private String price;
    private String category;

}
