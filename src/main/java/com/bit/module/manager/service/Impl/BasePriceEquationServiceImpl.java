package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.wxenum.ResultCode;
import com.bit.module.equation.bean.BasePriceEquation;
import com.bit.module.equation.bean.BasePriceEquationRel;
import com.bit.module.equation.dao.BasePriceEquationDao;
import com.bit.module.equation.dao.BasePriceEquationRelDao;
import com.bit.module.manager.dao.QueryParamsDao;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.manager.vo.BasePriceEquationVO;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.module.manager.vo.BasePriceEquationPageVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bit.module.manager.service.BasePriceEquationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("basePriceEquationService")
public class BasePriceEquationServiceImpl extends BaseService implements BasePriceEquationService {

	@Autowired
	private BasePriceEquationDao basePriceEquationDao;

	@Autowired
	private BasePriceEquationRelDao basePriceEquationRelDao;

	@Autowired
	private QueryParamsDao queryParamsDao;

	@Autowired
	private QueryParamsService queryParamsService;

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
		BaseVo<QueryParams> eleParamTree = queryParamsService.getEleParamTree(queryParams);
		return eleParamTree;
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

	/**
	 * 更新数据
	 * @param basePriceEquation
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo update(BasePriceEquation basePriceEquation) {

		basePriceEquationDao.updateBasePriceEquation(basePriceEquation);
		return successVo();
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		basePriceEquationDao.delBasePriceEquationById(id);
		return successVo();
	}

	/**
	 * 参数验重
	 * @param basePriceEquation
	 * @return
	 */
	@Override
	public BaseVo distinctParam(BasePriceEquation basePriceEquation) {
		BaseVo baseVo = new BaseVo();
		List<BasePriceEquation> byParam = basePriceEquationDao.findByParam(basePriceEquation);
		if (CollectionUtils.isNotEmpty(byParam)){
			baseVo.setCode(ResultCode.PARAMS_KEY_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_EXIST.getInfo());
		}else {
			baseVo.setCode(ResultCode.PARAMS_KEY_NOT_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_NOT_EXIST.getInfo());
		}
		return baseVo;
	}


	/**
	 * 新增数据
	 * @param basePriceEquation
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo add(BasePriceEquation basePriceEquation) {
		BasePriceEquation param = new BasePriceEquation();
		BeanUtils.copyProperties(basePriceEquation,param);
		param.setOutput(null);
		param.setCostPrice(null);
		List<BasePriceEquation> byParam = basePriceEquationDao.findByParam(param);
		if (CollectionUtils.isNotEmpty(byParam)){
			throw new BusinessException("参数相同不能新增");
		}

		basePriceEquationDao.addBasePriceEquation(basePriceEquation);

		return successVo();
	}

	/**
	 * 返显数据
	 * @param id
	 * @return
	 */
	@Override
	public BaseVo reflectById(Long id) {
		BasePriceEquation basePriceEquationById = basePriceEquationDao.getBasePriceEquationById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(basePriceEquationById);
		return baseVo;
	}
	/**
	 * 确定表头 - 价格类别 电梯类型
	 * @param basePriceEquationRel
	 * @return
	 */
	@Override
	public BaseVo determineHeader(BasePriceEquationRel basePriceEquationRel) {
		List<BasePriceEquationRel> byParam = basePriceEquationRelDao.findByParam(basePriceEquationRel);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}
}