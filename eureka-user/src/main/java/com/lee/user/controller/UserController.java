package com.lee.user.controller;

import com.lee.user.bean.User;
import com.lee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @RequestMapping("/getUser/{id}")
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

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/getAllUser/all")
    public Object getAllUser(){
        return userService.listMaps();
    }

    /**
     * 根据ID删除某一个用户
     * @param id
     */
    @RequestMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.removeById(id);
    }


    /**
     * 根据ID修改某个用户
     * @param id
     */
    @GetMapping("/updateUserById/{id}")
    public void updateUserById(@PathVariable long id){
        User user = new User();
        user.setId(id);
        user.setAge(28);
        userService.updateById(user);
    }

    /**
     * 发送异步消息
     * @return
     */
    @GetMapping("/chainUpdateUser")
    public String chainUpdateUser(){

        return userService.chainUpdateUser();

    }

    /**
     * 发送事务消息
     * @return
     */
    @GetMapping("/chainUpdateUserByTransaction")
    public String chainUpdateUserByTransaction(){

        return userService.chainUpdateUserByTransaction();

    }
}
