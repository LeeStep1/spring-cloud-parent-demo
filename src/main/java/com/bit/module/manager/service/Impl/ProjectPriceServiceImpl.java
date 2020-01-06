package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ProjectPageVO;
import com.bit.module.manager.vo.ProjectShowVO;
import org.springframework.stereotype.Service;

import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.ProjectPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service("projectPriceService")
public class ProjectPriceServiceImpl extends BaseService implements ProjectPriceService {

	@Autowired
	private ProjectPriceDao projectPriceDao;


	/**
	 * 编辑数据
	 *
	 * @param projectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(ProjectPrice projectPrice) {
		projectPriceDao.updateProjectPrice(projectPrice);
		return successVo();
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return ProjectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		BaseVo baseVo = new BaseVo();
		ProjectPrice projectPrice = projectPriceDao.getProjectPriceById(id);
		baseVo.setData(projectPrice);
		return baseVo;
	}

	/**
	 * 报价列表分页查询
	 * @param projectPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(ProjectPageVO projectPageVO) {
		Page<ProjectShowVO> page = new Page<>(projectPageVO.getPageNum(), projectPageVO.getPageSize());
		IPage<ProjectShowVO> listPage = projectPriceDao.listPage(page, projectPageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(listPage);
		return baseVo;
	}


}