package com.bit.module.miniapp.service;

import com.bit.base.vo.BaseVo;
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
    public BaseVo wxUserLogin(WxUser wxUser);
}
