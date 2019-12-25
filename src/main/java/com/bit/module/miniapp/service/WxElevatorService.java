package com.bit.module.miniapp.service;

import com.bit.module.manager.bean.Project;
import com.bit.module.miniapp.bean.Options;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface WxElevatorService {



     List<Options> getOptions( Integer optionType,Long elevatorId,Long orderId);




}
