package com.bit.module.manager.vo;


import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 14:38:14
 */
@Data
public class EquationVO  {

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private String category;
	/**
	 * 
	 */
	private String type;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 0：无关联 1：电梯类型 2：可选项
	 */
	private Integer relevanceType;
	/**
	 * 
	 */
	private String title1;
	/**
	 * 
	 */
	private String equation;
	/**
	 * 
	 */
	private String coefficient;
	/**
	 * 
	 */
	private String equation3;
	/**
	 * 输出
	 */
	private String output;

}
