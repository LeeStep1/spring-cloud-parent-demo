package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Data
@TableName("t_project")
public class Project {

	/**
	 * id
	 **/
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * '项目名称'
	 **/
	private String projectName;
	/**
	 * '客户名称'
	 **/
	private String customerName;

	/**
	 * '代理商名称'
	 **/
	private String agentName;
	/**
	 * '创建人id'
	 **/
	private Long createUserId;
	/**
	 * '创建人姓名'
	 **/
	private String createUserName;
	/**
	 * 创建时间
	 **/
	private Date createTime;
	/**
	 * 地址id  对应area表中的ID
	 **/
	private String addressId;
	/**
	 * 名称
	 **/
	private String addressName;
	/**
	 * '项目状态1:正常，0 ：流失'
	 **/
	private Integer projectStatus;
	/**
	 * 公司id
	 **/
	private Long companyId;
	/**
	 * 公司名称
	 **/
	private String companyName;

	//-----添加字段-----

	//忽略此字段的映射
	/**
	 * 历史报价
	 */
	@TableField(exist = false)
	private List<ProjectPrice> projectPriceList;


}
