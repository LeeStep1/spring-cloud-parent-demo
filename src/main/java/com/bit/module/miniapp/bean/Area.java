package com.bit.module.miniapp.bean;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 采集居民中的一标三实的区划代码
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
@Data
@TableName("t_area")
public class Area implements Serializable {

	/**
	 * 地域编码
	 */
	private String arCode;
	/**
	 * 地域名称
	 */
	private String arName;
	/**
	 * 地域类型
	 */
	private String arType;
	/**
	 * 父级编码
	 */
	private String parentCode;
	/**
	 * 地域等级
	 */
	private Integer arLeavel;
	/**
	 * 运输费吨位单价
	 */
	private Integer tonsPrice;
	/**
	 * 安装系数
	 */
	private Float installCoefficient;
	/**
	 * 前端web页面使用 0- 否 1- 是
	 */
	@TableField(exist = false)
	private Integer flag;

}
