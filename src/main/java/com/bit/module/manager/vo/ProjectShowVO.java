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
	 * 是否标准，1：标准，0非标
	 */
	private Integer standard;
	/**
	 * 标准名称1：标准，0非标
	 */
	private String standardName;
	/**
	 * 审批人姓名
	 */
	private String auditUserName;
	/**
	 * 议价流程节点的审批人
	 */
	private Long enquiryAuditUserId;
	/**
	 * 议价的环节点的审批人所属公司id
	 */
	private Long enquiryAuditUserCompanyId;

	/**
	 * 提交议价的时间
	 */
	private Date enquiryApplyTime;
	/**
	 * 报价次数
	 */
	private Integer enquireTimes;
	/**
	 * 最近报价日期
	 */
	private Date latestEnquireDate;
}
