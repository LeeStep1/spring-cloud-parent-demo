package com.bit.module.manager.service;

import com.bit.base.vo.BasePageVo;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.vo.ProjectVo;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface ProjectService  {

    void add(Project project);


    ProjectVo
     List<> queryProject(BasePageVo vo);




    ProjectVo queryProjectPri(Long projectId);
}
