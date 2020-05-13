package com.lee.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.user.bean.User;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
public interface UserService extends IService<User>{

    User getUserById(Long id);

    String chainUpdateUser();
}
