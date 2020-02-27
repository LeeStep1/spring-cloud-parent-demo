package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ProjectPriceDetailInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description 项目下订单详情
 * @Author chenduo
 * @Date 2020/1/6 16:20
 **/
@Data
public class ProjectOrderWebVO {

	//-----项目细节------

	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 代理商名称
	 */
	private String  agentName;
	/**
	 * 创建人姓名
	 */
	private String createUserName;
	/**
	 * 报价日期
	 */
	private Date createTime;
	/**
	 * 项目地点
	 */
	private String addressName;
	/**
	 * 项目状态1:正常，0 ：流失
	 */
	private Integer projectStatus;

	//----项目报价-----

	/**
	 * 报价
	 */
	private String totalPrice;

	/**
	 * 版本
	 */
	private Integer version;


	/**
	 * 安装标识
	 */
	private Integer installFlag;

	/**
	 * 运输标识
	 */
	private Integer transportFlag;

	/**
	 * 安装费用
	 */
	private String installPrice;

	/**
	 * 运输费用
	 */
	private String transportPrice;
	//----电梯细节----
	/**
	 * 电梯细节 集合
	 */
	private List<ProjectPriceDetailInfo> projectPriceDetailInfos;

	/**
	 * 订单的是否非标 1：标准，0非标',
	 */
	private Integer standard;
	/**
	 * 名称：1：标准，0非标',
	 */
	private String standardName;
	/**
	 * 订单价格
	 */
	private String orderPrice;


	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 报价id
	 */
	private Long priceId;

	//1.2新增字段
	/**
	 * 议价审批状态：0：未提交审批，1：审批中，2：审批通过，3：审批拒绝，4，撤销
	 */
	private Integer enquiryApplyStatus;
	/**
	 * 关闭状态分类 关闭状态分类：1：成交，2：流失
	 **/
	private Integer closedStatus;
	/**
	 * 关闭的原因类型
	 **/
	private Integer reasonCustomerChurnId;
	/**
	 * 关闭项目的原因的名称
	 */
	private String reasonCustomerChurnName;
	/**
	 * 流失原因备注
	 */
	private String reasonCustomerChurmRemarks;
}
