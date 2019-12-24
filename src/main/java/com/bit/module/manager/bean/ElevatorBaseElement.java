package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Data
@TableName(value = "t_elevator_base_element")
public class ElevatorBaseElement {


    /**数据id**/
    private Long id;


    /**电梯类型表ID**/
    private Long elevatorTypeId;


    /**电梯类型的查询的key值**/
    private String elevatorTypeKey;


    /**元素名称**/
    private String elementName;


    /**元素展示类型：1，选择，2手输入**/
    private Integer elementType;


    /** 基础信息的类别：1规格参数，2：井道参数**/
    private Integer categoryType;


    /**运算key值**/
    private String paramsKey;


    /**排序**/
    private Integer orderNum;


}
