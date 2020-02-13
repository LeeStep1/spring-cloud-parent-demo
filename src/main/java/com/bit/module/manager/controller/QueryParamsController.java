package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.manager.vo.QueryParamsPageVO;
import com.bit.module.miniapp.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/queryParmas")
public class QueryParamsController {

	@Autowired
	private QueryParamsService queryParamsService;



	/**
	 * 新增数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody QueryParams queryParams){
		return queryParamsService.add(queryParams);
	}




	/**
	 * 编辑数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody QueryParams queryParams){
		return queryParamsService.update(queryParams);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") String id){
		return queryParamsService.delete(id);
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ${entity}
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo reflectById(@PathVariable(value = "id") String id){
		return queryParamsService.reflectById(id);
	}

	/**
	 * 分页查询
	 * @param queryParamsPageVO
	 * @return
	 */
	@PostMapping("/listPage")
	public BaseVo listPage(@RequestBody QueryParamsPageVO queryParamsPageVO){
		return queryParamsService.listPage(queryParamsPageVO);
	}

	/**
	 * 不同的key
	 * @return
	 */
	@PostMapping("/distinctKey")
	public BaseVo distinctKey(){
		return queryParamsService.distinctKey();
	}

	/**
	 * 查询下一级数据
	 * @param queryParams
	 * @return
	 */
	@PostMapping("/findNextLevel")
	public BaseVo findNextLevel(@RequestBody QueryParams queryParams){
		return queryParamsService.findNextLevel(queryParams);
	}
}
