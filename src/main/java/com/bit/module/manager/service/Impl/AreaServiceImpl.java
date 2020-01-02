package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.AreaDao;
import com.bit.module.manager.service.AreaService;
import com.bit.module.manager.vo.AreaTreeVO;
import com.bit.module.miniapp.bean.Area;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
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
		List<AreaTreeVO> all = areaDao.findAll();
		//根节点
		List<AreaTreeVO> rootList = new ArrayList<>();
		//递归调用
		if (CollectionUtils.isNotEmpty(all)){
			for (AreaTreeVO areaTreeVO : all) {
				if (areaTreeVO.getParentCode().equals("-1")){
					rootList.add(areaTreeVO);
				}
			}
		}
		for (AreaTreeVO areaTreeVO : rootList) {
			areaTreeVO.setChildList(getChildList(areaTreeVO,all, areaTreeVO.getArCode()));
		}

		BaseVo baseVo = new BaseVo();
		baseVo.setData(rootList);
		return baseVo;
	}

	/**
	 * 地域查询
	 * @param area
	 * @return
	 */
	@Override
	public BaseVo<Area> listPage(Area area) {
		List<Area> byParam = areaDao.findByParam(area);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}

	/**
	 * 更新地域信息
	 * @param area
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo updateArea(Area area) {
		//省和直辖市
		if (area.getArLeavel().equals(1)){
			area.setTonsPrice(null);
		}else{
			area.setInstallCoefficient(null);
		}
		areaDao.updateArea(area);
		return successVo();
	}
	/**
	 * 查询子节点
	 * @param arCode
	 * @return
	 */
	@Override
	public BaseVo getChildArea(String arCode) {
		Area area = new Area();
		area.setParentCode(arCode);
		List<Area> byParam = areaDao.findByParam(area);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}

	/**
	 * 递归查询子节点
	 * @param areaTreeVO
	 * @param all
	 * @param arCode
	 * @return
	 */
	private List<AreaTreeVO> getChildList(AreaTreeVO areaTreeVO, List<AreaTreeVO> all, String arCode){
		List<AreaTreeVO> list = new ArrayList<>();
		for (AreaTreeVO vo : all) {
			if (StringUtil.isNotEmpty(vo.getParentCode()) && vo.getParentCode().equals(arCode)){
				list.add(vo);
			}
		}
		//设置子集
		areaTreeVO.setChildList(list);

		for (AreaTreeVO aa : list) {
			aa.setChildList(getChildList(aa,all,aa.getArCode()));
		}

		if (list.size() == 0) {
			return null;
		}
		return list;
	}
}