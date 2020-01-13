package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.vo.BaseVo;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.equation.dao.BasePriceEquationDao;
import com.bit.module.equation.dao.BasePriceEquationRelDao;
import com.bit.module.manager.dao.QueryParamsDao;
import com.bit.module.manager.vo.BasePriceEquationVO;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.manager.vo.BasePriceEquationPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bit.module.manager.service.BasePriceEquationService;

import java.util.List;

@Service("basePriceEquationService")
public class BasePriceEquationServiceImpl implements BasePriceEquationService {

	@Autowired
	private BasePriceEquationDao basePriceEquationDao;

	@Autowired
	private BasePriceEquationRelDao basePriceEquationRelDao;

	@Autowired
	private QueryParamsDao queryParamsDao;

	/**
	 * 查询电梯的属性 载重 速度 层站 提升高度
	 * @param basePriceEquationRel
	 * @return
	 */
	@Override
	public BaseVo findBasePriceEquationRelByParam(BasePriceEquationRel basePriceEquationRel) {
		List<BasePriceEquationRel> byParam = basePriceEquationRelDao.findByParam(basePriceEquationRel);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}

	/**
	 * 电梯各属性值的查询
	 * @param queryParams
	 * @return
	 */
	@Override
	public BaseVo findQueryParamsByParam(QueryParams queryParams) {
		List<QueryParams> byParam = queryParamsDao.getDetailParam(queryParams);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}

	/**
	 * 列表查询
	 * @param basePriceEquationPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(BasePriceEquationPageVO basePriceEquationPageVO) {
		Page<BasePriceEquationVO> page = new Page<>(basePriceEquationPageVO.getPageNum(), basePriceEquationPageVO.getPageSize());
		IPage<BasePriceEquationVO> basePriceEquationVOIPage = basePriceEquationDao.listPage(page, basePriceEquationPageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(basePriceEquationVOIPage);
		return baseVo;
	}
}