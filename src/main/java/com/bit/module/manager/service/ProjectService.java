package com.bit.module.manager.service;

import com.bit.base.vo.BasePageVo;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.vo.ProjectVo;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface ProjectService {

	void add(Project project);

	/**
	 *我的项目一级页面
	 * @param vo  分页组件 pageNum pageSize
	 * @return
	 */
	BaseVo queryProject(BasePageVo vo);


	ProjectVo queryProjectPri(Long projectId);

	/**
	 * 查询项目报价详情
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	BaseVo getProjectDetail(Long projectId,Long projectPriceId);

	/**
	 * 查询订单详情
	 * @param projectId
	 * @param orderId
	 * @return
	 */
	BaseVo getOrderDetail(Long projectId,Long orderId);
}