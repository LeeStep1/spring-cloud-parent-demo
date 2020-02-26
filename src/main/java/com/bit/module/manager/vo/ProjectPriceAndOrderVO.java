package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ProjectPrice;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/25 9:49
 **/
@Data
public class ProjectPriceAndOrderVO {
	private ProjectPrice projectPrice; //项目
	private List<Map> eleInputs; //项目下的所有产品
}
