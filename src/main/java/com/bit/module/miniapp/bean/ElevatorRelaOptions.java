package com.bit.module.miniapp.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 电梯类型与可选项关联表
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-24 10:03:48
 */
@Data
@TableName("t_elevator_rela_options")
public class ElevatorRelaOptions implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据id
	 */
	private Long id;
	/**
	 * 电梯型号，对应t_elevator_type表中的id
	 */
	private Long elevatorTypeId;
	/**
	 * 对应t_options表中的id
	 */
	private Long optionId;

}
