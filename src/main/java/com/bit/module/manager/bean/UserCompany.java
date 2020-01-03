package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户与公司关联表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Data
@TableName("t_user_company")
public class UserCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 公司id
	 */
	private Long companyId;

}
