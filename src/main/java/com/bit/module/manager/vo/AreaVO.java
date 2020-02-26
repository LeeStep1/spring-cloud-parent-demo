package com.bit.module.manager.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/2/26 16:50
 **/
@Data
public class AreaVO {

	/**
	 * 地域编码
	 */
	private String arCode;
	/**
	 * 地域名称
	 */
	private String arName;
	/**
	 * 地域类型
	 */
	private String arType;
	/**
	 * 父级编码
	 */
	private String parentCode;
	/**
	 * 地域等级
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
	 * 前端web页面使用 0- 否 1- 是
	 */
	private Integer hasChildren;
	/**
	 * 子节点
	 */
	private List<AreaVO> childList;
}
