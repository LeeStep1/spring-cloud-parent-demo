package com.bit.module.manager.dao;

import com.bit.module.miniapp.bean.ElevatorType;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 电梯系列基础信息表
 * 
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:33:26
 */
@Repository
public interface ElevatorTypeDao {

	/**
    * 根据id单查记录
    * @param id
    */
	ElevatorType getElevatorTypeById(@Param(value = "id") Long id);


	/**
    * 多参数查询
    * @return
    */
	List<ElevatorType> findByParam(ElevatorType elevatorType);

	/**
	* 新增记录
    */
	void addElevatorType(ElevatorType elevatorType);

	/**
    * 编辑记录
    */
	void updateElevatorType(ElevatorType elevatorType);

	/**
    * 删除记录
    */
	void delElevatorTypeById(Long id);
	
}
