package com.bit.module.manager.bean;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

/**
 * @description:
 * @author: liyang
 * @date: 2019-11-13
 **/
@Data
public class WxTemplateData {
    private String name;
    private String value;
    private String color;

    public WxTemplateData(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
