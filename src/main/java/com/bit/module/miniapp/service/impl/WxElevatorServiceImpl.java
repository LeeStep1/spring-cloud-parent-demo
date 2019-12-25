package com.bit.module.miniapp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bit.base.service.BaseService;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Service
@Transactional
@Slf4j
public class WxElevatorServiceImpl extends BaseService implements WxElevatorService {


    @Autowired
    private OptionsDao optionsDao;

    @Autowired
    private ProjectEleOptionsDao projectEleOptionsDao;

    @Autowired
    private ElevatorTypeDao  elevatorTypeDao;

    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;


    @Autowired
    private ProjectEleOrderDao projectEleOrderDao;

    @Autowired
    private ProjectPriceDao projectPriceDao;

    @Autowired
    private EquationServiceImpl equationServiceImpl;


    @Override
    public List<Options> getOptions( Integer optionType, Long elevatorTypeId,Long orderId) {
        List<Options>ops=optionsDao.findOptionByElevatorType(elevatorTypeId,optionType);
        Map cod=new HashMap<>();
        cod.put("order_id",orderId);
        List<ProjectEleOrderBaseInfo>list=projectEleOrderBaseInfoDao.selectByMap(cod);
        cod.clear();
        list.forEach(c->{
            cod.put(c.getParamKey(),c.getInfoValue());
        });
        List<Options> rs=equationServiceImpl. executeEquationsForOption(cod,ops);
        return ops;
    }

    /**
     * @description: 获得相关的选项下拉
     * @author liyujun
     * @date 2019-12-25
     * @param optionType : 填写的类型
     * @param elevatorTypeId: 电梯类型的id
     * @param orderBaseInfos:  订单基本信息
     * @return : List
     */
    @Override
    public  List<Options> getOptions(Integer optionType, Long elevatorTypeId, List<ProjectEleOrderBaseInfo> orderBaseInfos){
        List<Options>ops=optionsDao.findOptionByElevatorType(elevatorTypeId,optionType);
        Map cod=new HashMap<>();
        cod.clear();
        orderBaseInfos.forEach(c->{
            cod.put(c.getParamKey(),c.getInfoValue());
        });
        List<Options> rs=equationServiceImpl. executeEquationsForOption(cod,ops);
        return ops;
    }

    /**
     * @description:  微信端添加报价
     * @author liyujun
     * @date 2019-12-19
     * @param vo :
     * @return : void
     */
    @Override
    public void wxAddReportInfo(ReportInfoVO vo){

        /**新增报价**/
        Map<String,Object>baseParams=new HashMap<>(vo.getBaseinfo().size());
        ProjectEleOrder order=new ProjectEleOrder();
        order.setElevatorTypeId(vo.getElevatorTypeId());
        ElevatorType elevatorType=elevatorTypeDao.selectById(vo.getElevatorTypeId());
        order.setElevatorTypeName(elevatorType.getTypeName());
        try {
            baseParams= BeanReflectUtil.bean2map1(vo.getBaseinfo(),"paramKey","infoValue");
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setNum(String.valueOf(baseParams.get("数量")));
        projectEleOrderDao.insert(order);
        //todo 需要优化为批量新增方法,填写整体的基础信息
        vo.getBaseinfo().stream().forEach(c->{
            c.setOrderId(order.getId());
            projectEleOrderBaseInfoDao.insert(c);
        });
        vo.getProjectEleOptions().stream().forEach(c->{
            c.setOrderId(order.getId());
            c.setProjectInfoId(vo.getProjectId());
            projectEleOptionsDao.insert(c);
        });
        //todo 需要进行报价算法
    }

}
