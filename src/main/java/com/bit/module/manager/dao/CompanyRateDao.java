package com.bit.module.manager.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.CompanyRate;
import com.bit.module.manager.vo.CompanyRatePageVO;
import com.bit.module.manager.vo.CompanyRateVO;
import com.bit.module.manager.vo.ProjectPageVO;
import com.bit.module.manager.vo.ProjectShowVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Repository
public interface CompanyRateDao  extends BaseMapper<CompanyRate> {

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

	/**
	 * 分页查询
	 * @param page
	 * @param companyRatePageVO
	 * @return
	 */
	IPage<CompanyRateVO> listPage(@Param("pg")Page<CompanyRateVO> page, @Param("companyRatePageVO")CompanyRatePageVO companyRatePageVO);
}
