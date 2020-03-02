package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.ProjectEleNonstandardDao;
import com.bit.module.manager.dao.ProjectEleOrderDao;
import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.NegotiationService;
import com.bit.module.manager.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author chenduo
 * @Date 2020/2/27 8:47
 **/
@Service("negotiationService")
public class NegotiationServiceImpl extends BaseService implements NegotiationService {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectPriceDao projectPriceDao;

	@Autowired
	private ProjectEleOrderDao projectEleOrderDao;

	@Autowired
	private ProjectEleNonstandardDao projectEleNonstandardDao;
	/**
	 * 洽谈项目分页查询
	 * @param projectPageVO
	 * @return
	 */
	@Override
	public BaseVo negotiationlistPage(ProjectPageVO projectPageVO) {
		Page<ProjectShowVO> page = new Page<>(projectPageVO.getPageNum(),projectPageVO.getPageSize());
		IPage<ProjectShowVO> projectShowVOIPage = projectDao.negotiationlistPage(page, projectPageVO);
		for (ProjectShowVO projectShowVO : projectShowVOIPage.getRecords()) {
			List<ProjectPrice> latestProjectPrice = projectPriceDao.getLatestProjectPriceNonDraft(projectShowVO.getProjectId());
			if (CollectionUtils.isNotEmpty(latestProjectPrice)){
				projectShowVO.setEnquireTimes(latestProjectPrice.get(0).getVersion());
				projectShowVO.setLatestEnquireDate(latestProjectPrice.get(0).getCreateTime());
				projectShowVO.setProjectPriceId(latestProjectPrice.get(0).getId());
			}
		}

		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectShowVOIPage);
		return baseVo;
	}

	/**
	 * 返显项目
	 * @param negotiationVO
	 * @return
	 */
	@Override
	public BaseVo reflectById(NegotiationVO negotiationVO) {
		BaseVo baseVo = new BaseVo();
		Project projectById = projectDao.getProjectById(negotiationVO.getProjectId());
		ProjectOrderWebVO projectOrderWebVO = new ProjectOrderWebVO();
		BeanUtils.copyProperties(projectById,projectOrderWebVO);
		ProjectPrice projectPriceById = projectPriceDao.getProjectPriceById(negotiationVO.getProjectPriceId());
		if (projectPriceById==null){
			baseVo.setData(projectOrderWebVO);
			return baseVo;
		}

		ProjectPrice pp = new ProjectPrice();
		pp.setProjectId(negotiationVO.getProjectId());
		pp.setVersion(negotiationVO.getEnquireTimes());
		List<ProjectPrice> p2 = projectPriceDao.findByParam(pp);

		//根据projectid查询 所有的订单
		ProjectEleOrder order = new ProjectEleOrder();
		order.setProjectId(negotiationVO.getProjectId());
		order.setVersionId(p2.get(0).getId());
		List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);

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
			ProjectPrice projectPrice = projectPriceDao.selectById(p2.get(0).getId());
			projectOrderWebVO.setVersion(projectPrice.getVersion());
			projectOrderWebVO.setStandard(projectPrice.getStandard());
			projectOrderWebVO.setStandardName(projectPrice.getStandardName());
			projectOrderWebVO.setTotalPrice(projectPrice.getTotalPrice());
			projectOrderWebVO.setProjectPriceDetailInfos(projectPriceDetailInfos);
		}

		baseVo.setData(projectOrderWebVO);
		return baseVo;
	}
}
