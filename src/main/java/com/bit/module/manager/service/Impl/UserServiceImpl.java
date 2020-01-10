package com.bit.module.manager.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.dto.UserInfo;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.consts.Const;
import com.bit.common.consts.RedisKey;
import com.bit.common.informationEnum.TidUrlEnum;
import com.bit.common.informationEnum.UserRoleEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.CompanyDao;
import com.bit.module.manager.dao.UserCompanyDao;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.dao.UserRoleDao;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;
import com.bit.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.bit.common.informationEnum.UserStatusEnum.DISABLE_FLAG;
import static com.bit.common.informationEnum.UserStatusEnum.USING_FLAG;


/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:44
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private CacheUtil cacheUtil;

	@Value("${atToken.expire}")
	private Long atTokenExpire;


	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 用户相关dao
	 */
	@Autowired
	private UserDao userDao;

	/**
	 * 用户关联角色
	 */
	@Autowired
	private UserCompanyDao userCompanyDao;


	/**
	 * 公司基础信息
	 */
	@Autowired
	private CompanyDao companyDao;

	/**
	 * 后台管理用户登陆
	 *
	 * @param adminLogin
	 * @return
	 */
	@Override
	public BaseVo adminLogin(UserLogin adminLogin) {

		//根据用户名密码查询用户是否存在
		if (!adminLogin.getTid().equals(TidUrlEnum.TERMINALURL_MANAGER.getTid())) {
			throw new RuntimeException("此端不支持");
		}
		UserVo portalUser = userDao.findByUsername(adminLogin.getUserName(), null);

		if (portalUser == null) {
			throw new BusinessException("用户不存在");
		}

		if (Integer.valueOf(DISABLE_FLAG.getCode()).equals(portalUser.getStatus())) {
			throw new BusinessException("该用户已被停用！");
		}

		//查询权限
		UserRelRole obj = new UserRelRole();
		obj.setUserId(portalUser.getId());
		List<UserRelRole> byParam = userRoleDao.findByParam(obj);
		if (CollectionUtils.isEmpty(byParam)) {
			throw new BusinessException("未分配权限！");
		} else {
			for (UserRelRole userRelRole : byParam) {
				if (!userRelRole.getRoleId().equals(UserRoleEnum.BOSS.getRoleId()) &&
						!userRelRole.getRoleId().equals(UserRoleEnum.SUPPORT.getRoleId())) {
					throw new BusinessException("无权限登录！");
				}
			}
		}

		if (portalUser != null) {

			//校验密码
			String pw = MD5Util.compute(adminLogin.getPassWord() + portalUser.getSalt());
			if (!pw.equals(portalUser.getPassWord())) {
				throw new BusinessException("密码错误");
			}
			UserInfo userInfo = new UserInfo();
			String token = UUIDUtil.getUUID();

			userInfo.setToken(token);
			userInfo.setId(portalUser.getId());
			userInfo.setUserName(portalUser.getUserName());
			userInfo.setTid(adminLogin.getTid());
			userInfo.setRealName(portalUser.getRealName());
			userInfo.setRole(portalUser.getRoleId());
			userInfo.setRoleName(portalUser.getRoleName());
			userInfo.setEmail(portalUser.getEmail());
			userInfo.setRole(portalUser.getRoleId());
			//查询用户的公司
			Company company = userCompanyDao.getUserCompanyByUserId(portalUser.getId());
			if (company != null) {
				userInfo.setCompanyId(company.getId());
				userInfo.setCompanyName(company.getCompanyName());
			}

			String key1 = RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN, String.valueOf(adminLogin.getTid()), token);
			String userJson = JSON.toJSONString(userInfo);
			cacheUtil.set(key1, userJson, atTokenExpire);
			//干掉之前的token
			UserRelRole userRelRoleByUserIdRoleId = userRoleDao.getUserRelRoleByUserIdRoleId(portalUser.getId(), null);
			if (userRelRoleByUserIdRoleId != null) {
				String tt = userRelRoleByUserIdRoleId.getToken();
				Map maps = new HashMap();
				if (StringUtil.isNotEmpty(tt)){
					maps = (Map) JSON.parse(tt);
					if (maps != null) {
						if (maps.get(TidUrlEnum.TERMINALURL_MANAGER.getTid())!=null){
							String ss = maps.get(TidUrlEnum.TERMINALURL_MANAGER.getTid()).toString();
							cacheUtil.del(ss);
						}
					}
				}
				maps.put(String.valueOf(TidUrlEnum.TERMINALURL_MANAGER.getTid()), key1);
				//更新t_user_role 刷新token值
				UserRelRole userRelRole = new UserRelRole();
				userRelRole.setId(byParam.get(0).getId());

				String tokenkey = JSONObject.toJSONString(maps);
				userRelRole.setToken(tokenkey);
				userRoleDao.updateById(userRelRole);
			}


			Map map = new HashMap<>();
			map.put("token", token);
			map.put("id", portalUser.getId());
			map.put("username", portalUser.getUserName());
			map.put("realName", portalUser.getRealName());
			map.put("roleId", portalUser.getRoleId());
			map.put("roleName", portalUser.getRoleName());
			map.put("companyId", company.getId());
			map.put("companyName", company.getCompanyName());
			BaseVo baseVo = new BaseVo();
			baseVo.setData(map);
			return baseVo;

		} else {
			throw new BusinessException("该用户不存在！");
		}
	}

	/**
	 * 后台管理端登出
	 *
	 * @return
	 */
	@Override
	public BaseVo adminLogout() {

		//获取token
		UserInfo userInfo = getCurrentUserInfo();
		Integer tid = userInfo.getTid();
		//干掉之前的token
		UserRelRole userRelRoleByUserIdRoleId = userRoleDao.getUserRelRoleByUserIdRoleId(userInfo.getId(), null);
		if (userRelRoleByUserIdRoleId != null) {
			String tt = userRelRoleByUserIdRoleId.getToken();
			Map maps = new HashMap();
			if (StringUtil.isNotEmpty(tt)){
				maps = (Map) JSON.parse(tt);
				if (maps != null) {
					String ss = "";
					if (TidUrlEnum.TERMINALURL_MANAGER.getTid()== tid.intValue()){
						ss = maps.get(TidUrlEnum.TERMINALURL_MANAGER.getTid()).toString();
					}
					if (TidUrlEnum.TERMINALURL_WX.getTid()== tid.intValue()){
						ss = maps.get(TidUrlEnum.TERMINALURL_WX.getTid()).toString();
					}
					cacheUtil.del(ss);
				}
			}
			//去掉maps中此平台的值
			if (TidUrlEnum.TERMINALURL_MANAGER.getTid()== tid.intValue()){
				maps.remove(String.valueOf(TidUrlEnum.TERMINALURL_MANAGER.getTid()));
			}
			if (TidUrlEnum.TERMINALURL_WX.getTid()== tid.intValue()){
				maps.remove(String.valueOf(TidUrlEnum.TERMINALURL_WX.getTid()));
			}

			//更新t_user_role 刷新token值
			UserRelRole userRelRole = new UserRelRole();
			userRelRole.setUserId(userInfo.getId());
			String tokenkey = "";
			if (maps.size()==0){
				tokenkey = "";
			}else {
				tokenkey = JSONObject.toJSONString(maps);
			}
			userRelRole.setToken(tokenkey);
			userRoleDao.updateTokenByUserId(userRelRole);
		}

		return successVo();

	}

	/**
	 * 用户管理新增
	 *
	 * @param user : 用户详情
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-11
	 */
	@Override
	@Transactional
	public BaseVo add(UserVo user) {

		//根据用户名密码查询用户是否存在
		User portalUser = userDao.findByUsername(user.getUserName(), null);

		if (portalUser == null) {
			//随机密码盐
			String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);

			//密码和盐=新密码  md5加密新密码
			String password = MD5Util.compute(user.getPassWord() + salt);
			user.setPassWord(password);
			user.setSalt(salt);

			//状态  0 停用  1 启用
			user.setStatus(USING_FLAG.getCode());

			//新增时间
			user.setInsertTime(new Date());

			//修改时间
			user.setUpdateTime(new Date());

			User aa = new User();
			BeanUtils.copyProperties(user, aa);
			userDao.addUser(user);
			user.getRoleIds().forEach(c -> {
				UserRelRole a = new UserRelRole();
				a.setUserId(user.getId());
				a.setRoleId(c.getId());
				userDao.addUserRelRole(a);

			});
			UserCompany uc = new UserCompany();
			uc.setUserId(user.getId());
			uc.setCompanyId(user.getCompanyId());
			userCompanyDao.insert(uc);
			return successVo();
		} else {
			throw new BusinessException("该用户已存在！");
		}


	}

	/**
	 * 用户列表展示
	 *
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-11
	 */
	@Override
	public BaseVo findAll(PortalUserVo portalUserVo) {

		Page<UserVo> page = new Page<>(portalUserVo.getPageNum(), portalUserVo.getPageSize());
		IPage<UserVo> userIPage = userDao.findAll(page, portalUserVo);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(userIPage);
		return baseVo;
	}

	/**
	 * 修改用户信息（不带密码）
	 *
	 * @param portalUser : 用户详情
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-27
	 */
	@Override
	@Transactional
	public BaseVo update(UserVo portalUser) {

		//如果是修改密码
		if (StringUtil.isNotEmpty(portalUser.getPassWord())) {

			//随机密码盐
			String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);

			//密码和盐=新密码  md5加密新密码
			String password = MD5Util.compute(portalUser.getPassWord() + salt);
			portalUser.setPassWord(password);
			portalUser.setSalt(salt);
		}

		portalUser.setUpdateTime(new Date());
		//如果传进来的role集合不为空 就更新角色
		if (CollectionUtils.isNotEmpty(portalUser.getRoleIds())) {
			userDao.deleteUserRole(portalUser.getId());
			portalUser.getRoleIds().forEach(c -> {
				UserRelRole a = new UserRelRole();
				a.setUserId(portalUser.getId());
				a.setRoleId(c.getId());
				userDao.addUserRelRole(a);
			});
			Map cd = new HashMap();
			cd.put("user_id", portalUser.getId());
			List<UserCompany> ucList = userCompanyDao.selectByMap(cd);
			if (ucList.size() > 0) {
				UserCompany aa = ucList.get(0);
				aa.setCompanyId(portalUser.getCompanyId());
				userCompanyDao.updateUserCompany(aa);
			}

		}

		userDao.update(portalUser);

		return successVo();
	}

	/**
	 * 删除用户
	 *
	 * @param id : 用户ID
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-11
	 */
	@Override
	public BaseVo delete(Long id) {
		userDao.delete(id);
		//删除token
		delUserRoleToken(id);
		//删除角色
		userDao.deleteUserRole(id);
		Map cod = new HashMap();
		cod.put("user_id", id);
		userCompanyDao.deleteByMap(cod);

		return successVo();
	}

	/**
	 * 根据用户id删除token
	 *
	 * @param userId
	 */
	private void delUserRoleToken(Long userId) {
		//查询用户token
		UserRelRole param = new UserRelRole();
		param.setUserId(userId);
		QueryWrapper<UserRelRole> userRelRoleQueryWrapper = new QueryWrapper<>();
		userRelRoleQueryWrapper.setEntity(param);
		List<UserRelRole> userRelRoles = userRoleDao.selectList(userRelRoleQueryWrapper);

		if (CollectionUtils.isNotEmpty(userRelRoles)) {
			for (UserRelRole userRelRole : userRelRoles) {
				//得到token放进数组
				if (StringUtil.isNotEmpty(userRelRole.getToken())) {
					Map maps = (Map) JSON.parse(userRelRole.getToken());
					if (maps!=null){
						//删除全平台token
						if (maps.get(TidUrlEnum.TERMINALURL_WX.getTid())!=null){
							cacheUtil.del(maps.get(TidUrlEnum.TERMINALURL_WX.getTid()).toString());
						}
						if (maps.get(TidUrlEnum.TERMINALURL_MANAGER.getTid())!=null){
							cacheUtil.del(maps.get(TidUrlEnum.TERMINALURL_MANAGER.getTid()).toString());
						}
						//清除map
						maps.clear();
					}
				}
			}
		}
	}

	/**
	 * 重置用户密码
	 *
	 * @param id         : 用户ID
	 * @param portalUser : 用户密码
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-27
	 */
	@Override
	public BaseVo resetPwd(Long id, User portalUser) {
		User a = userDao.findByUserId(id);
		if (a == null) {
			throw new RuntimeException("无此用户");
		}
		//随机密码盐
		String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);
		//密码和盐=新密码  md5加密新密码
		String newPwd = MD5Util.compute(portalUser.getPassWord() + salt);
		a.setPassWord(newPwd);
		a.setSalt(salt);
		a.setId(id);
		a.setUpdateTime(new Date());
		userDao.update(a);
		return successVo();
	}

	/**
	 * 根据ID查询用户详情
	 *
	 * @param id :用户ID
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-27
	 */
	@Override
	public BaseVo findUser(Long id) {

		User user = userDao.findUserSql(id);
		List<Role> list = userDao.findRoleByUserId(id);
		Map cod = new HashMap();
		cod.put("user_id", id);
		List<UserCompany> listc = userCompanyDao.selectByMap(cod);
		UserVo vo = new UserVo();
		if (listc.size() > 0) {
			vo.setCompanyId(listc.get(0).getCompanyId());
			Company company = companyDao.selectById(listc.get(0).getCompanyId());
			vo.setCompanyName(company.getCompanyName());
		}
		BeanUtils.copyProperties(user, vo);
		vo.setRoleIds(list);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(vo);
		return baseVo;
	}

	/**
	 * 修改密码
	 *
	 * @param portalUserVo : 密码集合
	 * @return : BaseVo
	 * @author liyang
	 * @date 2019-06-27
	 */
	@Override
	public BaseVo updatePassword(PortalUserVo portalUserVo) {

		UserInfo userInfo = getCurrentUserInfo();

		//根据ID获取用户
		User pu = userDao.findByUserId(userInfo.getId());

		//校验密码
		String pw = MD5Util.compute(portalUserVo.getOldPassword() + pu.getSalt());
		if (!pw.equals(pu.getPassWord())) {
			throw new BusinessException("旧密码错误");
		}

		//修改新密码
		User portalUserUpdate = new User();

		//随机密码盐
		String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);

		//密码和盐=新密码  md5加密新密码
		String password = MD5Util.compute(portalUserVo.getPassword() + salt);
		portalUserUpdate.setPassWord(password);
		portalUserUpdate.setSalt(salt);
		portalUserUpdate.setId(userInfo.getId());

		userDao.update(portalUserUpdate);

		return successVo();
	}

	/**
	 * @return : com.bit.base.vo.BaseVo
	 * @description: 角色
	 * @author liyujun
	 * @date 2019-12-20
	 */
	@Override
	public BaseVo findRoles() {
		BaseVo vo = new BaseVo();
		vo.setData(userDao.findRoles());
		return vo;
	}

	/**
	 * 停用启用用户
	 *
	 * @param userId
	 * @param status
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo statusUser(Long userId, Integer status) {
		User byId = userDao.findById(userId);
		if (byId == null) {
			throw new BusinessException("用户不存在");
		}
		User user = new User();
		user.setId(userId);
		user.setStatus(status);
		user.setUpdateTime(new Date());
		//停用用户
		userDao.update(user);
		//如果停用
		if (status.equals(DISABLE_FLAG.getCode())) {
			//删除token
			delUserRoleToken(userId);

			UserRelRole userRelRole = new UserRelRole();
			userRelRole.setToken("");
			userRelRole.setUserId(userId);
			//去除用户token
			userRoleDao.updateTokenByUserId(userRelRole);
		}

		return successVo();
	}


}
