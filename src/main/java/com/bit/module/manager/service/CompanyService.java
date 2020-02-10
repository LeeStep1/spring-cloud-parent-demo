package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Company;
import com.bit.module.miniapp.bean.Area;

/**
 * 采集居民中的一标三实的区划代码
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
public interface CompanyService {

	/**
	 * 全部公司
	 * @return
	 */
	BaseVo getCompany( );

	/**
	 * 公司树
	 * @return
	 */
	BaseVo companyTree();

	/**
	 * 新增公司
	 * @return
	 */
	BaseVo addCompany(Company company);

	/**
	 * 更新公司
	 * @param company
	 * @return
	 */
	BaseVo updateCompany(Company company);

	/**
	 * 删除公司
	 * @param id
	 * @return
	 */
	BaseVo delCompany(Long id);

	/**
	 * 单查公司
	 * @param id
	 * @return
	 */
	BaseVo reflectById(Long id);
}

