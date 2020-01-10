package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BasePageVo;
import com.bit.base.vo.BaseVo;
import com.bit.common.businessEnum.CalculateFlagEnum;
import com.bit.common.businessEnum.ProjectEnum;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.ProjectEleNonstandardDao;
import com.bit.module.manager.dao.ProjectEleOrderDao;
import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.ProjectService;
import com.bit.module.manager.vo.*;
import com.bit.module.miniapp.bean.Options;
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

    @Autowired
	private ProjectEleOrderDao projectEleOrderDao;

	@Autowired
	private ProjectEleNonstandardDao projectEleNonstandardDao;

    @Override
	@Transactional
    public void add(Project project) {
        project.setCreateTime(new Date());
        project.setProjectStatus(ProjectEnum.PROJECT_SUC.getCode());
        project.setCreateUserId(getCurrentUserInfo().getId());
        project.setCreateUserName(getCurrentUserInfo().getRealName());
		project.setCompanyId(getCurrentUserInfo().getCompanyId());
		project.setCompanyName(getCurrentUserInfo().getCompanyName());
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
			if(latestProjectPrice.size()>0){
			       if(latestProjectPrice.get(0).getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
			       	Map cods=new HashMap();
			       	cods.put("version_id",latestProjectPrice.get(0).getId());
			       	//cods.put("calculate_flag",0);
			       	cods.put("calculate_flag",CalculateFlagEnum.NO.getCode());
			       	if(latestProjectPrice.get(0).getNonStandardApplyStatus()==2){
						//project.setCalculateFlag(0);
						project.setCalculateFlag(CalculateFlagEnum.NO.getCode());
					}else if(latestProjectPrice.get(0).getNonStandardApplyStatus()==3){
						List<ProjectEleOrder> odrList=  projectEleOrderDao.selectByMap(cods);
						if(CollectionUtils.isNotEmpty(odrList)){
							for(ProjectEleOrder or:odrList){
								if(or.getCalculateFlag()==0){
									//project.setCalculateFlag(0);
									project.setCalculateFlag(CalculateFlagEnum.NO.getCode());
									break;
								}else{
								   continue;
								}
							}

						}else{
							//project.setCalculateFlag(1);
							project.setCalculateFlag(CalculateFlagEnum.YES.getCode());
						}
					}

				   }else{
					   //project.setCalculateFlag(1);
					   project.setCalculateFlag(CalculateFlagEnum.YES.getCode());
				   }
			}else{
				//project.setCalculateFlag(1);
				project.setCalculateFlag(CalculateFlagEnum.YES.getCode());
			}
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
		List<ProjectPriceVo> projectPriceVoList = new ArrayList<>();
		for(ProjectPrice p:prices){
			ProjectPriceVo projectPriceVo = new ProjectPriceVo();
			projectEleOrder.setVersionId(p.getId());
			BeanUtils.copyProperties(p,projectPriceVo);
			projectPriceVo.setElevatorOrderVo(projectPriceDao.queryOrderByProjectId(projectEleOrder));
			for (ElevatorOrderVo elevatorOrderVo : projectPriceVo.getElevatorOrderVo()) {
				elevatorOrderVo.setPicture(contextPath + "/images/" + elevatorOrderVo.getPicture());
				//组装订单电梯的参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(elevatorOrderVo.getId());
				elevatorOrderVo.setParams(elementParamByOrderId);
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
		Map totalMap=projectDao.getPriceInfo(projectPriceId);
		if(totalMap!=null){


			if(totalMap.containsKey("installPrice")){
				projectPriceDetailVO.setInstallPrice(String.valueOf(totalMap.get("installPrice")));
			}
			if(totalMap.containsKey("transportPrice")){
				projectPriceDetailVO.setTransportPrice(String.valueOf(totalMap.get("transportPrice")));
			}
		}
		if (projectPriceDetailVO==null){
			throw new BusinessException("无记录");
		}

		List<ProjectPriceDetailInfo> projectPriceDetailInfos = new ArrayList<>();
		//组装电梯详情
		Map cods=new HashMap(1);
		cods.put("version_id",projectPriceId);
		List<ProjectEleOrder> orderByProjectId=projectEleOrderDao.selectByMap(cods);
		if (CollectionUtils.isNotEmpty(orderByProjectId)){
			for (ProjectEleOrder projectEleOrder : orderByProjectId) {
				ProjectPriceDetailInfo projectPriceDetailInfo = new ProjectPriceDetailInfo();
				//设置电梯类型和单价
				projectPriceDetailInfo.setElevatorTypeName(projectEleOrder.getElevatorTypeName());
				projectPriceDetailInfo.setUnitPrice(projectEleOrder.getUnitPrice());
				projectPriceDetailInfo.setRate(projectEleOrder.getRate());
				projectPriceDetailInfo.setCalculateFlag(projectEleOrder.getCalculateFlag());
				projectPriceDetailInfo.setInstallPrice(projectEleOrder.getInstallPrice());
				projectPriceDetailInfo.setTransportPrice(projectEleOrder.getTransportPrice());

				projectPriceDetailInfo.setNums(projectEleOrder.getNum());
				projectPriceDetailInfo.setTotalPrice(projectEleOrder.getTotalPrice());
				//设置规格参数 和 井道参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setElementParams(elementParamByOrderId);

				List<Options> projectOptions = projectDao.getProjectOptions(projectEleOrder.getId());
				projectPriceDetailInfo.setOptions(projectOptions);

				// 新增电梯非标项
				projectPriceDetailInfo.setStandard(projectEleOrder.getStandard());
				if (projectEleOrder.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
					Map cod=new HashMap();
					cod.put("order_id",projectEleOrder.getId());
					List<ProjectEleNonstandard>  list =projectEleNonstandardDao.selectByMap(cod);
					projectPriceDetailInfo.setProjectEleNonstandardOptionList(list);
					projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					if(projectEleOrder.getCalculateFlag()==0){
						projectPriceDetailInfo.setAuditRemark(list.get(0).getAuditRemark());
					}
				}else{
					projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
				}
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
			projectOrderDetailInfoVO.setVersion(projectPriceByProjectId.getVersion());
			projectOrderDetailInfoVO.setInstallFlag(projectPriceByProjectId.getInstallFlag());
			projectOrderDetailInfoVO.setTransportFlag(projectPriceByProjectId.getTransportFlag());
			projectOrderDetailInfoVO.setNonStandardApplyStatus(projectPriceByProjectId.getNonStandardApplyStatus());
			//Map totalMap=projectDao.getPriceInfo(projectPriceByProjectId.getId());
			/*if(totalMap!=null){

				if(totalMap.containsKey("installPrice")){
					projectOrderDetailInfoVO.setInstallPrice(String.valueOf(totalMap.get("installPrice")));
				}
				if(totalMap.containsKey("transportPrice")){
					projectOrderDetailInfoVO.setTransportPrice(String.valueOf(totalMap.get("transportPrice")));
				}
			}*/

		}
		//组装电梯详情

		//设置规格参数 和 井道参数
		List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(orderId);
		projectOrderDetailInfoVO.setElementParams(elementParamByOrderId);

		List<Options> projectOptions = projectDao.getProjectOptions(orderId);
		projectOrderDetailInfoVO.setProjectOptions(projectOptions);
		ProjectEleOrder projectEleOrder=projectEleOrderDao.selectById(orderId);

		//新增的附加项
		if(projectEleOrder!=null){
			projectOrderDetailInfoVO.setStandard(projectEleOrder.getStandard());
			if(projectEleOrder.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
				projectOrderDetailInfoVO.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
				Map cod=new HashMap();
				cod.put("order_id",orderId);
				List<ProjectEleNonstandard>  list =projectEleNonstandardDao.selectByMap(cod);
				projectOrderDetailInfoVO.setProjectEleNonstandardOptionList(list);
				if(projectOrderDetailInfoVO.getCalculateFlag()==0){
					projectOrderDetailInfoVO.setAuditRemark(list.get(0).getAuditRemark());
				}

			}else{
				projectOrderDetailInfoVO.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
			}
		}

		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectOrderDetailInfoVO);
		return baseVo;
	}
}
