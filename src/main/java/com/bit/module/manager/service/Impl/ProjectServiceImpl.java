package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BasePageVo;
import com.bit.base.vo.BaseVo;
import com.bit.common.businessEnum.ProjectEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.ProjectService;
import com.bit.module.manager.vo.*;
import com.bit.module.miniapp.bean.Options;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Service
@Transactional
public class ProjectServiceImpl extends BaseService implements ProjectService{

	@Value("${server.servlet.context-path}")
	private String contextPath;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectPriceDao projectPriceDao;

    @Override
	@Transactional
    public void add(Project project) {
        project.setCreateTime(new Date());
        project.setProjectStatus(ProjectEnum.PROJECT_SUC.getCode());
        project.setCreateUserId(getCurrentUserInfo().getId());
        project.setCreateUserName(getCurrentUserInfo().getUserName());
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
    public BaseVo queryProject(BasePageVo vo){

        QueryWrapper<Project> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.eq("create_user_id",getCurrentUserInfo().getId());
        Page<Project> page = new Page<>(vo.getPageNum(),vo.getPageSize());  // 查询第1页，每页返回5条
        IPage<Project> iPage = projectDao.selectPage(page,queryWrapper);


        for(Project project:iPage.getRecords()){
			List<ProjectPrice> latestProjectPrice = projectPriceDao.getLatestProjectPrice(project.getId());
			project.setProjectPriceList(latestProjectPrice);
        }

        //第二种方案 批量查询后 找出version最大的

//		//项目id集合
//		List<Long> projectIdlist= new ArrayList<>();
//
//		for(Project project:iPage.getRecords()){
//			projectIdlist.add(project.getId());
//		}
//		if (CollectionUtils.isNotEmpty(projectIdlist)){
//			for (Long aLong : projectIdlist) {
//				List<ProjectPrice> latestProjectPrice = projectPriceDao.getLatestProjectPrice(aLong);
//				for (Project project:iPage.getRecords()) {
//					List<ProjectPrice> rsPrice=new ArrayList<>();
//					for(ProjectPrice p:latestProjectPrice){
//						if (p.getProjectId().equals(project.getId())){
//							rsPrice.add(p);
//						}
//					}
//					//找出来最大值
//					List<Integer> versions = rsPrice.stream().map(p->p.getVersion()).collect(Collectors.toList());
//					if (CollectionUtils.isNotEmpty(versions)){
//						Integer min = Collections.min(versions);
//						System.out.println(min);
//						project.setProjectPriceList(rsPrice);
//					}
//
//				}
//			}
//		}

		BaseVo baseVo = new BaseVo();
		baseVo.setData(iPage);
		return baseVo;

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
        queryWrapper.orderByDesc("version");
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
            projectPriceVo.setElevatorOrderVo(projectPriceDao.queryOrderByProjectId(projectEleOrder));
			for (ElevatorOrderVo elevatorOrderVo : projectPriceVo.getElevatorOrderVo()) {
				elevatorOrderVo.setPicture(contextPath + "/images/" + elevatorOrderVo.getPicture());
			}
			projectPriceVoList.add(projectPriceVo);
        }
        vo.setProjectPriceOrderList(projectPriceVoList);
        return vo;
    }

    /**
     * 查询项目报价详情
     * @param projectId
     * @param projectPriceId
     * @return
     */
    @Override
    public BaseVo getProjectDetail(Long projectId, Long projectPriceId) {
    	//查询项目详情和报价
		ProjectPriceDetailVO projectPriceDetailVO = projectDao.getProjectDetailById(projectId, projectPriceId);
		if (projectPriceDetailVO==null){
			throw new BusinessException("无记录");
		}

		List<ProjectPriceDetailInfo> projectPriceDetailInfos = new ArrayList<>();
		//组装电梯详情
		List<ProjectEleOrder> orderByProjectId = projectDao.getOrderByProjectId(projectId);
		if (CollectionUtils.isNotEmpty(orderByProjectId)){
			for (ProjectEleOrder projectEleOrder : orderByProjectId) {
				ProjectPriceDetailInfo projectPriceDetailInfo = new ProjectPriceDetailInfo();
				//设置电梯类型和单价
				ElevatorTypeNameAndUnitPrice elevatorTypeNameAndUnitPrice = new ElevatorTypeNameAndUnitPrice();
				elevatorTypeNameAndUnitPrice.setElevatorTypeName(projectEleOrder.getElevatorTypeName());
				elevatorTypeNameAndUnitPrice.setUnitPrice(projectEleOrder.getUnitPrice());
				elevatorTypeNameAndUnitPrice.setRate(projectEleOrder.getRate());

				projectPriceDetailInfo.setElevatorTypeNameAndUnitPrice(elevatorTypeNameAndUnitPrice);
				//设置规格参数 和 井道参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setElementParams(elementParamByOrderId);

				List<Options> projectOptions = projectDao.getProjectOptions(projectEleOrder.getId());
				projectPriceDetailInfo.setOptions(projectOptions);

				projectPriceDetailInfos.add(projectPriceDetailInfo);
			}
		}
		projectPriceDetailVO.setProjectPriceDetailInfos(projectPriceDetailInfos);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectPriceDetailVO);
		return baseVo;
    }

	/**
	 * 查询订单详情
	 * @param projectId
	 * @param orderId
	 * @return
	 */
	@Override
	public BaseVo getOrderDetail(Long projectId, Long orderId) {
		ProjectOrderDetailInfoVO projectOrderDetailInfoVO = projectDao.getOrderDetailById(projectId, orderId);
		if (projectOrderDetailInfoVO==null){
			throw new BusinessException("无记录");
		}
		Project project = projectDao.selectById(projectId);
		if (project!=null){
			projectOrderDetailInfoVO.setAddressName(project.getAddressName());
		}

		ProjectPrice projectPriceByProjectId = projectPriceDao.getProjectPriceByProjectIdAndOrderId(projectId,orderId);
		if (projectPriceByProjectId!=null){
			projectOrderDetailInfoVO.setPriceVersion(projectPriceByProjectId.getVersion());
		}
		//组装电梯详情

		//设置规格参数 和 井道参数
		List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(orderId);
		projectOrderDetailInfoVO.setElementParams(elementParamByOrderId);

		List<Options> projectOptions = projectDao.getProjectOptions(orderId);
		projectOrderDetailInfoVO.setProjectOptions(projectOptions);

		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectOrderDetailInfoVO);
		return baseVo;
	}
}
