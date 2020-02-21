package com.bit.module.manager.vo;

import com.bit.base.vo.BasePageVo;
import lombok.Data;

/**
 * 基础信息填写模板数据信息表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-21 10:46:23
 */
@Data
public class ElevatorBaseElementPageVO extends BasePageVo {

	/**
	 * 
	 */
	private Long id;
	/**
	 * 电梯类型表ID
	 */
	private Long elevatorTypeId;
	/**
	 * 电梯类型的查询的key值
	 */
	private String elevatorTypeKey;
	/**
	 * 元素名称
	 */
	private String elementName;
	/**
	 * 元素展示类型：1，选择，2手输入
	 */
	private Integer elementType;
	/**
	 * 基础信息的类别：1规格参数，2：井道参数
	 */
	private Integer categoryType;
	/**
	 * 变量的参数，页面提交用作的key值
	 */
	private String paramsKey;
	/**
	 * 排序
	 */
	private Integer orderNum;


}
