package com.nacosDemo.dao;

import com.nacosDemo.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    User selectUserById(@Param("id") Long id);
}
