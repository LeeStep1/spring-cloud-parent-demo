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
	 *
	 * @param projectPriceId
	 * @return
	 */
	@Override
	public BaseVo orderList(Long projectPriceId) {
		BaseVo baseVo = new BaseVo();
		ProjectPrice projectPriceById = projectPriceDao.getProjectPriceById(projectPriceId);
		if (projectPriceById == null) {
			throw new BusinessException("报价不存在");
		}
		Project project = projectDao.selectById(projectPriceById.getProjectId());
		ProjectOrderWebVO projectOrderWebVO = new ProjectOrderWebVO();
		BeanUtils.copyProperties(project, projectOrderWebVO);
		//根据projectid查询 所有的订单
		ProjectEleOrder order = new ProjectEleOrder();
		order.setProjectId(projectPriceById.getProjectId());
		order.setVersionId(projectPriceId);
		List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);
		//List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);

		if (CollectionUtils.isNotEmpty(byParam)) {

			List<ProjectPriceDetailInfo> projectPriceDetailInfos = new ArrayList<>();

			for (ProjectEleOrder projectEleOrder : byParam) {
				ProjectPriceDetailInfo projectPriceDetailInfo = new ProjectPriceDetailInfo();
				//设置电梯类型和单价
				projectPriceDetailInfo.setElevatorTypeName(projectEleOrder.getElevatorTypeName());
				projectPriceDetailInfo.setUnitPrice(projectEleOrder.getUnitPrice());
				projectPriceDetailInfo.setRate(projectEleOrder.getRate());
				projectPriceDetailInfo.setBasePrice(projectEleOrder.getBasePrice());
				projectPriceDetailInfo.setAdditionPrice(projectEleOrder.getAdditionPrice());
				projectPriceDetailInfo.setTotalPrice(projectEleOrder.getTotalPrice());
				projectPriceDetailInfo.setNums(projectEleOrder.getNum());
				//查询订单总价
				projectPriceDetailInfo.setOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setPriceId(projectEleOrder.getVersionId());

				ProjectPrice orderprice = projectPriceDao.getProjectPriceById(projectEleOrder.getVersionId());
				if (orderprice != null) {
					projectPriceDetailInfo.setOrderPrice(orderprice.getTotalPrice());
					//乐观锁 更新时候使用
					projectPriceDetailInfo.setPositiveLock(orderprice.getPositiveLock());
				}

				//设置规格参数 和 井道参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setElementParams(elementParamByOrderId);


				// 新增电梯非标项
				projectPriceDetailInfo.setStandard(projectEleOrder.getStandard());
				if (projectEleOrder.getStandard() != null) {
					if (projectEleOrder.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
						Map cod = new HashMap();
						cod.put("order_id", projectEleOrder.getId());
						List<ProjectEleNonstandard> list = projectEleNonstandardDao.selectByMap(cod);
						projectPriceDetailInfo.setProjectEleNonstandardOptionList(list);
						projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					} else {
						projectPriceDetailInfo.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
					}
				}
				projectPriceDetailInfos.add(projectPriceDetailInfo);

			}
			//查询项目报价版本
			ProjectPrice projectPrice = projectPriceDao.selectById(projectPriceId);
			projectOrderWebVO.setVersion(projectPrice.getVersion());
			projectOrderWebVO.setStandard(projectPrice.getStandard());
			projectOrderWebVO.setStandardName(projectPrice.getStandardName());
			projectOrderWebVO.setTotalPrice(projectPrice.getTotalPrice());
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
		if (CollectionUtils.isEmpty(projectPrices)) {
			throw new BusinessException("参数为空");
		}
		List<ProjectEleNonstandardVO> updatelist = new ArrayList<>();
		List<ProjectEleOrder> orderList = new ArrayList<>();
		Long priceId = null;
		ProjectPrice temp = null;
		for (ProjectEleNonstandardVO priceVO : projectPrices) {

			if (priceId == null) {
				priceId = priceVO.getPriceId();
			}

			if (temp == null) {
				temp = projectPriceDao.selectById(priceId);
			}
			if (temp != null) {
				if (temp.getNonStandardApplyStatus().equals(NonStandardApplyStatusEnum.TONGGUO.getCode())) {
					throw new BusinessException("此报价已经通过");
				}
				if (priceVO.getPositiveLock().equals(temp.getPositiveLock())) {
					updatelist.add(priceVO);
				}
			}
			ProjectEleOrder order = new ProjectEleOrder();
			order.setId(priceVO.getOrderId());
			if (priceVO.getProductionFlag().equals(ProductionFlagEnum.YES.getCode())) {
				order.setCalculateFlag(CalculateFlagEnum.YES.getCode());
			} else if (priceVO.getProductionFlag().equals(ProductionFlagEnum.NO.getCode())) {
				order.setCalculateFlag(CalculateFlagEnum.NO.getCode());
			}

			orderList.add(order);
		}

		if (CollectionUtils.isNotEmpty(updatelist)) {
			//更改t_project_price的non_standard_apply_status状态
			projectPriceDao.updatebatchProjectPrice(updatelist);
			//更改t_project_ele_nonstandard的total_price
			projectEleNonstandardDao.updatebatchNonstandard(projectPrices);
			//更改t_project_ele_order的calculate_flag
			if (CollectionUtils.isNotEmpty(orderList)) {
				projectEleOrderDao.updateBatchEleOrder(orderList);
			}

			Long userId = getCurrentUserInfo().getId();
			String realName = getCurrentUserInfo().getRealName();
			//插入审批表
			Audit audit = new Audit();

			audit.setAuditUserId(userId);
			audit.setAuditUserName(realName);
			audit.setAuditTime(new Date());
			audit.setAuditType(AuditTypeEnum.AUDIT.getCode());
			ProjectPrice pp = projectPriceDao.selectById(priceId);
			if (pp!=null){
				audit.setProjectId(pp.getProjectId());
			}
			audit.setProjectPriceId(priceId);
			auditDao.insert(audit);


			//算价钱

			//查询项目id
			ProjectPrice ppr = projectPriceDao.selectById(projectPrices.get(0).getPriceId());
			Map<String, Object> cod = new HashMap<>();
			cod.put("projectId", ppr.getProjectId());
			cod.put("version", ppr.getVersion());
			cod.put("isUpdate", true);
			if (temp != null) {

				if (temp.getInstallFlag().equals(InstallFlagEnum.YES.getCode())) {
					cod.put("包括安装", true);
				}
				if (temp.getTransportFlag().equals(TransportFlagEnum.YES.getCode())) {
					cod.put("包括运费", true);
				}

				equationServiceImpl.executeCountProjectPrice(cod);

			} else {
				throw new BusinessException("无此报价数据");
			}
			//ProjectPrice projectPriceByProjectId = projectPriceDao.getProjectPriceByProjectIdWithVersion(ppr.getProjectId(), ppr.getVersion());


		}

		return successVo();
	}


	/**
	 * 报价列表分页查询
	 *
	 * @param projectPageVO
	 * @return
	 */
	@Override
	public BaseVo listPage(ProjectPageVO projectPageVO) {
		Page<ProjectShowVO> page = new Page<>(projectPageVO.getPageNum(), projectPageVO.getPageSize());

		if(projectPageVO.getNonStandardApplyStatus()==null){
			List<Integer> statusList=new ArrayList<>();
			statusList.add(NonStandardApplyStatusEnum.DAISHENHE.getCode());
			statusList.add(NonStandardApplyStatusEnum.TONGGUO.getCode());
			projectPageVO.setNonStandardApplyStatusList(statusList);
		}
		IPage<ProjectShowVO> listPage = projectPriceDao.listPage(page, projectPageVO);
		List<Long> projectIds = new ArrayList<>();
		List<Long> projectPriceIds = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(listPage.getRecords())) {
			for (ProjectShowVO projectShowVO : listPage.getRecords()) {

				projectIds.add(projectShowVO.getProjectId());
				projectPriceIds.add(projectShowVO.getProjectPriceId());
			}
		}
		Map map = new HashMap();
		map.put("projectIds", projectIds);
		map.put("projectPriceIds", projectPriceIds);

		List<Audit> byParamOnlyOne = auditDao.findByParamBatchByProjectIdAndProjectPriceId(map);
		if (CollectionUtils.isNotEmpty(byParamOnlyOne)){
			/*for (ProjectShowVO projectShowVO : listPage.getRecords()) {
				for (Audit audit : byParamOnlyOne) {
					if (audit.getProjectId().equals(projectShowVO.getProjectId()) &&
							audit.getProjectPriceId().equals(projectShowVO.getProjectPriceId())){
						projectShowVO.setCreateTime(audit.getAuditTime());
						projectShowVO.setAuditUserName(audit.getAuditUserName());
						break;
					}
				}
			}*/
			for (int i=0;i<listPage.getRecords().size();i++) {
				for (Audit audit : byParamOnlyOne) {
					if (audit.getProjectPriceId().equals(listPage.getRecords().get(i).getProjectPriceId())){
						listPage.getRecords().get(i).setCreateTime(audit.getAuditTime());
						listPage.getRecords().get(i).setAuditUserName(audit.getAuditUserName());
						break;
					}
				}
				//如果待审批 就把审批人设置为空
				if (listPage.getRecords().get(i).getNonStandardApplyStatus().equals(NonStandardApplyStatusEnum.DAISHENHE.getCode())) {
					listPage.getRecords().get(i).setAuditUserName("");
				}
			}
		}
		BaseVo baseVo = new BaseVo();
		baseVo.setData(listPage);
		return baseVo;
	}


}