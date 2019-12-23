package com.bit.module.manager.bean;


import lombok.Data;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Map;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-13
 **/
@Data
public class WxTemplateMessage {

    private static final long serialVersionUID = 5063374783759519418L;
    private String touser;
    private String template_id;
    private String url;
    private WxTemplateMessage.MiniProgram miniprogram;
    private Map<String,Object> data;

    @Data
    public static class MiniProgram implements Serializable {
        private static final long serialVersionUID = -7945254706501974849L;
        private String appid;
        private String pagepath;
        private boolean usePath = true;
    }

}
