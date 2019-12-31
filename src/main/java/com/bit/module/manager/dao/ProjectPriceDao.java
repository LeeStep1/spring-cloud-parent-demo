package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ElevatorOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Repository
public interface ProjectPriceDao extends BaseMapper<ProjectPrice> {

    /**
     * 根据项目id集合批量查询报价记录
     * @param list
     * @return
     */
    List<ProjectPrice> getProjectPrice(@Param(value = "projectList") List<Long> list);


	/**
	 * 根据项目id查询订单
	 * @param projectEleOrder
	 * @return
	 */
    List<ElevatorOrderVo> queryOrderByProjectId(ProjectEleOrder  projectEleOrder);

	/**
	 * 获取报价的最大版本号
	 * @param projectId
	 * @return
	 */
    Integer  getMaxVersion(@Param(value = "projectId") Long projectId);

	/**
	 * 根据项目id查询项目报价
	 * @param projectId
	 * @return
	 */
	ProjectPrice getProjectPriceByProjectId(@Param(value = "projectId") Long projectId);


}
