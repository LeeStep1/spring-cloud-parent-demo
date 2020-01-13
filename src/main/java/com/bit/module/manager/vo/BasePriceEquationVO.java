/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bit.module.manager.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class BasePriceEquationVO implements Serializable {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String category;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String val1;
    /**
     *
     */
    private String val2;
    /**
     *
     */
    private String val3;
    /**
     *
     */
    private String val4;
    /**
     *
     */
    private String val5;
    /**
     *
     */
    private String output;
}
