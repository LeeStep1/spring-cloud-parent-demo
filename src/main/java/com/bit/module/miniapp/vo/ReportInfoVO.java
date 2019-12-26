package com.bit.module.miniapp.vo;

import com.bit.module.manager.bean.ProjectEleOptions;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import com.bit.module.miniapp.bean.Options;
import lombok.Data;

import java.util.List;

/**
 * @Description:  项目报价实体
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Data
public class ReportInfoVO {


    /**項目ID**/
    private Long projectId;

    /**电梯类型ID**/
    private Long elevatorTypeId;


    /**电梯基础信息**/
    private List<ProjectEleOrderBaseInfo> baseinfo;

    /**电梯选择项*/
    private List<ProjectEleOptions>projectEleOptions;

    /**下浮率**/
    private Double rate;

    /**非标标识**/
    private String status;




}
