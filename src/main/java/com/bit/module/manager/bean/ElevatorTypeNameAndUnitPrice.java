package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @Description 电梯类型名称和产品单价
 * @Author chenduo
 * @Date 2019/12/26 9:36
 **/
@Data
public class ElevatorTypeNameAndUnitPrice {

	/**
	 * 电梯名称
	 */
	private String elevatorTypeName;
	/**
	 * 设备单价
	 */
	private String unitPrice;
	/**
	 * 下浮率
	 */
	private Double rate;
}
