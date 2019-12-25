package com.bit.module.manager.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/25 9:49
 **/
@Data
public class AreaTreeVO {

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
	private String parentCode;
	/**
	 * 子节点
	 */
	private List<AreaTreeVO> childList;
}
