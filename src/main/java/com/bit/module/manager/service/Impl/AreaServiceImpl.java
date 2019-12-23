package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.AreaDao;
import com.bit.module.manager.service.AreaService;
import com.bit.module.miniapp.bean.Area;
import com.bit.utils.StringUtil;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("areaService")
public class AreaServiceImpl extends BaseService implements AreaService {

	@Autowired
	private AreaDao areaDao;

	/**
	 * 查询下级地域
	 * @param id
	 * @return
	 */
	@Override
	public BaseVo getAreas(String id) {
		BaseVo baseVo = new BaseVo();
		Area area = new Area();
		if (StringUtil.isNotEmpty(id)){
			area.setParentCode(id);
		}else {
			area.setParentCode("-1");
		}
		//使用mybatisplus进行多参数查询
		QueryWrapper<Area> wrapper = new QueryWrapper<>();
		wrapper.setEntity(area);
		List<Area> byParam = areaDao.selectList(wrapper);
		baseVo.setData(byParam);
		return baseVo;
	}
}