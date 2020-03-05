package com.bit.module.miniapp.service.impl;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.dto.UserInfo;
import com.bit.base.exception.BusinessException;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.base.vo.SuccessVo;
import com.bit.common.businessEnum.*;
import com.bit.common.consts.Const;
import com.bit.common.informationEnum.StageEnum;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.common.informationEnum.UserRoleEnum;
import com.bit.common.wxenum.ResultCode;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import com.bit.module.manager.vo.*;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.service.ExportService;
import com.bit.module.miniapp.service.WxElevatorService;
import com.bit.module.miniapp.vo.ExcelVo;
import com.bit.module.miniapp.vo.PriceEnquireAuditVo;
import com.bit.module.miniapp.vo.ProjectEleOptionsVo;
import com.bit.module.miniapp.vo.ReportInfoVO;
import com.bit.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.OpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Service
@Slf4j
public class WxElevatorServiceImpl extends BaseService implements WxElevatorService {


	@Autowired
	private OptionsDao optionsDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectEleOptionsDao projectEleOptionsDao;

	@Autowired
	private ElevatorTypeDao elevatorTypeDao;

	@Autowired
	private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;

	@Autowired
	private CompanyRateDao companyRateDao;

	@Autowired
	private ProjectEleOrderDao projectEleOrderDao;

	@Autowired
	private ProjectPriceDao projectPriceDao;

	@Autowired
	private EquationServiceImpl equationServiceImpl;

	@Autowired
	private ProjectEleNonstandardDao projectEleNonstandardDao;

	@Autowired
	private EnquiryAuditDao enquiryAuditDao;

	@Autowired
	private AuditDao auditDao;

	@Autowired
	private ExportService exportService;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private UserCompanyDao userCompanyDao;

	@Autowired
	private UserDao userDao;


	@Value("${upload.imagesPath}")
	private String filePath;


	@Override
	public List<Options> getOptions(Integer optionType, Long elevatorTypeId, Long orderId) {
		List<Options> ops = optionsDao.findOptionByElevatorType(elevatorTypeId, optionType);
		Map cod = new HashMap<>();
		cod.put("order_id", orderId);
		List<ProjectEleOrderBaseInfo> list = projectEleOrderBaseInfoDao.selectByMap(cod);
		cod.clear();
		list.forEach(c -> {
			cod.put(c.getParamKey(), c.getInfoValue());
		});
		List<Options> rs = equationServiceImpl.executeEquationsForOption(cod, ops);
		return ops;
	}

	/**
	 * @param optionType      : 填写的类型
	 * @param elevatorTypeId: 电梯类型的id
	 * @param orderBaseInfos: 订单基本信息
	 * @return : List
	 * @description: 获得相关的选项下拉
	 * @author liyujun
	 * @date 2019-12-25
	 */
	@Override
	public List<Options> getOptions(Integer optionType, Long elevatorTypeId, List<ProjectEleOrderBaseInfo> orderBaseInfos) {
		ElevatorType e = elevatorTypeDao.selectById(elevatorTypeId);
		List<Options> ops = optionsDao.findOptionByElevatorType(elevatorTypeId, optionType);
		Map cod = new HashMap<>();
		cod.clear();
		orderBaseInfos.forEach(c -> {
			cod.put(c.getParamKey(), c.getInfoValue());
		});
		cod.put("系列", e.getParamsKey());
		cod.put("梯型", e.getCategory());
		List<Options> allList = optionsDao.selectList(new QueryWrapper<Options>()
				.isNotNull("ocode"));
//		Map<String,Options> allMap = new HashMap<>();
//		allList.forEach(item-> {
//			allMap.put(item.getOcode(), item);
//		});
		Map<String, Options> allMap = allList.stream().collect(Collectors.toMap((key -> key.getOcode()), (value -> value)));
		List<Options> rs = equationServiceImpl.executeEquationsForOption(cod, ops);
		HashSet<String> set = new HashSet();
		rs.forEach(item -> {
			if (StringUtil.isNotEmpty(item.getOcode())) {
				addSet(set, item.getOcode());
			}
		});
		List<Options> res = new ArrayList<>();
		for (String s : set) {
			Options options = allMap.get(s);
			if (options.getOcode().length() <= 3) {
				options.setPocode(null);
			} else {
				options.setPocode(options.getOcode().substring(0, options.getOcode().length() - 3));
			}
			res.add(allMap.get(s));
		}
		return res;
	}

	private void addSet(Set set, String code) {
		if (code.length() <= 3) {
			set.add(code);
		} else {
			set.add(code);
			addSet(set, code.substring(0, code.length() - 3));
		}
	}

	/**
	 * @param vo :
	 * @return : void
	 * @description: 微信端添加报价
	 * @author liyujun
	 * @date 2019-12-19
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map wxAddReportInfo(ReportInfoVO vo) {
		/**新增报价**/
		Map<String, Object> baseParams = new HashMap<>(vo.getBaseinfo().size());
		ProjectEleOrder order = new ProjectEleOrder();
		order.setElevatorTypeId(vo.getElevatorTypeId());
		ElevatorType elevatorType = elevatorTypeDao.selectById(vo.getElevatorTypeId());
		order.setElevatorTypeName(elevatorType.getTypeName());
		try {
			baseParams = BeanReflectUtil.listmap(vo.getBaseinfo(), "paramKey", "infoValue");
		} catch (Exception e) {
			e.printStackTrace();
		}
		order.setRate(vo.getRate());
		order.setProjectId(vo.getProjectId());
		order.setNum(String.valueOf(baseParams.get("台量")));
		order.setStandard(StandardEnum.STANDARD_ONE.getCode());
		order.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
		Map<String, Object> cod = new HashMap<>();
		cod.put("project_id", vo.getProjectId());
		cod.put("version", -1);
		List<ProjectPrice> list1 = projectPriceDao.selectByMap(cod);
		ProjectPrice a = new ProjectPrice();
		if (list1.size() == 0) {
			//todo 非标报价更新价格版本的信息，目前只更新一次，需要优化
			a.setCreateTime(new Date());
			a.setProjectId(vo.getProjectId());
			a.setVersion(-1);
			a.setCreateUserId(getCurrentUserInfo().getId());
			a.setStage(StageEnum.STAGE_ONE.getCode());
			a.setStageName(StageEnum.STAGE_ONE.getInfo());

			//默认为标准
			a.setStandard(StandardEnum.STANDARD_ONE.getCode());
			a.setStandardName(StandardEnum.STANDARD_ONE.getInfo());

			a.setInstallFlag(InstallFlagEnum.NO.getCode());
			a.setTransportFlag(TransportFlagEnum.NO.getCode());
			a.setEnquiryApplyStatus(EnquiryApplyStatusEnum.WEITIJIAO.getCode());
			projectPriceDao.insert(a);
		} else {
			a = list1.get(0);
		}
		order.setVersionId(a.getId());
		//新增电梯订单
		projectEleOrderDao.insert(order);

