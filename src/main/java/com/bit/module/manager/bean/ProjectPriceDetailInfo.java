package com.bit.module.manager.bean;

import com.bit.module.miniapp.bean.Options;
import lombok.Data;

import java.util.List;

/**
 * @Description 电梯细节 参数 和 可选选项
 * @Author chenduo
 * @Date 2019/12/26 9:21
 **/
@Data
public class ProjectPriceDetailInfo {

	/**
	 * 电梯名称
	 */
	private String elevatorTypeName;
	/**
	 * 设备单价
	 */
	private String unitPrice;
	/**
	 * 下浮率
	 */
	private Double rate;

	/**
	 * 基价
	 */
	private String basePrice;
	/**
	 * 高度加价
	 */
	private String additionPrice;
	/**
	 * 总价
	 */
	private String totalPrice;
	/**
	 * 电梯数量
	 */
	private String nums;

	/**
	 * 安装价格
	 */
	private String installPrice;

	/**
	 * '运输单价'
	 */
	private String transportPrice;

	/**
	 * 是否能做
	 */
	private Integer calculateFlag;


	/**
	 * 审批备注
	 */
	private String auditRemark;

	//----规格参数----
	/**
	 * 规格参数 和 井道参数
	 */
	private List<ElementParam> elementParams;

	//----功能定制----
	/**
	 * 可选选项
	 */
	private List<Options> options;

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
	 * 订单价格
	 */
	private String orderPrice;
	/**
	 * 对应t_projec_price表中的id
	 */
	private Integer version;

	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 报价id
	 */
	private Long priceId;
	/**
	 * 乐观锁
	 */
	private Integer positiveLock;

	/**1.2版本增加字段**/
	/**
	 * 电梯类型字段
	 */
	private Long  elevatorTypeId;

	/**
	 * 单台基础价格成本
	 */
	private String  costBasePrice;
}
