package com.bit.common.informationEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-10-15
 **/
public enum UserRoleEnum {

    /**
     * 销售
     */
    SALES(1,"销售经理"),

    /**
     * 支持人员
     */
    SUPPORT(2,"支持人员"),

    /**
     * 分公司总经理
     */
    MANAGER(3,"分公司总经理"),

    /**
     * 集团总经理
     */
    BOSS(4,"集团总经理"),
    /**
     * 区域总经理
     */
    REGIONAL_MANAGER(5,"区域总经理"),

    /**
     * 销售总监
     */
    SALES_BOSS(6,"销售总监"),


    ;

    /**
     * 角色ID
     */
    private int roleId;

    /**
     * 角色mi
     */
    private String roleName;

    /**
     * @param roleId  状态码
     * @param roleName  状态信息
     */
    UserRoleEnum(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    /**
     * 根据code得到info
     * @param code
     * @return
     */
    public static String getValueByCode(int code){
        for(UserRoleEnum typeEnum : UserRoleEnum.values()){
            if(code==typeEnum.getRoleId()){
                return typeEnum.getRoleName();
            }
        }
        return  null;
    }
    /**
     * 根据info得到code
     * @param roleName
     * @return
     */
    public static Integer getCodeByValue(String roleName){
        for(UserRoleEnum typeEnum : UserRoleEnum.values()){
            if(roleName.equals(typeEnum.getRoleName())){
                return typeEnum.getRoleId();
            }
        }
        return  null;
    }

	/**
	 * 枚举转换成list
	 * @return
	 */
	public static List<String> getList(){
		List<String> categories = new ArrayList<>();
		for (UserRoleEnum typeEnum : UserRoleEnum.values()){
			categories.add(typeEnum.getRoleName());
		}
		return categories;
	}
}
