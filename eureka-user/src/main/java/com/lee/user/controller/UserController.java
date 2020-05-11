package com.lee.user.controller;

import com.lee.user.bean.User;
import com.lee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/getAllUser/all")
    public Object getAllUser(){
        return userService.listMaps();
    }

    @RequestMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.removeById(id);
    }



    @GetMapping("/updateUserById/{id}")
    public void updateUserById(@PathVariable long id){
        User user = new User();
        user.setId(id);
        user.setAge(28);
        userService.updateById(user);
    }

}
