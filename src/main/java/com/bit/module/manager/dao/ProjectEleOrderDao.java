package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 *
 */
@Repository
public interface ProjectEleOrderDao extends BaseMapper<ProjectEleOrder> {
	/**
	 * 批量添加
	 * @param list
	 */
	void batchInsert(@Param(value = "list") List<ProjectEleOrder> list);

	/**
	 * 多参数查询
	 * @param projectEleOrder
	 * @return
	 */
	List<ProjectEleOrder> findByParam(ProjectEleOrder projectEleOrder);

	/**
	 * 根据订单id查询报价版本
	 * @param orderId
	 * @return
	 */
	Integer getPriceVersionByOrderId(@Param(value = "orderId")Long orderId);

	/**
	 * 批量更新
	 * @param list
	 */
	void updateBatchEleOrder(@Param(value = "list") List<ProjectEleOrder> list);


	/**
	 * 批量更新
	 * @param list
	 */
	void updateBatch(@Param(value = "list") List<ProjectEleOrder> list);
}
