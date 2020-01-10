package com.bit.module.manager.vo;


import java.io.Serializable;
import java.util.Date;

import com.bit.base.vo.BasePageVo;
import lombok.Data;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 14:04:17
 */
@Data
public class CompanyRatePageVO extends BasePageVo implements Serializable {

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
