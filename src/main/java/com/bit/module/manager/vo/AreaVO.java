package com.bit.module.manager.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/25 9:49
 **/
@Data
public class AreaVO {

	/**
	 *
	 */
	private String arCode;
	/**
	 *
	 */
	private String arName;
	/**
	 *
	 */
	private String arType;
	/**
	 *
	 */
	private String parentCode;
	/**
	 *
	 */
	private Integer arLeavel;
	/**
	 * 运输费吨位单价
	 */
	private Integer tonsPrice;
	/**
	 * 安装系数
	 */
	private Float installCoefficient;
	/**
	 * 子节点
	 */
	private List<AreaVO> childList;
}
