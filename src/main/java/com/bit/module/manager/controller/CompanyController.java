package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Company;
import com.bit.module.manager.service.CompanyService;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:37
 */
@RestController
@RequestMapping("/manager/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

	/**
	 * 全部公司
	 * @return
	 */
    @GetMapping(value = "/allCompany")
    public BaseVo allCompany(){

        return companyService.getCompany();
    }

	/**
	 * 公司树
	 * @return
	 */
    @PostMapping("/companyTree")
    public BaseVo companyTree(@RequestBody Company company){
		return companyService.companyTree(company);
    }

	/**
	 * 新增公司
	 * @return
	 */
    @PostMapping("/add")
    public BaseVo addCompany(@RequestBody Company company){
		return companyService.addCompany(company);
	}

	/**
	 * 更新公司
	 * @param company
	 * @return
	 */
	@PutMapping("/update")
	public BaseVo updateCompany(@RequestBody Company company){
    	return companyService.updateCompany(company);
	}

	/**
	 * 删除公司
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delCompany/{id}")
	public BaseVo delCompany(@PathVariable(value = "id")Long id){
		return companyService.delCompany(id);
	}

	/**
	 * 单查公司
	 * @param id
	 * @return
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo reflectById(@PathVariable(value = "id")Long id){
		return companyService.reflectById(id);
	}
}
