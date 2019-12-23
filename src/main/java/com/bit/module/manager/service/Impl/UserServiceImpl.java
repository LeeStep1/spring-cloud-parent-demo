package com.bit.module.manager.service.Impl;

import com.alibaba.fastjson.JSON;
import com.bit.base.dto.UserInfo;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.consts.Const;
import com.bit.common.consts.RedisKey;
import com.bit.common.informationEnum.TidUrlEnum;
import com.bit.common.informationEnum.UserRoleEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.UserDao;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.RefreshTokenVO;
import com.bit.module.manager.vo.UserVo;
import com.bit.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
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
    private String atTokenExpire;

    @Value("${rtToken.expire}")
    private String rtTokenExpire;

    /**
     * 用户相关dao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 微信登录工具
     */
    @Autowired
    private WxUserComponent wxUserComponent;

    /**
     * 后台管理用户登陆
     * @param adminLogin
     * @return
     */
    @Override
    public BaseVo adminLogin(UserLogin adminLogin) {

        //根据用户名密码查询用户是否存在
        if(!adminLogin.getTid().equals(TidUrlEnum.TERMINALURL_MANAGER.getTid())){
              throw  new RuntimeException("此端不支持");
        }
        UserVo portalUser = userDao.findByUsername(adminLogin.getUserName(), UserRoleEnum.MANAGER.getRoleId());

        if(portalUser == null){
            throw new BusinessException("用户不存在或无此权限");
        }
        if(Integer.valueOf(DISABLE_FLAG.getCode()).equals(portalUser.getStatus())){
            throw new BusinessException("该用户已被停用！");
        }

        if(portalUser!=null){

            //校验密码
            String pw = MD5Util.compute(adminLogin.getPassWord() + portalUser.getSalt());
            if (!pw.equals(portalUser.getPassWord())) {
                throw new BusinessException("密码错误");
            }
            User a =new User();
            a.setId(portalUser.getId());
            UserInfo userInfo = new UserInfo();
            String token = UUIDUtil.getUUID();

            //userInfo.setToken(token);
            userInfo.setId(portalUser.getId());
            userInfo.setUserName(portalUser.getUserName());
            userInfo.setTid(adminLogin.getTid());
            userInfo.setRealName(portalUser.getRealName());
            userInfo.setRole(portalUser.getRoleId());
            userInfo.setRoleName(portalUser.getRoleName());
            String userJson = JSON.toJSONString(userInfo);
            Integer tid = null;
            tid = TidUrlEnum.TERMINALURL_MANAGER.getTid();
            String key1=RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN,String.valueOf(adminLogin.getTid()),token);
            a.setToken(key1);
            userDao.update(a);
            cacheUtil.set(key1,userJson,Long.valueOf(rtTokenExpire));
           // cacheUtil.set(Const.TOKEN_PREFIX+ tid+":"+token, userJson,Long.valueOf(atTokenExpire));
            //rt token 失效时间为7天
            String rtToken = UUIDUtil.getUUID();
            RefreshTokenVO refreshTokenVO = new RefreshTokenVO();
            refreshTokenVO.setUserInfo(userInfo);
            refreshTokenVO.setAtKey(key1);
            String rtJson = JSON.toJSONString(refreshTokenVO);
            cacheUtil.set(RedisKeyUtil.getRedisKey(RedisKey.REFRESHTOKEN_TOKEN_PREFIX,String.valueOf(adminLogin.getTid()),rtToken),rtJson,Long.valueOf(rtTokenExpire));
           // cacheUtil.set(Const.REFRESHTOKEN_TOKEN_PREFIX + adminLogin.getTid() + ":" + rtToken, rtJson, Long.valueOf(rtTokenExpire));

            Map map = new HashMap<>();
            map.put("token", token);
            map.put("refreshToken", rtToken);
            map.put("id",portalUser.getId());
            map.put("username",portalUser.getUserName());
            map.put("realName",portalUser.getRealName());
            map.put("roleId",portalUser.getRoleId());
            map.put("roleName",portalUser.getRoleName());
            BaseVo baseVo = new BaseVo();
            baseVo.setData(map);
            return baseVo;

        }else {
            throw new BusinessException("该用户不存在！");
        }
    }

    /**
     * 后台管理端登出
     * @return
     */
    @Override
    public BaseVo adminLogout() {

        //获取token
        UserInfo userInfo = getCurrentUserInfo();
        String token = userInfo.getToken();
        String terminalId = userInfo.getTid().toString();
        String key= RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN,terminalId,token);
        User a=new User();
        a.setId(userInfo.getId());
        a.setToken("-1");
        userDao.update(a);
        cacheUtil.del(key);
        return successVo();

    }

    /**
     * 用户管理新增
     * @author liyang
     * @date 2019-06-11
     * @param user : 用户详情
     * @return : BaseVo
    */
    @Override
    @Transactional
    public BaseVo add(UserVo user) {

        //根据用户名密码查询用户是否存在
        User portalUser = userDao.findByUsername(user.getUserName(),null);

        if(portalUser == null){
            //随机密码盐
            String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);

            //密码和盐=新密码  md5加密新密码
            String password= MD5Util.compute(user.getPassWord() + salt);
            user.setPassWord(password);
            user.setSalt(salt);

            //状态  0 启用  1 停用
            user.setStatus(USING_FLAG.getCode());

            //新增时间
            user.setInsertTime(new Date());

            //修改时间
            user.setUpdateTime(new Date());

            User aa=new User ();
            BeanUtils.copyProperties(user ,aa);
            userDao.addUser(user);
            user.getRoleIds().forEach(c->{
                UserRelRole a=new UserRelRole();
                a.setUserId(user.getId());
                a.setRoleId(c.getId());
                userDao.addUserRelRole(a);
            });
            return successVo();
        }else {
            throw new BusinessException("该用户已存在！");
        }


    }

    /**
     * 用户列表展示
     * @author liyang
     * @date 2019-06-11
     * @return : BaseVo
    */
    @Override
    public BaseVo findAll(PortalUserVo portalUserVo) {
        PageHelper.startPage(portalUserVo.getPageNum(), portalUserVo.getPageSize());
        List<User> portalUserLIst = userDao.findAll(portalUserVo);
        PageInfo<User> pageInfo = new PageInfo<User>(portalUserLIst);

        BaseVo baseVo = new BaseVo();
        baseVo.setData(pageInfo);
        return baseVo;
    }

    /**
     * 修改用户信息（不带密码）
     * @author liyang
     * @date 2019-06-27
     * @param portalUser : 用户详情
     * @return : BaseVo
    */
    @Override
    @Transactional
    public BaseVo update(UserVo portalUser) {

        //如果是修改密码
        if(portalUser.getPassWord() !=null && !("").equals(portalUser.getPassWord())){

            //随机密码盐
            String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);

            //密码和盐=新密码  md5加密新密码
            String password= MD5Util.compute(portalUser.getPassWord() + salt);
            portalUser.setPassWord(password);
            portalUser.setSalt(salt);
        }

        //更新人物所属
        portalUser.setUpdateTime(new Date());
        userDao.deleteUserRole(portalUser.getId());
        portalUser.getRoleIds().forEach(c->{
            UserRelRole a=new UserRelRole();
            a.setUserId(portalUser.getId());
            a.setRoleId(c.getId());
            userDao.addUserRelRole(a);
        });
        userDao.update(portalUser);

        return successVo();
    }

    /**
     * 删除用户
     * @author liyang
     * @date 2019-06-11
     * @param id : 用户ID
     * @return : BaseVo
    */
    @Override
    public BaseVo delete(Long id) {
        userDao.delete(id);
        userDao.deleteUserRole(id);

        //todo 删除redis
        return successVo();
    }

    /**
     * 重置用户密码
     * @author liyang
     * @date 2019-06-27
     * @param id : 用户ID
     * @param portalUser : 用户密码
     * @return : BaseVo
    */
    @Override
    public BaseVo resetPwd(Long id, User portalUser) {
        User a= userDao.findByUserId(id);
        if(a==null){
            throw new RuntimeException("无此用户");
           /*if(!StringUtils.isEmpty(a.getToken()){
               cacheUtil.del(RedisKeyUtil.getRedisKey(RedisKey.LOGIN_TOKEN),g);
           }*/
        }
        //随机密码盐
        String salt = StringRandom.getStringRandom(Const.RANDOM_PASSWORD_SALT);
        //密码和盐=新密码  md5加密新密码
        String newPwd= MD5Util.compute(portalUser.getPassWord() + salt);
        a.setPassWord(newPwd);
        a.setSalt(salt);
        a.setId(id);
        a.setUpdateTime(new Date());
        userDao.update(a);
        return successVo();
    }

    /**
     * 根据ID查询用户详情
     * @author liyang
     * @date 2019-06-27
     * @param id :用户ID
     * @return : BaseVo
    */
    @Override
    public BaseVo findUser(Long id) {

        User user = userDao.findUserSql(id);
        List<Role> list=userDao.findRoleByUserId(id);
        UserVo vo=new UserVo();
        BeanUtils.copyProperties(user,vo);
        vo.setRoleIds(list);
        BaseVo baseVo = new BaseVo();
        baseVo.setData(vo);
        return baseVo;
    }

    /**
     * 修改密码
     * @author liyang
     * @date 2019-06-27
     * @param portalUserVo : 密码集合
     * @return : BaseVo
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
        String password= MD5Util.compute(portalUserVo.getPassword() + salt);
        portalUserUpdate.setPassWord(password);
        portalUserUpdate.setSalt(salt);
        portalUserUpdate.setId(userInfo.getId());

        userDao.update(portalUserUpdate);

        return successVo();
    }

    /**
     * @description: 角色
     * @author liyujun
     * @date 2019-12-20

     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    public BaseVo findRoles(){
        BaseVo vo=new BaseVo();
        vo.setData(  userDao.findRoles());
       return  vo;
    }


}
