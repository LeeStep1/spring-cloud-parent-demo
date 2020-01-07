package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ElementParam;
import com.bit.module.manager.bean.ProjectEleOrder;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class ElevatorOrderVo extends ProjectEleOrder {
    /**
     * 运算用key
     */
    private String params_key;
    /**
     * 1:客梯,2:货梯，3：扶梯
     */
    private Integer type;
    /**
     * 图片地址
     */
    private String picture;

	/**
	 * 电梯的参数
	 */
	private List<ElementParam> params;

}
