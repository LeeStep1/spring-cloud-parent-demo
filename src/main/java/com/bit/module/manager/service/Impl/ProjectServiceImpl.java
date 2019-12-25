package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BasePageVo;
import com.bit.common.businessEnum.ProjectEnum;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.ProjectService;
import com.bit.module.manager.vo.ProjectPriceVo;
import com.bit.module.manager.vo.ProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private ProjectPriceDao projectPriceDao;

    @Override
    public void add(Project project) {
        project.setCreateTime(new Date());
        project.setProjectStatus(ProjectEnum.PROJECT_SUC.getCode());
        project.setCreateUserId(1L);
        project.setCreateUserName("ceshi");
        projectDao.insert(project);
    }

    /**
     * @description:  我的报价一级页面
     * @author liyujun
     * @date 2019-12-25
     * @param vo :
     * @return : java.util.List<com.bit.module.manager.vo.ProjectVo>
     */
    @Override
    public List<ProjectVo>   queryProject(BasePageVo vo){

        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Project> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("create_user_id",getCurrentUserInfo().getId());
        Page<Project> page = new Page<>(vo.getPageNum(),vo.getPageSize());  // 查询第1页，每页返回5条
        IPage<Project> iPage = projectDao.selectPage(page,queryWrapper);
        List<Long> projectIdlist= new ArrayList<>(iPage.getRecords().size());
        List<ProjectVo> listRs=new ArrayList<>(iPage.getRecords().size());
        for(Project project:iPage.getRecords()){
            projectIdlist.add(project.getId());
            ProjectVo vo1=new ProjectVo();
            BeanUtils.copyProperties(project,vo1);
            listRs.add(vo1);
        }
        List<ProjectPrice> prices= projectPriceDao.getProjectPrice(projectIdlist);
        for (ProjectVo vo2:listRs){
            List<ProjectPrice>rsPrice=new ArrayList<>();
            for(ProjectPrice p:prices){
                 if(p.getProjectId().equals(vo2.getId())){
                     rsPrice.add(p);
                 }
            }
             vo2.setProjectPriceList(rsPrice);
        }
        return  listRs;
    }

    /**
     * @description:  项目二级
     * @author liyujun
     * @date 2019-12-25
     * @param projectId :
     * @return : java.util.List<com.bit.module.manager.vo.ProjectVo>
     */
    @Override
    public  ProjectVo queryProjectPri(Long projectId){
        ProjectVo vo=new ProjectVo();
        BeanUtils.copyProperties(projectDao.selectById(projectId),vo);
        QueryWrapper<ProjectPrice> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("version_id");
        queryWrapper.eq("project_id",projectId);
        //历史报价
        List<ProjectPrice> prices =projectPriceDao.selectList(queryWrapper);
        ProjectEleOrder projectEleOrder=new ProjectEleOrder();
        projectEleOrder.setProjectId(projectId);
        List<ProjectPriceVo>projectPriceVoList=new ArrayList<>();
        for(ProjectPrice p:prices){
            ProjectPriceVo  projectPriceVo=new ProjectPriceVo();
            projectEleOrder.setVersionId(p.getId());
            BeanUtils.copyProperties(p,projectPriceVo);
            projectPriceVo.setElevatorOrderVo(projectPriceDao.queryOrderByPriceId(projectEleOrder));
            projectPriceVoList.add(projectPriceVo);
        }
        vo.setProjectPriceOrderList(projectPriceVoList);
        return vo;
    }
}
