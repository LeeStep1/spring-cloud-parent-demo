package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleOptions;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Repository
public interface ProjectEleOrderBaseInfoDao  extends BaseMapper<ProjectEleOrderBaseInfo> {
	/**
	 * 批量新增
	 * @param list
	 */
	void batchAdd(@Param(value = "list") List<ProjectEleOrderBaseInfo> list);

}
