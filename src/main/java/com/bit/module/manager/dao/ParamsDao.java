package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.Params;
import java.util.List;

import com.bit.module.manager.vo.ParamsPageVO;
import com.bit.module.manager.vo.ParamsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author chenduo
 * @email ${email}
 * @date 2019-12-30 09:49:11
 */
@Repository
public interface ParamsDao {

	/**
    * 根据id单查记录
    * @param id
    */
	Params getParamsById(Long id);


	/**
    * 多参数查询
    * @return
    */
	List<Params> findByParam(Params params);

	/**
	* 新增记录
    */
	void addParams(Params params);

	/**
    * 编辑记录
    */
	void updateParams(Params params);

	/**
    * 删除记录
    */
	void delParamsById(Long id);
	/**
	 * 参数列表查询
	 * @return
	 */
	IPage<ParamsVO> paramsListPage(@Param("pg")Page<ParamsVO> page, @Param("paramsPageVO")ParamsPageVO paramsPageVO);

}
