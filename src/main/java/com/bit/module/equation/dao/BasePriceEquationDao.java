package com.bit.module.equation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.manager.vo.BasePriceEquationPageVO;
import com.bit.module.manager.vo.BasePriceEquationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 16:38:19
 */
@Repository
public interface BasePriceEquationDao extends BaseMapper<BasePriceEquation>{

	/**
    * 根据id单查记录
    * @param id
    */
	BasePriceEquation getBasePriceEquationById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<BasePriceEquation> findByParam(BasePriceEquation basePriceEquation);

	/**
	* 新增记录
    */
	void addBasePriceEquation(BasePriceEquation basePriceEquation);

	/**
    * 编辑记录
    */
	void updateBasePriceEquation(BasePriceEquation basePriceEquation);

	/**
    * 删除记录
    */
	void delBasePriceEquationById(Long id);


	/**
	 * 列表查询
	 * @param page
	 * @param basePriceEquationPageVO
	 * @return
	 */
	IPage<BasePriceEquationVO> listPage(@Param("pg")Page<BasePriceEquationVO> page, @Param("basePriceEquationPageVO") BasePriceEquationPageVO basePriceEquationPageVO);
}
