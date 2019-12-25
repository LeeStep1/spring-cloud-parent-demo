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
}
