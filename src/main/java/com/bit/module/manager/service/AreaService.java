package com.bit.module.manager.service;

import java.util.List;

import com.bit.base.vo.BaseVo;
import com.bit.module.miniapp.bean.Area;

/**
 * 采集居民中的一标三实的区划代码
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
public interface AreaService {

	/**
	 * 查询下级地域
	 * @param id
	 * @return
	 */
	BaseVo getAreas(String id);

	/**
	 * 地域缓存树
	 * @return
	 */
	BaseVo getAreasCacheTree();
	/**
	 * 地域查询
	 * @param area
	 * @return
	 */
	BaseVo<Area> listPage(Area area);
	/**
	 * 更新地域信息
	 * @param area
	 * @return
	 */
	BaseVo updateArea(Area area);
	/**
	 * 查询子节点
	 * @param arCode
	 * @return
	 */
	BaseVo getChildArea(String arCode);

}

