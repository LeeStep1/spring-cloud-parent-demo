package com.bit.common.informationEnum;

/**
 * @description:
 * @author: liyang
 * @date: 2019-05-07
 **/
public enum InfomationEnum {

    /**
     * 0-待受理
     */
    WAIT_ACCEPT_STATUS(0,"待受理"),

    /**
     * 1-已受理
     */
    ALREADY_ACCEPT_STATUS(1,"已受理"),

    /**
     * 2-已反馈
     */
    ALREADY_FEEDBACK_STATUS(2,"已反馈"),

    /**
     * 3-已拒绝
     */
    ALREADY_REFUSE_STATUS(3,"已拒绝"),

    /**
     * 存疑上报
     */
    ERROR_REPORT_FLG(1,"存疑上报"),

    /**
     * 描述类型  语音
     */
    DESCRIBEID_MUSIC(2,"语音"),

    /**
     * 描述类型  文字
     */
    DESCRIBEID_CHAR(1,"文字"),


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
    InfomationEnum(int code, String info) {
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