		// 需要优化为批量新增方法,填写整体的基础信息
		vo.getBaseinfo().stream().forEach(c -> {
			c.setOrderId(order.getId());
		});
		List<ProjectEleOrderBaseInfo> baseinfo = vo.getBaseinfo();
		if (CollectionUtils.isNotEmpty(baseinfo)) {
			projectEleOrderBaseInfoDao.batchAdd(baseinfo);
		}

		vo.getProjectEleOptions().stream().forEach(c -> {
			c.setOrderId(order.getId());
			c.setProjectInfoId(vo.getProjectId());
		});

		List<ProjectEleOptions> projectEleOptions = vo.getProjectEleOptions();
		if (CollectionUtils.isNotEmpty(projectEleOptions)) {
			projectEleOptionsDao.batchAdd(projectEleOptions);
		}

		// 需要进行报价算法,估价组织数据
		Map par = new HashMap();
		par.put("下浮", vo.getRate());
		par.put("台量", order.getNum());
		par.put("orderId", order.getId());
		if (a.getInstallFlag().equals(InstallFlagEnum.YES.getCode())) {
			par.put("包括安装", true);
		}
		if (a.getTransportFlag().equals(TransportFlagEnum.YES.getCode())) {
			par.put("包括运费", true);
		}
		//新增参数
		par.put("isUpdate", true);
		//算价
		equationServiceImpl.executeCount(par);
		//反查报价
		a = projectPriceDao.selectById(a.getId());
		String sysNodOptions = "";
		if (par != null || par.containsKey("是否为非标")) {
			if (Boolean.TRUE.equals(par.get("是否为非标"))) {
				order.setStandard(StandardEnum.STANDARD_ZERO.getCode());
				order.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
				if (!a.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
					a.setStandard(StandardEnum.STANDARD_ZERO.getCode());
					a.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					a.setNonStandardApplyStatus(NonStandardApplyStatusEnum.DAITIJIAO.getCode());
					projectPriceDao.updateById(a);
				}
				if (par.containsKey("非标详情")) {
					sysNodOptions = String.valueOf(par.get("非标详情"));
				}

			} else {
				a.setStandard(StandardEnum.STANDARD_ONE.getCode());
				a.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
				a.setNonStandardApplyStatus(NonStandardApplyStatusEnum.WUXUSHENPI.getCode());
			}

			//新增电梯的非标项数据
			if (CollectionUtils.isNotEmpty(vo.getProjectEleNonstandardList())) {

				for (ProjectEleNonstandard non : vo.getProjectEleNonstandardList()) {
					//添加订单id
					non.setOrderId(order.getId());
					Map rs = new HashMap();
					rs.put("input", non.getContent());
					rs.put("auto", sysNodOptions);
					non.setContent(JSON.toJSONString(rs));
				}

				//报价非标
				if (!a.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
					a.setStandard(StandardEnum.STANDARD_ZERO.getCode());
					a.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					a.setNonStandardApplyStatus(NonStandardApplyStatusEnum.DAITIJIAO.getCode());

					projectPriceDao.updateById(a);
				}
				if (!order.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
					order.setStandard(StandardEnum.STANDARD_ZERO.getCode());
					order.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
					order.setCalculateFlag(NodeOrderCalculateStatusEnum.BUJISUAN.getCode());
				}
				projectEleOrderDao.updateById(order);
				//新增非标项
				projectEleNonstandardDao.batchAdd(vo.getProjectEleNonstandardList());

			} else {
				if (sysNodOptions != null && !sysNodOptions.equals("")) {
					Map rs = new HashMap();
					rs.put("input", "");
					rs.put("auto", sysNodOptions);
					ProjectEleNonstandard aaa = new ProjectEleNonstandard();
					aaa.setContent(JSON.toJSONString(rs));
					aaa.setOrderId(order.getId());
					projectEleNonstandardDao.insert(aaa);
					//报价非标
					if (!a.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
						a.setStandard(StandardEnum.STANDARD_ZERO.getCode());
						a.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
						a.setNonStandardApplyStatus(NonStandardApplyStatusEnum.DAITIJIAO.getCode());

						projectPriceDao.updateById(a);
					}
					if (!order.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
						order.setStandard(StandardEnum.STANDARD_ZERO.getCode());
						order.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
						order.setCalculateFlag(NodeOrderCalculateStatusEnum.BUJISUAN.getCode());
					} else {
						order.setStandard(StandardEnum.STANDARD_ZERO.getCode());
						order.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
						order.setCalculateFlag(NodeOrderCalculateStatusEnum.BUJISUAN.getCode());
					}
					projectEleOrderDao.updateById(order);
				}


			}


		}

