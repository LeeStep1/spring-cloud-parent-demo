package com.bit.module.miniapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
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
        return rs;
    }

    /**
     * @description:  微信端添加报价
     * @author liyujun
     * @date 2019-12-19
     * @param vo :
     * @return : void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map wxAddReportInfo(ReportInfoVO vo){

        /**新增报价**/
        Map<String,Object>baseParams=new HashMap<>(vo.getBaseinfo().size());
        ProjectEleOrder order=new ProjectEleOrder();
        order.setElevatorTypeId(vo.getElevatorTypeId());
        ElevatorType elevatorType=elevatorTypeDao.selectById(vo.getElevatorTypeId());
        order.setElevatorTypeName(elevatorType.getTypeName());
        try {
            baseParams= BeanReflectUtil.listmap(vo.getBaseinfo(),"paramKey","infoValue");
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setRate(vo.getRate());
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
        Map<String, Object> cod=new HashMap<>();
        cod.put("project_id",vo.getProjectId());
        cod.put("version",-1);
        List<ProjectPrice>list1=projectPriceDao.selectByMap(cod);
        ProjectPrice a=new ProjectPrice();
        if(list1.size()==0){
            a.setCreateTime(new Date());
            a.setProjectId(vo.getProjectId());
            a.setVersion(-1);
            a.setCreateUserId(getCurrentUserInfo().getId());
            a.setStage(1);
            a.setStandardName("常规");
            projectPriceDao.insert(a);
        }else{
            a=list1.get(0);
        }
        //todo 需要进行报价算法
        Map par=new HashMap();
        par.put("下浮率",vo.getRate());
        Map rs= equationServiceImpl.executeEquations(par);
        if(rs!=null||rs.containsKey("是否为非标")){
           if(Boolean.TRUE.equals(rs.get("是否为非标"))){
               a.setStandard(0);
               a.setStandardName("非标");
           }else{
               a.setStandard(1);
               a.setStandardName("标准");
           }
            projectPriceDao.updateById(a);
        }

           Map rrs=new HashMap();
           rrs.put("nums",order.getNum());
           par.clear();
           par.put("project_id",vo.getProjectId());
           par.put("version_id",-1);
           List<ProjectPrice> projectPriceList=projectPriceDao.selectByMap(par);
           if(projectPriceList.size()>0){
               rrs.put("price",projectPriceList.get(0).getTotalPrice());
           }
            return rrs;
    }


    /**
     * @description:  计算按钮
     * @author liyujun
     * @date 2019-12-19
     * @param projectId
     * @return : void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  Map pojectPriceTest(Long projectId){
        Map cod=new HashMap();
        cod.put("projectId",projectId);
        cod.put("verison_id",-1);
        equationServiceImpl.executeCountProjectPrice(cod);
        Map par=new HashMap();
        par.put("project_id",projectId);
        par.put("version_id",-1);
        List<ProjectPrice> projectPriceList=projectPriceDao.selectByMap(par);
        par.clear();
        par.put("version_id",projectPriceList.get(0).getId());
        par.put("project_id",projectId);
        cod.clear();
        cod.put("totalPrice",projectPriceList.get(0).getTotalPrice());
        List<ProjectEleOrder>list=projectEleOrderDao.selectByMap(par);
        cod.put("orderPrice",list);
        return cod;
    }

    /**
     * @description:  转正是版本
     * @author liyujun
     * @date 2019-12-19
     * @param projectId
     * @return : void
     */
   @Override
   @Transactional(rollbackFor = Exception.class)
   public BaseVo proPriceToVersion(Long projectId){
       Integer version= projectPriceDao.getMaxVersion(projectId);
       if(version!=null&&version>-1){
           version=version+1;

       }else if(version.equals(-1)||version==null){
           version=1;
       }
       ProjectPrice o=new ProjectPrice();
       o.setVersion(-1);
       o.setProjectId(projectId);
       QueryWrapper<ProjectPrice> projectPriceQueryWrapper = new QueryWrapper<>();
       ProjectPrice entity=new ProjectPrice();
       entity.setVersion(version);
       projectPriceQueryWrapper.setEntity(o);
       projectPriceDao.update(entity,projectPriceQueryWrapper);
       return new BaseVo();
   }


         /**
          * @description:  删除旧版本
          * @author liyujun
          * @date 2019-12-26
          * @param projectId : 
          * @return : void
          */
         private void  delELePriceInfo(Long projectId){
             Map<String,Object>p=new HashMap<>();
             p.put("project_id",projectId);
             p.put("version",-1);
             List <ProjectPrice>list =projectPriceDao.selectByMap(p);
             if (list.size()==1){
                 p.clear();
                 ProjectPrice a=list.get(0);
                 p.put("version_id",a.getId());
                 List<ProjectEleOrder> orderList=projectEleOrderDao.selectByMap(p);
                 if(orderList.size()>0){
                     List<Long>ids=new ArrayList<>();
                     orderList.forEach(c->{
                         ids.add(c.getId());
                     });
                     QueryWrapper<ProjectEleOptions> queryWrapper =  new QueryWrapper<>();
                     queryWrapper.in("order_id",ids);
                     projectEleOptionsDao.delete(queryWrapper);//删除所选项；
                     QueryWrapper<ProjectEleOrder> queryWrapper1 =  new QueryWrapper<>();
                     queryWrapper.eq("version_id",a.getId());
                     projectEleOrderDao.delete(queryWrapper1);//删除电梯订单；
                 }
             }
             QueryWrapper<ProjectPrice> queryWrapper =  new QueryWrapper<>();
             queryWrapper.eq("version",-1);
             projectPriceDao.delete(queryWrapper);//删除草稿状态

             
         }

     }
