package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.OptionsPageVO;
import com.bit.module.miniapp.vo.OptionsVO;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.bit.module.manager.dao.OptionsDao;
import com.bit.module.manager.service.OptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.bit.base.vo.BaseVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("optionsService")
public class OptionsServiceImpl extends BaseService implements OptionsService {

	@Autowired
	private OptionsDao optionsDao;


	/**
	 * 新增数据
	 *
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(Options options) {
		//计算id
		String s = this.calculateCode(options.getParentId());
		options.setOcode(s);
		optionsDao.addOptions(options);
		return successVo();
	}

	/**
	 * 计算id
	 * @param parentId
	 * @return
	 */
	private String calculateCode(Long parentId){
		Options opt = new Options();
		Integer level = -1;
		String parentCode = "";
		if (parentId==null){
			//第一层级数据
			opt.setLength(3);
			level = 1;
		}else {
			Options optionsById = optionsDao.getOptionsById(parentId);
			if (optionsById!=null){
				level = (optionsById.getOcode().length()+3)/3;
			}
			opt.setParentId(parentId);
			parentCode = optionsById.getOcode();
		}
		List<Options> optionParam = optionsDao.getOptionParam(opt);
		String code = getMaxCode(optionParam,level,parentCode);
		return code;
	}

	/**
	 * 根据层级算id
	 * @param optionParam
	 * @param level
	 * @param parentCode
	 * @return
	 */
	public String getMaxCode(List<Options> optionParam,Integer level,String parentCode){
		String maxid = "";
		if (CollectionUtils.isNotEmpty(optionParam)){
			List<Integer> ids = new ArrayList<>();
			for (Options options : optionParam) {
				ids.add(Integer.valueOf(options.getOcode()));
			}
			Integer max = Collections.max(ids);
			max = max + 1;
			if (level.equals(1)) {
				//一级
				maxid = String.format("%03d",max);
			}else {
				maxid = String.format("%0"+(level * 3)+"d",max);
			}
		}else {
			if (StringUtil.isNotEmpty(parentCode)){
				maxid = parentCode + "001";
			}
		}
		return maxid;
	}

	/**
	 * 编辑数据
	 *
	 * @param options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(Options options) {
		optionsDao.updateOptions(options);
		return successVo();
	}

	/**
	 * 删除数据
	 *
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(Long id) {
		Options optionsById = optionsDao.getOptionsById(id);
		if (optionsById==null){
			throw new BusinessException("数据不存在");
		}
		Options param = new Options();
		param.setParentId(id);
		List<Options> byParam = optionsDao.findByParam(param);
		if (CollectionUtils.isNotEmpty(byParam)){
			List<Long> ids = new ArrayList<>();
			for (Options options : byParam) {
				ids.add(options.getId());
			}
			//批量删除
			optionsDao.deleteByIds(ids);
		}else {
			optionsDao.delOptionsById(id);
		}
		return successVo();
	}

	/**
	 * 多参数查询数据
	 *
	 * @param options
	 * @return List<Options>
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo findByParam(Options options) {
		List<Options> optionsList = optionsDao.findByParam(options);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(optionsList);
		return baseVo;
	}

	/**
	 * 单查数据
	 *
	 * @param id
	 * @return Options
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		Options options = optionsDao.getOptionsById(id);
		if (options!=null){
			Options optionsById = optionsDao.getOptionsById(options.getParentId());
			if (optionsById!=null){
				options.setParentName(optionsById.getOptionsName());
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(options);
		return baseVo;
	}

	/**
	 * 分页查询
	 *
	 * @param optionsPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(OptionsPageVO optionsPageVO) {
		Page<OptionsVO> page = new Page<>(optionsPageVO.getPageNum(), optionsPageVO.getPageSize());
		IPage<OptionsVO> equationList = optionsDao.listPage(page, optionsPageVO);
		List<Long> ids = new ArrayList<>();
		for (OptionsVO optionsVO : equationList.getRecords()) {
			if (optionsVO.getParentId()!=null){
				ids.add(optionsVO.getParentId());
			}
		}
		if (CollectionUtils.isNotEmpty(ids)){
			List<Options> options = optionsDao.batchSelectByIds(ids);
			if (CollectionUtils.isNotEmpty(options)){
				for (OptionsVO optionsVO : equationList.getRecords()) {
					for (Options option : options) {
						if (optionsVO.getParentId().equals(option.getId())){
							optionsVO.setParentName(option.getOptionsName());
						}
					}
				}
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(equationList);
		return baseVo;
	}


}