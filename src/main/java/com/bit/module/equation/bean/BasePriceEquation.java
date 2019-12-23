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
import lombok.Data;

/**
 */
@Data
public class BasePriceEquation {
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
