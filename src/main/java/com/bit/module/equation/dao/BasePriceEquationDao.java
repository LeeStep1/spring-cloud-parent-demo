/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bit.module.equation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.equation.bean.BasePriceEquation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Repository
public interface BasePriceEquationDao extends BaseMapper<BasePriceEquation> {

}
