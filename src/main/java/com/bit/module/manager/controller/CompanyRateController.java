package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.CompanyRate;
import com.bit.module.manager.vo.CompanyRatePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.module.manager.service.CompanyRateService;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 14:04:17
 */
@RestController
@RequestMapping("manager/companyRate")
public class CompanyRateController {
    @Autowired
    private CompanyRateService companyRateService;



	/**
	 * 新增数据
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	@PostMapping("/add")
	public BaseVo add(@RequestBody CompanyRate companyRate){
        return companyRateService.add(companyRate);
    }




	/**
	 * 编辑数据
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	@PutMapping("/update")
	public BaseVo update(@RequestBody CompanyRate companyRate){
        return companyRateService.update(companyRate);
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public BaseVo deleteById(@PathVariable(value = "id") Long id){
		return companyRateService.delete(id);
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ${entity}
	 */
	@GetMapping("/reflectById/{id}")
	public BaseVo<CompanyRate> reflectById(@PathVariable(value = "id") Long id){
		return companyRateService.reflectById(id);
    }

	/**
	 * 分页查询
	 * @param ratePageVO
	 * @return
	 */
    @PostMapping("/listPage")
    public BaseVo listPage(@RequestBody CompanyRatePageVO ratePageVO){
		return companyRateService.listPage(ratePageVO);
	}


}
