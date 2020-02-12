package com.bit.module.manager.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bit.base.vo.BasePageVo;
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
public class QueryParamsPageVO extends BasePageVo {

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
