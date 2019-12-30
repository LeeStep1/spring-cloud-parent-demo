package com.bit.module.manager.service;

import java.util.List;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.ElevatorTypePageVO;
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
	BaseVo add(ElevatorType elevatorType);

	/**
	 * 编辑数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(ElevatorType elevatorType);

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
	 * @return ElevatorType
	 */
    BaseVo reflectById(Long id);
	/**
	 * 电梯类型列表查询
	 * @return
	 */
    BaseVo elevatorTypeListPage(ElevatorTypePageVO elevatorTypePageVO);

	/**
	 * 电梯类型
	 * @return
	 */
	BaseVo categoryList();
	/**
	 * paramsKey验重
	 * @param elevatorType
	 * @return
	 */
	BaseVo distinctParams(ElevatorType elevatorType);
}

