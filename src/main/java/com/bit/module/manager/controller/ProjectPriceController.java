package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ProjectPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.service.ProjectPriceService;
import org.springframework.web.bind.annotation.*;


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
	 * 编辑数据
	 * @param projectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/update")
	public BaseVo update(@RequestBody ProjectPrice projectPrice){
        return projectPriceService.update(projectPrice);
	}



	/**
	 * 单查项目数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ${entity}
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo reflectById(@PathVariable(value = "id") Long id){
		return projectPriceService.reflectById(id);
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
