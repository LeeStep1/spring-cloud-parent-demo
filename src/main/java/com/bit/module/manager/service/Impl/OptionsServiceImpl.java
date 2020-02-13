package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.OptionsPageVO;
import com.bit.module.miniapp.vo.OptionsVO;
import org.springframework.stereotype.Service;
import com.bit.module.manager.dao.OptionsDao;
import com.bit.module.manager.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.bit.base.vo.BaseVo;

import java.util.List;

@Service("optionsService")
public class OptionsServiceImpl extends BaseService implements OptionsService {

	@Autowired
	private OptionsDao optionsDao;


	/**
	 * 新增数据
	 *
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(Options options) {
		optionsDao.addOptions(options);
		return successVo();
	}

	/**
	 * 编辑数据
	 *
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(Options options) {
		optionsDao.updateOptions(options);
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
		optionsDao.delOptionsById(id);
		return successVo();
	}

	/**
	 * 多参数查询数据
	 *
	 * @param options
	 * @return List<Options>
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo findByParam(Options options) {
		List<Options> optionsList = optionsDao.findByParam(options);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(optionsList);
		return baseVo;
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return Options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		Options options = optionsDao.getOptionsById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(options);
		return baseVo;
	}

	/**
	 * 分页查询
	 *
	 * @param optionsPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(OptionsPageVO optionsPageVO) {
		Page<OptionsVO> page = new Page<>(optionsPageVO.getPageNum(), optionsPageVO.getPageSize());
		IPage<OptionsVO> equationList = optionsDao.listPage(page, optionsPageVO);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(equationList);
		return baseVo;
	}


}