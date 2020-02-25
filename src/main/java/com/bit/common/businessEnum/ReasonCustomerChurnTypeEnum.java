package com.bit.common.businessEnum;

import java.util.Arrays;

/**
 * @Description: 项目流失原因枚举类
 * @Author: liyujun
 * @Date: 2020-02-25
 **/
public enum ReasonCustomerChurnTypeEnum {
    /**
     * 提交
     */
    REASON1(1, "价格未达成一致"),

    /**
     * 审批
     */
    REASON2(2, "无法满足客户定制要求"),
    /**
     * 提交
     */
    TREASON3(3, "客户选择其他品牌产品"),

    /**
     * 审批
     */
    REASON4(4, "项目变动或取消"),
    /**
     * 提交
     */
    REASON5(5, "其他原因");


    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code 流失原因
     * @param info 描述
     */
    ReasonCustomerChurnTypeEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }


    public String getInfo() {
        return info;
    }

    public static ReasonCustomerChurnTypeEnum  getReasonCustomerChurnTypeEnum(int code){

        for(ReasonCustomerChurnTypeEnum c:ReasonCustomerChurnTypeEnum.values()){
            if(c.code==code){
                return c;
            }
        }
        return null;

    }
}