package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @Description 规格参数 和 井道参数
 * @Author chenduo
 * @Date 2019/12/26 9:32
 **/
@Data
public class ElementParam {
	/**
	 * 元素名称
	 */
	private String elementName;

	/**
	 * 基础信息的类别：1规格参数，2：井道参数
	 */
	private Integer categoryType;
	/**
	 * 所填写的属性参数
	 */
	private String infoValue;
	/**
	 * 参数的单位
	 */
	private String paramsUnit;

}
