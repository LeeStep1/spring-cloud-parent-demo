package com.bit.common.informationEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-10-15
 **/
public enum TidUrlEnum {


    /**
     * 销售人员tid，对应URL
     */
    TERMINALURL_RESIDENT(20,"wx"),

    /**
     * web 管理端 tid，对应URL
     */
    TERMINALURL_MANAGER(21,"manager");

    /**
     * 操作码
     */
    private int tid;

    /**
     * 操作信息
     */
    private String url;

    /**
     * @param tid  接入端
     * @param url 服务前缀
     */
    TidUrlEnum(int tid, String url) {
        this.tid = tid;
        this.url = url;
    }

    public int getTid() {
        return this.tid;
    }


    public String getUrl() {
        return this.url;
    }
}
