package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.NegotiationVO;
import com.bit.module.manager.vo.ProjectPageVO;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/2/27 8:46
 **/
public interface NegotiationService {
	/**
	 * 洽谈项目分页查询
	 * @param projectPageVO
	 * @return
	 */
	BaseVo negotiationlistPage(ProjectPageVO projectPageVO);

	/**
	 * 返显项目
	 * @param projectId
	 * @return
	 */
	BaseVo reflectById(NegotiationVO negotiationVO);
}
