package com.bit.module.manager.vo;


import java.util.Date;
import lombok.Data;

/**
 * 电梯类型与可选项关联表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-21 10:34:30
 */
@Data
public class ElevatorRelaOptionsVO  {

	/**
	 * 数据id
	 */
	private Long id;
	/**
	 * 电梯型号，对应t_elevator_type表中的id
	 */
	private Long elevatorTypeId;
	/**
	 * 对应t_options表中的id
	 */
	private Long optionId;


}
