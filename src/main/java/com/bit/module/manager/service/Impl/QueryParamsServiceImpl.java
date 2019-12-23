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
	 * 查询电梯参数
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 * @return List<QueryParams>
	 */
	@Override
	public BaseVo getEleParams(QueryParams queryParams) {
		BaseVo baseVo = new BaseVo();
		List<QueryParams> queryParamsList = queryParamsDao.getEleParams(queryParams);
		baseVo.setData(queryParamsList);
		return baseVo;
	}


}