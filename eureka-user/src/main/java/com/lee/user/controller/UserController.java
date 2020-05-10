package com.lee.user.controller;

import com.lee.user.bean.User;
import com.lee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    public UserService userService;

    /**
     * 获取根据ID获取单个user
     * @param id
     * @return
     */
    @GetMapping("/getUser/{id}")
    public User getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return user ;
    }

    /**
     * 批量添加
     * @param userList
     * @return
     */
    @PostMapping("/batchAdd")
    public boolean batchUser(@RequestBody List<User> userList){
        return userService.saveBatch(userList);
    }
}
