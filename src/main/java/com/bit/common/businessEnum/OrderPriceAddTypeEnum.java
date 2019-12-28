package com.bit.common.businessEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-28
 **/
public enum  OrderPriceAddTypeEnum {

    /**
     *实施
     */
    SHISHI(1, "实施"),


    /**
     *运费
     */
    YUNFEI(2,"运费");


    /**
     * 操作码
     */
    private int code;

    /**
     * 操作信息
     */
    private String info;

    /**
     * @param code  附加费用
     * @param info  描述
     */
    OrderPriceAddTypeEnum(int code, String info) {
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
