package com.bit.module.manager.dao;

import com.bit.module.manager.bean.UserCompany;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 用户与公司关联表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Repository
public interface UserCompanyDao {

	/**
    * 根据id单查记录
    * @param id
    */
	UserCompany getUserCompanyById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<UserCompany> findByParam(UserCompany userCompany);

	/**
	* 新增记录
    */
	void addUserCompany(UserCompany userCompany);

	/**
    * 编辑记录
    */
	void updateUserCompany(UserCompany userCompany);

	/**
    * 删除记录
    */
	void delUserCompanyById(Long id);
	
}
