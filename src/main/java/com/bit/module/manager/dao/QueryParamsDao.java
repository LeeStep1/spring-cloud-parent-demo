package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.miniapp.bean.QueryParams;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 电梯的基础数据的筛选关系
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-23 11:04:01
 */
@Repository
public interface QueryParamsDao extends BaseMapper<QueryParams>{



	/**
	 * 多参数查询
	 *
	 * @return
	 */
	List<QueryParams> findByParam(QueryParams queryParams);

	/**
	 * 查询电梯参数
	 * @param queryParams
	 * @return
	 */
	List<QueryParams> getEleParam(QueryParams queryParams);

}
