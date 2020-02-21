package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.miniapp.bean.ElevatorRelaOptions;

/**
 * 电梯类型与可选项关联表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-21 10:34:30
 */
public interface ElevatorRelaOptionsService {

	/**
	 * 新增数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 编辑数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(Long id);


	/**
	 * 多参数查询数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorRelaOptions>
	 */
	BaseVo findByParam(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ElevatorRelaOptions
	 */
	BaseVo reflectById(Long id);

}

