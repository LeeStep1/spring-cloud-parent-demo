package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-06
 **/
public enum EnquiryApplyStatusEnum {

    /**
     *未提交审批
     */
    WEITIJIAO(0, "未提交审批"),

    /**
     *审批中
     */
    SHENNPIZHONG(1, "审批中"),

    /**
     *审批通过
     */
    SHENPITONGGUO(2, "审批通过"),
    /**
     *审批中
     */
    SHENNPIJUJUE(3, "审批拒绝"),
    /**
     *撤销
     */
    CHEXIAO(4, "撤销");

    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  对应报价的审批状态
     * @param info  描述
     */
    EnquiryApplyStatusEnum(int code, String info) {
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
