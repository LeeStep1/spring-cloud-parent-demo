package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.miniapp.bean.QueryParams;
import java.util.List;

/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:04:01
 */
public interface QueryParamsService {



	/**
	 * 查询电梯参数
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 * @return List<QueryParams>
	 */
	BaseVo getEleParams(QueryParams queryParams);


}

