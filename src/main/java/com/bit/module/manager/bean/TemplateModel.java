package com.bit.module.manager.bean;

import lombok.Data;

/**
 * @description: 模板详情
 * @author: liyang
 * @date: 2019-11-11
 **/
@Data
public class TemplateModel {

    /**
     * appId
     */
//    private String appId;

    /**
     * secret
     */
//    private String secret;

    /**
     * templateId
     */
    private String templateId;

    /**
     * url
     */
    private String url;

    /**
     * 推送人的openId
     */
    private String openId;


    private String miniAppid;


    private String accToken;

}
