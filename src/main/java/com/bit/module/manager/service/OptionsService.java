package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.OptionsPageVO;

/**
 * 选项以及非标项
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 16:44:24
 */
public interface OptionsService {

	/**
	 * 新增数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(Options options);

	/**
	 * 编辑数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(Options options);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);


	/**
	 * 多参数查询数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 * @return List<Options>
	 */
	BaseVo findByParam(Options options);

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Options
	 */
	BaseVo reflectById(Long id);
	/**
	 * 分页查询
	 * @param optionsPageVO
	 * @return
	 */
	BaseVo listPage(OptionsPageVO optionsPageVO);
}

