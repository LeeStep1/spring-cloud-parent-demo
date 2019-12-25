package com.bit.module.manager.bean;

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

    private Long id;

    private Long projectInfoId;

    private Long optionId;

    private String optionName;


    private Integer nums;


    private Integer optionPrice;

    private Long orderId;

}