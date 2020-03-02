package com.bit.module.manager.vo;

import lombok.Data;

/**
 * @Description 洽谈项目返显传参使用
 * @Author chenduo
 * @Date 2020/3/2 11:10
 **/
@Data
public class NegotiationVO {
	/**
	 * 项目id
	 */
	private Long projectId;
	/**
	 * 项目报价id
	 */
	private Long projectPriceId;
	/**
	 * 报价次数
	 */
	private Integer enquireTimes;
}
