package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.ProjectDao;
import com.bit.module.manager.dao.ProjectEleNonstandardDao;
import com.bit.module.manager.dao.ProjectEleOrderDao;
import com.bit.module.manager.vo.ProjectPageVO;
import com.bit.module.manager.vo.ProjectPriceDetailVO;
import com.bit.module.manager.vo.ProjectShowVO;
import com.bit.module.miniapp.bean.Options;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bit.module.manager.dao.ProjectPriceDao;
import com.bit.module.manager.service.ProjectPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	/**
	 * 编辑数据
	 *
	 * @param projectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	@Transactional
	public BaseVo update(ProjectPrice projectPrice) {
		projectPriceDao.updateProjectPrice(projectPrice);
		return successVo();
	}

	/**
	 * 单查项目数据
	 *
	 * @param id
	 * @return ProjectPrice
	 * @author chenduo
	 * @since ${date}
	 */
	@Override
	public BaseVo reflectById(Long id) {
		BaseVo baseVo = new BaseVo();

		Project project = projectDao.selectById(id);
		ProjectPriceDetailVO projectPriceDetailVO = new ProjectPriceDetailVO();
		BeanUtils.copyProperties(project,projectPriceDetailVO);
		//根据projectid查询 所有的订单
		ProjectEleOrder order = new ProjectEleOrder();
		order.setProjectId(id);
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
				//查询订单总价
				projectPriceDetailInfo.setElevatorTypeNameAndUnitPrice(elevatorTypeNameAndUnitPrice);
				ProjectPrice orderprice = projectPriceDao.selectById(projectEleOrder.getVersionId());
				projectPriceDetailInfo.setOrderPrice(orderprice.getTotalPrice());
				//设置规格参数 和 井道参数
				List<ElementParam> elementParamByOrderId = projectDao.getElementParamByOrderId(projectEleOrder.getId());
				projectPriceDetailInfo.setElementParams(elementParamByOrderId);

				List<Options> projectOptions = projectDao.getProjectOptions(projectEleOrder.getId());
				projectPriceDetailInfo.setOptions(projectOptions);

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
			projectPriceDetailVO.setProjectPriceDetailInfos(projectPriceDetailInfos);
			baseVo.setData(projectPriceDetailVO);
		}

		return baseVo;
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