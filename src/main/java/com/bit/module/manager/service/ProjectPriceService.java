package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.ProjectEleNonstandardVO;
import com.bit.module.manager.vo.ProjectPageVO;

import java.util.List;

/**
 * 项目报价信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-06 09:15:45
 */
public interface ProjectPriceService {

	/**
	 * 项目下订单列表
	 * @param projectPriceId
	 * @return
	 */
	BaseVo orderList(Long projectPriceId);

	/**
	 * 批量编辑数据
	 * @param projectPrices
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(List<ProjectEleNonstandardVO> projectPrices);


	/**
	 * 单查项目数据
	 * @param orderId
	 * @author chenduo
	 * @since ${date}
	 * @return ProjectPrice
	 */
	BaseVo reflectById(Long orderId);

	/**
	 * 报价列表分页查询
	 * @param projectPageVO
	 * @return
	 */
	BaseVo listPage(ProjectPageVO projectPageVO);

}

