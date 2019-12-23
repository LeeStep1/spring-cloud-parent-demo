package com.bit.module.manager.vo;

import com.bit.module.manager.bean.Role;
import com.bit.module.manager.bean.User;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-20
 **/
@Data
public class UserVo extends User {


    private String  roleName;

    private Integer roleId;

    private List<Role> roleIds;
}
