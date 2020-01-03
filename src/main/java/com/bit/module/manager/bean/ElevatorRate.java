package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @Description 判断销售人员下浮率使用
 * @Author chenduo
 * @Date 2020/1/3 14:48
 **/
@Data
public class ElevatorRate {
	/**
	 * 电梯类型id
	 */
	private Long elevatorTypeId;
	/**
	 * 下浮率
	 */
	private Double rate;
}
