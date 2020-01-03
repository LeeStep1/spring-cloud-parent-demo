package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author chenduo
 * @email ${email}
 * @date 2020-01-03 13:19:11
 */
@Data
@TableName("t_company_rate")
public class CompanyRate implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 下浮率
	 */
	private Double rate;
	/**
	 * 公司id  t_company
	 */
	private Long companyId;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 电梯类型id,电梯类型表中的id
	 */
	private Long elevatorTypeId;

}
