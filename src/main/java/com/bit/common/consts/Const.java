package com.bit.common.consts;

/**
 * 静态常量类
 * @author mifei
 * @date 2019-05-05
 */
public class Const {



    public static final long TOKEN_EXPIRE_SECONDS = 3600;

   /* public static final String TOKEN_PREFIX = "token:";

    *//**
     * 刷新token
     *//*
    public static final String REFRESHTOKEN_TOKEN_PREFIX = "refreshToken:";*/

    /**
     * 密码盐 （6位）
     */
    public static final int RANDOM_PASSWORD_SALT = 6;

    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = "123456";

    /**
     * 验证码redis key
     */
    public static final String REDIS_KEY_CAPTCHA = "captcha";

    /**
     * 验证码redis key
     */
    public static final String CMS_LOGIN_CODE = "CMSLOGIN";


    /**
     * REDIS每条短信间隔
     */
    public static final String EACH_SMS_INTERVAL = "90";

    /**
     * 每日短信发送上限
     */
    public static final String DAY_SMS_LIMIT = "86400";

    /**
     * 数量
     */
    public static final int COUNT = 0;

    /**
     * 存疑提交上限
     */
    public static final int ERRORCOUNT = 5;

    /**
     * 存疑人员
     */
    public static final int ERRORUSERFLG = 1;

    /**
     * 正常人员
     */
    public static final int NORMALUSERFLG = 0;

    /**
     * 初始上报次数
     */
    public static final int INITERRORREPORT = 1;

    /**
     * 上报次数范围界定(大于这个次数)
     */
    public static final int LIMITERRORREPORT = 4;

    /**
     * 计算基数
     */
    public static final int BASETOTAL = 100;

    /**
     * 开发者微信号
     */
    public static final String TOUSERNAME = "ToUserName";

    /**
     * 关注者openId
     */
    public static final String FROMUSERNAME = "FromUserName";

    /**
     * 消息创建时间
     */
    public static final String CREATETIME = "CreateTime";

    /**
     * 消息类型
     */
    public static final String MSGTYPE = "MsgType";

    /**
     * 关注消息类型
     */
    public static final String MSGTYPEFOREVENT = "event";

    /**
     * 开发者微信号
     */
    public static final String EVENT = "Event";

    /**
     * 事件类型--关注
     */
    public static final String SUBSCRIBE = "subscribe";

    /**
     * 事件类型--取消关注
     */
    public static final String UNSUBSCRIBE = "unsubscribe";

    /**
     * 全局ACCESS_TOKEN
     */
    public static final String WX_ACC_TOKEN = "wx_app:wx_acc_token";

    /**
     * 微信消息模板 value
     */
    public static final String WX_TEMPLATE_VALUE = "value";

    /**
     * 微信消息模板 color
     */
    public static final String WX_TEMPLATE_COLOR = "color";
    /**
     * 标识 0 -否 1-是
     */
    public static final Integer FLAG_NO = 0;
    public static final Integer FLAG_YES = 1;
}
