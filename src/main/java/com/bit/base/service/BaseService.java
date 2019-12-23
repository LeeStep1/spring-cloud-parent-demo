package com.bit.base.service;

import com.alibaba.fastjson.JSON;
import com.bit.base.dto.UserInfo;
import com.bit.base.vo.BaseVo;
import com.bit.base.vo.SuccessVo;
import com.bit.utils.RequestThreadBinder;

/**
 * @Description:
 * @Author: mifei
 * @Date: 2018-10-18
 **/
public class BaseService {


	private static SuccessVo successVo = new SuccessVo();
	/**
	 * 得到成功Vo,不用每次都new,提高性能
	 * @return
	 */
	public BaseVo successVo(){
		return successVo;
	}

	/**
	 * 得到当前用户
	 * @return
	 */
	public UserInfo getCurrentUserInfo() {
		String user = RequestThreadBinder.getUser();
		UserInfo userInfo = (UserInfo) JSON.parseObject(user, UserInfo.class);
		return userInfo;
	}
}
