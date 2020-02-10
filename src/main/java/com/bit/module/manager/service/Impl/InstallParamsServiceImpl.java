package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.module.manager.bean.InstallParams;
import com.bit.module.manager.vo.InstallParamsPageVO;
import com.bit.module.manager.vo.InstallParamsVO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

import com.bit.module.manager.dao.InstallParamsDao;
import com.bit.module.manager.service.InstallParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.bit.base.vo.BaseVo;

@Service("installParamsService")
public class InstallParamsServiceImpl extends BaseService implements InstallParamsService {

	@Autowired
	private InstallParamsDao installParamsDao;


	/**
	 * 新增数据
	 *
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(InstallParams installParams) {
		installParamsDao.addInstallParams(installParams);
		return successVo();
	}

	/**
	 * 编辑数据
	 *
	 * @param installParams
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(InstallParams installParams) {
		installParamsDao.updateInstallParams(installParams);
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
		installParamsDao.delInstallParamsById(id);
		return successVo();
	}

	/**
	 * 多参数查询数据
	 *
	 * @param installParams
	 * @return List<InstallParams>
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo findByParam(InstallParams installParams) {
		List<InstallParams> installParamsList = installParamsDao.findByParam(installParams);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(installParamsList);
		return baseVo;
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return InstallParams
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		InstallParams installParams = installParamsDao.getInstallParamsById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(installParams);
		return baseVo;
	}

	/**
	 * 分页查询
	 * @param installParamsPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(InstallParamsPageVO installParamsPageVO) {
		Page<InstallParamsVO> page = new Page<>(installParamsPageVO.getPageNum(), installParamsPageVO.getPageSize());
		IPage<InstallParamsVO> installParamsList = installParamsDao.listPage(page, installParamsPageVO);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(installParamsList);
		return baseVo;
	}


}