package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Params;
import com.bit.module.manager.dao.ParamsDao;
import com.bit.module.manager.dao.QueryParamsDao;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.manager.vo.QueryParamsPageVO;
import com.bit.module.manager.vo.QueryParamsVO;
import com.bit.module.miniapp.bean.QueryParams;
import com.bit.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("queryParamsService")
public class QueryParamsServiceImpl extends BaseService implements QueryParamsService {

	@Autowired
	private QueryParamsDao queryParamsDao;
	@Autowired
	private ParamsDao paramsDao;



	/**
	 * 根据电梯的key查询params的level1数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 * @return List<QueryParams>
	 */
	@Override
	public BaseVo getEleParamLevelOne(QueryParams queryParams) {
		BaseVo baseVo = new BaseVo();
		//只查询1级
		queryParams.setLevel(1);
		List<QueryParams> queryParamsList = queryParamsDao.getEleParam(queryParams);
		baseVo.setData(queryParamsList);
		return baseVo;
	}

	/**
	 * 查询电梯的参数
	 * @param queryParams
	 * @return
	 */
	@Override
	public BaseVo getEleParam(QueryParams queryParams) {
		BaseVo baseVo = new BaseVo();
		List<QueryParams> queryParamsList = queryParamsDao.getEleParam(queryParams);
		baseVo.setData(queryParamsList);
		return baseVo;
	}

