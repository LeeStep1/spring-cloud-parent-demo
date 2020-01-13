package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.ElevatorTypeService;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.miniapp.bean.ElevatorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


/**
 * 电梯系列基础信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-30 08:47:10
 */
@RestController
@RequestMapping("/manager/elevatortype")
public class ElevatorTypeController {
    @Autowired
    private ElevatorTypeService elevatorTypeService;



	/**
	 * 新增数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody ElevatorType elevatorType){
		return elevatorTypeService.add(elevatorType);
    }




	/**
	 * 编辑数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody ElevatorType elevatorType){
        return elevatorTypeService.update(elevatorType);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
		return elevatorTypeService.delete(id);
    }

	/**
	 * 电梯类型
	 * @return
	 */
	@PostMapping("/categoryList")
    public BaseVo categoryList(){
		return elevatorTypeService.categoryList();
	}

	/**
	 * 电梯类型参数验重
	 * @param elevatorType
	 * @return
	 */
	@PostMapping("/distinctParams")
	public BaseVo distinctParamsKey(@RequestBody ElevatorType elevatorType){
		return elevatorTypeService.distinctParams(elevatorType);
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
		return elevatorTypeService.reflectById(id);
    }


	/**
	 * 电梯类型列表查询
	 * @return
	 */
	@PostMapping("/elevatorTypeListPage")
	public BaseVo elevatorTypeListPage(@RequestBody ElevatorTypePageVO elevatorTypePageVO){
		return elevatorTypeService.elevatorTypeListPage(elevatorTypePageVO);
	}

	/**
	 * 电梯类型全查
	 * @return
	 */
	@PostMapping("/findAll")
	public BaseVo findAll(){
		return elevatorTypeService.findAll();
	}

}
