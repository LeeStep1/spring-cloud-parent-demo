package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.AreaDao;
import com.bit.module.manager.service.AreaService;
import com.bit.module.manager.vo.AreaVO;
import com.bit.module.miniapp.bean.Area;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

	/**
	 * 地域缓存树
	 * @return
	 */
	@Override
	public BaseVo getAreasCacheTree() {
		Area area = new Area();
		QueryWrapper<Area> wrapper = new QueryWrapper<>();
		wrapper.setEntity(area);
		List<AreaVO> all = areaDao.findAll();
		//根节点
		List<AreaVO> rootList = new ArrayList<>();
		//递归调用
		if (CollectionUtils.isNotEmpty(all)){
			for (AreaVO areaVO : all) {
				if (areaVO.getParentCode().equals("-1")){
					rootList.add(areaVO);
				}
			}
		}
		for (AreaVO areaVO : rootList) {
			areaVO.setChildList(getChildList(areaVO,all,areaVO.getArCode()));
		}

		BaseVo baseVo = new BaseVo();
		baseVo.setData(rootList);
		return baseVo;
	}

	/**
	 * 递归查询子节点
	 * @param areaVO
	 * @param all
	 * @param arCode
	 * @return
	 */
	private List<AreaVO> getChildList(AreaVO areaVO,List<AreaVO> all,String arCode){
		List<AreaVO> list = new ArrayList<>();
		for (AreaVO vo : all) {
			if (StringUtil.isNotEmpty(vo.getParentCode()) && vo.getParentCode().equals(arCode)){
				list.add(vo);
			}
		}
		//设置子集
		areaVO.setChildList(list);

		for (AreaVO aa : list) {
			aa.setChildList(getChildList(aa,all,aa.getArCode()));
		}

		if (list.size() == 0) {
			return null;
		}
		return list;
	}
}