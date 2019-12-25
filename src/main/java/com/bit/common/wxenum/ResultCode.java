package com.bit.common.wxenum;

/**
 * @Description:  返回的类型的枚举类
 * @Author: liyujun
 * @Date: 2018-09-17
 **/
public enum ResultCode {

    /**
     *操作成功
     */
    SUCCESS(0, "成功"),

    /**
     *操作失败
     */
    WRONG(1, "操作失败"),



    /**
     *参数错误
     */
    PARAMETER_ERROR(400, "参数错误"),

    /**
     *未授权
     */
    UNAUTH(401, "未授权"),

    /**
     *未授权
     */
    FORBIDDEN(403, "身份无法访问此接口"),

    /**
     *内部调用RPC返回
     */
    HYSTRIX_TIME_OUT(-1, "未授权"),

    /**
     * 恶意报名三次警告
     */
    WARNING_THREE_TIMES(10,"您已多次取消活动，下次报名将不可取消"),
    /**
     * 超过三次报名警告
     */
    WARNING_OVER_THREE_TIMES(11,"您已多次取消活动，此次活动禁止报名"),
    /**
     * 活动不存在
     */
    WARNING_CAMPAIGN_NOT_EXIST(12,"该活动不存在"),
    /**
     * 活动已开始不能取消报名
     */
    WARNING_CAMPAIGN_IN_PROGRESS_NOT_UNENROLL(13,"活动已开始不能取消报名"),
    /**
     * 活动已开始不能报名
     */
    WARNING_CAMPAIGN_IN_PROGRESS_NOT_ENROLL(14,"活动已开始不能报名"),


    /**
     * 短信发送超过上限
     */
    SMS_OUT_LIMIT(50002,"短信发送超过上限"),

    /**
     * 手机号不存在
     */
    MOBILE_NOT_EXIST(50001,"手机号不存在"),



    /**
     * 审批提示handle
     */
    ADUITS_ALREADY_HANDLE(60001,"该审批已经被处理"),

    /**
     *账号已冻结，无法登陆
     */
    USER_ALREADY_STOP(70001,"账号已冻结，无法登陆");

    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  状态码
     * @param info  状态信息
     */
    ResultCode(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }


    public String getInfo() {
        return info;
    }
}