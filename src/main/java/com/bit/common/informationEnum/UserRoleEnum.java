package com.bit.common.informationEnum;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-10-15
 **/
public enum UserRoleEnum {

    /**
     * 销售
     */
    SALES(1,"销售"),

    /**
     * 管理
     */
    SUPPORT(2,"管理"),

    /**
     * 分公司经理
     */
    MANAGER(3,"分公司经理"),

    /**
     * 集团总经理
     */
    BOSS(4,"集团总经理"),

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

}
