package com.bit.module.manager.vo;

import com.bit.module.manager.bean.ElevatorBaseElement;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-02
 **/
@Data
public class ElevatorBaseElementVO extends ElevatorBaseElement {

    private String paramsUnit;
    /**
     * 电梯类型名称
     */
    private String elevatorTypeName;
}
