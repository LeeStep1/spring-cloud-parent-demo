package com.bit.module.manager.service;

import java.util.List;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.miniapp.bean.ElevatorType;

/**
 * 电梯系列基础信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:33:26
 */
public interface ElevatorTypeService {

	/**
	 * 新增数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	void add(ElevatorType elevatorType);

	/**
	 * 编辑数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	void update(ElevatorType elevatorType);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	void delete(Long id);


	/**
	 * 多参数查询数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorType>
	 */
	List<ElevatorType> findByParam(ElevatorType elevatorType);

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ElevatorType
	 */
    BaseVo reflectById(Long id);
	/**
	 * 电梯类型列表查询
	 * @return
	 */
    BaseVo elevatorTypeListPage(ElevatorTypeVO elevatorTypeVO);
}

