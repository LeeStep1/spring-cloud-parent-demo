package com.bit.base.dto;

import lombok.Data;

/**
 * @Description:当前用户信息
 * @Author: mifei
 * @Date: 2018-10-18
 **/
@Data
public class UserInfo {


	/**用户ID**/
	private Long id;

	/**用户名称**/
	private String userName;

	/**真实姓名**/
	private String realName;

	/**用户名称**/
	private String openid;

	/**微信登录后的sessionKeY**/
	private String sessionKey;

	/**角色Id**/
	private Integer role;

	/**角色Id**/
	private String roleName;

	/**接入端id**/
	private Integer tid;

	/**用户头像**/
	//private String avatarUrl;

	/**授权token**/
	private String token;

	/**邮箱**/
	private String email;
	/**
	 * 公司id
	 */
	private Long companyId;
	/**
	 * 公司名称
	 */
	private String companyName;
}
