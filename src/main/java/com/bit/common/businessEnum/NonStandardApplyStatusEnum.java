package com.bit.common.businessEnum;

/**
 * @Description:  非标审批流程状态枚举类
 * @Author: liyujun
 * @Date: 2020-01-04
 **/
public enum  NonStandardApplyStatusEnum {

    /**
     *无需审批
     */
    WUXUSHENPI(0, "无需审批"),

    /**
     *非标审批
     */
    DAITIJIAO(1, "非标的待提交"),

    /**
     *待审核
     */
    DAISHENHE(2, "待审核"),


    /**
     *通过
     */
    TONGGUO(3,"通过"),

    /**
     *拒绝
     */
    JUJUE(4,"拒绝"),

    /**
     *撤销
     */
    CHEXIAO(-1,"撤销");

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
