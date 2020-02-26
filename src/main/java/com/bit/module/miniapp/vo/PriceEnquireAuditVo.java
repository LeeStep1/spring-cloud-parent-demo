package com.bit.module.miniapp.vo;

import com.bit.module.manager.bean.ProjectEleOrder;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-26
 **/

@Data
public class PriceEnquireAuditVo {

    private Long projectPriceId;

    private List<ProjectEleOrder> orderList;
}
