package com.bit.module.manager.vo;

import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectPrice;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class ProjectVo  extends Project{


    /**历史报价**/
    private List<ProjectPrice>   projectPriceList;
    /**
     * 订单报价
     */
   /**二级页面才有此项**/
    private List<ProjectPriceVo> projectPriceOrderList;
}
