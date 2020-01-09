package com.bit.module.miniapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bit.base.dto.UserInfo;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.consts.RedisKey;
import com.bit.common.informationEnum.TidUrlEnum;
import com.bit.common.informationEnum.UserRoleEnum;
import com.bit.common.wx.WxLoginRs;
import com.bit.module.manager.bean.Company;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserRelRole;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.UserCompanyDao;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.dao.UserRoleDao;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;
import com.bit.module.miniapp.bean.WxUser;
import com.bit.module.miniapp.service.WxUserService;
import com.bit.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bit.common.informationEnum.UserStatusEnum.DISABLE_FLAG;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-19
 **/
@Service
@Slf4j
public class WxUserServiceImpl extends BaseService implements WxUserService  {


    @Value("${atToken.expire}")
    private Long atTokenExpire;

    @Autowired
    private WxUserComponent wxUserComponent;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserService userService;

    @Autowired
	private ProjectDao projectDao;
	@Autowired
	private UserCompanyDao userCompanyDao;

    /**
     * @description:  微信端登陆
     * @author liyujun
     * @date 2019-12-19
     * @param wxUser :
     * @return : void
     */
    @Override
    public BaseVo wxUserLogin(WxUser wxUser){

		if(!wxUser.getTid().equals(TidUrlEnum.TERMINALURL_WX.getTid())){
			throw  new RuntimeException("此端不支持");
		}
        //根据用户名密码查询用户是否存在
        UserVo portalUser = userDao.findByUsername(wxUser.getUserName(),null);

        if(portalUser == null){
            throw new BusinessException("用户不存在！");
        }

        if(Integer.valueOf(DISABLE_FLAG.getCode()).equals(portalUser.getStatus())){
            throw new BusinessException("该用户已被停用！");
        }

		//查询权限
		UserRelRole obj = new UserRelRole();
		obj.setUserId(portalUser.getId());
		List<UserRelRole> byParam = userRoleDao.findByParam(obj);
		if (CollectionUtils.isEmpty(byParam)){
			throw new BusinessException("未分配权限！");
		}

        if(portalUser!=null){

            //校验密码
            String pw = MD5Util.compute(wxUser.getPassWord() + portalUser.getSalt());
            if (!pw.equals(portalUser.getPassWord())) {
                throw new BusinessException("密码错误");
            }

			WxLoginRs rs= wxUserComponent.getSessionKeyAndOpenId(wxUser.getCode(),wxUser.getTid());
            //查询包括unionId在内的敏感信息
            JSONObject js = wxUserComponent.getUserInfo(wxUser.getEncryptedData(),rs.getSessionKey(),wxUser.getIv());

            User a =new User();
            a.setId(portalUser.getId());
            a.setOpenId(js.getString("openId"));
            userDao.update(a);

            UserInfo userInfo = new UserInfo();
            String token = UUIDUtil.getUUID();
            portalUser.setOpenId(js.getString("openId"));
            userInfo.setId(portalUser.getId());
            userInfo.setUserName(portalUser.getUserName());
            userInfo.setTid(wxUser.getTid());
            userInfo.setRealName(portalUser.getRealName());
			userInfo.setEmail(portalUser.getEmail());
			userInfo.setToken(token);
            userInfo.setRole(portalUser.getRoleId());

			//查询用户的公司
			Company company = userCompanyDao.getUserCompanyByUserId(portalUser.getId());
			if (company!=null){
				userInfo.setCompanyId(company.getId());
				userInfo.setCompanyName(company.getCompanyName());
			}else{
			    throw new BusinessException("无所属机构");
            }

            Integer tid = null;
            tid = TidUrlEnum.TERMINALURL_WX.getTid();
            String key1=RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN,String.valueOf(tid),token);
			String userJson = JSON.toJSONString(userInfo);
            cacheUtil.set(key1,userJson,atTokenExpire);

			//干掉之前的token
			UserRelRole userRelRoleByUserIdRoleId = userRoleDao.getUserRelRoleByUserIdRoleId(portalUser.getId(), null);
			if (userRelRoleByUserIdRoleId!=null){
				String tt = userRelRoleByUserIdRoleId.getToken();
				Map maps = new HashMap();
				if (StringUtil.isNotEmpty(tt)){
					maps = (Map) JSON.parse(tt);
					if (maps!=null){
						if (maps.get(TidUrlEnum.TERMINALURL_WX.getTid())!=null){
							String ss = maps.get(TidUrlEnum.TERMINALURL_WX.getTid()).toString();
							cacheUtil.del(ss);
						}
					}
				}

				maps.put(String.valueOf(TidUrlEnum.TERMINALURL_WX.getTid()),key1);
				//更新t_user_role 刷新token值
				UserRelRole userRelRole = new UserRelRole();
				userRelRole.setId(byParam.get(0).getId());

				String tokenkey = JSONObject.toJSONString(maps);
				userRelRole.setToken(tokenkey);
				userRoleDao.updateById(userRelRole);
			}

            Map map = new HashMap<>();
            map.put("token", token);
            map.put("id",portalUser.getId());
            map.put("username",portalUser.getUserName());
            map.put("realName",portalUser.getRealName());
            map.put("role",portalUser.getRoleId());
            map.put("openId",portalUser.getOpenId());
            //根据人查询项目 返回前端
			Project project = new Project();
			project.setCreateUserId(portalUser.getId());
			List<Project> byParamProject = projectDao.findByParam(project);
			map.put("project",byParamProject);
			map.put("companyId",company.getId());
			map.put("companyName",company.getCompanyName());

			BaseVo baseVo = new BaseVo();
            baseVo.setData(map);
            return baseVo;

        }else {
            throw new BusinessException("该用户不存在！");
        }
    }

    /**
     * 微信端登出
     * @return
     */
    @Override
    public BaseVo wxUserLogout() {
        userService.adminLogout();
        return successVo();
    }

	/**
	 * 密码修改
	 * @param portalUserVo
	 * @return
	 */
	@Override
	public BaseVo wxUpdatePassWord(PortalUserVo portalUserVo) {
		return userService.updatePassword(portalUserVo);
	}

	/**
	 * 邮箱修改
	 * @param portalUserVo
	 * @return
	 */
	@Override
	public BaseVo wxUpdateEmail(PortalUserVo portalUserVo) {
		Long id = getCurrentUserInfo().getId();
		UserVo userVo = new UserVo();
		userVo.setId(id);
		userVo.setEmail(portalUserVo.getEmail());
		userService.update(userVo);
		return successVo();
	}

	/**
	 * 微信用户信息返显
	 * @return
	 */
	@Override
	public BaseVo wxUserReflect() {
		Long id = getCurrentUserInfo().getId();
		User byId = userDao.findById(id);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(byId);
		return baseVo;
	}
}
