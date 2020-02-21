package com.bit.module.manager.service.Impl;

import com.bit.module.miniapp.bean.ElevatorRelaOptions;
import org.springframework.stereotype.Service;
import com.bit.module.manager.dao.ElevatorRelaOptionsDao;
import com.bit.module.manager.service.ElevatorRelaOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.bit.base.vo.BaseVo;
import com.bit.base.service.BaseService;

import java.util.List;

@Service("elevatorRelaOptionsService")
public class ElevatorRelaOptionsServiceImpl extends BaseService implements ElevatorRelaOptionsService {

	@Autowired
	private ElevatorRelaOptionsDao elevatorRelaOptionsDao;


	/**
	 * 新增数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(ElevatorRelaOptions elevatorRelaOptions){
             elevatorRelaOptionsDao.addElevatorRelaOptions(elevatorRelaOptions);
		return successVo();
		}

	/**
	 * 编辑数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(ElevatorRelaOptions elevatorRelaOptions) {
		 elevatorRelaOptionsDao.updateElevatorRelaOptions(elevatorRelaOptions);
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
		 elevatorRelaOptionsDao.delElevatorRelaOptionsById(id);
		return successVo();
	}

	/**
	 * 多参数查询数据
	 * @param elevatorRelaOptions
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorRelaOptions>
	 */
	@Override
	public BaseVo findByParam(ElevatorRelaOptions elevatorRelaOptions) {
		List<ElevatorRelaOptions> elevatorRelaOptionsList = elevatorRelaOptionsDao.findByParam(elevatorRelaOptions);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorRelaOptionsList);
		return baseVo;
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ElevatorRelaOptions
	 */
	@Override
	public BaseVo reflectById(Long id) {
		ElevatorRelaOptions elevatorRelaOptions =	elevatorRelaOptionsDao.getElevatorRelaOptionsById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorRelaOptions);
		return baseVo;
	}


}