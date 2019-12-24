package com.bit.module.miniapp.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.service.*;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.miniapp.service.WxElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

	@Autowired
	private WxElevatorService wxElevatorService;
	@Autowired
	private ProjectService projectService;


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
	 * 电梯类型列表查询
	 * @return
	 */
	@PostMapping("/elevatorTypeListPage")
	public BaseVo elevatorTypeListPage(@RequestBody ElevatorTypeVO elevatorTypeVO){
		return elevatorTypeService.elevatorTypeListPage(elevatorTypeVO);
	}

	/**
	 * 根据电梯类型查询电梯的基础信息填写模板
	 * @param eletypeId
	 * @return
	 */
	@GetMapping("/getElevatorBaseElement/{eletypeId}")
	public BaseVo getElevatorBaseElement(@PathVariable(value = "eletypeId")Long eletypeId){
		ElevatorBaseElement elevatorBaseElement=new ElevatorBaseElement();
		elevatorBaseElement.setElevatorTypeId(eletypeId);
		return elevatorBaseElementService.findAllByElevator(elevatorBaseElement);
	}


	/**
	 * 根据电梯类型id 订单以及相关可选项类别查询电梯的基础信息填写模板
	 * @param optionType
	 * @param elevatorTypeId
	 * @param orderId
	 * @return
	 */
	@GetMapping("/options/{orderId}/{elevatorTypeId}/{optionType}")
	public BaseVo<List<Options>> getElevatorOption(@PathVariable(value = "elevatorTypeId")Long elevatorTypeId, @PathVariable(value = "orderId")Long orderId, @PathVariable(value = "optionType")Integer optionType){
		BaseVo rs=new BaseVo();
		rs.setData(wxElevatorService.getOptions(optionType,elevatorTypeId,orderId));
		return  rs;
	}

	/**
	 * 新建项目
	 * @param project
	 * @return
	 */

	@PostMapping("/project")
	public BaseVo<Project> getElevator(@RequestBody Project project){
		projectService.add(project);
		BaseVo a=new BaseVo();
		a.setData(project);
		return a;
	}

}
