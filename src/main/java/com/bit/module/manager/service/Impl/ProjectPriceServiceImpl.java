package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.businessEnum.*;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.manager.vo.*;
import com.bit.module.miniapp.bean.Options;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bit.module.manager.service.ProjectPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("projectPriceService")
public class ProjectPriceServiceImpl extends BaseService implements ProjectPriceService {

	@Autowired
	private ProjectPriceDao projectPriceDao;
	
	@Autowired
	private ProjectEleOrderDao projectEleOrderDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectEleNonstandardDao projectEleNonstandardDao;

	@Autowired
	private AuditDao auditDao;

	@Autowired
	private EquationServiceImpl equationServiceImpl;
	/**
	 * 项目下订单列表
	 * @param projectId
	 * @return
	 */
	@Override
	public BaseVo orderList(Long projectId) {
		BaseVo baseVo = new BaseVo();
		Project project = projectDao.selectById(projectId);
		ProjectOrderWebVO projectOrderWebVO = new ProjectOrderWebVO();
		BeanUtils.copyProperties(project,projectOrderWebVO);
		//根据projectid查询 所有的订单
		ProjectEleOrder order = new ProjectEleOrder();
		order.setProjectId(projectId);
		List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);

		if (CollectionUtils.isNotEmpty(byParam)){

			List<ProjectPriceDetailInfo> projectPriceDetailInfos = new ArrayList<>();

			for (ProjectEleOrder projectEleOrder : byParam) {
				ProjectPriceDetailInfo projectPriceDetailInfo = new ProjectPriceDetailInfo();
				//设置电梯类型和单价
				ElevatorTypeNameAndUnitPrice elevatorTypeNameAndUnitPrice = new ElevatorTypeNameAndUnitPrice();
				elevatorTypeNameAndUnitPrice.setElevatorTypeName(projectEleOrder.getElevatorTypeName());
				elevatorTypeNameAndUnitPrice.setUnitPrice(projectEleOrder.getUnitPrice());
				elevatorTypeNameAndUnitPrice.setRate(projectEleOrder.getRate());
				elevatorTypeNameAndUnitPrice.setBasePrice(projectEleOrder.getBasePrice());
				elevatorTypeNameAndUnitPrice.setAdditionPrice(projectEleOrder.getAdditionPrice());
				elevatorTypeNameAndUnitPrice.setTotalPrice(projectEleOrder.getTotalPrice());
				elevatorTypeNameAndUnitPrice.setNums(projectEleOrder.getNum());
				//查询订单总价
				projectPriceDetailInfo.setElevatorTypeNameAndUnitPrice(elevatorTypeNameAndUnitPrice);
				projectPriceDetailInfo.setOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setPriceId(projectEleOrder.getVersionId());

				ProjectPrice orderprice = projectPriceDao.selectById(projectEleOrder.getVersionId());
				projectPriceDetailInfo.setOrderPrice(orderprice.getTotalPrice());
				//报价的版本 更新时候使用
				projectPriceDetailInfo.setVersion(orderprice.getVersion());
				//设置规格参数 和 井道参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setElementParams(elementParamByOrderId);


				// 新增电梯非标项
				projectPriceDetailInfo.setStandard(projectEleOrder.getStandard());
				if (projectEleOrder.getStandard()!=null){
					if (projectEleOrder.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
						Map cod=new HashMap();
						cod.put("order_id",projectEleOrder.getId());
						List<ProjectEleNonstandard>  list =projectEleNonstandardDao.selectByMap(cod);
						projectPriceDetailInfo.setProjectEleNonstandardOptionList(list);
						projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					}else{
						projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
					}
				}
				projectPriceDetailInfos.add(projectPriceDetailInfo);

			}
			//查询项目报价版本
			List<ProjectPrice> latestProjectPrice = projectPriceDao.getLatestProjectPrice(projectId);
			projectOrderWebVO.setVersion(latestProjectPrice.get(0).getVersion());
			projectOrderWebVO.setStandard(latestProjectPrice.get(0).getStandard());
			projectOrderWebVO.setStandardName(latestProjectPrice.get(0).getStandardName());
			projectOrderWebVO.setProjectPriceDetailInfos(projectPriceDetailInfos);
			baseVo.setData(projectOrderWebVO);
		}

