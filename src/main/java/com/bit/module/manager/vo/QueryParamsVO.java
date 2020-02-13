package com.bit.module.manager.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:04:01
 */
@Data
public class QueryParamsVO implements Serializable {

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
	 * 前端web页面使用 0- 否 1- 是
	 */
	@TableField(exist = false)
	private Integer hasChildren;
}
