package com.bit.common.informationEnum;

/**
 * @description:
 * @author: liyang
 * @date: 2019-05-07
 **/
public enum UserStatusEnum {

    /**
     * 启用标识
     */
    USING_FLAG(0,"启用"),

    /**
     * 停用标识
     */
    DISABLE_FLAG(1,"停用"),

    ;

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
    UserStatusEnum(int code, String info) {
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