		return baseVo;
	}

	/**
	 * 单查项目数据
	 *
	 * @param orderId
	 * @return ProjectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long orderId) {
		BaseVo baseVo = new BaseVo();
		ProjectPriceDetailInfo projectPriceDetailInfo = new ProjectPriceDetailInfo();

		List<Options> projectOptions = projectDao.getProjectOptions(orderId);
		projectPriceDetailInfo.setOptions(projectOptions);

		baseVo.setData(projectPriceDetailInfo);
		return baseVo;
	}

	/**
	 * 批量编辑数据
	 *
	 * @param projectPrices
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(List<ProjectEleNonstandardVO> projectPrices) {
		if (CollectionUtils.isEmpty(projectPrices)){
			throw new BusinessException("参数为空");
		}
		List<ProjectEleNonstandardVO> updatelist = new ArrayList<>();
		List<ProjectEleOrder> orderList = new ArrayList<>();
		for (ProjectEleNonstandardVO priceVO : projectPrices) {
			Long priceId = priceVO.getPriceId();
			ProjectPrice temp = projectPriceDao.selectById(priceId);
			if (temp==null){
				throw new BusinessException("无此记录");
			}
			if (priceVO.getVersion().equals(temp.getVersion())){
				updatelist.add(priceVO);
			}
			ProjectEleOrder order = new ProjectEleOrder();
			order.setId(priceVO.getOrderId());
			if (priceVO.getProductionFlag().equals(ProductionFlagEnum.YES.getCode())){
				order.setCalculateFlag(CalculateFlagEnum.YES.getCode());
			}else if (priceVO.getProductionFlag().equals(ProductionFlagEnum.NO.getCode())){
				order.setCalculateFlag(CalculateFlagEnum.NO.getCode());
			}

			orderList.add(order);
		}

		if (CollectionUtils.isNotEmpty(updatelist)){
			//更改t_project_price的non_standard_apply_status状态
			projectPriceDao.updatebatchProjectPrice(updatelist);
			//更改t_project_ele_nonstandard的total_price
			projectEleNonstandardDao.updatebatchNonstandard(projectPrices);
			//更改t_project_ele_order的calculate_flag
			if (CollectionUtils.isNotEmpty(orderList)){
				projectEleOrderDao.updateBatchEleOrder(orderList);
			}

			Long userId = getCurrentUserInfo().getId();
			String realName = getCurrentUserInfo().getRealName();
			List<Audit> audits = new ArrayList<>();
			for (ProjectEleNonstandardVO projectEleNonstandardVO : updatelist) {
				Audit audit = new Audit();

				audit.setAuditUserId(userId);
				audit.setAuditUserName(realName);
				audit.setAuditTime(new Date());
				audit.setAuditType(AuditTypeEnum.SUBMIT.getCode());
				audit.setProjectId(projectEleNonstandardVO.getProjectId());

				audits.add(audit);
			}

			//插入审批表
			if (CollectionUtils.isNotEmpty(audits)){
				auditDao.batchAdd(audits);
				//算价钱

				//查询项目id
				ProjectPrice ppr = projectPriceDao.selectById(projectPrices.get(0).getPriceId());
				Map<String, Object> cod = new HashMap<>();
				cod.put("projectId", ppr.getProjectId());
				cod.put("version", ppr.getVersion());
				cod.put("isUpdate", true);
				ProjectPrice projectPriceByProjectId = projectPriceDao.getProjectPriceByProjectIdWithVersion(ppr.getProjectId(), -1);
				if (projectPriceByProjectId != null) {
					if (projectPriceByProjectId.getInstallFlag().equals(InstallFlagEnum.YES.getCode())) {
						cod.put("包括安装", true);
					}
					if (projectPriceByProjectId.getTransportFlag().equals(TransportFlagEnum.YES.getCode())) {
						cod.put("包括运费", true);
					}
				}
				equationServiceImpl.executeCountProjectPrice(cod);
			}

		}

		return successVo();
	}



	/**
	 * 报价列表分页查询
	 * @param projectPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(ProjectPageVO projectPageVO) {
		Page<ProjectShowVO> page = new Page<>(projectPageVO.getPageNum(), projectPageVO.getPageSize());
		IPage<ProjectShowVO> listPage = projectPriceDao.listPage(page, projectPageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(listPage);
		return baseVo;
	}


}