package com.bit.module.manager.service.Impl;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.QueryParamsDao;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.miniapp.bean.QueryParams;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("queryParamsService")
public class QueryParamsServiceImpl implements QueryParamsService {

	@Autowired
	private QueryParamsDao queryParamsDao;



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
}