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
@TableName("t_base_price_equation")
public class BasePriceEquation implements Serializable {

    private static final long serialVersionUID = -5710707056860294136L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;
    private String category;
    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String output;

}
