package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.wxenum.ResultCode;
import com.bit.module.manager.bean.CompanyRate;
import com.bit.module.manager.vo.CompanyRatePageVO;
import com.bit.module.manager.vo.CompanyRateVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.bit.module.manager.dao.CompanyRateDao;
import com.bit.module.manager.service.CompanyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("companyRateService")
public class CompanyRateServiceImpl extends BaseService implements CompanyRateService {

	@Autowired
	private CompanyRateDao companyRateDao;


	/**
	 * 新增数据
	 *
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(CompanyRate companyRate) {
		companyRateDao.addCompanyRate(companyRate);
		return successVo();
	}

	/**
	 * 编辑数据
	 *
	 * @param companyRate
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(CompanyRate companyRate) {
		companyRateDao.updateCompanyRate(companyRate);
		return successVo();
	}

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		companyRateDao.delCompanyRateById(id);
		return successVo();
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return CompanyRate
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		BaseVo baseVo = new BaseVo();
		CompanyRate companyRate = companyRateDao.getCompanyRateById(id);
		baseVo.setData(companyRate);
		return baseVo;
	}

	/**
	 * 分页查询
	 * @param ratePageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(CompanyRatePageVO ratePageVO) {
		Page<CompanyRateVO> page = new Page<>(ratePageVO.getPageNum(), ratePageVO.getPageSize());
		IPage<CompanyRateVO> listPage = companyRateDao.listPage(page, ratePageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(listPage);
		return baseVo;
	}
	/**
	 * 多参数查询
	 * @param companyRate
	 * @return
	 */
	@Override
	public BaseVo findByParam(CompanyRate companyRate) {
		BaseVo baseVo = new BaseVo();
		List<CompanyRate> byParam = companyRateDao.findByParam(companyRate);
		if (CollectionUtils.isNotEmpty(byParam)){
			baseVo.setData(ResultCode.PARAMS_KEY_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_EXIST.getInfo());
		}else {
			baseVo.setData(ResultCode.PARAMS_KEY_NOT_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_NOT_EXIST.getInfo());
		}
		return baseVo;
	}


}