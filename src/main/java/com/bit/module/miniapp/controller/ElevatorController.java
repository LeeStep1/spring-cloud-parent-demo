package com.bit.module.miniapp.controller;

import com.bit.base.vo.BasePageVo;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.service.*;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ProjectPageVO;
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
	 *
	 * @param typeId
	 * @return
	 */
	@GetMapping("/getElevatorType/{typeId}")
	public BaseVo getElevator(@PathVariable(value = "typeId") Long typeId) {
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
	 *
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
	 * 查询电梯的参数 树形结构
	 *
	 * @param queryParams
	 * @return
	 */
	@PostMapping("/getEleParamTree")
	public BaseVo<QueryParams> getEleParamTree(@RequestBody QueryParams queryParams) {
		return queryParamsService.getEleParamTree(queryParams);
	}


	/**
	 * 根据电梯类型查询电梯的可选项
	 *
	 * @param typeId
	 * @return
	 */
	@GetMapping("/eleOptions/{typeId}")
	public BaseVo getEleOptions(@PathVariable(value = "typeId") Long typeId) {
		return elevatorService.getEleOptions(typeId);
	}

	/**
	 * 电梯类型列表查询
	 *
	 * @return
	 */
	@PostMapping("/elevatorTypeListPage")
	public BaseVo elevatorTypeListPage(@RequestBody ElevatorTypePageVO elevatorTypePageVO) {
		return elevatorTypeService.elevatorTypeListPage(elevatorTypePageVO);
	}

	/**
	 * 根据电梯类型查询电梯的基础信息填写模板
	 *
	 * @param eletypeId
	 * @return
	 */
	@GetMapping("/elevatorBaseElement/{eletypeId}")
	public BaseVo getElevatorBaseElement(@PathVariable(value = "eletypeId") Long eletypeId) {
		ElevatorBaseElement elevatorBaseElement = new ElevatorBaseElement();
		elevatorBaseElement.setElevatorTypeId(eletypeId);
		return elevatorBaseElementService.findAllByElevator(elevatorBaseElement);
	}


	/**
	 * 根据电梯类型id 订单以及相关可选项类别查询电梯的基础信息填写模板
	 *
	 * @param optionType
	 * @param elevatorTypeId
	 * @param baseInfo
	 * @return
	 */
	@PostMapping("/options/{elevatorTypeId}/{optionType}")
	public BaseVo<List<Options>> getElevatorOption(@PathVariable(value = "elevatorTypeId") Long elevatorTypeId, @PathVariable(value = "optionType") Integer optionType,
												   @RequestBody List<ProjectEleOrderBaseInfo> baseInfo) {
		BaseVo rs = new BaseVo();
		rs.setData(wxElevatorService.getOptions(optionType, elevatorTypeId, baseInfo));
		return rs;
	}
	
	/**
	 * 新建项目
	 *
	 * @param project
	 * @return
	 */
	@PostMapping("/project")
	public BaseVo<Project> addProject(@RequestBody Project project) {
		projectService.add(project);
		BaseVo a = new BaseVo();
		a.setData(project);
		return a;
	}

	/**
	 * 新建项目下的电梯 算钱
	 * 包括基础参数、选项、以及手动输入的非标项
	 * @param vo 返回项目
	 * @return
	 */
	@PostMapping("/project/elevator/order")
	public BaseVo<Project> wxAddReportInfo(@RequestBody ReportInfoVO vo) {
		BaseVo a = new BaseVo();
		a.setData(wxElevatorService.wxAddReportInfo(vo));
		return a;
	}


	/**
	 * 我的项目 一级页面
	 *
	 * @param vo 分页组件 pageNum pageSize
	 * @return
	 */
	@PostMapping("/user/project")
	public BaseVo<Project> queryUserProject(@RequestBody BasePageVo vo) {
		return projectService.queryProject(vo);
	}

	/**
	 * 历史项目 和 我的项目
	 * @param projectPageVO
	 * @return
	 */
	@PostMapping("/user/projectHistory")
	public BaseVo<Project> historyProject(@RequestBody ProjectPageVO projectPageVO){
		return projectService.historyProject(projectPageVO);
	}



	/**
	 * 我的项目二级级页面  一个项目对应多个历史版本，以及下的电梯订单数据
	 *
	 * @param projectId 项目ID
	 * @return
	 */
	@PostMapping("/user/project/price/{projectId}")
	public BaseVo<ProjectVo> queryUserProjectPrice(@PathVariable(value = "projectId") Long projectId) {
		BaseVo a = new BaseVo();
		a.setData(projectService.queryProjectPri(projectId));
		return a;
	}

	/**
	 * 查询项目报价详情
	 *
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	@GetMapping("/getProjectDetail/{projectId}/{projectPriceId}")
	public BaseVo getProjectDetail(@PathVariable(value = "projectId") Long projectId, @PathVariable(value = "projectPriceId") Long projectPriceId) {
		return projectService.getProjectDetail(projectId, projectPriceId);
	}

	/**
	 * 查询订单详情
	 *
	 * @param projectId
	 * @param orderId
	 * @return
	 */
	@GetMapping("/getOrderDetail/{projectId}/{orderId}")
	public BaseVo getOrderDetail(@PathVariable(value = "projectId") Long projectId, @PathVariable(value = "orderId") Long orderId) {
		return projectService.getOrderDetail(projectId, orderId);
	}


	/*
   * 计算按钮动作 输入定制信息页面的生成报价单按钮
   * @param projectId  项目ID
   * @return 返回总价和各个订单的价各
   */
/*	@PostMapping("/poject/test/{projectId}")
	public BaseVo<Map> pojectPriceTest(@PathVariable(value = "projectId") Long projectId) {
		BaseVo<Map> rs = new BaseVo<>();
		rs.setData(wxElevatorService.pojectPriceTest(projectId));
		return rs;
	}*/

	/**
	 * 草稿转正式版本生成报价单，标准的草稿转正式版本，非标的草稿转正式并进行非标技术支持预审核
	 *
	 * @param projectId 项目ID
	 * @return 成功与失败
	 */
	@PostMapping("/project/version/{projectId}")
	public BaseVo proPriceToVersion(@PathVariable(value = "projectId") Long projectId) {

		return wxElevatorService.proPriceToVersion(projectId);

	}
	/**
	 * 各类草稿转正式版本（保存正式版，是否发送非标审批）
	 *
	 * @param projectId 项目ID
	 * @param  operationType  操作类型：
	 * @return 成功与失败
	 */
/*	@PostMapping("/project/versionApply/{projectId}/{operationType}")
	public BaseVo proPriceToVersionMethod(@PathVariable(value = "projectId") Long projectId) {

		return wxElevatorService.proPriceToVersion(projectId);

	}*/
	/**
	 * 修改项目报价
	 *
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	@GetMapping("/updateProjectPrice/{projectId}/{projectPriceId}")
	public BaseVo updateProjectPrice(@PathVariable(value = "projectId") Long projectId, @PathVariable(value = "projectPriceId") Long projectPriceId) {
		return wxElevatorService.updateProjectPrice(projectId, projectPriceId);
	}


	/**
	 * 根据订单id删除订单
	 *
	 * @param orderId
	 * @return
	 */
	@GetMapping("/delOrderByOrderId/{orderId}")
	public BaseVo delOrderByOrderId(@PathVariable(value = "orderId") Long orderId) {
		return wxElevatorService.delOrderByOrderId(orderId);
	}

	/**
	 * 更新订单
	 *
	 * @param vo
	 * @return
	 */
	@PostMapping("/updateOrder")
	public BaseVo updateOrder(@RequestBody ReportInfoVO vo) {
		return wxElevatorService.updateOrder(vo);
	}

	/**
	 * 更新报价表的运输 和 安装 标识
	 *
	 * @param projectPrice
	 * @return
	 */
	@PostMapping("/updateProjectPriceFlag")
	public BaseVo updateProjectPriceFlag(@RequestBody ProjectPrice projectPrice) {
		return wxElevatorService.updateProjectPriceFlag(projectPrice);
	}

	/**
	 * 發送郵件  報價單
	 *
	 * @param projectPriceId 报价的id
	 * @return
	 */
	@PostMapping("/sendMail/{projectPriceId}")
	public BaseVo sendPriceMail(@PathVariable(value = "projectPriceId") Long projectPriceId,@RequestBody(required = false) List<String>ccAdress) {
		return	wxElevatorService.sendPriceMail(projectPriceId,ccAdress);


	}

	/**
	 * 判断下浮率
	 *
	 * @param elevatorRate
	 * @return
	 */
	@PostMapping("/judgeRate")
	public BaseVo judgeRate(@RequestBody ElevatorRate elevatorRate) {
		return wxElevatorService.judgeRate(elevatorRate);
	}


	/**
	 * 根据用户获取电梯类型下的下浮率
	 *
	 * @param elevatorTypeId  電梯的類型ID
	 * @return
	 */
	@GetMapping("/rate/{elevatorTypeId}")
	public BaseVo getRate(@PathVariable(value = "elevatorTypeId")Long elevatorTypeId) {

		BaseVo vo=new BaseVo();
		vo.setData(wxElevatorService.getRate(elevatorTypeId));
		return vo;
	}


	/**
	 * 非标报价取消接口
	 *
	 * @param elevatorPriceId  电梯版本报价的id
	 * @return
	 */
	@DeleteMapping("/nonstandardApply/{elevatorPriceId}")
	public BaseVo cancelApply(@PathVariable(value = "elevatorPriceId")Long elevatorPriceId) {
		return wxElevatorService.cancelApply(elevatorPriceId);
	}

}
