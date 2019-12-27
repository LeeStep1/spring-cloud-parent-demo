package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-23
 **/
@Data

@TableName("t_project_ele_options")
public class ProjectEleOptions {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 项目id
     */
    private Long projectInfoId;
    /**
     * 选项id
     */
    private Long optionId;
    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 数量
     */
    private Integer nums;

    /**
     * 选装总价
     */
    private double optionPrice;
    /**
     * 对应电梯订单t_project_ele_order中的id
     */
    private Long orderId;

}
