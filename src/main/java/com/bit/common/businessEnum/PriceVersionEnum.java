package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-26
 **/
public enum PriceVersionEnum {

    /**
     *流失
     */
    TEMP(-1, "草稿状态");


    /**
     * 状态码
     */
    private int code;

    /**
     * 描述
     */
    private String info;
    /**
     * @param code  状态码
     * @param info  描述
     */
    PriceVersionEnum(int code, String info) {
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
