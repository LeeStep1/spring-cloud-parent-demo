package com.bit.module.miniapp.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.miniapp.bean.WxUser;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-19
 **/
public interface WxUserService {

    /**
     * @description:  微信端登陆
     * @author liyujun
     * @date 2019-12-19
     * @param wxUser :
     * @return : void
     */
    BaseVo wxUserLogin(WxUser wxUser);

    /**
     * 微信端登出
     * @return
     */
    BaseVo wxUserLogout();
    /**
     * 密码修改
     * @param portalUserVo
     * @return
     */
    BaseVo wxUpdatePassWord(PortalUserVo portalUserVo);

	/**
	 * 邮箱修改
	 * @param portalUserVo
	 * @return
	 */
	BaseVo wxUpdateEmail(PortalUserVo portalUserVo);
}
