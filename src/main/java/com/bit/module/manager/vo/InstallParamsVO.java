package com.bit.module.manager.vo;


import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-10 11:28:40
 */
@Data
public class InstallParamsVO  {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 安装系数
	 */
	private Double value;
	/**
	 * 区间最小值
	 */
	private Double minValue;
	/**
	 * 区间最大值
	 */
	private Double maxValue;
	/**
	 * 业务代码
	 */
	private String moduleCode;

}
