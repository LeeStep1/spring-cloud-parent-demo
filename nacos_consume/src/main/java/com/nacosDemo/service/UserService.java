package com.nacosDemo.service;

import com.nacosDemo.bean.User;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
public interface UserService {

    User getUserById(Long id);
}
