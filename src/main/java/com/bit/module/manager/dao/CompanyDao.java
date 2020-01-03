package com.bit.module.manager.dao;

import com.bit.module.manager.bean.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分公司表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Repository
public interface CompanyDao {

	/**
    * 根据id单查记录
    * @param id
    */
	Company getCompanyById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<Company> findByParam(Company company);

	/**
	* 新增记录
    */
	void addCompany(Company company);

	/**
    * 编辑记录
    */
	void updateCompany(Company company);

	/**
    * 删除记录
    */
	void delCompanyById(Long id);

	
}
