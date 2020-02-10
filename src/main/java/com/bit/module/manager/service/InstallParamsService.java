package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.InstallParams;
import com.bit.module.manager.vo.InstallParamsPageVO;

/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-10 11:28:40
 */
public interface InstallParamsService {

	/**
	 * 新增数据
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(InstallParams installParams);

	/**
	 * 编辑数据
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(InstallParams installParams);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);


	/**
	 * 多参数查询数据
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 * @return List<InstallParams>
	 */
	BaseVo findByParam(InstallParams installParams);

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return InstallParams
	 */
	BaseVo reflectById(Long id);

	/**
	 * 分页查询
	 * @param installParamsPageVO
	 * @return
	 */
	BaseVo listPage(InstallParamsPageVO installParamsPageVO);

}

