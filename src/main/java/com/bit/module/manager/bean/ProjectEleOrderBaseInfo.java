package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @date:
 **/
@Data
@TableName("t_project_ele_order_base_info")
public class ProjectEleOrderBaseInfo {

    /**
     * id
     */
    private Long id;
    private String key;
    private String infoValue;
    private Long orderId;
}
