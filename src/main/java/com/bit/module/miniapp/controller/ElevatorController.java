package com.bit.module.miniapp.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.service.ElevatorBaseElementService;
import com.bit.module.manager.service.ElevatorService;
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
	@Autowired
	private ElevatorService elevatorService;
	@Autowired
	private ElevatorBaseElementService elevatorBaseElementService;




	/**
	 * 单查电梯类型
	 * @param typeId
	 * @return
	 */
	@GetMapping("/getElevatorType/{typeId}")
	public BaseVo getElevator(@PathVariable(value = "typeId")Long typeId){
		return elevatorTypeService.reflectById(typeId);
	}



	/**
	 * 根据电梯的key查询params的level1数据
	 *
	 * @param queryParams
	 * @return List<QueryParams>
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/getEleParamLevelOne")
	public BaseVo getEleParamLevelOne(@RequestBody QueryParams queryParams) {
		return queryParamsService.getEleParamLevelOne(queryParams);
	}

	/**
	 * 查询电梯的参数
	 * @param queryParams
	 * @return List<QueryParams>
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/getEleParam")
	public BaseVo getEleParams(@RequestBody QueryParams queryParams) {
		return queryParamsService.getEleParam(queryParams);
	}


	/**
	 * 根据电梯类型查询电梯的可选项
	 * @param typeId
	 * @return
	 */
	@GetMapping("/getEleOptions/{typeId}")
	public BaseVo getEleOptions(@PathVariable(value = "typeId")Long typeId){
		return elevatorService.getEleOptions(typeId);
	}


	/**
	 * 根据电梯类型查询电梯的可选项
	 * @param eletypeId
	 * @return
	 */
	@GetMapping("/getElevatorBaseElement/{eletypeId}")
	public BaseVo getElevatorBaseElement(@PathVariable(value = "eletypeId")Long eletypeId){
		ElevatorBaseElement elevatorBaseElement=new ElevatorBaseElement();
		elevatorBaseElement.setElevatorTypeId(eletypeId);
		return elevatorBaseElementService.findAllByElevator(elevatorBaseElement);
	}





}
