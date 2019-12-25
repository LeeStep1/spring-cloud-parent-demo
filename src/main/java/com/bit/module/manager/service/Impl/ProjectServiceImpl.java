package com.bit.module.manager.service.Impl;

import com.bit.base.service.BaseService;
import com.bit.common.businessEnum.ProjectEnum;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Service
@Transactional
public class ProjectServiceImpl  extends BaseService implements ProjectService{


    @Autowired
    private ProjectDao   projectDao;

    @Override

    public void add(Project project) {
        project.setCreateTime(new Date());
        project.setProjectStatus(ProjectEnum.PROJECT_SUC.getCode());
        project.setCreateUserId(1L);
        project.setCreateUserName("ceshi");
        projectDao.insert(project);
    }
}
