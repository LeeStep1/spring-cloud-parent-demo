package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ElementParam;
import com.bit.module.manager.bean.ProjectEleNonstandard;
import com.bit.module.manager.bean.ProjectPriceDetailInfo;
import com.bit.module.miniapp.bean.Options;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description 项目的订单的详情
 * @Author chenduo
 * @Date 2019/12/30 13:51
 **/
@Data
public class ProjectOrderDetailInfoVO {

	//-----项目细节------
	/**
	 * 项目id
	 */
	private Long projectId;

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
	 * 总价
	 */
	private String totalPrice;
	/**
	 * 台数
	 */
	private Integer num;
	/**
	 * 设备单价
	 */
	private String unitPrice;
	/**
	 * 电梯类型id
	 */
	private Integer elevatorTypeId;
	/**
	 * 电梯类型名称
	 */
	private String elevatorTypeName;
	/**
	 * 项目地点
	 */
	private String addressName;
	/**
	 * 下浮率
	 */
	private Double rate;
	/**
	 * 报价表里的version
	 */
	private Integer version;
	/**
	 * 是否选择安装费 0-否 1-是
	 */
	private Integer installFlag;
	/**
	 * 是否选择运输费 0-否 1-是
	 */
	private Integer transportFlag;
	//----电梯细节----
	/**
	 * 电梯细节 集合
	 */
	private List<ElementParam> elementParams;
	/**
	 * 功能定制
	 */
	private List<Options> projectOptions;


	/**
	 * 安装费用总价
	 */
	private String installPrice;

	/**
	 * 运输费用总价
	 */
	private String transportPrice;

	/**
	 * 订单的是否非标 1：标准，0非标',
	 */
	private Integer standard;
	/**
	 * 名称：1：标准，0非标',
	 */
	private String standardName;

	/**
	 * 非标时的额外非标项
	 */
	private List<ProjectEleNonstandard> projectEleNonstandardOptionList;

	/**
	 * 是否参与计算 1：参与，0 不参与
	 */
	private Integer calculateFlag;

	/**
	 * 非标审批的备注
	 */
	private String auditRemark;


	private Integer nonStandardApplyStatus;
}
