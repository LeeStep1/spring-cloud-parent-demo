package com.bit.module.miniapp.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.UserLogin;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.miniapp.bean.WxUser;
import com.bit.module.miniapp.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	/**
	 * 微信端登出
	 * @return
	 */
    @GetMapping("/logout")
    public BaseVo adminLogout(){
		return wxUserService.wxUserLogout();
    }

	/**
	 * 密码修改
	 * @param portalUserVo
	 * @return
	 */
	@PostMapping("/wxUpdatePassWord")
    public BaseVo wxUpdatePassWord(@RequestBody PortalUserVo portalUserVo){
    	return wxUserService.wxUpdatePassWord(portalUserVo);
	}

	/**
	 * 邮箱修改
	 * @return
	 */
	@PostMapping("/wxUpdateEmail")
	public BaseVo wxUpdateEmail(@RequestBody PortalUserVo portalUserVo){
		return wxUserService.wxUpdateEmail(portalUserVo);
	}
}
