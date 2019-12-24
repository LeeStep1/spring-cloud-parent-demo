package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.UserRelRole;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/24 8:41
 **/
@Repository
public interface UserRoleDao extends BaseMapper<UserRelRole>{
	/**
	 * 根据userid更新token
	 * @param userRelRole
	 */
	void updateTokenByUserId(UserRelRole userRelRole);
}
