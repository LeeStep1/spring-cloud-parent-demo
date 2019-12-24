package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.FileInfo;
import com.bit.module.manager.dao.ElevatorTypeDao;
import com.bit.module.manager.dao.FileInfoDao;
import com.bit.module.manager.service.ElevatorTypeService;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service("elevatorTypeService")
public class ElevatorTypeServiceImpl extends BaseService implements ElevatorTypeService {

	@Autowired
	private ElevatorTypeDao elevatorTypeDao;
	@Autowired
	private FileInfoDao fileInfoDao;


	/**
	 * 新增数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public void add(ElevatorType elevatorType){
            elevatorTypeDao.addElevatorType(elevatorType);
		}

	/**
	 * 编辑数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public void update(ElevatorType elevatorType) {
            elevatorTypeDao.updateElevatorType(elevatorType);
	}

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public void delete(Long id) {
            elevatorTypeDao.delElevatorTypeById(id);
	}

	/**
	 * 多参数查询数据
	 * @param elevatorType
	 * @author chenduo
	 * @since ${date}
	 * @return List<ElevatorType>
	 */
	@Override
	public List<ElevatorType> findByParam(ElevatorType elevatorType) {
		List<ElevatorType> elevatorTypeList = elevatorTypeDao.findByParam(elevatorType);
		return elevatorTypeList;
	}

	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ElevatorType
	 */
	@Override
	public BaseVo reflectById(Long id) {
		ElevatorType elevatorTypeById = elevatorTypeDao.getElevatorTypeById(id);
		ElevatorTypeVO elevatorTypeVO = new ElevatorTypeVO();
		BeanUtils.copyProperties(elevatorTypeById,elevatorTypeVO);
		if (StringUtil.isNotEmpty(elevatorTypeById.getPicture())){
			FileInfo byId = fileInfoDao.findById(Long.valueOf(elevatorTypeById.getPicture()));
			elevatorTypeVO.setFileInfo(byId);
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypeVO);
		return baseVo;
	}

	/**
	 * 电梯类型列表查询
	 * @return
	 */
	@Override
	public BaseVo elevatorTypeListPage(ElevatorTypeVO elevatorTypeVO) {
		Page<ElevatorType> page = new Page<>(elevatorTypeVO.getPageNum(), elevatorTypeVO.getPageSize());
		Page<ElevatorType> elevatorTypePage = elevatorTypeDao.elevatorTypeListPage(page, elevatorTypeVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypePage);
		return baseVo;
	}


}