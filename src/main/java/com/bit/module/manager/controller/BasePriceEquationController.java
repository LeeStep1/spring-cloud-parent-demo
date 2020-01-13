package com.bit.module.manager.controller;


import com.bit.base.vo.BaseVo;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.manager.service.BasePriceEquationService;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.manager.vo.BasePriceEquationPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-10 16:38:19
 */
@RestController
@RequestMapping("manager/basePriceEquation")
public class BasePriceEquationController {

	@Autowired
	private BasePriceEquationService basePriceEquationService;

	/**
	 * 查询电梯的属性 载重 速度 层站 提升高度
	 * @param basePriceEquationRel
	 * @return
	 */
	@PostMapping("/findBasePriceEquationRelByParam")
	public BaseVo findBasePriceEquationRelByParam(@RequestBody BasePriceEquationRel basePriceEquationRel){
		return basePriceEquationService.findBasePriceEquationRelByParam(basePriceEquationRel);
	}

	/**
	 * 电梯各属性值的查询
	 * @param queryParams
	 * @return
	 */
	@PostMapping("/findQueryParamsByParam")
	public BaseVo findQueryParamsByParam(@RequestBody QueryParams queryParams){
		return basePriceEquationService.findQueryParamsByParam(queryParams);
	}

	/**
	 * 列表查询
	 * @param basePriceEquationPageVO
	 * @return
	 */
	@PostMapping("/listPage")
	public BaseVo listPage(@RequestBody BasePriceEquationPageVO basePriceEquationPageVO){
		return basePriceEquationService.listPage(basePriceEquationPageVO);
	}


}
