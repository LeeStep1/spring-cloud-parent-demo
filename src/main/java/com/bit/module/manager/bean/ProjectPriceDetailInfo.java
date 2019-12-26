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
	 * 电梯类型名称和产品单价
	 */
	private ElevatorTypeNameAndUnitPrice elevatorTypeNameAndUnitPrice;

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

}
