package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.xml.bind.Element;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Data
@TableName("t_project_ele_order_base_info")
public class ProjectEleOrderBaseInfo {

    /***数据id*/
    private Long id;
    /**参数的变量**/
    private String paramKey;
    /***所填写的属性参数*/
    private String infoValue;
    /***'数据id'*/
    private Long orderId;

    /**对应的页面元素id**/
    private Long elementId;

}
