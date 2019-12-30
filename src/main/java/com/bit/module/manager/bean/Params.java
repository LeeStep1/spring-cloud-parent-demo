package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author chenduo
 * @email ${email}
 * @date 2019-12-30 09:49:11
 */
@Data
@TableName("t_params")
public class Params implements Serializable {

	/**
	 *
	 */
	private Long id;
	/**
	 * 参数名称
	 */
	private String paramsName;
	/**
	 * 参数的运算key值
	 */
	private String paramsKey;
	/**
	 * 参数的数据类型
	 */
	private String paramsValueType;
	/**
	 * 参数的单位
	 */
	private String paramsUnit;
	/**
	 * 参数的描述
	 */
	private String paramsDescription;

}
