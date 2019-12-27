/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleOptions;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 */
@Repository
public interface ProjectEleOptionsDao extends BaseMapper<ProjectEleOptions> {

	/**
	 * 批量新增
	 * @param list
	 */
	void batchAdd(@Param(value = "list") List<ProjectEleOptions> list);

}
