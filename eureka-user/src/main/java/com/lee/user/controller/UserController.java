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
    public void getAllUser(){
        List<Map<String, Object>> maps = userService.listMaps();
        System.out.println(maps);
    }

    @RequestMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.removeById(id);
    }

}
