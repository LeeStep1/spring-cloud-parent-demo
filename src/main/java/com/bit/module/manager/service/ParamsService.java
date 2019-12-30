package com.bit.module.manager.service;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Params;
import com.bit.module.manager.vo.ParamsPageVO;

/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-30 09:49:11
 */
public interface ParamsService {

	/**
	 * 新增数据
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(Params params);

	/**
	 * 编辑数据
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(Params params);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);


	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Params
	 */
	BaseVo reflectById(Long id);

	/**
	 * 参数验重
	 * @param params
	 * @return
	 */
	BaseVo distinctParams(Params params);

	/**
	 * 分页查询数据
	 * @param paramsPageVO
	 * @author chenduo
	 * @since ${date}
	 * @return PageInfo
	 */
	BaseVo paramsListPage(ParamsPageVO paramsPageVO);

}

