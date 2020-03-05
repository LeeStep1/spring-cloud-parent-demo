package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.CompanyRate;
import com.bit.module.manager.vo.CompanyRatePageVO;

/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 14:04:17
 */
public interface CompanyRateService {

	/**
	 * 新增数据
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(CompanyRate companyRate);

	/**
	 * 编辑数据
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(CompanyRate companyRate);


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
	 * @return CompanyRate
	 */
	BaseVo reflectById(Long id);

	/**
	 * 分页查询
	 * @param ratePageVO
	 * @return
	 */
	BaseVo listPage(CompanyRatePageVO ratePageVO);

	/**
	 * 多参数查询
	 * @param companyRate
	 * @return
	 */
	BaseVo findByParam(CompanyRate companyRate);

}

