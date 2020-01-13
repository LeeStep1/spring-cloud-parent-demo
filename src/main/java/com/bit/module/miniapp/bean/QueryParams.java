package com.bit.module.miniapp.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:04:01
 */
@Data
@TableName("t_query_params")
public class QueryParams implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String id;
	/**
	 * 基础数据的选项名称
	 */
	private String title;
	/**
	 * 通用的参数key
	 */
	private String key;
	/**
	 *
	 */
	private String value;
	/**
	 * 电梯类型描述
	 */
	private String category;

	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private List<QueryParams> childList;
	/**
	 * 电梯类型参数关键字
	 */
	@TableField(exist = false)
	private String elevatorTypeParamsKey;
	/**
	 * key的集合
	 */
	@TableField(exist = false)
	private List<String> keys;
}
