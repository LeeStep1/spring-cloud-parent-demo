package com.bit.module.manager.controller;

import com.bit.base.exception.BusinessException;
import com.bit.base.vo.BaseVo;

import com.bit.base.vo.SuccessVo;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserLogin;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:37
 */
@RestController
@RequestMapping("/manager/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private UserDao userDao;


    /**
     * 管理端登陆
     * @param adminLogin
     * @return
     */
    @PostMapping(value = "/login")
    public BaseVo adminLogin(@RequestBody UserLogin adminLogin){
        return userService.adminLogin(adminLogin);
    }

    /**
     * 管理端登出
     * @return
     */
    @GetMapping(value = "/logout")
    public BaseVo adminLogout(){
        return userService.adminLogout();
    }

    /**
     * 用户新增
     * @author liyang
     * @date 2019-06-11
     * @param user : 用户详情
     * @return : BaseVo
    */
    @PostMapping("/add")
    public BaseVo addUser(@Valid @RequestBody UserVo user){
        return userService.add(user);
    }

    /**
     * 用户列表展示
     * @author liyang
     * @date 2019-06-11
     * @return : BaseVo
     */
    @PostMapping("/findAll")
    public BaseVo findAll(@RequestBody PortalUserVo portalUserVo){
        return userService.findAll(portalUserVo);
    }

    /**
     * 修改用户信息（不带密码）
     * @author liyang
     * @date 2019-06-11
     * @return : BaseVo
     */
    @PostMapping("/update")
    public BaseVo update(@RequestBody UserVo portalUser){
        return userService.update(portalUser);
    }

    /**
     * 删除用户
     * @author liyang
     * @date 2019-06-11
     * @return : BaseVo
     */
    @DeleteMapping("/delete/{id}")
    public BaseVo delete(@PathVariable Long id){
        return userService.delete(id);
    }

    /**
     * 重置用户密码
     * @author liyang
     * @date 2019-06-27
     * @param id :  用户ID
     * @param portalUser :  重置的密码
     * @return : BaseVo
    */
    @PostMapping("/reset/{id}")
    public BaseVo resetPwd(@PathVariable(value = "id") Long id, @RequestBody User portalUser){
        return userService.resetPwd(id,portalUser);
    }

    /**
     * 根据ID查询用户明细
     * @author liyang
     * @date 2019-06-27
     * @param id : 用户ID
     * @return : BaseVo
    */
    @GetMapping("/find/{id}")
    public BaseVo findUser(@PathVariable(value = "id") Long id){
        return userService.findUser(id);
    }

    /**
     * 密码修改
     * @author liyang
     * @date 2019-06-27
     * @param portalUserVo : 密码集合
     * @return : BaseVo
    */
    @PostMapping("/updatePwd")
    public BaseVo updatePwd(@RequestBody PortalUserVo portalUserVo){

        return userService.updatePassword(portalUserVo);

    }

    /**
     * 获取角色列表
     * @author lyj
     * @date 2019-06-27
     * @return : BaseVo
     */
    @GetMapping("/roles")
    public BaseVo findRoles(){
        return userService.findRoles();
    }

    /**
     * 停用启用用户
     * @param userId
	 * @param status
     * @return
     */
    @GetMapping("/status/{userId}/{status}")
    public BaseVo statusUser(@PathVariable(value = "userId")Long userId,@PathVariable(value = "status")Integer status){
		return userService.statusUser(userId,status);
    }


    /**
     * 校验用户名
     * @param userName
     * @return
     */
    @GetMapping("/username/{userName}")
    public BaseVo checkUserName(@PathVariable(value = "userName")String userName){

        Map cod=new HashMap(1);
        cod.put("user_name",userName);
        List<User> list=userDao.selectByMap(cod);
        if(list.size()>0){
            throw new BusinessException("账户名已存在");
        }else{
            return new SuccessVo();
        }




    }
}
