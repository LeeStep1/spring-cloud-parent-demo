package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.miniapp.bean.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Repository
public interface OptionsDao  extends BaseMapper <Options> {


  List<Options> findOptionByElevatorType ( @Param("elevatorTypeId") Long elevatorTypeId,@Param("optionType") Integer optionType);


}
