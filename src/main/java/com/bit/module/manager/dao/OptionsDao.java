package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.OptionsPageVO;
import com.bit.module.miniapp.vo.OptionsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Repository
public interface OptionsDao extends BaseMapper<Options> {


	List<Options> findOptionByElevatorType(@Param("elevatorTypeId") Long elevatorTypeId, @Param("optionType") Integer optionType);
	/**
	 * 根据id单查记录
	 * @param id
	 */
	Options getOptionsById(Long id);
	/**
	 * 多参数查询
	 * @return
	 */
	List<Options> findByParam(Options options);

	/**
	 * 新增记录
	 */
	void addOptions(Options options);

	/**
	 * 编辑记录
	 */
	void updateOptions(Options options);

	/**
	 * 删除记录
	 */
	void delOptionsById(Long id);

	/**
	 * 分页查询
	 */
	IPage<OptionsVO> listPage(@Param("pg")Page<OptionsVO> page, @Param("optionsPageVO") OptionsPageVO optionsPageVO);

	/**
	 * 层级数据查询
	 * @param options
	 * @return
	 */
	List<Options> getOptionParam(Options options);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(@Param(value = "ids")List<Long> ids);

	/**
	 * 批量查询
	 * @param ids
	 * @return
	 */
	List<Options> batchSelectByIds(@Param(value = "ids")List<Long> ids);

	/**
	 * 批量 更新
	 * @param list
	 */
	void batchUpdateList(@Param(value = "list")List<Options> list);
}
