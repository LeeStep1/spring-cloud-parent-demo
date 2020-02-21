package com.bit.module.manager.controller;

import com.bit.module.miniapp.bean.ElevatorRelaOptions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.service.ElevatorRelaOptionsService;
import org.springframework.web.bind.annotation.*;
import com.bit.base.vo.BaseVo;


/**
 * 电梯类型与可选项关联表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-21 10:34:30
 */
@RestController
@RequestMapping("module.manager/elevatorrelaoptions")
public class ElevatorRelaOptionsController {
    @Autowired
    private ElevatorRelaOptionsService elevatorRelaOptionsService;



	/**
	 * 新增数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody ElevatorRelaOptions elevatorRelaOptions){
        return elevatorRelaOptionsService.add(elevatorRelaOptions);
    }




	/**
	 * 编辑数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody ElevatorRelaOptions elevatorRelaOptions){
        return elevatorRelaOptionsService.update(elevatorRelaOptions);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
        return elevatorRelaOptionsService.delete(id);
    }


	/**
	 * 多参数查询数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorRelaOptions>
	 */
	@PostMapping("/findByParam")
	public BaseVo findByParam(ElevatorRelaOptions elevatorRelaOptions){
		return elevatorRelaOptionsService.findByParam(elevatorRelaOptions);
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
		return elevatorRelaOptionsService.reflectById(id);
    }


}
