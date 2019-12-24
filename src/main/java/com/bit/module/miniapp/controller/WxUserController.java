package com.bit.module.miniapp.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.UserLogin;
import com.bit.module.manager.service.UserService;
import com.bit.module.miniapp.bean.WxUser;
import com.bit.module.miniapp.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-19
 **/
@RestController
@RequestMapping("/wx/user")
public class WxUserController {

    @Autowired
    private  WxUserService wxUserService;

    /**
     * admin登陆
     * @param wxUser   userName,passWord,code ,iv，encrypteData，signature
     *
     * @return
     */
    @PostMapping(value = "/login")
    public BaseVo adminLogin(@RequestBody WxUser wxUser){

        return wxUserService.wxUserLogin(wxUser);

    }
}
