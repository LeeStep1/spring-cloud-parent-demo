package com.bit.module.manager.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bit.base.vo.BasePageVo;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class ProjectPageVO extends BasePageVo {

	/**
	 * id
	 **/
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * '项目名称'
	 **/
	private String projectName;
	/**
	 * '客户名称'
	 **/
	private String customerName;

	/**
	 * '代理商名称'
	 **/
	private String agentName;
	/**
	 * '创建人id'
	 **/
	private Long createUserId;
	/**
	 * '创建人姓名'
	 **/
	private String createUserName;
	/**
	 * 创建时间
	 **/
	private Date createTime;
	/**
	 * 地址id  对应area表中的ID
	 **/
	private String addressId;
	/**
	 * 名称
	 **/
	private String addressName;
	/**
	 * '项目状态1:正常，0 ：流失'
	 **/
	private Integer projectStatus;
	/**
	 * 公司id
	 **/
	private Long companyId;
	/**
	 * 公司名称
	 **/
	private String companyName;
	/**
	 * 非标审批状态:-1：撤销 0：无需审批，1：非标的待提交，2：待审核，3：通过
	 */
	private Integer nonStandardApplyStatus;
	/**
	 * 销售人员姓名
	 */
	private String realName;
}
