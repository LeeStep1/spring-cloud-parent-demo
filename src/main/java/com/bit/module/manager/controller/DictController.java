package com.bit.module.manager.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.bean.Dict;
import com.bit.module.manager.service.DictService;
import org.springframework.web.bind.annotation.*;
import com.bit.base.vo.BaseVo;


/**
 * 字典表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-14 09:06:31
 */
@RestController
@RequestMapping("manager/dict")
public class DictController {
    @Autowired
    private DictService dictService;



	/**
	 * 新增数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody Dict dict){
        return dictService.add(dict);
    }




	/**
	 * 编辑数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody Dict dict){
        return dictService.update(dict);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
        return dictService.delete(id);
    }


	/**
	 * 多参数查询数据
	 * @param dict
	 * @author chenduo
	 * @since ${date}
	 * @return List<Dict>
	 */
	@PostMapping("/findByModule")
	public BaseVo findByModule(Dict dict){
		return dictService.findByModule(dict);
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
		return dictService.reflectById(id);
    }


}
