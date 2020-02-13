package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.equation.bean.Equation;

import java.util.List;
import com.bit.module.manager.vo.EquationPageVO;
import com.bit.module.manager.vo.EquationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 14:38:14
 */
@Repository
public interface EquationManagerDao extends BaseMapper<Equation>{

	/**
    * 根据id单查记录
    * @param id
    */
	Equation getEquationById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<Equation> findByParam(Equation equation);

	/**
	* 新增记录
    */
	void addEquation(Equation equation);

	/**
    * 编辑记录
    */
	void updateEquation(Equation equation);

	/**
    * 删除记录
    */
	void delEquationById(Long id);

	/**
     * 分页查询
     */
	IPage<EquationVO> listPage(@Param("pg")Page<EquationVO> page, @Param("equationPageVO") EquationPageVO equationPageVO);
	
}
