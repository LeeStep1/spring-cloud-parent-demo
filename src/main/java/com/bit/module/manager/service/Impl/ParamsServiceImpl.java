package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.wxenum.ResultCode;
import com.bit.module.manager.bean.Params;
import com.bit.module.manager.dao.ParamsDao;
import com.bit.module.manager.service.ParamsService;
import com.bit.module.manager.vo.ParamsPageVO;
import com.bit.module.manager.vo.ParamsVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("paramsService")
public class ParamsServiceImpl extends BaseService implements ParamsService {

	@Autowired
	private ParamsDao paramsDao;


	/**
	 * 新增数据
	 *
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(Params params) {
		paramsDao.addParams(params);
		return successVo();
	}

	/**
	 * 编辑数据
	 *
	 * @param params
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(Params params) {
		paramsDao.updateParams(params);
		return successVo();
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		paramsDao.delParamsById(id);
		return successVo();
	}


	/**
	 * 单查数据
	 *
	 * @param id
	 * @return Params
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		BaseVo baseVo = new BaseVo();
		Params paramsById = paramsDao.getParamsById(id);
		baseVo.setData(paramsById);
		return baseVo;
	}

	/**
	 * 参数验重
	 * @param params
	 * @return
	 */
	@Override
	public BaseVo distinctParams(Params params) {
		BaseVo baseVo = new BaseVo();
		List<Params> byParam = paramsDao.findByParam(params);
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
	 * 分页查询数据
	 * @param paramsPageVO
	 * @author chenduo
	 * @since ${date}
	 * @return PageInfo
	 */
	@Override
	public BaseVo paramsListPage(ParamsPageVO paramsPageVO) {
		Page<ParamsVO> page = new Page<>(paramsPageVO.getPageNum(), paramsPageVO.getPageSize());
		IPage<ParamsVO> listPage = paramsDao.paramsListPage(page, paramsPageVO);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(listPage);
		return baseVo;
	}
	/**
	 * 全查
	 * @return
	 */
	@Override
	public BaseVo findAll() {
		List<Params> byParam = paramsDao.findByParam(null);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}


}