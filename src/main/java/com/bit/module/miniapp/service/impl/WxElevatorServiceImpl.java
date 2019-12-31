package com.bit.module.miniapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.common.businessEnum.OrderPriceAddTypeEnum;
import com.bit.common.informationEnum.StageEnum;
import com.bit.common.informationEnum.StandardEnum;
import com.bit.module.manager.bean.*;
import com.bit.module.manager.dao.*;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import com.bit.module.miniapp.bean.ElevatorType;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.service.WxElevatorService;
import com.bit.module.miniapp.vo.ReportInfoVO;
import com.bit.utils.BeanReflectUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WxElevatorServiceImpl extends BaseService implements WxElevatorService {


    @Autowired
    private OptionsDao optionsDao;


    @Autowired
    private ProjectEleOptionsDao projectEleOptionsDao;

    @Autowired
    private ElevatorTypeDao elevatorTypeDao;

    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;


    @Autowired
    private ProjectEleOrderDao projectEleOrderDao;

    @Autowired
    private ProjectPriceDao projectPriceDao;

    @Autowired
    private EquationServiceImpl equationServiceImpl;


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
        ElevatorType e =elevatorTypeDao.selectById(elevatorTypeId);
        List<Options> ops = optionsDao.findOptionByElevatorType(elevatorTypeId, optionType);
        Map cod = new HashMap<>();
        cod.clear();
        orderBaseInfos.forEach(c -> {
            cod.put(c.getParamKey(), c.getInfoValue());
        });
        cod.put("系列",e.getParamsKey());
        cod.put("梯型",e.getCategory());
        List<Options> rs = equationServiceImpl.executeEquationsForOption(cod, ops);
        return rs;
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
        Map<String, Object> cod = new HashMap<>();
        cod.put("project_id", vo.getProjectId());
        cod.put("version", -1);
        List<ProjectPrice> list1 = projectPriceDao.selectByMap(cod);
        ProjectPrice a = new ProjectPrice();
        if (list1.size() == 0) {
            a.setCreateTime(new Date());
            a.setProjectId(vo.getProjectId());
            a.setVersion(-1);
            a.setCreateUserId(getCurrentUserInfo().getId());
            a.setStage(StageEnum.STAGE_ONE.getCode());
            a.setStageName(StageEnum.STAGE_ONE.getInfo());
            a.setStandard(StandardEnum.STANDARD_DEFAULT.getCode());
            a.setStandardName(StandardEnum.STANDARD_DEFAULT.getInfo());
            projectPriceDao.insert(a);
        } else {
            a = list1.get(0);
        }
        order.setVersionId(a.getId());
        projectEleOrderDao.insert(order);
        // 需要优化为批量新增方法,填写整体的基础信息
        vo.getBaseinfo().stream().forEach(c -> {
            c.setOrderId(order.getId());
        });
		List<ProjectEleOrderBaseInfo> baseinfo = vo.getBaseinfo();
        projectEleOrderBaseInfoDao.batchAdd(baseinfo);

        vo.getProjectEleOptions().stream().forEach(c -> {
            c.setOrderId(order.getId());
            c.setProjectInfoId(vo.getProjectId());
        });

		List<ProjectEleOptions> projectEleOptions = vo.getProjectEleOptions();

		projectEleOptionsDao.batchAdd(projectEleOptions);

        // 需要进行报价算法
        Map par = new HashMap();
        par.put("下浮", vo.getRate());
        par.put("台量",order.getNum());


        par.put("orderId",order.getId());
        equationServiceImpl.executeCount(par);
        //Map rs = equationServiceImpl.executeEquations(par);
        if (par != null || par.containsKey("是否为非标")) {
            if (Boolean.TRUE.equals(par.get("是否为非标"))) {
                a.setStandard(StandardEnum.STANDARD_ZERO.getCode());
                a.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
            } else {
                a.setStandard(StandardEnum.STANDARD_ONE.getCode());
                a.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
            }
            projectPriceDao.updateById(a);
        }

        Map rrs = new HashMap();
        rrs.put("nums", order.getNum());
        par.clear();
        par.put("project_id", vo.getProjectId());
        par.put("version", -1);
        rrs.put("elePriceId",a.getId());
        rrs.put("orderId",order.getId());
        ProjectEleOrder a1=projectEleOrderDao.selectById(order.getId());
        if(a1!=null){
            rrs.put("orderPrice",a1.getTotalPrice());
        }
        return rrs;
    }


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map wxUpdateReportInfo(ReportInfoVO vo) {

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
		Map<String, Object> cod = new HashMap<>();
		cod.put("project_id", vo.getProjectId());
		cod.put("version", -1);
		List<ProjectPrice> list1 = projectPriceDao.selectByMap(cod);
		ProjectPrice a = new ProjectPrice();
		if (list1.size() == 0) {
			a.setCreateTime(new Date());
			a.setProjectId(vo.getProjectId());
			a.setVersion(-1);
			a.setCreateUserId(getCurrentUserInfo().getId());
			a.setStage(StageEnum.STAGE_ONE.getCode());
			a.setStageName(StageEnum.STAGE_ONE.getInfo());
			a.setStandard(StandardEnum.STANDARD_DEFAULT.getCode());
			a.setStandardName(StandardEnum.STANDARD_DEFAULT.getInfo());
			projectPriceDao.insert(a);
		} else {
			a = list1.get(0);
		}
		order.setVersionId(a.getId());
		projectEleOrderDao.insert(order);
		// 需要优化为批量新增方法,填写整体的基础信息
		vo.getBaseinfo().stream().forEach(c -> {
			c.setOrderId(order.getId());
		});
		List<ProjectEleOrderBaseInfo> baseinfo = vo.getBaseinfo();
		projectEleOrderBaseInfoDao.batchAdd(baseinfo);

		vo.getProjectEleOptions().stream().forEach(c -> {
			c.setOrderId(order.getId());
			c.setProjectInfoId(vo.getProjectId());
		});

		List<ProjectEleOptions> projectEleOptions = vo.getProjectEleOptions();

		projectEleOptionsDao.batchAdd(projectEleOptions);

		// 需要进行报价算法
		Map par = new HashMap();
		par.put("下浮", vo.getRate());
		par.put("台量",order.getNum());


		par.put("orderId",order.getId());
		equationServiceImpl.executeCount(par);
		//Map rs = equationServiceImpl.executeEquations(par);
		if (par != null || par.containsKey("是否为非标")) {
			if (Boolean.TRUE.equals(par.get("是否为非标"))) {
				a.setStandard(StandardEnum.STANDARD_ZERO.getCode());
				a.setStandardName(StandardEnum.STANDARD_ZERO.getInfo());
			} else {
				a.setStandard(StandardEnum.STANDARD_ONE.getCode());
				a.setStandardName(StandardEnum.STANDARD_ONE.getInfo());
			}
			projectPriceDao.updateById(a);
		}

		Map rrs = new HashMap();
		rrs.put("nums", order.getNum());
		par.clear();
		par.put("project_id", vo.getProjectId());
		par.put("version", -1);
		rrs.put("elePriceId",a.getId());
		rrs.put("orderId",order.getId());
		ProjectEleOrder a1=projectEleOrderDao.selectById(order.getId());
		if(a1!=null){
			rrs.put("orderPrice",a1.getTotalPrice());
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
     * @param proPriceToVersion  (1,实施，2运费)
     * @return : void
     * @description: 转正式版本
     * @author liyujun
     * @date 2019-12-19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseVo proPriceToVersion(Long projectId,List<Integer> proPriceToVersion) {
        Integer version = projectPriceDao.getMaxVersion(projectId);
        //算价钱
        Map <String,Object> cod=new HashMap<>();
        cod.put("projectId",projectId);
        cod.put("version","-1");

        if(proPriceToVersion.size()>0){
            proPriceToVersion.forEach(c->{
                if(c.equals(OrderPriceAddTypeEnum.SHISHI.getCode())){
                    cod.put("包括安装","true");
                }else{
                    cod.put("包括运费","true");
                }
            });
        }
        equationServiceImpl.executeCountProjectPrice (cod);
        if (version != null && version > -1) {
            version = version + 1;

        } else if (version.equals(-1) || version == null) {
            version = 1;
        }
        ProjectPrice o = new ProjectPrice();
        o.setVersion(-1);
        o.setProjectId(projectId);
        QueryWrapper<ProjectPrice> projectPriceQueryWrapper = new QueryWrapper<>();
        ProjectPrice entity = new ProjectPrice();
        entity.setVersion(version);
        projectPriceQueryWrapper.setEntity(o);
        projectPriceDao.update(entity, projectPriceQueryWrapper);
        return new BaseVo();
    }

    /**
     * @param projectId 项目id
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
                //新增一个草稿报价
                projectPriceDao.insert(projectPrice);
                //todo 优化查询算法
                for (ProjectEleOrder pro : list1) {//遍历订单数据，查询关联的option

                    ids.add(pro.getId());
                }
                //根据订单id集合批量查询项目的可选项
                QueryWrapper<ProjectEleOptions> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("order_id", ids);
                List<ProjectEleOptions> optionsList = projectEleOptionsDao.selectList(queryWrapper);//得到原来的关联的数据
                for (ProjectEleOrder pro : list1) {
                    pro.setId(null);
                    //批量新增 返回id
                    projectEleOrderDao.insert(pro);
                    idsNew.add(pro.getId());
                    for (Long orderId : ids) {
                        for (ProjectEleOptions options : optionsList) {
                            //todo 优化批量插入
                            if (options.getOrderId().equals(orderId)) {
                                options.setOrderId(pro.getId());
                                projectEleOptionsDao.insert(options);
                            }

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
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public BaseVo delOrderByOrderId(Long orderId) {
        //先删除关联数据
        projectEleOrderBaseInfoDao.delByOrderId(orderId);

        projectEleOptionsDao.delByOrderId(orderId);
        //删除订单记录
        projectEleOrderDao.deleteById(orderId);

        return successVo();
    }

    /**
     * 更新订单
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
//		if (version.equals(-1)){
//			//草稿状态
//			Map map = this.wxAddReportInfo(vo);
//			baseVo.setData(map);
//		}else {
//			//正式状态
//			Map map = this.wxUpdateReportInfo(vo);
//			baseVo.setData(map);
//		}

        return baseVo;
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
                projectEleOptionsDao.delete(queryWrapper);//删除所选项；
                QueryWrapper<ProjectEleOrder> queryWrapper1 = new QueryWrapper<>();
                queryWrapper.eq("version_id", a.getId());
                projectEleOrderDao.delete(queryWrapper1);//删除电梯订单；
            }
        }
        QueryWrapper<ProjectPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version", -1);
        projectPriceDao.delete(queryWrapper);//删除草稿状态

    }

}