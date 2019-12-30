package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ElementParam;
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
	 * 电梯类型名称
	 */
	private String elevatorTypeName;
	/**
	 * 项目地点
	 */
	private String addressName;

	//----电梯细节----
	/**
	 * 电梯细节 集合
	 */
	private List<ElementParam> elementParams;
	/**
	 * 功能定制
	 */
	private List<Options> projectOptions;
}
