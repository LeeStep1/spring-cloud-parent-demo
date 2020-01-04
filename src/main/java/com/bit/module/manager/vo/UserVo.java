package com.bit.module.manager.vo;

import com.bit.module.manager.bean.Role;
import com.bit.module.manager.bean.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-20
 **/
@Data
public class UserVo extends User {



	private Long id;

    /**
     * 登陆票据
     */
    private String token;

	/**
	 * 角色名称
	 */
    private String  roleName;
	/**
	 * 角色id
	 */
    private Integer roleId;
	/**
	 * 角色集合
	 */
    private List<Role> roleIds;

	/**
	 * 公司id
	 */
    private Long companyId;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 邮箱
	 */
	private String email;
}
