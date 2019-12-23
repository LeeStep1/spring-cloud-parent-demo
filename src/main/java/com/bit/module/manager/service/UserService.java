package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserLogin;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;

/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:44
 */
public interface UserService {
    /**
     * 后端管理员登陆
     * @param adminLogin
     * @return
     */
    BaseVo adminLogin(UserLogin adminLogin);

    /**
     * admin登出
     * @return
     */
    BaseVo adminLogout();

    /**
     * 用户新增
     * @return
     */
    BaseVo add(UserVo user);

    /**
     * 用户列表展示
     * @return
     */
    BaseVo findAll(PortalUserVo portalUserVo);

    /**
     * 修改用户信息（不带密码）
     * @return
     */
    BaseVo update(UserVo portalUser);

    /**
     * 删除用户
     * @return
     */
    BaseVo delete(Long id);

    /**
     * 重置用户密码
     * @author liyang
     * @date 2019-06-27
     * @param id :  用户ID
     * @param portalUser :  重置的密码
     * @return : BaseVo
     */
    BaseVo resetPwd(Long id,User portalUser);

    /**
     * 根据ID查询用户明细
     * @author liyang
     * @date 2019-06-27
     * @param id : 用户ID
     * @return : BaseVo
     */
    BaseVo findUser(Long id);

    /**
     * 修改密码
     * @author liyang
     * @date 2019-06-27
     * @param portalUserVo : 密码集合
     * @return : BaseVo
    */
    BaseVo updatePassword(PortalUserVo user);

    /**
     * @description: 角色
     * @author liyujun
     * @date 2019-12-20

     * @return : com.bit.base.vo.BaseVo
     */
    BaseVo findRoles();

}
