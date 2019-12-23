package com.bit.module.manager.vo;


import java.io.Serializable;
import java.util.Date;

import com.bit.module.manager.bean.FileInfo;
import lombok.Data;

/**
 * 电梯系列基础信息表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:33:26
 */
@Data
public class ElevatorTypeVO implements Serializable {
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
	private String key;
	/**
	 * 图片地址
	 */
	private String picture;
	/**
	 * 附件信息
	 */
	private FileInfo fileInfo;

}
