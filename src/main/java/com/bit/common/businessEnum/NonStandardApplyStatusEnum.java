package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-04
 **/
public enum  NonStandardApplyStatusEnum {

    /**
     *无需审批
     */
    WUXUSHENPI(0, "无需审批"),

    /**
     *待审核
     */
    DAISHENHE(1, "待审核"),


    /**
     *通过
     */
    TONGGUO(2,"通过"),

    /**
     *拒绝
     */
    JUJUE(3,"拒绝");

    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  设备状态
     * @param info  描述
     */
    NonStandardApplyStatusEnum(int code, String info) {
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
