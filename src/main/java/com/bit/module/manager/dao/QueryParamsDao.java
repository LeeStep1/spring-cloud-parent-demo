package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.vo.InstallParamsPageVO;
import com.bit.module.manager.vo.InstallParamsVO;
import com.bit.module.manager.vo.QueryParamsPageVO;
import com.bit.module.manager.vo.QueryParamsVO;
import com.bit.module.miniapp.bean.QueryParams;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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

	/**
	 * 根据id 和 key,value查询
	 * @param queryParams
	 * @return
	 */
	List<QueryParams> getDetailParam(QueryParams queryParams);

	/**
	 * 新增记录
	 */
	void addQueryParams(QueryParams queryParams);

	/**
	 * 编辑记录
	 */
	void updateQueryParams(QueryParams queryParams);

	/**
	 * 删除记录
	 */
	void delQueryParamsById(String id);

	/**
	 * 根据id单查记录
	 * @param id
	 */
	QueryParams getQueryParamsById(String id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(@Param(value = "list") List<String> ids);


	/**
	 * 分页查询
	 */
	IPage<QueryParamsVO> listPage(@Param("pg")Page<QueryParamsVO> page, @Param("queryParamsPageVO") QueryParamsPageVO queryParamsPageVO);

	/**
	 * 不同的key
	 * @return
	 */
	List<String> distinctKey();
}
