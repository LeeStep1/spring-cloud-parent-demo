package com.bit.module.manager.controller;

import com.bit.module.equation.bean.Equation;
import com.bit.module.manager.service.EquationManagerService;
import com.bit.module.manager.vo.EquationPageVO;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.bit.base.vo.BaseVo;


/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-02-12 14:38:14
 */
@RestController
@RequestMapping("manager/equation")
public class EquationController {
    @Autowired
    private EquationManagerService equationManagerService;



	/**
	 * 新增数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody Equation equation){
        return equationManagerService.add(equation);
    }




	/**
	 * 编辑数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody Equation equation){
        return equationManagerService.update(equation);
	}




	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@DeleteMapping("/delete/{id}")
	public BaseVo delete(@PathVariable(value = "id") Long id){
        return equationManagerService.delete(id);
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
		return equationManagerService.reflectById(id);
    }

	/**
	 * 分页查询
	 * @param equationPageVO
	 * @return
	 */
	@PostMapping("/listPage")
    public BaseVo listPage(@RequestBody EquationPageVO equationPageVO){
		return equationManagerService.listPage(equationPageVO);
	}
}
