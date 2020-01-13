package com.bit.module.equation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.equation.bean.BasePriceEquationRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 16:38:20
 */
@Repository
public interface BasePriceEquationRelDao extends BaseMapper<BasePriceEquationRel>{

	/**
    * 根据id单查记录
    * @param id
    */
	BasePriceEquationRel getBasePriceEquationRelById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<BasePriceEquationRel> findByParam(BasePriceEquationRel basePriceEquationRel);

	/**
	* 新增记录
    */
	void addBasePriceEquationRel(BasePriceEquationRel basePriceEquationRel);

	/**
    * 编辑记录
    */
	void updateBasePriceEquationRel(BasePriceEquationRel basePriceEquationRel);

	/**
    * 删除记录
    */
	void delBasePriceEquationRelById(Long id);

	
}
