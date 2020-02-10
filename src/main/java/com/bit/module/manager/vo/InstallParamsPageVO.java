package com.bit.module.manager.vo;

import com.bit.base.vo.BasePageVo;
import lombok.Data;

@Data
public class InstallParamsPageVO extends BasePageVo {

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
