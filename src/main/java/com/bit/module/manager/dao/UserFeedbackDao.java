package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserFeedback;
import com.bit.module.manager.vo.ElevatorTypePageVO;
import com.bit.module.manager.vo.ElevatorTypeVO;
import com.bit.module.manager.vo.UserFeedbackPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
@Repository
public interface UserFeedbackDao extends BaseMapper<UserFeedback> {


    /**
     * 电梯类型列表查询
     * @return
     */
    IPage<UserFeedback> listPage(@Param("pg")Page<UserFeedback> page, @Param("userFeedbackPageVO") UserFeedbackPageVO userFeedbackPageVO);
}
