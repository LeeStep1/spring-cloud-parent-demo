package com.bit.module.miniapp.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.ElevatorTypeService;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.miniapp.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:04:01
 */
@RestController
@RequestMapping("wx/elevator")
public class ElevatorController {
	@Autowired
	private QueryParamsService queryParamsService;
	@Autowired
	private ElevatorTypeService elevatorTypeService;


	/**
	 * 单查电梯类型
	 * @param id
	 * @return
	 */
	@GetMapping("/getElevatorType/{id}")
	public BaseVo getElevator(@PathVariable(value = "id")Long id){
		return elevatorTypeService.reflectById(id);
	}



	/**
	 * 查询电梯参数
	 *
	 * @param queryParams
	 * @return List<QueryParams>
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/getEleParams")
	public BaseVo getEleParams(@RequestBody QueryParams queryParams) {
		return queryParamsService.getEleParams(queryParams);
	}




}
