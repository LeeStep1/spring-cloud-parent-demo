package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.vo.AreaTreeVO;
import com.bit.module.miniapp.bean.Area;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 采集居民中的一标三实的区划代码
 * 
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 10:29:33
 */
@Repository
public interface AreaDao extends BaseMapper<Area>{

	/**
    * 根据id单查记录
    * @param id
    */
	Area getAreaById(String id);


	/**
    * 多参数查询
    * @return
    */
	List<Area> findByParam(Area area);

	/**
	* 新增记录
    */
	void addArea(Area area);

	/**
    * 编辑记录
    */
	void updateArea(Area area);

	/**
	 * 全表查询
	 * @return
	 */
	List<AreaTreeVO> findAll();
}
