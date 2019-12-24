package com.bit.module.manager.dao;

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
}
