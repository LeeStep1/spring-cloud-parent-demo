package com.bit.module.miniapp.bean;


import java.io.Serializable;
import java.util.Date;

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
	private static final long serialVersionUID = 1L;

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
	private String arType;
	/**
	 *
	 */
	private String parentCode;
	/**
	 *
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

}
