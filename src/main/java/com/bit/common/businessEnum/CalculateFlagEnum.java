package com.bit.common.businessEnum;

/**
 * @Description:  返回的类型的枚举类
 * @Author: liyujun
 * @Date: 2018-09-17
 **/
public enum CalculateFlagEnum {

    /**
     * 否
     */
    NO(0, "否"),


    /**
     * 是
     */
    YES(1,"是");

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
    CalculateFlagEnum(int code, String info) {
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
