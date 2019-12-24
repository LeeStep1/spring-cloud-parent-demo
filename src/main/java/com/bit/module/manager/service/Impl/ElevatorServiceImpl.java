package com.bit.module.manager.service.Impl;

import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.ElevatorRelaOptionsDao;
import com.bit.module.manager.service.ElevatorService;
import com.bit.module.miniapp.bean.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/24 10:00
 **/
@Service("elevatorService")
public class ElevatorServiceImpl extends BaseService implements ElevatorService {

	@Autowired
	private ElevatorRelaOptionsDao elevatorRelaOptionsDao;


	/**
	 * 根据电梯类型查询电梯的可选项
	 * @param typeId
	 * @return
	 */
	@Override
	public BaseVo getEleOptions(Long typeId) {
		List<Options> optionsByElevatorTypeId = elevatorRelaOptionsDao.getOptionsByElevatorTypeId(typeId);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(optionsByElevatorTypeId);
		return baseVo;
	}
}
