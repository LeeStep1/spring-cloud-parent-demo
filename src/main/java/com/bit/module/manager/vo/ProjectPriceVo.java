package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ProjectPrice;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class ProjectPriceVo  extends ProjectPrice{

   private List<ElevatorOrderVo>elevatorOrderVo;

   /**
    * '操作类型 1-提交 2-审批通过 3-审批拒绝，4，转交，5，关闭项目撤回
    */
   private Integer auditType;
   /**
    * '操作类型 名称
    */
   private String auditTypeName;
}
