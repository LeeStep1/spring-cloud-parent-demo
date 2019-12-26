package com.bit.module.miniapp.bean;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 电梯系列基础信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:33:26
 */
@Data
@TableName("t_elevator_type")
public class ElevatorType implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 类别名称
	 */
	private String typeName;
	/**
	 * 1:客梯,2:别墅梯，3:货梯，4：扶梯
	 */
	private Integer type;
	/**
	 * 所属系列
	 */
	private String series;
	/**
	 * 运算用key
	 */
	private String paramsKey;
	/**
	 * 图片地址
	 */
	private String picture;
	/**
	 * 梯型
	 */
	private String category;

}
