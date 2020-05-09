package com.nacosDemo.controller;

import com.nacosDemo.bean.User;
import com.nacosDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return user ;
    }
}
