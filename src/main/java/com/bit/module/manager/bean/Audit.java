package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 审批表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-06 17:02:40
 */
@Data
@TableName("t_audit")
public class Audit {


	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 项目id
	 */
	private Long projectId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 审批类型
	 */
	private Integer auditType;

	/**
	 * 审批人id
	 */
	private Long auditUserId;
	/**
	 * 审批人姓名
	 */
	private String auditUserName;
	/**
	 * 审批时间
	 */
	private Date auditTime;
	/**
	 * 报价id
	 */
	private Long projectPriceId;

}
