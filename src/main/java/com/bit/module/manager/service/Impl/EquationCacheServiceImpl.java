package com.bit.module.manager.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bit.base.exception.BusinessException;
import com.bit.common.businessEnum.CalculateFlagEnum;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.equation.bean.Equation;
import com.bit.module.equation.dao.BasePriceEquationDao;
import com.bit.module.equation.dao.BasePriceEquationRelDao;
import com.bit.module.equation.dao.EquationDao;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.miniapp.bean.Area;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.module.miniapp.bean.Options;
import org.apache.commons.lang.StringUtils;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * @description:
 * @author: mifei
 * @create: 2019-05-06 15:44
 */
@Service
public class EquationCacheServiceImpl extends ServiceImpl<EquationDao, Equation> {

    @Autowired
    private EquationDao equationDao;
    @Autowired
    private BasePriceEquationDao basePriceEquationDao;
    @Autowired
    private BasePriceEquationRelDao basePriceEquationRelDao;

    @Cacheable(value = "cache:equation")
    public List<Equation> getEquations(String type, String category) {
        return equationDao.selectList(new QueryWrapper<Equation>()
                .eq(StringUtils.isNotEmpty(type), "type", type)
                .eq("category", category));
    }

    @Cacheable(value = "cache:basePriceEquation")
    public BasePriceEquation getBasePriceEquation(String type, String val3,String category) {
        QueryWrapper<BasePriceEquation> query = new QueryWrapper<BasePriceEquation>()
                .eq("category", category)
                .eq("type", type)
                .eq("val3", val3).last(" limit 1");
        BasePriceEquation basePriceEquation = basePriceEquationDao.selectOne(query);
        return basePriceEquation;
    }

    @Cacheable(value = "cache:basePriceEquationRel")
    public List<BasePriceEquationRel> getBasePriceEquationRelList(String type, String category) {
        List<BasePriceEquationRel> basePriceRel = basePriceEquationRelDao.selectList(
                new QueryWrapper<BasePriceEquationRel>()
                        .eq("type", type)
                        .eq("category", category)
        );
        return basePriceRel;
    }


    @CacheEvict(value = "cache:equation",allEntries=true)
    public void cleanCache() {
    }
    @CacheEvict(value = "cache:basePriceEquation",allEntries=true)
    public void cleanCache2() {
    }
    @CacheEvict(value = "cache:basePriceEquationRel",allEntries=true)
    public void cleanCache3() {
    }
}
