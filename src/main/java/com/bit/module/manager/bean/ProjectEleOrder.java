package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:
 * @date:
 **/
@Data
@TableName("t_project_ele_order")
public class ProjectEleOrder {

    /**
     * id
     */
    private Long id;
    private Long projectId;
    private Long elevatorTypeId;
    private String elevatorTypeName;
    private String totalPrice;
    private String unitPrice;
    private String num;
    private Long versionId;

}
