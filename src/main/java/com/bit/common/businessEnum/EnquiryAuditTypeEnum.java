package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-25
 **/
public enum EnquiryAuditTypeEnum {

    /**
     *提交
     */
    SUBMIT(1, "提交"),

    /**
     *审批
     */
    SHENPITONGGUO(2, "审批通过"),
    /**
     *审批
     */
    SHENPIJUJUE(3, "审批拒绝"),

    /**
     *转交
     */
    SHENPIJIANJIAO(4, "转交"),
    /**
     *撤回
     */
    SHENPICHEHUI(5, "关闭项目撤回");

    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  为议价审批操作的类型
     * @param info  描述
     */
    EnquiryAuditTypeEnum(int code, String info) {
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
