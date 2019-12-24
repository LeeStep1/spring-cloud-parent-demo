package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/24 9:59
 **/
public interface ElevatorService {
	/**
	 * 根据电梯类型查询电梯的可选项
	 * @param typeId
	 * @return
	 */
	BaseVo getEleOptions(Long typeId);
}