	/**
	 * 查询电梯的参数 树形结构
	 * @param queryParams
	 * @return
	 */
	@Override
	public BaseVo<QueryParams> getEleParamTree(QueryParams queryParams) {
		BaseVo baseVo = new BaseVo();
		//查询全表
		List<QueryParams> all = queryParamsDao.findByParam(null);

		QueryParams root = new QueryParams();
		if (CollectionUtils.isNotEmpty(all)){
			for (QueryParams params : all) {
				if (queryParams.getElevatorTypeParamsKey().equals(params.getValue())){
					root = params;
				}
			}
		}
		root.setChildList(getChildList(root,all, root.getLevel()+1));

		baseVo.setData(root);
		return baseVo;
	}
	/**
	 * 新增数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo add(QueryParams queryParams) {
		//校验重复
		QueryParams pp = new QueryParams();
		pp.setKey(queryParams.getKey());
		pp.setValue(queryParams.getValue());
		pp.setLevel(queryParams.getLevel());
		pp.setParentId(queryParams.getParentId());
		List<QueryParams> byParam = queryParamsDao.checkValue(pp);
		if (CollectionUtils.isNotEmpty(byParam)){
			throw new BusinessException("业务数据值重复");
		}

		//计算id
		String s = this.calucateId(queryParams.getParentId(), queryParams.getLevel());
		queryParams.setId(s);
		queryParamsDao.addQueryParams(queryParams);
		return successVo();
	}
	/**
	 * 编辑数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(QueryParams queryParams) {
		//校验重复
		QueryParams pp = new QueryParams();
		pp.setKey(queryParams.getKey());
		pp.setValue(queryParams.getValue());
		pp.setLevel(queryParams.getLevel());
		pp.setParentId(queryParams.getParentId());
		List<QueryParams> byParam = queryParamsDao.checkValue(pp);
		if (CollectionUtils.isNotEmpty(byParam)){
			throw new BusinessException("业务数据值重复");
		}
		queryParamsDao.updateQueryParams(queryParams);
		return successVo();
	}
	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo delete(String id) {
		QueryParams queryParamsById = queryParamsDao.getQueryParamsById(id);
		if (queryParamsById==null){
			throw new BusinessException("数据不存在");
		}
		//先查询有没有子集
		QueryParams params = new QueryParams();
		params.setId(id);
		List<QueryParams> eleParam = queryParamsDao.getEleParam(params);
		if (CollectionUtils.isNotEmpty(eleParam)){

			List<String> ids = new ArrayList<>();
			for (QueryParams queryParams : eleParam) {
				ids.add(queryParams.getId());
			}
			//批量删除
			queryParamsDao.deleteByIds(ids);
		}else {
			queryParamsDao.delQueryParamsById(id);
		}
		return successVo();
	}
	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return QueryParams
	 */
	@Override
	public BaseVo reflectById(String id) {
		QueryParams queryParamsById = queryParamsDao.getQueryParamsById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(queryParamsById);
		return baseVo;
	}
	/**
	 * 分页查询
	 * @param queryParamsPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(QueryParamsPageVO queryParamsPageVO) {
		Page<QueryParamsVO> page = new Page<>(queryParamsPageVO.getPageNum(), queryParamsPageVO.getPageSize());
		IPage<QueryParamsVO> installParamsList = queryParamsDao.listPage(page, queryParamsPageVO);
		for (QueryParamsVO vo :installParamsList.getRecords() ) {
			QueryParams po = new QueryParams();
			po.setLevel(vo.getLevel()+1);
			po.setId(vo.getId());
			List<QueryParams> eleParam = queryParamsDao.getEleParam(po);
			if (CollectionUtils.isNotEmpty(eleParam)){
				vo.setHasChildren(1);
			}else {
				vo.setHasChildren(0);
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(installParamsList);
		return baseVo;
	}

	/**
	 * 不同的key
	 * @return
	 */
	@Override
	public BaseVo distinctKey() {
		List<Params> byParam = paramsDao.findByParam(null);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byParam);
		return baseVo;
	}
	/**
	 * 多参数查询
	 * @param queryParams
	 * @return
	 */
	@Override
	public BaseVo findNextLevel(QueryParams queryParams) {
		List<QueryParamsVO> list = new ArrayList<>();
		QueryParams temp = new QueryParams();
		temp.setId(queryParams.getId());
		temp.setLevel(queryParams.getLevel()+1);
		List<QueryParams> eleParam = queryParamsDao.getEleParam(temp);
		if (CollectionUtils.isNotEmpty(eleParam)){
			for (QueryParams params : eleParam) {
				QueryParamsVO queryParamsVO = new QueryParamsVO();
				BeanUtils.copyProperties(params,queryParamsVO);

				QueryParams po = new QueryParams();
				po.setLevel(params.getLevel()+1);
				po.setId(params.getId());
				List<QueryParams> ele = queryParamsDao.getEleParam(po);
				if (CollectionUtils.isNotEmpty(ele)){
					queryParamsVO.setHasChildren(1);
				}else {
					queryParamsVO.setHasChildren(0);
				}
				list.add(queryParamsVO);
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(list);
		return baseVo;
	}

	/**
	 * 查询子节点
	 * @param queryParams
	 * @param all
	 * @param level
	 * @return
	 */
	private List<QueryParams> getChildList(QueryParams queryParams,List<QueryParams> all,Integer level){
		List<QueryParams> list = new ArrayList<>();
		for (QueryParams params : all) {
			if (level.equals(params.getLevel()) && params.getId().startsWith(queryParams.getId())){
				list.add(params);
			}
		}
		//设置子集
		queryParams.setChildList(list);
		for (QueryParams aa : list) {
			aa.setChildList(getChildList(aa,all,aa.getLevel()+1));
		}

		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 计算id
	 * @param parentId
	 * @param level
	 * @return
	 */
	private String calucateId(String parentId,Integer level){
		QueryParams params = new QueryParams();
		params.setLevel(level);
		//判断父id是否为空
		//父id为空说明 新增的是系列数据
		//父id不为空说明 新增的是参数数据
		if (StringUtil.isNotEmpty(parentId)){
			params.setId(parentId);
		}

		List<QueryParams> eleParam = queryParamsDao.getEleParam(params);

		String id = getMaxId(eleParam,level,parentId);

		return id;
	}

	/**
	 * 根据层级算id
	 * @param eleParam
	 * @param level
	 * @return
	 */
	private String getMaxId(List<QueryParams> eleParam,Integer level,String parentId){
		String id = "";
		if (CollectionUtils.isNotEmpty(eleParam)){
			List<Integer> ids = new ArrayList<>();
			for (QueryParams queryParams : eleParam) {
				ids.add(Integer.valueOf(queryParams.getId()));
			}
			//算出最大值
			Integer max = Collections.max(ids);
			max = max + 1;
			if (level.equals(1)){
				//一级
				if (max<10){
					id = "00" + max;
				}else if (max<100 && max>= 10){
					id = "0" + max;
				}else if (max<1000 && max >= 100){
					id = max+"";
				}
			}else {
				if (level == 1){
					id = String.format("%03d",max);
				}else if (level == 2){
					id = String.format("%06d",max);
				}else if (level == 3){
					id = String.format("%09d",max);
				}else if (level == 4){
					id = String.format("%012d",max);
				}

			}
		}else {
			id = parentId + "001";
		}
		return id;
	}
}