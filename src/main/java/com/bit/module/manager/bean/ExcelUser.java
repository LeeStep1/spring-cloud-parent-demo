package com.bit.module.manager.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/1/13 14:26
 **/
@Data
public class ExcelUser {

	/**
	 * 用户名
	 */
	@Excel(name = "用户名",orderNum = "0")
	private String userName;

	/**
	 * 用户的实际名称
	 */
	@Excel(name = "真实姓名",orderNum = "1")
	private String realName;


	/**
	 * 手机号
	 */
	@Excel(name = "手机号",orderNum = "2")
	private String mobile;

	/**
	 * 密码
	 */
	@Excel(name = "密码",orderNum = "3")
	private String passWord;

	/**
	 * 密码盐
	 */
	@Excel(name = "密码盐",orderNum = "4")
	private String salt;

	/**
	 * 0已删除 1可用
	 */
	@Excel(name = "状态",orderNum = "5")
	private Integer status;

	/**
	 * 邮箱
	 */
	@Excel(name = "邮箱",orderNum = "6")
	private String email;
	/**
	 * 公司名称
	 */
	@Excel(name = "公司名称",orderNum = "7")
	private String companyName;
	/**
	 * 角色名称
	 */
	@Excel(name = "角色名称",orderNum = "8")
	private String roleName;
}
