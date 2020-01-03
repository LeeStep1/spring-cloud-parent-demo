package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.equation.bean.Equation;
import com.bit.module.manager.bean.Role;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserRelRole;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User管理的Dao
 * @author 
 *
 */
@Repository
public interface UserDao extends BaseMapper<User> {

	/**
	 * 根据条件查询User
	 * @param portalUser
	 * @return
	 */
	List<User> findByConditionPage(User portalUser);


	/**
	 * 查询所有User
	 * @return
	 */
	Page<UserVo> findAll(@Param("pg") Page<UserVo> page, @Param("portalUserVo") PortalUserVo portalUserVo);

	/**
	 * 通过主键查询单个User
	 * @param id
	 * @return
	 */
	User findById(@Param(value = "id") Long id);

	/**
	 * 保存User新方法
	 * @param portalUser
	 */
	void addUser(User portalUser);

	/**
	 * 保存角色与用户关联表新方法
	 * @param userRelRole
	 */
	void addUserRelRole(UserRelRole userRelRole);

	/**
	 * 更新User
	 * @param portalUser
	 */
	void update(User portalUser);

	/**
	 * 删除User
	 * @param id
	 */
	void delete(@Param(value = "id") Long id);

	/**
	 * 删除角色与用户关系
	 * @param userId
	 */
	void deleteUserRole(@Param(value = "userId") Long userId);
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	UserVo findByUsername(@Param(value = "userName") String username, @Param(value = "role")Integer role);

	/**
	 * 根据ID查询用户详情
	 * @param id 用户ID
	 * @return
	 */
	User findUserSql(Long id);

	/**
	 * 根据ID查询用户
	 * @param id 用户ID
	 * @return
	 */
	User findByUserId(Long id);


	/**
	 * @description:
	 * @author liyujun
	 * @date 2019-12-20
	 * @param userId :
	 * @return : java.util.List<com.bit.module.manager.bean.Role>
	 */
	List<Role>findRoleByUserId (@Param(value = "userId")Long userId);


	List<Role> findRoles();


}
