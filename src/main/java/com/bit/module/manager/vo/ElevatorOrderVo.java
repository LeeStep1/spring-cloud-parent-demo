package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ProjectEleOrder;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class ElevatorOrderVo extends ProjectEleOrder {

    private String params_key;

    private Integer type;

    private String picture;

}
