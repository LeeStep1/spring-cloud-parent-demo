package com.bit.module.miniapp.vo;

import com.bit.module.manager.bean.ProjectEleOrder;
import lombok.Data;

import java.util.List;

/**
 * @Description:  议价审批通过时，审批通过时提交数据
 * @Author: liyujun
 * @Date: 2020-02-26
 **/
@Data
public class PriceEnquireAuditVo {

    /**
     *
     * **/
    private Long projectPriceId;

    private List<ProjectEleOrder> projectEleOrderList;
}
