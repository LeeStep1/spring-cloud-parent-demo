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
	/**
	 * 关闭状态分类：1：洽谈中，2：成交，3：流失
	 */
	private Integer closedStatus;
	/**
	 * 关闭的原因类型
	 */
	private Integer reasonCustomerChurnId;
	/**
	 * 关闭项目的原因的名称
	 */
	private String reasonCustomerChurnName;
	/**
	 * 流失原因备注
	 */
	private String reasonCustomerChurmRemarks;
	/**
	 * 关闭人Id
	 */
	private Long closedUserId;
	/**
	 * 关闭项目的用户姓名
	 */
	private String closedUserName;
	/**
	 * 关闭时间
	 */
	private Date closedTime;
	/**
	 * 关闭项目时所关联的保价ID
	 */
	private Long closedProjectPriceId;

	/**
	 * 议价审批状态：0：未提交审批，1：审批中，2：审批通过，3：审批拒绝，4，撤销
	 */
	private Integer enquiryApplyStatus;
	/**
	 * 无优惠的总价
	 */
	private String nonRateTotalPrice;
	/**
	 * 平均下浮率
	 */
	private Double averageRate;
	/**
	 * 最大下浮率
	 */
	private Double maxRate;
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
	 * 提交议价的期望价格
	 */
	private String inquiryPrice;
	/**
	 * 成本总价
	 */
	private String costTotalPrice;


//	搜索传参字段
	/**
	 * 小程序 历史项目 里的多参数混合查询
	 */
	private String search;
	/**
	 * 排序
	 */
	private String orderBy;
	/**
	 * 排序 接口类型 0-个人 1-洽谈
	 */
	private Integer orderByType;
}
