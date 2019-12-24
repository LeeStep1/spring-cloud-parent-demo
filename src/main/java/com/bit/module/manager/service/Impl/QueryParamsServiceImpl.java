package com.bit.module.manager.service.Impl;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.dao.QueryParamsDao;
import com.bit.module.manager.service.QueryParamsService;
import com.bit.module.miniapp.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}