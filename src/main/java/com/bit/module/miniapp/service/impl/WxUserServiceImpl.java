package com.bit.module.miniapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bit.base.dto.UserInfo;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.consts.Const;
import com.bit.common.consts.RedisKey;
import com.bit.common.wx.WxLoginRs;
import com.bit.common.informationEnum.TidUrlEnum;
import com.bit.common.informationEnum.UserRoleEnum;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.vo.RefreshTokenVO;
import com.bit.module.miniapp.bean.WxUser;
import com.bit.module.miniapp.service.WxUserService;
import com.bit.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Value("${rtToken.expire}")
    private Long rtTokenExpire;

    @Autowired
    private WxUserComponent wxUserComponent;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private UserDao userDao;
    /**
     * @description:  微信端登陆
     * @author liyujun
     * @date 2019-12-19
     * @param wxUser :
     * @return : void
     */
    @Override
    public BaseVo  wxUserLogin(WxUser wxUser){
        //根据用户名密码查询用户是否存在
        User portalUser = userDao.findByUsername(wxUser.getUserName(), UserRoleEnum.RESIDENT.getRoleId());

        if(portalUser == null){
            throw new BusinessException("用户不存在！");
        }

        if(Integer.valueOf(DISABLE_FLAG.getCode()).equals(portalUser.getStatus())){
            throw new BusinessException("该用户已被停用！");
        }

        if(portalUser!=null){

            //校验密码
            String pw = MD5Util.compute(wxUser.getPassWord() + portalUser.getSalt());
            if (!pw.equals(portalUser.getPassWord())) {
                throw new BusinessException("密码错误");
            }

             WxLoginRs rs= wxUserComponent.getSessionKeyAndOpenId(wxUser.getCode(),wxUser.getTerminalId());
            //查询包括unionId在内的敏感信息
            JSONObject js = wxUserComponent.getUserInfo(wxUser.getEncrypteData(),rs.getSessionKey(),wxUser.getIv());
            User a =new User();
            a.setId(portalUser.getId());
            // a.setOpenId(rs.getOpenid());
            UserInfo userInfo = new UserInfo();
            String token = UUIDUtil.getUUID();
            a.setToken(token);
            a.setOpenId(js.getString("openId"));
            portalUser.setOpenId(js.getString("openId"));
            userDao.update(a);
            userInfo.setId(portalUser.getId());
            userInfo.setUserName(portalUser.getUserName());
            userInfo.setTid(wxUser.getTerminalId());
            userInfo.setRealName(portalUser.getRealName());
            String userJson = JSON.toJSONString(userInfo);
            Integer tid = null;
            tid = TidUrlEnum.TERMINALURL_RESIDENT.getTid();
            String key1=RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN,String.valueOf(tid),token);
            cacheUtil.set(key1,userJson,atTokenExpire);
          //  cacheUtil.set(Const.TOKEN_PREFIX+ tid+":"+token, userJson,Long.valueOf(atTokenExpire));

            //rt token 失效时间为7天
            String rtToken = UUIDUtil.getUUID();
            RefreshTokenVO refreshTokenVO = new RefreshTokenVO();
            refreshTokenVO.setUserInfo(userInfo);
            refreshTokenVO.setAtKey(key1);
            String rtJson = JSON.toJSONString(refreshTokenVO);
            cacheUtil.set(RedisKeyUtil.getRedisKey(RedisKey.REFRESHTOKEN_TOKEN_PREFIX,String.valueOf(tid),rtToken),rtJson,rtTokenExpire);
            Map map = new HashMap<>();
            map.put("token", token);
            map.put("refreshToken", rtToken);
            map.put("id",portalUser.getId());
            map.put("username",portalUser.getUserName());
            map.put("realName",portalUser.getRealName());
           // map.put("role",portalUser.getRoleId());
            map.put("openId",portalUser.getOpenId());
            BaseVo baseVo = new BaseVo();
            baseVo.setData(map);
            return baseVo;

        }else {
            throw new BusinessException("该用户不存在！");
        }
    }
}
