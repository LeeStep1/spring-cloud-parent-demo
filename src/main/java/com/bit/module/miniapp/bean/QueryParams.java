package com.bit.module.miniapp.bean;


import java.io.Serializable;
import java.util.Date;

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

}