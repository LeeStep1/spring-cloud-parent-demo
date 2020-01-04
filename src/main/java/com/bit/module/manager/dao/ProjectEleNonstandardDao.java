package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleNonstandard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-04
 **/
@Repository
public interface ProjectEleNonstandardDao extends BaseMapper<ProjectEleNonstandard>{


    /**
     * 批量新增
     * @param list
     */
    void batchAdd(@Param(value = "list") List<ProjectEleNonstandard> list);
}
