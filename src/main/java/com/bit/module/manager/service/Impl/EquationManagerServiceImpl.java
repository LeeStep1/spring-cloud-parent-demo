package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.equation.bean.Equation;
import com.bit.module.manager.dao.EquationManagerDao;
import com.bit.module.manager.service.EquationManagerService;
import com.bit.module.manager.vo.EquationPageVO;
import com.bit.module.manager.vo.EquationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquationManagerServiceImpl extends BaseService implements EquationManagerService{

	@Autowired
	private EquationManagerDao equationManagerDao;
	/**
	 * 新增数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(Equation equation) {
		equationManagerDao.addEquation(equation);
		return successVo();
	}
	/**
	 * 编辑数据
	 * @param equation
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(Equation equation) {
		equationManagerDao.updateEquation(equation);
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
		equationManagerDao.delEquationById(id);
		return successVo();
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return Equation
	 */
	@Override
	public BaseVo reflectById(Long id) {
		Equation equationById = equationManagerDao.getEquationById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(equationById);
		return baseVo;
	}
	/**
	 * 分页查询
	 * @param equationPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(EquationPageVO equationPageVO) {
		Page<EquationVO> page = new Page<>(equationPageVO.getPageNum(), equationPageVO.getPageSize());
		IPage<EquationVO> equationList = equationManagerDao.listPage(page, equationPageVO);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(equationList);
		return baseVo;
	}
}
