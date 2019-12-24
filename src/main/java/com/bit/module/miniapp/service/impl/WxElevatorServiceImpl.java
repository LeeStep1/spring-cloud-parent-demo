package com.bit.module.miniapp.service.impl;

import com.bit.base.service.BaseService;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import com.bit.module.manager.dao.OptionsDao;
import com.bit.module.manager.dao.ProjectEleOrderBaseInfoDao;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.service.WxElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Service
public class WxElevatorServiceImpl extends BaseService implements WxElevatorService {


    @Autowired
    private OptionsDao optionsDao;

    @Autowired
    private EquationServiceImpl equationServiceImpl;

    @Autowired
    private ProjectEleOrderBaseInfoDao projectEleOrderBaseInfoDao;

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
}
