package com.bit.module.manager.vo;



import com.bit.base.vo.BasePageVo;
import lombok.Data;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 14:38:14
 */
@Data
public class EquationPageVO extends BasePageVo{

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
	private String equation;

	/**
	 * 输出
	 */
	private String output;


}
