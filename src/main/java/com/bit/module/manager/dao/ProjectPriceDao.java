package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ElevatorOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Repository
public interface ProjectPriceDao extends BaseMapper<ProjectPrice> {


    List<ProjectPrice> getProjectPrice(@Param(value = "projectList") List<Long> list);




    List<ElevatorOrderVo> queryOrderByPriceId(ProjectEleOrder  projectEleOrder);

}
