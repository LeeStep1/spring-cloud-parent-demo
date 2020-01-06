package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ProjectPageVO;

/**
 * 项目报价信息表
 *
 * @author chenduo
 * @email ${email}
 * @date 2020-01-06 09:15:45
 */
public interface ProjectPriceService {



	/**
	 * 编辑数据
	 * @param projectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(ProjectPrice projectPrice);


	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return ProjectPrice
	 */
	BaseVo reflectById(Long id);

	/**
	 * 报价列表分页查询
	 * @param projectPageVO
	 * @return
	 */
	BaseVo listPage(ProjectPageVO projectPageVO);

}

