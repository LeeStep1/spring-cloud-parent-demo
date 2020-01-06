package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-06
 **/
public enum NodeOrderCalculateStatusEnum {

    /**
     *无需审批
     */
    JISUAN(1, "计算"),

    /**
     *非标审批
     */
    BUJISUAN(0, "不计算");

    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  order 中的是佛计算状态
     * @param info  描述
     */
    NodeOrderCalculateStatusEnum(int code, String info) {
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
