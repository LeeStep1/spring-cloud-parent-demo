package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.informationEnum.ElevatorTypeEnum;
import com.bit.module.manager.bean.FileInfo;
import com.bit.module.manager.dao.ElevatorTypeDao;
import com.bit.module.manager.dao.FileInfoDao;
import com.bit.module.manager.service.ElevatorTypeService;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service("elevatorTypeService")
public class ElevatorTypeServiceImpl extends BaseService implements ElevatorTypeService {

	@Autowired
	private ElevatorTypeDao elevatorTypeDao;
	@Autowired
	private FileInfoDao fileInfoDao;

	@Value("${server.servlet.context-path}")
	private String contextPath;



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
		ElevatorTypePageVO elevatorTypePageVO = new ElevatorTypePageVO();
		BeanUtils.copyProperties(elevatorTypeById, elevatorTypePageVO);
		if (StringUtil.isNotEmpty(elevatorTypeById.getPicture())){
			FileInfo byId = fileInfoDao.findById(Long.valueOf(elevatorTypeById.getPicture()));
			elevatorTypePageVO.setFileInfo(byId);
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypePageVO);
		return baseVo;
	}

	/**
	 * 电梯类型列表查询
	 * @return
	 */
	@Override
	public BaseVo elevatorTypeListPage(ElevatorTypePageVO elevatorTypePageVO) {
		Page<ElevatorTypeVO> page = new Page<>(elevatorTypePageVO.getPageNum(), elevatorTypePageVO.getPageSize());
		Page<ElevatorTypeVO> elevatorTypePage = elevatorTypeDao.elevatorTypeListPage(page, elevatorTypePageVO);
		if (CollectionUtils.isNotEmpty(elevatorTypePage.getRecords())){
			for (ElevatorTypeVO vo : elevatorTypePage.getRecords()) {
				vo.setTypeEnumName(ElevatorTypeEnum.getValueByCode(vo.getType()));
				//设置图片地址
				String pic = contextPath + "/images/" + vo.getPicture();
				vo.setPicture(pic);
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elevatorTypePage);
		return baseVo;
	}


}