		Map rrs = new HashMap();
		rrs.put("nums", order.getNum());
		par.clear();
		par.put("project_id", vo.getProjectId());
		par.put("version", -1);
		rrs.put("elePriceId", a.getId());
		rrs.put("orderId", order.getId());
		ProjectEleOrder a1 = projectEleOrderDao.selectById(order.getId());
		if (a1 != null) {
			rrs.put("orderPrice", a1.getTotalPrice());
		}
		return rrs;
	}


	/**
	 * @param projectId
	 * @return : void
	 * @description: 计算按钮
	 * @author liyujun
	 * @date 2019-12-19
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map pojectPriceTest(Long projectId) {
		Map cod = new HashMap();
		cod.put("projectId", projectId);
		cod.put("verison_id", -1);
		equationServiceImpl.executeCountProjectPrice(cod);
		Map par = new HashMap();
		par.put("project_id", projectId);
		par.put("version", -1);
		List<ProjectPrice> projectPriceList = projectPriceDao.selectByMap(par);
		par.clear();
		par.put("version_id", projectPriceList.get(0).getId());
		par.put("project_id", projectId);
		cod.clear();
		cod.put("totalPrice", projectPriceList.get(0).getTotalPrice());
		List<ProjectEleOrder> list = projectEleOrderDao.selectByMap(par);
		cod.put("orderPrice", list);
		return cod;
	}

	/**
	 * @param projectId
	 * @return : void
	 * @description: 标准的草稿转正式版本，非标的草稿转正式并进行非标技术支持预审核
	 * @author liyujun
	 * @date 2019-12-19
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseVo proPriceToVersion(Long projectId) {
		Integer version = projectPriceDao.getMaxVersion(projectId);
		//算价钱
		Map<String, Object> cod = new HashMap<>();
		cod.put("projectId", projectId);
		cod.put("version", "-1");
		cod.put("isUpdate", true);
		ProjectPrice o = new ProjectPrice();
		ProjectPrice projectPriceByProjectId = projectPriceDao.getProjectPriceByProjectIdWithVersion(projectId, -1);
		if (projectPriceByProjectId != null) {
			if (projectPriceByProjectId.getInstallFlag().equals(Const.FLAG_YES)) {
				cod.put("包括安装", true);
				o.setStage(StageEnum.STAGE_ZERO.getCode());
				o.setStageName(StageEnum.STAGE_ZERO.getInfo());
			}
			if (projectPriceByProjectId.getTransportFlag().equals(Const.FLAG_YES)) {
				cod.put("包括运费", true);
				o.setStage(StageEnum.STAGE_ZERO.getCode());
				o.setStageName(StageEnum.STAGE_ZERO.getInfo());
			}
		}
		if (version != null && version > -1) {
			version = version + 1;

		} else if (version.equals(-1) || version == null) {
			version = 1;
		}
		//根据projectid和version查询记录 只有一条

		o.setVersion(-1);
		o.setProjectId(projectId);
		QueryWrapper<ProjectPrice> projectPriceQueryWrapper = new QueryWrapper<>();
		ProjectPrice projectPrice = projectPriceDao.getProjectPriceByProjectIdWithVersion(projectId, -1);
		//更新记录
		ProjectPrice entity = new ProjectPrice();
		entity.setVersion(version);
		projectPriceQueryWrapper.setEntity(o);

		// 新增非标预审核的状态,转为待审核
		if (projectPriceByProjectId.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
			//非标准的话进行状态转为待审批
			entity.setNonStandardApplyStatus(NonStandardApplyStatusEnum.DAISHENHE.getCode());

			Audit audit = new Audit();
			audit.setAuditUserId(getCurrentUserInfo().getId());
			audit.setAuditUserName(getCurrentUserInfo().getRealName());
			audit.setAuditTime(new Date());
			audit.setAuditType(AuditTypeEnum.SUBMIT.getCode());
			audit.setProjectId(projectId);
			audit.setProjectPriceId(projectPrice.getId());
			auditDao.insert(audit);
		} else {
			//标准不进行总价的计算
			equationServiceImpl.executeCountProjectPrice(cod);
			entity.setNonStandardApplyStatus(NonStandardApplyStatusEnum.WUXUSHENPI.getCode());
		}

		projectPriceDao.update(entity, projectPriceQueryWrapper);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectPrice.getId());
		return baseVo;
	}

	/**
	 * @param projectId      项目id
	 * @param projectPriceId 报价数据的id 就是t_project_ele_order的versionid
	 * @return : void
	 * @description: 修改前的备份数据
	 * @author liyujun
	 * @date 2019-12-26
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseVo updateProjectPrice(Long projectId, Long projectPriceId) {
		BaseVo baseVo = new BaseVo();
		Map<String, Object> p = new HashMap<>();
		//根据订单的报价版本 查询报价记录
		ProjectPrice projectPrice = projectPriceDao.selectById(projectPriceId);

		//订单id集合
		List<Long> ids = new ArrayList();
		List<Long> idsNew = new ArrayList();
		if (projectPrice.getVersion() == -1) {
			baseVo.setData(p);
			return baseVo;
		} else {
			//正式版本copy 转成草稿
			if (projectPrice != null) {
				//删除草稿
				delELePriceInfo(projectId);
				p.put("version_id", projectPrice.getId().longValue());
				//根据versionid查询订单记录
				List<ProjectEleOrder> list1 = projectEleOrderDao.selectByMap(p);
				projectPrice.setId(null);
				projectPrice.setVersion(-1);
				projectPrice.setCreateTime(new Date());
				projectPrice.setTotalPrice(null);
				projectPrice.setPositiveLock(1);

				//非标的话，就强制将审批状态置为待提交
				if (projectPrice.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
					projectPrice.setNonStandardApplyStatus(NonStandardApplyStatusEnum.DAITIJIAO.getCode());
				}
				//1.2版本新增逻辑，将如果是议价则所属字段为空
				if (!projectPrice.getEnquiryApplyStatus().equals(EnquiryApplyStatusEnum.WEITIJIAO.getCode())) {
					projectPrice.setEnquiryApplyStatus(EnquiryApplyStatusEnum.WEITIJIAO.getCode());
					projectPrice.setInquiryPrice(null);
					projectPrice.setAverageRate(null);
					projectPrice.setMaxRate(null);
					projectPrice.setCostTotalPrice(null);
					projectPrice.setEnquiryApplyTime(null);
					projectPrice.setEnquiryAuditUserCompanyId(null);
					projectPrice.setEnquiryAuditUserId(null);
				}
				//新增一个草稿报价
				projectPriceDao.insert(projectPrice);

				//todo 优化查询算法
				for (ProjectEleOrder pro : list1) {//遍历订单数据，查询关联的option
					//准备order 数据
					ids.add(pro.getId());
				}
				//根据订单id集合批量查询项目的可选项
				QueryWrapper<ProjectEleOptions> queryWrapper = new QueryWrapper<>();
				queryWrapper.in("order_id", ids);
				List<ProjectEleOptions> optionsList = projectEleOptionsDao.selectList(queryWrapper);//得到原来的关联的数据
				for (ProjectEleOrder pro : list1) {
					long orderIdOld = pro.getId();
					pro.setId(null);
					//批量新增 返回id
					pro.setVersionId(projectPrice.getId());
					if (pro.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
						pro.setCalculateFlag(CalculateFlagEnum.NO.getCode());
					} else {
						pro.setCalculateFlag(CalculateFlagEnum.YES.getCode());
					}
					//start 1.2 版本新加逻辑，置空之前算的价格
					pro.setRate(0.0);
					pro.setCostBasePrice(null);
					pro.setBasePrice(null);
					pro.setTotalPrice(null);
					pro.setSingleTotalPrice(null);
					pro.setUnitPrice(null);
					pro.setTransportPrice(null);
					pro.setInstallPrice(null);
					//end
					projectEleOrderDao.insert(pro);

					//新增非标项的复制功能
					if (pro.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
						Map cods = new HashMap(1);
						cods.put("order_id", orderIdOld);
						List<ProjectEleNonstandard> listNon = projectEleNonstandardDao.selectByMap(cods);
						if (CollectionUtils.isNotEmpty(listNon)) {
							listNon.forEach(c -> {
								c.setOrderId(pro.getId());
								c.setId(null);
								c.setAuditRemark(null);
								c.setProductionFlag(null);
								c.setSignalPrice(null);
								c.setTotalPrice(null);
							});
							projectEleNonstandardDao.batchAdd(listNon);
						}
					}
					//新增基础数据
					Map cod = new HashMap();
					cod.put("order_id", orderIdOld);
					List<ProjectEleOrderBaseInfo> list = projectEleOrderBaseInfoDao.selectByMap(cod);
					list.forEach(c -> {
						c.setOrderId(pro.getId());
						c.setId(null);
					});
					projectEleOrderBaseInfoDao.batchAdd(list);
					idsNew.add(pro.getId());
					for (ProjectEleOptions options : optionsList) {
						//todo 优化批量插入
						if (options.getOrderId().equals(orderIdOld)) {
							options.setOrderId(pro.getId());
							options.setId(null);
							projectEleOptionsDao.insert(options);
						}

					}
				}
			}

		}
		p.put("orderIds", ids);
		p.put("newOrderIds", idsNew);
		p.put("projectPriceId", projectPrice.getId());
		p.put("version", -1);
		baseVo.setData(p);
		return baseVo;
	}

	/**
	 * 根据订单id删除订单
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo delOrderByOrderId(Long orderId) {

		ProjectEleOrder projectEleOrder = projectEleOrderDao.selectById(orderId);
		if (projectEleOrder == null) {

			throw new BusinessException("无此数据");
		} else {
			//先删除关联数据
			projectEleOrderBaseInfoDao.delByOrderId(orderId);

			projectEleOptionsDao.delByOrderId(orderId);
			Long projectPriceId = projectEleOrder.getVersionId();
			//删除订单记录
			projectEleOrderDao.deleteById(orderId);

			ProjectPrice projectPrice = projectPriceDao.selectById(projectPriceId);
			if (projectEleOrder.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
				//新增 删除非标项
				Map cod = new HashMap(1);
				cod.put("order_id", orderId);
				projectEleNonstandardDao.deleteByMap(cod);
				cod.clear();

				//判断整体删除数据后的逻辑

				if (projectPrice.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())) {
					Map codd = new HashMap(1);
					codd.put("version_id", projectPriceId);
					codd.put("standard", StandardEnum.STANDARD_ZERO.getCode());
					//查询非标的数据剩下多少
					List<ProjectEleOrder> list = projectEleOrderDao.selectByMap(codd);
					if (list == null || list.size() == 0) {
						//判断项目下订单还有多少
						ProjectEleOrder t1 = new ProjectEleOrder();
						t1.setProjectId(projectEleOrder.getProjectId());
						t1.setVersionId(projectPriceId);
						List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(t1);
						if (CollectionUtils.isEmpty(byParam)) {
							projectPrice.setStandard(StandardEnum.STANDARD_ONE.getCode());
							projectPrice.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
							log.info("系统判定为：正常");
						} else {
							projectPrice.setStandard(StandardEnum.STANDARD_ONE.getCode());
							projectPrice.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
							log.info("系统判定为：标准");
						}


					} else {
						projectPrice.setStandard(StandardEnum.STANDARD_ZERO.getCode());
						projectPrice.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());

						log.info("系统判定为：" + StandardEnum.STANDARD_ZERO.getInfo());

					}
					projectPriceDao.updateById(projectPrice);
				}

			} else {
				//添加判断订单数量 更新报价的标准和非标状态

				//查询剩下的订单有几个是非标的 大于0 报价非标 否则标准
				ProjectEleOrder tt = new ProjectEleOrder();
				tt.setProjectId(projectEleOrder.getProjectId());
				tt.setVersionId(projectPriceId);
				List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(tt);
				if (CollectionUtils.isEmpty(byParam)) {
					//如果是空  项目下没订单
					log.info("剩余非标订单数量{}", 0);
					/*projectPrice.setStandard(StandardEnum.STANDARD_DEFAULT.getCode());
					projectPrice.setStandardName(StandardEnum.STANDARD_DEFAULT.getInfo());*/
					projectPrice.setStandard(StandardEnum.STANDARD_ONE.getCode());
					projectPrice.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
					log.info("系统判定为：正常");
				} else {
					tt.setStandard(StandardEnum.STANDARD_ZERO.getCode());
					List<ProjectEleOrder> byParam1 = projectEleOrderDao.findByParam(tt);
					if (CollectionUtils.isNotEmpty(byParam1)) {
						projectPrice.setStandard(StandardEnum.STANDARD_ZERO.getCode());
						projectPrice.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
						log.info("剩余非标订单数量{}", byParam.size());
						log.info("系统判定为：" + StandardEnum.STANDARD_ZERO.getInfo());
					} else {
						projectPrice.setStandard(StandardEnum.STANDARD_ONE.getCode());
						projectPrice.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
						log.info("系统判定为：标准");
					}
				}

				projectPriceDao.updateById(projectPrice);
			}


			//增加统一计算报价
			//新增参数
			/*Map<String, Object> cod = new HashMap<>();
			cod.put("isUpdate", true);
			//算价
			ProjectPrice projectPrice1 =projectPriceDao.selectById(projectEleOrder.getVersionId());
			if(projectPrice1.getTransportFlag()==1){
				cod.put("包含运费",true);
			}
			if(projectPrice1.getInstallFlag()==1){
				cod.put("包括安装", true);
			}
			cod.put("projectId",projectEleOrder.getProjectId());

			cod.put("version",projectPrice1.getVersion());
			equationServiceImpl.executeCount(cod);*/
		}

		return successVo();
	}

	/**
	 * 更新订单
	 *
	 * @param vo
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo updateOrder(ReportInfoVO vo) {
		BaseVo baseVo = new BaseVo();
//		Integer version = projectEleOrderDao.getPriceVersionByOrderId(vo.getOrderId());
		//先删除订单记录
		this.delOrderByOrderId(vo.getOrderId());
		Map map = this.wxAddReportInfo(vo);
		baseVo.setData(map);

		return baseVo;
	}

	/**
	 * 更新报价表的运输 和 安装 标识
	 *
	 * @param projectPrice
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo updateProjectPriceFlag(ProjectPrice projectPrice) {
		//查询项目id
		ProjectPrice ppr = projectPriceDao.selectById(projectPrice.getId());
		if (ppr == null) {
			throw new BusinessException("报价不存在");
		}

		if (projectPrice.getInstallFlag().equals(InstallFlagEnum.YES.getCode()) ||
				projectPrice.getTransportFlag().equals(TransportFlagEnum.YES.getCode())) {
			projectPrice.setStage(StageEnum.STAGE_ZERO.getCode());
			projectPrice.setStageName(StageEnum.STAGE_ZERO.getInfo());
		} else {
			projectPrice.setStage(StageEnum.STAGE_ONE.getCode());
			projectPrice.setStageName(StageEnum.STAGE_ONE.getInfo());
		}
		projectPriceDao.updateById(projectPrice);

		//算价钱
		Map<String, Object> cod = new HashMap<>();
		cod.put("projectId", ppr.getProjectId());
		cod.put("version", "-1");
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
		ProjectEleOrder order = new ProjectEleOrder();
		order.setVersionId(projectPrice.getId());
		List<ProjectEleOrder> byParam = projectEleOrderDao.findByParam(order);
		if (CollectionUtils.isNotEmpty(byParam)) {
			for (ProjectEleOrder projectEleOrder : byParam) {
				if (!cod.containsKey("包括安装")) {
					projectEleOrder.setInstallPrice("");
				}
				if (!cod.containsKey("包括运费")) {
					projectEleOrder.setTransportPrice("");
				}
			}
			projectEleOrderDao.updateBatchEleOrder(byParam);
		}

		equationServiceImpl.executeCountProjectPrice(cod);
		//批量更新运费和安装费

		return successVo();
	}


	/**
	 * @param projectId :
	 * @return : void
	 * @description: 删除旧版本
	 * @author liyujun
	 * @date 2019-12-26
	 */
	private void delELePriceInfo(Long projectId) {
		Map<String, Object> p = new HashMap<>();
		p.put("project_id", projectId);
		p.put("version", -1);
		List<ProjectPrice> list = projectPriceDao.selectByMap(p);
		if (list.size() == 1) {
			p.clear();
			ProjectPrice a = list.get(0);
			p.put("version_id", a.getId());
			List<ProjectEleOrder> orderList = projectEleOrderDao.selectByMap(p);
			if (orderList.size() > 0) {
				List<Long> ids = new ArrayList<>();
				orderList.forEach(c -> {
					ids.add(c.getId());
				});
				QueryWrapper<ProjectEleOptions> queryWrapper = new QueryWrapper<>();
				queryWrapper.in("order_id", ids);
				//删除所选项；
				projectEleOptionsDao.delete(queryWrapper);
				QueryWrapper<ProjectEleOrder> queryWrapper1 = new QueryWrapper<>();
				queryWrapper1.eq("version_id", a.getId());
				//删除电梯订单；
				projectEleOrderDao.delete(queryWrapper1);
				//  新增删除非标的关联的数据
				if (a.getStandard().equals(StandardEnum.STANDARD_ZERO)) {
					QueryWrapper<ProjectEleNonstandard> queryWrapperPd = new QueryWrapper<ProjectEleNonstandard>();
					queryWrapperPd.in("order_id", ids);
					projectEleNonstandardDao.delete(queryWrapperPd);
				}
			}

		}
		QueryWrapper<ProjectPrice> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("version", -1);
		queryWrapper.eq("project_id", projectId);
		//删除草稿状态
		projectPriceDao.delete(queryWrapper);

	}

	/**
	 * 生成报价单发送邮件
	 *
	 * @param projectPriceId (id)
	 * @return
	 */
	@Override
	public BaseVo sendPriceMail(Long projectPriceId, List<String> ccAddress) {
		if (StringUtils.isEmpty(getCurrentUserInfo().getEmail())) {
			throw new BusinessException("无邮件地址");
		} else {
			exportService.sendPriceMail(projectPriceId, ccAddress, getCurrentUserInfo().getEmail());
		}
		return new SuccessVo();
	}

	/**
	 * 生成报价单发送邮件
	 *
	 * @param projectPriceId (id)
	 * @return
	 */
	/* @Async
	 void sendPriceMail(Long projectPriceId, List<String>ccAddress, String toAdress) {

		Map cod = new HashMap();
		cod.put("version_id", projectPriceId);
		Project  j=new Project();

		ProjectPrice p = projectPriceDao.selectById(projectPriceId);
		if (p != null) {
			cod.put("project_id", p.getProjectId());
			j=projectDao.selectById(p.getProjectId());
		}else{
			throw new BusinessException("无此项目数据");
		}
		//订单数据
		List<ProjectEleOrder> orderList = projectEleOrderDao.selectByMap(cod);
		List<ExcelVo> listVo = new ArrayList<ExcelVo>();

		int i = 1;
		for (ProjectEleOrder c : orderList) {
			ExcelVo vo = new ExcelVo();
			vo.setElevatorName(c.getElevatorTypeName());
			vo.setNums(c.getNum());
			vo.setInstallPrice(c.getInstallPrice());
			vo.setSingleTotalPrice(c.getSingleTotalPrice());
			vo.setTotalPrice(c.getTotalPrice());
			vo.setCid(i);
			//项目名称
			if(i==1){
				vo.setProjectName(j.getProjectName());
			}
			Map codss = new HashMap();
			codss.put("order_id", c.getId());
			List<ProjectEleOrderBaseInfo> list = projectEleOrderBaseInfoDao.selectByMap(codss);
			list.forEach(cc -> {

				if (cc.getParamKey().equals("速度")) {
					vo.setSpeed(cc.getInfoValue());
				} else if (cc.getParamKey().equals("载重")) {
					vo.setWeight(cc.getInfoValue());
				} else if (cc.getParamKey().equals("层站")) {
					vo.setFloors(cc.getInfoValue());
				}else if(cc.getParamKey().equals("角度")){
					vo.setFloors(cc.getInfoValue()+"°");
				}else if(cc.getParamKey().equals("梯级宽度")){
					vo.setWeight(cc.getInfoValue());
				}
			});
			//结束
			listVo.add(vo);
			int indexOder=listVo.size();
			//
			i++;
			//选装项
			List<ProjectEleOptionsVo> optins = projectEleOptionsDao.findOptionByOrder(c.getId(), null);

            if(optins.size()>0&&optins.size()>1){
            	for(int ii=0;ii<optins.size();ii++){
            		if(ii==0){
            			vo.setOptions(optins.get(ii).getOptionName());
					}else{
						ExcelVo voOption = new ExcelVo();
						voOption.setOptions(optins.get(ii).getOptionName());
						listVo.add(voOption);
					}
				}
			}else if(optins.size()==1){
                vo.setOptions(optins.get(0).getOptionName());
			}
            int endIndex=listVo.size();
            //选装项结束

			//非标项
			if(c.getStandard().equals(StandardEnum.STANDARD_ZERO.getCode())){
				Map cods=new HashMap();
				cods.put("order_id",c.getId());
				List<ProjectEleNonstandard>listNon= projectEleNonstandardDao.selectByMap(cods);

				if(listNon.size()==1){
					 JSONObject object= JSON.parseObject(listNon.get(0).getContent());
                     vo.setNonStandard( object.get("input").toString()+object.get("auto").toString());
				}else  if(listNon.size()>1){
					vo.setNonStandard(listNon.get(0).getContent());
					for(int ii=1;ii<listNon.size();ii++){
						if((endIndex-1+ii)<=endIndex){
							listVo.get(endIndex-1+ii).setNonStandard(listNon.get(ii).getContent());
						}else{
							ExcelVo voOption = new ExcelVo();
							voOption.setNonStandard(listNon.get(ii).getContent());
							listVo.add(voOption);
						}

					}
				}
			}


		}
		ExcelVo rs = new ExcelVo();
		if (orderList.size() > 0) {
			ProjectPrice aa = projectPriceDao.selectById(orderList.get(0).getVersionId());
			rs.setElevatorName("总价");
			rs.setTotalPrice(aa.getTotalPrice());
			rs.setInstallPrice(ConvertMoneyUtil.convert(Double.parseDouble(orderList.get(0).getTotalPrice())));
		} else {
			rs.setElevatorName("总价");
			rs.setTotalPrice("0");
			rs.setInstallPrice("零");
		}
		listVo.add(rs);
		export(listVo, "电梯价格单",ccAddress,toAdress);

	}*/

	/**
	 * 判断下浮率
	 *
	 * @param elevatorRate
	 * @return
	 */
	@Override
	public BaseVo judgeRate(ElevatorRate elevatorRate) {
		BaseVo baseVo = new BaseVo();
		Long roleId = getCurrentUserInfo().getRole().longValue();
		Long companyId = getCurrentUserInfo().getCompanyId();
		CompanyRate companyRate = new CompanyRate();
		companyRate.setCompanyId(companyId);
		companyRate.setElevatorTypeId(elevatorRate.getElevatorTypeId());
		companyRate.setRoleId(roleId);
		List<CompanyRate> byParam = companyRateDao.findByParam(companyRate);
		if (byParam.size() == 1) {
			CompanyRate crate = byParam.get(0);
			//大于
			if (crate.getRate().compareTo(elevatorRate.getRate()) < 0) {
				baseVo.setCode(ResultCode.RATE_TOO_BIG.getCode());
				baseVo.setMsg(ResultCode.RATE_TOO_BIG.getInfo());
			}
		} else {
			throw new BusinessException("查询结果异常");
		}

		return baseVo;
	}

	/**
	 * 根據人員數據和電梯類型，獲取最大下浮率
	 *
	 * @param elevatorTypeId
	 * @return
	 */
	@Override
	public Map getRate(Long elevatorTypeId) {
		Map cod = new HashMap();
		if (elevatorTypeId == null) {
			throw new BusinessException("參數爲空");
		} else {
			cod.put("elevator_type_id", elevatorTypeId);
			cod.put("company_id", getCurrentUserInfo().getCompanyId());
			cod.put("role_id", getCurrentUserInfo().getRole());
			List<CompanyRate> list = companyRateDao.selectByMap(cod);
			cod.clear();
			if (list.size() > 0) {
				cod.put("rate", list.get(0).getRate());
			} else {
				throw new BusinessException("当前人无此梯型的下浮率");
			}
			return cod;
		}

	}


	/**
	 * 撤销申请
	 *
	 * @param elevatorPriceId
	 * @return
	 */
	@Override
	public BaseVo cancelApply(Long elevatorPriceId) {
		ProjectPrice projectPrice = projectPriceDao.selectById(elevatorPriceId);
		if (projectPrice != null) {
			if (!projectPrice.getCreateUserId().equals(getCurrentUserInfo().getId())) {
				throw new BusinessException("非本人撤销，无法操作");
			} else {
				if (projectPrice.getNonStandardApplyStatus() >= NonStandardApplyStatusEnum.TONGGUO.getCode()) {
					throw new BusinessException("已经审批过无法撤回");
				} else {
					projectPrice.setNonStandardApplyStatus(NonStandardApplyStatusEnum.CHEXIAO.getCode());
					projectPriceDao.updateById(projectPrice);
				}
			}
		}
		return new BaseVo();
	}

	/**
	 * 转发询价
	 *
	 * @param projectPriceVo
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo redirectEnquireAudit(ProjectPriceVo projectPriceVo) {
		ProjectPrice projectPriceById = projectPriceDao.getProjectPriceById(projectPriceVo.getId());
		if (projectPriceById == null) {
			throw new BusinessException("数据不存在");
		}

		Project projectById = projectDao.getProjectById(projectPriceById.getProjectId());
		if (projectById == null) {
			throw new BusinessException("项目数据不存在");
		}
		//验证项目状态
		if (projectById.getClosedStatus().equals(ProjectEnum.PROJECT_FAIL.getCode())){
			throw new BusinessException("项目已关闭");
		}

		//验证能不能询价 一个项目只能有一个审批中的询价
		if (projectPriceById.getEnquiryApplyStatus().equals(EnquiryApplyStatusEnum.CHEXIAO.getCode())){
			throw new BusinessException("项目已撤销");
		}
		//验证当前用户是不是审批人
		if(!getCurrentUserInfo().getId().equals(projectPriceById.getEnquiryAuditUserId())){
			throw new BusinessException("此人无法审批");
		}

		//上级审批人
		User auditor = this.auditor();
		if (auditor == null) {
			throw new BusinessException("无上级审批人");
		}
		projectPriceById.setEnquiryAuditUserId(auditor.getId());
		Company userCompanyByUserId = userCompanyDao.getUserCompanyByUserId(auditor.getId());
		projectPriceById.setEnquiryAuditUserCompanyId(userCompanyByUserId.getId());
//		projectPriceById.setEnquiryApplyTime(new Date());

//		projectPriceById.setInquiryPrice(projectPriceVo.getInquiryPrice());
//		projectPriceById.setCostTotalPrice(projectPriceVo.getCostTotalPrice());
		projectPriceDao.updateById(projectPriceById);

		//往t_enquiry_audit 插一条
		EnquiryAudit enquiryAudit = new EnquiryAudit();
		enquiryAudit.setProjectPriceId(projectPriceVo.getId());
		enquiryAudit.setAuditType(EnquiryAuditTypeEnum.SHENPIJIANJIAO.getCode());
		enquiryAudit.setAuditTypeName(EnquiryAuditTypeEnum.SHENPIJIANJIAO.getInfo());
		enquiryAudit.setAuditUserId(auditor.getId());
		enquiryAudit.setAuditRealName(auditor.getRealName());
		enquiryAudit.setAuditTime(new Date());

		enquiryAuditDao.addEnquiryAudit(enquiryAudit);

		return successVo();
	}


	/**
	 * 定价辅助
	 *
	 * @return
	 */
	@Override
	public BaseVo priceSupport(PriceEnquireAuditVo priceEnquireAuditVo) {
		ProjectPrice rs=equationServiceImpl.countProjectTotalPrice(priceEnquireAuditVo.getProjectPriceId(),priceEnquireAuditVo.getProjectEleOrderList());
		BaseVo vo = new BaseVo();
		Map<String, Object> cod = new HashMap<>();
		cod.put("totalPrice",rs.getTotalPrice());
		vo.setData(cod);
		return vo;
	}

	/**
	 * 拒绝询价
	 *
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo rejectEnquireAudit(Long projectPriceId) {
		ProjectPrice projectPriceById = projectPriceDao.getProjectPriceById(projectPriceId);
		if (projectPriceById == null) {
			throw new BusinessException("记录不存在");
		}
		//验证项目状态
		Project projectById = projectDao.getProjectById(projectPriceById.getProjectId());
		if (projectById == null) {
			throw new BusinessException("项目数据不存在");
		}
		if (projectById.getClosedStatus().equals(ProjectEnum.PROJECT_FAIL.getCode())){
			throw new BusinessException("项目已关闭");
		}
		//验证能不能询价 一个项目只能有一个审批中的询价
		if (projectPriceById.getEnquiryApplyStatus().equals(EnquiryApplyStatusEnum.CHEXIAO.getCode())){
			throw new BusinessException("项目已撤销");
		}
		//验证当前用户是不是审批人
		if(!getCurrentUserInfo().getId().equals(projectPriceById.getEnquiryAuditUserId())){
			throw new BusinessException("此人无法审批");
		}
		//projectPriceById.setEnquiryApplyStatus(EnquiryApplyStatusEnum.SHENNPIJUJUE.getCode());
		ProjectPrice a=new ProjectPrice();
		a.setId(projectPriceId);
		a.setEnquiryAuditUserId(null);
		a.setEnquiryAuditUserCompanyId(null);
		a.setEnquiryApplyStatus(EnquiryApplyStatusEnum.SHENNPIJUJUE.getCode());
		//projectPriceDao.updateProjectPrice(projectPriceById);
		projectPriceDao.updateProjectPriceEnquireAuditWithNull(projectPriceById);

		EnquiryAudit enquiryAudit = new EnquiryAudit();
		enquiryAudit.setAuditUserId(getCurrentUserInfo().getId());
		enquiryAudit.setAuditRealName(getCurrentUserInfo().getRealName());
		enquiryAudit.setAuditTime(new Date());
		enquiryAudit.setProjectPriceId(projectPriceId);
		enquiryAudit.setAuditType(EnquiryAuditTypeEnum.SHENPIJUJUE.getCode());
		enquiryAudit.setAuditTypeName(EnquiryAuditTypeEnum.SHENPIJUJUE.getInfo());
		enquiryAuditDao.addEnquiryAudit(enquiryAudit);

		return successVo();
	}


	/**
	 * 议价列表
	 *
	 * @return
	 */
	@Override
	public BaseVo inquireList(ProjectPageVO projectPageVO) {
		projectPageVO.setEnquiryAuditUserId(getCurrentUserInfo().getId());
		projectPageVO.setEnquiryApplyStatus(EnquiryApplyStatusEnum.SHENNPIZHONG.getCode());
		Page<ProjectShowVO> page = new Page<>(projectPageVO.getPageNum(), projectPageVO.getPageSize());
		IPage<ProjectShowVO> projectPriceVoIPage = projectPriceDao.listPage(page, projectPageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(projectPriceVoIPage);
		return baseVo;
	}

	/**
	 * 上级审批人
	 *
	 * @return
	 */
	private User auditor() {
		//公司id
		Long companyId = getCurrentUserInfo().getCompanyId();
		//角色id
		Long roleId = Long.valueOf(getCurrentUserInfo().getRole());

		//上级审批人
//		User user = this.test(companyId, roleId, companyById.getLevel(),true);
		UserCompany userCompany = new UserCompany();
		getEnquiryAuditUser(companyId, roleId, userCompany);
		User user = userDao.findByUserId(userCompany.getUserId());
		return user;
	}

	/**
	 * 查找审批人
	 *
	 * @param companyId 公司id
	 * @param roleId    角色id
	 * @return
	 */
	private void getEnquiryAuditUser(Long companyId, long roleId, UserCompany userCompany) {

		Company localc = companyDao.selectById(companyId);
		if (localc.getParentId().equals(-1L)) {//到头了
			if (roleId == UserRoleEnum.BOSS.getRoleId()) {
				userCompany = null;
			} else {
				List roleIds = new ArrayList<>();
				roleIds.add(UserRoleEnum.BOSS.getRoleId());
				Map cod = new HashMap();
				cod.put("roleIds", roleIds);
				cod.put("companyId", companyId);
				List<UserCompany> list = userDao.getUserByCompanyIdAndRoleIds(cod);
				if (CollectionUtils.isNotEmpty(list)) {
					BeanUtils.copyProperties(list.get(0),userCompany);
				} else {
					userCompany = null;
				}
			}

		} else {
			List roleIds = new ArrayList<>();
			if (roleId != 0) {
				if (!Long.valueOf(UserRoleEnum.BOSS.getRoleId()).equals(roleId)) {
					roleIds.add(UserRoleEnum.BOSS.getRoleId());
				}
				if (!Long.valueOf(UserRoleEnum.MANAGER.getRoleId()).equals(roleId)) {
					roleIds.add(UserRoleEnum.MANAGER.getRoleId());
				}
				if (!Long.valueOf(UserRoleEnum.REGIONAL_MANAGER.getRoleId()).equals(roleId) ) {
					roleIds.add(UserRoleEnum.REGIONAL_MANAGER.getRoleId());
				}
			} else {
				roleIds.add(UserRoleEnum.BOSS.getRoleId());
				roleIds.add(UserRoleEnum.MANAGER.getRoleId());
				roleIds.add(UserRoleEnum.REGIONAL_MANAGER.getRoleId());
			}
			Map cod = new HashMap();
			cod.put("roleIds", roleIds);
			cod.put("companyId", companyId);
			List<UserCompany> list = userDao.getUserByCompanyIdAndRoleIds(cod);
			if (CollectionUtils.isNotEmpty(list)) {
				BeanUtils.copyProperties(list.get(0),userCompany);
			} else {
				getEnquiryAuditUser(companyDao.selectById(localc.getParentId()).getId(), 0, userCompany);
			}
		}
	}


	/**
	 * 查找审批人
	 *
	 * @param companyId 公司id
	 * @param roleId    角色id
	 * @param level     公司等级
	 * @param flag      是否是第一次递归 是-true 否-false
	 * @return
	 */
	private User test(Long companyId, Long roleId, Integer level, Boolean flag) {
		Company companyById = companyDao.getCompanyById(companyId);
		Long parentId = companyById.getParentId();
		//销售经理
		if (roleId.equals(Long.valueOf(UserRoleEnum.SALES.getRoleId()))) {
			List<User> userByCompanyIdAndRoleId = userDao.getUserByCompanyIdAndRoleId(companyId, Long.valueOf(UserRoleEnum.MANAGER.getRoleId()));
			if (CollectionUtils.isNotEmpty(userByCompanyIdAndRoleId)) {
				User user = userByCompanyIdAndRoleId.get(0);
				return user;
			} else {
				if (level.equals(2)) {
					return test(parentId, Long.valueOf(UserRoleEnum.BOSS.getRoleId()), level, false);
				} else if (level.equals(3)) {
					return test(parentId, Long.valueOf(UserRoleEnum.MANAGER.getRoleId()), level, false);
				}
			}
		} else if (roleId.equals(Long.valueOf(UserRoleEnum.MANAGER.getRoleId()))) {
			//分公司总经理
			if (level.equals(2)) {
				List<User> userByCompanyIdAndRoleId = userDao.getUserByCompanyIdAndRoleId(parentId, Long.valueOf(UserRoleEnum.BOSS.getRoleId()));
				if (CollectionUtils.isNotEmpty(userByCompanyIdAndRoleId)) {
					User user = userByCompanyIdAndRoleId.get(0);
					return user;
				} else {
					return null;
				}
			} else if (level.equals(3)) {
				//第一次执行 传自己的companyId
				if (!flag) {
					List<User> userByCompanyIdAndRoleId = userDao.getUserByCompanyIdAndRoleId(companyId, Long.valueOf(UserRoleEnum.REGIONAL_MANAGER.getRoleId()));
					if (CollectionUtils.isNotEmpty(userByCompanyIdAndRoleId)) {
						User user = userByCompanyIdAndRoleId.get(0);
						return user;
					} else {
						return test(parentId, Long.valueOf(UserRoleEnum.REGIONAL_MANAGER.getRoleId()), level, false);
					}
				} else {
					//第二次执行 传上级的companyId
					List<User> userByCompanyIdAndRoleId = userDao.getUserByCompanyIdAndRoleId(parentId, Long.valueOf(UserRoleEnum.REGIONAL_MANAGER.getRoleId()));
					if (CollectionUtils.isNotEmpty(userByCompanyIdAndRoleId)) {
						User user = userByCompanyIdAndRoleId.get(0);
						return user;
					} else {
						return test(parentId, Long.valueOf(UserRoleEnum.BOSS.getRoleId()), level, false);
					}
				}

			}
		} else if (roleId.equals(Long.valueOf(UserRoleEnum.REGIONAL_MANAGER.getRoleId()))) {
			//区域总经理
			List<User> userByCompanyIdAndRoleId = userDao.getUserByCompanyIdAndRoleId(parentId, Long.valueOf(UserRoleEnum.BOSS.getRoleId()));
			if (CollectionUtils.isNotEmpty(userByCompanyIdAndRoleId)) {
				User user = userByCompanyIdAndRoleId.get(0);
				return user;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 根據人員數據和電梯類型，获得下浮率率
	 *
	 * @param elevatorTypeIds
	 * @return
	 */
	@Override
	public BaseVo getRates(List<Long> elevatorTypeIds) {

		BaseVo<List<CompanyRate>> rs = new BaseVo<>();
		List<CompanyRate> list = companyRateDao.selectList(new QueryWrapper<CompanyRate>().in("elevator_type_id", elevatorTypeIds)
				.eq("company_id", getCurrentUserInfo().getCompanyId())
				.eq("role_id", getCurrentUserInfo().getRole())
		);

		rs.setData(list);
		return rs;
	}

	/**
	 * 提交议价申请
	 *
	 * @param projectPrice
	 * @return
	 */
	@Override
	@Transactional
	public BaseVo applyInquire(ProjectPrice projectPrice) {
		//判断有没有议价中状态
		ProjectPrice projectPriceById = projectPriceDao.getProjectPriceById(projectPrice.getId());
		if (projectPriceById.getEnquiryApplyStatus().equals(EnquiryApplyStatusEnum.SHENNPIZHONG.getCode())){
			throw new BusinessException("项目正在议价中，暂时无法提交新的议价申请");
		}
		//验证项目状态
		Project projectById = projectDao.getProjectById(projectPriceById.getProjectId());
		if (projectById == null) {
			throw new BusinessException("项目数据不存在");
		}
		if (projectById.getProjectStatus().equals(ProjectEnum.PROJECT_FAIL.getCode())){
			throw new BusinessException("项目已关闭");
		}

		projectPrice.setEnquiryApplyTime(new Date());
		projectPrice.setEnquiryApplyStatus(EnquiryApplyStatusEnum.SHENNPIZHONG.getCode());
		projectPrice.setEnquiryApplyTime(new Date());
		//审批人
		User auditor = this.auditor();
		if (auditor == null) {
			throw new BusinessException("无上级审批人");
		}
		Company userCompanyByUserId = userCompanyDao.getUserCompanyByUserId(auditor.getId());
		projectPrice.setEnquiryAuditUserId(auditor.getId());
		projectPrice.setEnquiryAuditUserCompanyId(userCompanyByUserId.getId());
		projectPriceDao.updateProjectPrice(projectPrice);

		EnquiryAudit enquiryAudit = new EnquiryAudit();
		enquiryAudit.setAuditUserId(getCurrentUserInfo().getId());
		enquiryAudit.setAuditRealName(getCurrentUserInfo().getRealName());
		enquiryAudit.setAuditTime(new Date());
		enquiryAudit.setProjectPriceId(projectPrice.getId());
		enquiryAudit.setAuditType(AuditTypeEnum.SUBMIT.getCode());
		enquiryAudit.setAuditTypeName(AuditTypeEnum.SUBMIT.getInfo());
		enquiryAuditDao.addEnquiryAudit(enquiryAudit);

		// 计算接口
		Map map = new HashMap();
		map.put("projectPriceId", projectPrice.getId());
		//map.put("projectId",projectPrice.getProjectId());
		//map.put("version",projectPrice.getVersion());
		map.put("targetPrice", projectPrice.getInquiryPrice());
		map.put("isUpdate", true);

		equationServiceImpl.countAvgDiscountRate(map);

		return successVo();
	}

	/**
	 * 通过议价审批
	 *
	 * @param projectPrice
	 * @return
	 */
	@Transactional
	@Override
	public BaseVo passEnquireAudit1(PriceEnquireAuditVo projectPrice) {

		ProjectPriceDetailVO vo=projectDao.getProjectDetailById(null,projectPrice.getProjectPriceId());
		/*if(vo==null||vo.getProjectStatus().equals(ProjectEnum.PROJECT_FAIL.getCode())){
			throw new BusinessException("此项目已关闭或无此项目");
		}*/
		ProjectPrice price=projectPriceDao.selectById(projectPrice.getProjectPriceId());
		/*if(!getCurrentUserInfo().getId().equals(price.getEnquiryAuditUserId())){
			throw new BusinessException("此人无法审批");
		}*/
		//审批通过
		EnquiryAudit enquiryAudit = new EnquiryAudit();
		enquiryAudit.setAuditType(EnquiryAuditTypeEnum.SHENPITONGGUO.getCode());
		enquiryAudit.setAuditTypeName(EnquiryAuditTypeEnum.SHENPITONGGUO.getInfo());
		enquiryAudit.setAuditUserId(getCurrentUserInfo().getId());
		enquiryAudit.setAuditRealName(getCurrentUserInfo().getRealName());
		enquiryAudit.setAuditTime(new Date());
		enquiryAudit.setProjectPriceId(projectPrice.getProjectPriceId());
		List<Map> rates = new ArrayList<>();
		List<ProjectEleOrder> orders = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(projectPrice.getProjectEleOrderList())) {
			projectPrice.getProjectEleOrderList().forEach(c -> {
				Map a = new HashMap();
				a.put("orderId", c.getId());
				a.put("rate", c.getRate());
				rates.add(a);
				ProjectEleOrder b = new ProjectEleOrder();
				b.setId(c.getId());
				b.setRate(c.getRate());
				orders.add(b);
			});
			projectEleOrderDao.updateBatch(orders);
			enquiryAudit.setRateList(JSON.toJSONString(rates));
		}
		enquiryAuditDao.addEnquiryAudit(enquiryAudit);

		ProjectPrice  pr=new ProjectPrice();
		pr.setEnquiryApplyStatus(EnquiryApplyStatusEnum.SHENPITONGGUO.getCode());
		pr.setEnquiryAuditUserId(null);
		pr.setEnquiryAuditUserCompanyId(null);
		projectPriceDao.updateProjectPriceEnquireAuditWithNull(pr);
		//todo  計算
		Map<String, Object> cod = new HashMap<>();
		cod.put("projectPriceId", projectPrice.getProjectPriceId());
		cod.put("isUpdate", true);
		equationServiceImpl.executeCountProjectPrice(cod, projectPrice.getProjectEleOrderList());

		return successVo();

	}


}