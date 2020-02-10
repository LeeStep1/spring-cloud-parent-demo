package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.InstallParams;
import com.bit.module.manager.service.InstallParamsService;
import com.bit.module.manager.vo.InstallParamsPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author chenduo
 * @email ${email}
 * @date 2020-02-10 11:28:40
 */
@RestController
@RequestMapping("manager/installparams")
public class InstallParamsController {
	@Autowired
	private InstallParamsService installParamsService;


	/**
	 * 新增数据
	 *
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody InstallParams installParams) {
		return installParamsService.add(installParams);
	}


	/**
	 * 编辑数据
	 *
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody InstallParams installParams) {
		return installParamsService.update(installParams);
	}


	/**
	 * 删除数据
	 *
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id) {
		return installParamsService.delete(id);
	}


	/**
	 * 多参数查询数据
	 *
	 * @param installParams
	 * @return List<InstallParams>
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/findByParam")
	public BaseVo findByParam(InstallParams installParams) {
		return installParamsService.findByParam(installParams);
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return ${entity}
	 * @author chenduo
	 * @since ${date}
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo reflectById(@PathVariable(value = "id") Long id) {
		return installParamsService.reflectById(id);
	}

	/**
	 * 分页查询
	 * @param installParamsPageVO
	 * @return
	 */
	@PostMapping("/listPage")
	public BaseVo listPage(@RequestBody InstallParamsPageVO installParamsPageVO){
		return installParamsService.listPage(installParamsPageVO);
	}
}
