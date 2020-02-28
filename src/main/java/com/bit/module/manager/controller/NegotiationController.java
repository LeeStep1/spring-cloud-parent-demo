package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.NegotiationService;
import com.bit.module.manager.vo.ProjectPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 洽谈项目
 * @Description
 * @Author chenduo
 * @Date 2020/2/27 8:44
 **/
@RestController
@RequestMapping("/manager/negotiation")
public class NegotiationController {

	@Autowired
	private NegotiationService negotiationService;
	/**
	 * 洽谈项目分页查询
	 * @param projectPageVO
	 * @return
	 */
	@PostMapping("/negotiationlistPage")
	public BaseVo negotiationlistPage(@RequestBody ProjectPageVO projectPageVO){
		return negotiationService.negotiationlistPage(projectPageVO);
	}

	/**
	 * 返显项目
	 * @param projectId
	 * @return
	 */
	@GetMapping("/reflectById/{projectId}/{enquireTimes}")
	public BaseVo reflectById(@PathVariable(value = "projectId")Long projectId,@PathVariable(value = "enquireTimes")Integer enquireTimes){
		return negotiationService.reflectById(projectId, enquireTimes);
	}
}