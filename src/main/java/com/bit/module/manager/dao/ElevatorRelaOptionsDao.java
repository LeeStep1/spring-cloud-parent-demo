package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.vo.ElevatorRelaOptionsPageVO;
import com.bit.module.manager.vo.ElevatorRelaOptionsVO;
import com.bit.module.miniapp.bean.ElevatorRelaOptions;
import com.bit.module.miniapp.bean.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElevatorRelaOptionsDao {
	/**
	 * 根据电梯类型id查询选项
	 * @param typeId
	 * @return
	 */
	List<Options> getOptionsByElevatorTypeId(@Param(value = "typeId") Long typeId);


	/**
	 * 根据id单查记录
	 * @param id
	 */
	ElevatorRelaOptions getElevatorRelaOptionsById(Long id);


	/**
	 * 多参数查询
	 * @return
	 */
	List<ElevatorRelaOptions> findByParam(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 新增记录
	 */
	void addElevatorRelaOptions(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 编辑记录
	 */
	void updateElevatorRelaOptions(ElevatorRelaOptions elevatorRelaOptions);

	/**
	 * 删除记录
	 */
	void delElevatorRelaOptionsById(Long id);

	/**
	 * 分页查询
	 */
	IPage<ElevatorRelaOptionsVO> listPage(@Param("pg")Page<ElevatorRelaOptionsVO> page, @Param("elevatorRelaOptionsPageVO") ElevatorRelaOptionsPageVO elevatorRelaOptionsPageVO);
}
