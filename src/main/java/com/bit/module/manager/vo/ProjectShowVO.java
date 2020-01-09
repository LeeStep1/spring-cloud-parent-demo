package com.bit.module.manager.vo;

import com.bit.module.manager.bean.Project;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/1/6 9:50
 **/
@Data
public class ProjectShowVO  {
	/**
	 * 项目名称
	 */
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
	 * 非标审批状态:-1：撤销 0：无需审批，1：非标的待提交，2：待审核，3：通过，4：拒绝
	 */
	private Integer nonStandardApplyStatus;
	/**
	 * 报价id
	 */
	private Long projectPriceId;
	/**
	 * 项目id
	 */
	private Long projectId;
	/**
	 * 销售人员名字
	 */
	private String realName;
	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 创建时间
	 **/
	private Date createTime;
	/**
	 * 地址名称
	 **/
	private String addressName;
	/**
	 * 审批人姓名
	 */
	private String auditUserName;
}
