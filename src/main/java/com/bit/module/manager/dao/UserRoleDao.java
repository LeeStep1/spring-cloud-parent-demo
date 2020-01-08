package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.UserRelRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	/**
	 * 根据用户id和角色id查询记录
	 * @param roleId
	 * @param userId
	 * @return
	 */
	UserRelRole getUserRelRoleByUserIdRoleId(@Param(value = "userId")Long userId,@Param(value = "roleId")Long roleId);

	/**
	 * 多参数查询
	 * @param userRelRole
	 * @return
	 */
	List<UserRelRole> findByParam(UserRelRole userRelRole);
}
