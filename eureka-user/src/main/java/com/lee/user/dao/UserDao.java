package com.lee.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.user.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {

    User selectUserById(@Param("id") Long id);
}
