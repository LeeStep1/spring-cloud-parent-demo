package com.bit.module.miniapp.vo;

import com.bit.module.manager.bean.ProjectEleNonstandard;
import com.bit.module.miniapp.bean.Options;

import java.util.List;

/**
 * @Description: 电梯订单信息
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public class OrderVo {


   /**所选中的电梯类型**/
   private Long elevatorTypeId;

   /**所选中的电梯类型**/
   private  String elevatorTypeName;

   /**台数**/
   private  Integer num;

   /**所选中项**/
   private List<Options> options;





}
