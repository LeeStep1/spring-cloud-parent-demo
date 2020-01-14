package com.bit.module.manager.service;


import com.bit.base.vo.BaseVo;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.manager.vo.BasePriceEquationPageVO;

/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 16:38:19
 */
public interface BasePriceEquationService {
	/**
	 * 查询电梯的属性 载重 速度 层站 提升高度
	 * @param basePriceEquationRel
	 * @return
	 */
	BaseVo findBasePriceEquationRelByParam(BasePriceEquationRel basePriceEquationRel);

	/**
	 * 电梯各属性值的查询
	 * @param queryParams
	 * @return
	 */
	BaseVo findQueryParamsByParam(QueryParams queryParams);

	/**
	 * 列表查询
	 * @param basePriceEquationPageVO
	 * @return
	 */
	BaseVo listPage(BasePriceEquationPageVO basePriceEquationPageVO);

	/**
	 * 更新数据
	 * @param basePriceEquation
	 * @return
	 */
	BaseVo update(BasePriceEquation basePriceEquation);

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	BaseVo delete(Long id);

	/**
	 * 参数验重
	 * @param basePriceEquation
	 * @return
	 */
	BaseVo distinctParam(BasePriceEquation basePriceEquation);

	/**
	 * 新增数据
	 * @param basePriceEquation
	 * @return
	 */
	BaseVo add(BasePriceEquation basePriceEquation);
}

