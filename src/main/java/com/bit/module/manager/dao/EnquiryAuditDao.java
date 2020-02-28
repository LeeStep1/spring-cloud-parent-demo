package com.bit.module.manager.dao;

import com.bit.module.manager.bean.EnquiryAudit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-25
 **/
@Repository
public interface EnquiryAuditDao {

    void batchAdd(@Param(value = "list") List<EnquiryAudit> list );

	/**
	 * 新增记录
	 */
	void addEnquiryAudit(EnquiryAudit enquiryAudit);

	/**
	 * 编辑记录
	 */
	void updateEnquiryAudit(EnquiryAudit enquiryAudit);

	/**
	 * 根据id单查记录
	 * @param id
	 */
	EnquiryAudit getEnquiryAuditById(Long id);
}
