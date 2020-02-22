package com.bit.module.manager.controller;

import com.bit.module.manager.vo.ElevatorBaseElementPageVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.service.ElevatorBaseElementService;
import org.springframework.web.bind.annotation.*;
import com.bit.base.vo.BaseVo;


/**
 * 基础信息填写模板数据信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-21 10:46:23
 */
@RestController
@RequestMapping("manager/elevatorBaseElement")
public class ElevatorBaseElementController {
    @Autowired
    private ElevatorBaseElementService elevatorBaseElementService;



	/**
	 * 新增数据
	 * @param elevatorBaseElement
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody ElevatorBaseElement elevatorBaseElement){
        return elevatorBaseElementService.add(elevatorBaseElement);
    }




	/**
	 * 编辑数据
	 * @param elevatorBaseElement
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody ElevatorBaseElement elevatorBaseElement){
        return elevatorBaseElementService.update(elevatorBaseElement);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
        return elevatorBaseElementService.delete(id);
    }


	/**
	 * 多参数查询数据
	 * @param elevatorBaseElement
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorBaseElement>
	 */
	@PostMapping("/findByParam")
	public BaseVo findByParam(ElevatorBaseElement elevatorBaseElement){
		return elevatorBaseElementService.findByParam(elevatorBaseElement);
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
		return elevatorBaseElementService.reflectById(id);
    }

	/**
	 * 分页查询
	 * @param elevatorBaseElementPageVO
	 * @return
	 */
    @PostMapping("/listPage")
    public BaseVo listPage(@RequestBody ElevatorBaseElementPageVO elevatorBaseElementPageVO){
		return elevatorBaseElementService.listPage(elevatorBaseElementPageVO);
	}
}
