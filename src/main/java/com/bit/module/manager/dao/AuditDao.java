package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.Audit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 审批表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-06 17:02:40
 */
@Repository
public interface AuditDao extends BaseMapper<Audit>{

	/**
    * 根据id单查记录
    * @param id
    */
	Audit getAuditById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<Audit> findByParam(Audit audit);

	/**
	* 新增记录
    */
	void addAudit(Audit audit);

	/**
    * 编辑记录
    */
	void updateAudit(Audit audit);

	/**
    * 删除记录
    */
	void delAuditById(Long id);

	/**
	 * 批量新增
	 * @param audits
	 */
	void batchAdd(@Param(value = "list")List<Audit> audits);

	/**
	 * 多参数查询 只取一个
	 * @return
	 */
	List<Audit> findByParamBatchByProjectIdAndProjectPriceId(@Param(value = "map")Map map);
}
