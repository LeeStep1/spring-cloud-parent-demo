package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.ProjectEleNonstandardVO;
import com.bit.module.manager.vo.ProjectPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.service.ProjectPriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 项目报价信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-06 09:15:45
 */
@RestController
@RequestMapping("manager/projectprice")
public class ProjectPriceController {
    @Autowired
    private ProjectPriceService projectPriceService;



	/**
	 * 批量编辑数据
	 * @param projectPrices
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody List<ProjectEleNonstandardVO> projectPrices){
        return projectPriceService.update(projectPrices);
	}

	/**
	 * 项目下订单列表
	 * @param projectId
	 * @return
	 */
	@GetMapping("/orderList/{projectId}")
	public BaseVo orderList(@PathVariable(value = "projectId") Long projectId){
		return projectPriceService.orderList(projectId);
	}


	/**
	 * 单查项目数据
	 * @param orderId
	 * @author chenduo
	 * @since ${date}
	 * @return ${entity}
	 */
	@GetMapping("/reflectById/{orderId}")
	public BaseVo reflectById(@PathVariable(value = "orderId") Long orderId){
		return projectPriceService.reflectById(orderId);
    }

	/**
	 * 报价列表分页查询
	 * @param projectPageVO
	 * @return
	 */
    @PostMapping("/listPage")
	public BaseVo listPage(@RequestBody ProjectPageVO projectPageVO){
		return projectPriceService.listPage(projectPageVO);
	}


}
