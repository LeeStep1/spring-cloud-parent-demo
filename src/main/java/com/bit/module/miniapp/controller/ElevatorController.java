package com.bit.module.miniapp.controller;

import com.bit.base.vo.BasePageVo;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import com.bit.module.manager.service.*;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ProjectVo;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.miniapp.service.WxElevatorService;
import com.bit.module.miniapp.vo.ReportInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email
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
	@GetMapping("/eleOptions/{typeId}")
	public BaseVo getEleOptions(@PathVariable(value = "typeId")Long typeId){
		return elevatorService.getEleOptions(typeId);
	}

	/**
	 * 电梯类型列表查询
	 * @return
	 */
	@PostMapping("/elevatorTypeListPage")
	public BaseVo elevatorTypeListPage(@RequestBody ElevatorTypePageVO elevatorTypePageVO){
		return elevatorTypeService.elevatorTypeListPage(elevatorTypePageVO);
	}

	/**
	 * 根据电梯类型查询电梯的基础信息填写模板
	 * @param eletypeId
	 * @return
	 */
	@GetMapping("/elevatorBaseElement/{eletypeId}")
	public BaseVo getElevatorBaseElement(@PathVariable(value = "eletypeId")Long eletypeId){
		ElevatorBaseElement elevatorBaseElement=new ElevatorBaseElement();
		elevatorBaseElement.setElevatorTypeId(eletypeId);
		return elevatorBaseElementService.findAllByElevator(elevatorBaseElement);
	}


	/**
	 * 根据电梯类型id 订单以及相关可选项类别查询电梯的基础信息填写模板
	 * @param optionType
	 * @param elevatorTypeId
	 * @param baseInfo
	 * @return
	 */
	@PostMapping("/options/{elevatorTypeId}/{optionType}")
	public BaseVo<List<Options>> getElevatorOption(@PathVariable(value = "elevatorTypeId")Long elevatorTypeId,  @PathVariable(value = "optionType")Integer optionType,
												   @RequestBody List<ProjectEleOrderBaseInfo> baseInfo){
		BaseVo rs=new BaseVo();
		rs.setData(wxElevatorService.getOptions(optionType,elevatorTypeId,baseInfo));
		return  rs;
	}

	/**
	 * 新建项目
	 * @param project
	 * @return
	 */
	@PostMapping("/project")
	public BaseVo<Project> addProject(@RequestBody Project project){
		projectService.add(project);
		BaseVo a=new BaseVo();
		a.setData(project);
		return a;
	}
	/**
	 * 新建项目下的电梯 算钱
	 * @param vo  返回项目
	 * @return
	 */
	@PostMapping("/project/elevator/order")
	public BaseVo<Project> wxAddReportInfo(@RequestBody ReportInfoVO vo){
		BaseVo a=new BaseVo();
		a.setData(wxElevatorService.wxAddReportInfo(vo));
		return a;
	}


	/**
	 *我的项目一级页面
	 * @param vo  分页组件 pageNum pageSize
	 * @return
	 */
	@PostMapping("/user/project")
	public BaseVo<Project> queryUserProject(@RequestBody BasePageVo vo){
		return projectService.queryProject(vo);
	}


	/**
	 *我的项目二级级页面  一个项目对应多个历史版本，以及下的电梯订单数据
	 * g
	 * @param projectId  项目ID
	 * @return
	 */
	@PostMapping("/user/project/price/{projectId}")
	public BaseVo<ProjectVo> queryUserProjectPrice(@PathVariable(value = "projectId")Long projectId){
		BaseVo a=new BaseVo();
		a.setData(projectService.queryProjectPri(projectId));
		return a;
	}

	/**
	 * 查询项目报价详情
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	@GetMapping("/getProjectDetail/{projectId}/{projectPriceId}")
	public BaseVo getProjectDetail(@PathVariable(value = "projectId")Long projectId,@PathVariable(value = "projectPriceId")Long projectPriceId){
		return projectService.getProjectDetail(projectId, projectPriceId);
	}

	/*
   * 计算按钮动作
   * @param projectId  项目ID
   * @return 返回总价和各个订单的价各
   */
	@PostMapping("/poject/test/{projectId}")
	public BaseVo<Map> pojectPriceTest(@PathVariable(value = "projectId")Long projectId){
		BaseVo<Map>rs=new BaseVo<>();
		rs.setData(wxElevatorService.pojectPriceTest(projectId));
		return rs;
	}

	/**
	 * 草稿转正是版本
	 * @param projectId  项目ID
	 * @return 成功与失败
	 */
	@PostMapping("/poject/version/{projectId}")
	public BaseVo proPriceToVersion(@PathVariable(value = "projectId")Long projectId){

		return wxElevatorService.proPriceToVersion(projectId);

	}

	/**
	 * 修改项目报价
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	@GetMapping("/updateProjectPrice/{projectId}/{projectPriceId}")
	public BaseVo updateProjectPrice(@PathVariable(value = "projectId")Long projectId,@PathVariable(value = "projectPriceId")Long projectPriceId){
		return wxElevatorService.updateProjectPrice(projectId, projectPriceId);
	}

}
