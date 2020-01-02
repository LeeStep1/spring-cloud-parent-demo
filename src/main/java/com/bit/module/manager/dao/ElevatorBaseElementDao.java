package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.miniapp.vo.ElevatorBaseElementVo;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface ElevatorBaseElementDao extends BaseMapper<ElevatorBaseElement> {




    public List<ElevatorBaseElementVo> findAll(ElevatorBaseElement a);

}
