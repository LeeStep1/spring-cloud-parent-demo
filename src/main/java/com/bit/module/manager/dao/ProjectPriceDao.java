package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.*;
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
     * @param projectId
     * @return
     */
    List<ProjectPrice> getLatestProjectPrice(@Param(value = "projectId") Long projectId);


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
	ProjectPrice getProjectPriceByProjectIdAndOrderId(@Param(value = "projectId") Long projectId,@Param(value = "orderId") Long orderId);

	/**
	 * 根据项目id和version查询记录
	 * @param projectId
	 * @param version
	 * @return
	 */
	ProjectPrice getProjectPriceByProjectIdWithVersion(@Param(value = "projectId") Long projectId,@Param(value = "version")Integer version);

	/**
	 * 多参数查询
	 * @param projectPrice
	 * @return
	 */
	List<ProjectPrice> findByParam(ProjectPrice projectPrice);

	/**
	 * 根据主键查询数据
	 * @param id
	 * @return
	 */
	ProjectPrice getProjectPriceById(@Param(value = "id") Long id);

	/**
	 * 批量编辑数据
	 * @param projectPrices
	 */
	void updatebatchProjectPrice(@Param(value = "projectPriceList") List<ProjectEleNonstandardVO> projectPrices);


	/**
	 * 分页查询
	 * @param projectPricePageVO
	 * @return
	 */
	/**
	 * 参数列表查询
	 * @return
	 */
	IPage<ProjectShowVO> listPage(@Param("pg")Page<ProjectShowVO> page, @Param("projectPageVO")ProjectPageVO projectPageVO);

	/**
	 * 编辑数据
	 * @param projectPrice
	 */
	void updateProjectPrice(ProjectPrice projectPrice);
}
