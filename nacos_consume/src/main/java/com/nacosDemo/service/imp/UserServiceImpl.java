package com.nacosDemo.service.imp;

import com.nacosDemo.bean.User;
import com.nacosDemo.dao.UserDao;
import com.nacosDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        User user = userDao.selectUserById(id);
        return user;
    }
}
