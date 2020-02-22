package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Params;
import com.bit.module.manager.service.ParamsService;
import com.bit.module.manager.vo.ParamsPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;



/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-30 09:49:11
 */
@RestController
@RequestMapping("/manager/params")
public class ParamsController {
    @Autowired
    private ParamsService paramsService;



	/**
	 * 新增数据
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody Params params){
        return paramsService.add(params);
    }




	/**
	 * 编辑数据
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody Params params){
		return paramsService.update(params);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
		return paramsService.delete(id);
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
		return paramsService.reflectById(id);
    }

	/**
	 * 参数验重
	 * @param params
	 * @return
	 */
    @PostMapping("/distinctParams")
    public BaseVo distinctParams(@RequestBody Params params){
		return paramsService.distinctParams(params);
	}



	/**
	 * 分页查询数据
	 * @param paramsPageVO
	 * @author chenduo
	 * @since ${date}
	 * @return PageInfo
	 */
	@PostMapping("/paramsListPage")
	public BaseVo paramsListPage(@RequestBody ParamsPageVO paramsPageVO) {
		return paramsService.paramsListPage(paramsPageVO);
	}

	/**
	 * 全查
	 * @return
	 */
	@PostMapping("/findAll")
	public BaseVo findAll(){
		return paramsService.findAll();
	}
}
