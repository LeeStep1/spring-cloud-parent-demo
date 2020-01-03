package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 分公司表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Data
@TableName("t_company")
public class Company implements Serializable {

	/**
	 *
	 */
	private Long id;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 父id
	 */
	private Long parentId;

}
