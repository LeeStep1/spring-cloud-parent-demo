package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;

/**
 * 字典表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-02-14 09:06:31
 */
@Data
@TableName("t_dict")
public class Dict  {

	/**
	 * 主键id
	 */
private Long id;
	/**
	 * 业务模块
	 */
private String module;
	/**
	 * code
	 */
private String dictCode;
	/**
	 * 显示名称
	 */
private String dictName;
	/**
	 * 排序
	 */
private Integer sort;
	/**
	 * 备注
	 */
private String remark;

}
