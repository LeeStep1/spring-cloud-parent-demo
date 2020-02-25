package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-06
 **/
public enum AuditTypeEnum {

    /**
     *提交
     */
    SUBMIT(1, "提交"),

    /**
     *审批
     */
    AUDIT(2, "审批"),


    /**
     *关闭项目撤回
     */
    AUDITCANCEL(3, "关闭项目撤回");

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
    AuditTypeEnum(int code, String info) {
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
