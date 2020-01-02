package com.bit.module.miniapp.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 选项以及非标项
 *
 * @author chenduo
 * @email ${email}
 * @date 2019-12-24 10:07:15
 */
@Data
@TableName("t_options")
public class Options implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 选项名称
	 */
	private String optionsName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 1：电梯装潢可选项 2：客梯功能配置可选项 3.客梯常用非标可选项，4：货梯非标，5：自动扶梯、人行道非标
	 */
	private Integer secOptionType;
	/**
	 * 输入方式：1，选择，2手输入
	 */
	private Integer inputType;
	/**
	 * 分项大类：1：装潢，2：功能（可选项），3：非标可选项
	 */
	private Integer optionType;
	/**
	 *
	 */
	private String defaultValue;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 分组值
	 */
	private String groupValue;
	/**
	 * 数据属性1:有且唯一，2：可多选
	 */
	private Integer rule;
	/**
	 * 选项数量
	 */
	@TableField(exist = false)
	private Integer nums;
}
