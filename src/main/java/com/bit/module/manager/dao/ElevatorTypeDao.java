package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ElevatorTypeVO;
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
public interface ElevatorTypeDao extends BaseMapper<ElevatorType> {

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
	/**
	 * 电梯类型列表查询
	 * @return
	 */
	Page<ElevatorTypeVO> elevatorTypeListPage(Page<ElevatorTypeVO> page, ElevatorTypePageVO elevatorTypePageVO);
	
}
