package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.AreaDao;
import com.bit.module.manager.service.AreaService;
import com.bit.module.manager.vo.AreaTreeVO;
import com.bit.module.manager.vo.AreaVO;
import com.bit.module.miniapp.bean.Area;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
			areaTreeVO.setChildList(getChildListTree(areaTreeVO,all, areaTreeVO.getArCode()));
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
		BaseVo baseVo = new BaseVo();
		List<Area> byParam = areaDao.findByParam(area);
		for (Area ar : byParam) {
			if (ar.getArLeavel()<3){
				ar.setHasChildren(1);
			}
		}

		if (StringUtil.isEmpty(area.getArName()) && area.getTonsPrice()==null && area.getInstallCoefficient()==null){
			baseVo.setData(byParam);
			return baseVo;
		}
		//根
		List<AreaVO> root = new ArrayList<>();
		List<AreaVO> areaVOS = new ArrayList<>();
		for (Area area1 : byParam) {
			AreaVO areaVO = new AreaVO();
			BeanUtils.copyProperties(area1,areaVO);
			if (area1.getArLeavel()==2){
				root.add(areaVO);
			}else {
				areaVOS.add(areaVO);
			}
		}

		for (AreaVO areaVO : root) {
			areaVO.setChildList(getChildList(areaVO,areaVOS,areaVO.getArCode()));
		}
		baseVo.setData(root);
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
		for (Area ar : byParam) {
			if (ar.getArLeavel()<3){
				ar.setHasChildren(1);
			}
		}
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
	private List<AreaTreeVO> getChildListTree(AreaTreeVO areaTreeVO, List<AreaTreeVO> all, String arCode){
		List<AreaTreeVO> list = new ArrayList<>();
		for (AreaTreeVO vo : all) {
			if (StringUtil.isNotEmpty(vo.getParentCode()) && vo.getParentCode().equals(arCode)){
				list.add(vo);
			}
		}
		//设置子集
		areaTreeVO.setChildList(list);

		for (AreaTreeVO aa : list) {
			aa.setChildList(getChildListTree(aa,all,aa.getArCode()));
		}

		if (list.size() == 0) {
			return null;
		}
		return list;
	}


	/**
	 * 递归查询子节点
	 * @param areaVO
	 * @param all
	 * @param arCode
	 * @return
	 */
	private List<AreaVO> getChildList(AreaVO areaVO, List<AreaVO> all, String arCode){
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