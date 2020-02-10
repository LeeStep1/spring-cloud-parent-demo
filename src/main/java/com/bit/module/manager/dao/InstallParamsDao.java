package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.InstallParams;
import com.bit.module.manager.vo.InstallParamsPageVO;
import com.bit.module.manager.vo.InstallParamsVO;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-10 11:28:40
 */
@Repository
public interface InstallParamsDao extends BaseMapper<InstallParams>{

	/**
    * 根据id单查记录
    * @param id
    */
	InstallParams getInstallParamsById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<InstallParams> findByParam(InstallParams installParams);

	/**
	* 新增记录
    */
	void addInstallParams(InstallParams installParams);

	/**
    * 编辑记录
    */
	void updateInstallParams(InstallParams installParams);

	/**
    * 删除记录
    */
	void delInstallParamsById(Long id);

	/**
     * 分页查询
     */
	IPage<InstallParamsVO> listPage(@Param("pg")Page<InstallParamsVO> page, @Param("installParamsPageVO") InstallParamsPageVO installParamsPageVO);
	
}
