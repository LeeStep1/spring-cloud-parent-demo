package com.bit.module.manager.dao;

import java.util.List;

import com.bit.module.manager.bean.CompanyRate;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Repository
public interface CompanyRateDao {

	/**
    * 根据id单查记录
    * @param id
    */
	CompanyRate getCompanyRateById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<CompanyRate> findByParam(CompanyRate companyRate);

	/**
	* 新增记录
    */
	void addCompanyRate(CompanyRate companyRate);

	/**
    * 编辑记录
    */
	void updateCompanyRate(CompanyRate companyRate);

	/**
    * 删除记录
    */
	void delCompanyRateById(Long id);

	
}
