package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.equation.bean.Equation;
import com.bit.module.manager.vo.EquationPageVO;

/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 14:38:14
 */
public interface EquationManagerService {

	/**
	 * 新增数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(Equation equation);

	/**
	 * 编辑数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(Equation equation);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);




	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Equation
	 */
	BaseVo reflectById(Long id);

	/**
	 * 分页查询
	 * @param equationPageVO
	 * @return
	 */
	BaseVo listPage(EquationPageVO equationPageVO);

}

