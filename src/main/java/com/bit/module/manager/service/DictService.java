package com.bit.module.manager.service;

import com.bit.module.manager.bean.Dict;
import com.bit.base.vo.BaseVo;

/**
 * 字典表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-14 09:06:31
 */
public interface DictService {

	/**
	 * 新增数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(Dict dict);

	/**
	 * 编辑数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(Dict dict);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);


	/**
	 * 多参数查询数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 * @return List<Dict>
	 */
	BaseVo findByModule(Dict dict);

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Dict
	 */
	BaseVo reflectById(Long id);

}

