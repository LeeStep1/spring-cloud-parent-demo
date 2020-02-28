package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ProjectEleOrder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 测试用的Vo将2个对象拼在一个VO
 * @Description
 * @Author mf
 **/
@Data
public class TestPoljectEleOrderAndMapVO {
	private List<ProjectEleOrder> rate;
	private Map map;
	private Long priceId;
}
