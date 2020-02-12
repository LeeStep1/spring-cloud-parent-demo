package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.QueryParamsPageVO;
import com.bit.module.miniapp.bean.QueryParams;
import org.springframework.web.bind.annotation.RequestBody;

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
	 * 根据电梯的key查询params的level1数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 * @return List<QueryParams>
	 */
	BaseVo getEleParamLevelOne(QueryParams queryParams);

	/**
	 * 查询电梯的参数
	 * @param queryParams
	 * @return
	 */
	BaseVo getEleParam(QueryParams queryParams);
	/**
	 * 查询电梯的参数 树形结构
	 * @param queryParams
	 * @return
	 */
	BaseVo<QueryParams> getEleParamTree(QueryParams queryParams);


	/**
	 * 新增数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo add(QueryParams queryParams);

	/**
	 * 编辑数据
	 * @param queryParams
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo update(QueryParams queryParams);

	/**
	 * 删除数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 */
	BaseVo delete(String id);



	/**
	 * 单查数据
	 * @param id
	 * @author chenduo
	 * @since ${date}
	 * @return QueryParams
	 */
	BaseVo reflectById(String id);

	/**
	 * 分页查询
	 * @param queryParamsPageVO
	 * @return
	 */
	BaseVo listPage(QueryParamsPageVO queryParamsPageVO);

	/**
	 * 不同的key
	 * @return
	 */
	BaseVo distinctKey();
}

