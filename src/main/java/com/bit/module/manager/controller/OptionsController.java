package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.OptionsService;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.OptionsPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 选项以及非标项
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 16:44:24
 */
@RestController
@RequestMapping("manager/options")
public class OptionsController {
    @Autowired
    private OptionsService optionsService;



	/**
	 * 新增数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody Options options){
        return optionsService.add(options);
    }




	/**
	 * 编辑数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody Options options){
        return optionsService.update(options);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
        return optionsService.delete(id);
    }


	/**
	 * 多参数查询数据
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 * @return List<Options>
	 */
	@PostMapping("/findByParam")
	public BaseVo findByParam(Options options){
		return optionsService.findByParam(options);
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ${entity}
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo reflectById(@PathVariable(value = "id") Long id){
		return optionsService.reflectById(id);
    }

	/**
	 * 分页查询
	 * @param optionsPageVO
	 * @return
	 */
    @PostMapping("/listPage")
	public BaseVo listPage(@RequestBody OptionsPageVO optionsPageVO){
		return optionsService.listPage(optionsPageVO);
	}


	/**
	 * 树形结构
	 * @return
	 */
	@PostMapping("/treeAll")
	public BaseVo treeAll() throws Exception {
		return optionsService.treeAll();
	}
}
