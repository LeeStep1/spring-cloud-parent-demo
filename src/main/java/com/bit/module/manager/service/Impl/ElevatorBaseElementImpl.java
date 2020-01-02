package com.bit.module.manager.service.Impl;

import com.bit.base.vo.BaseVo;

import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.dao.ElevatorBaseElementDao;
import com.bit.module.manager.service.ElevatorBaseElementService;
import com.bit.module.miniapp.bean.ElevatorBaseElementVo;
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
@Transactional
public class ElevatorBaseElementImpl implements ElevatorBaseElementService {


    @Autowired
    private ElevatorBaseElementDao elevatorBaseElementDao;

    /**
     * @description:  根据电梯类型得到基本信息填写模板
     * @author liyujun
     * @date 2019-12-24
     * @param elevatorBaseElement :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    public BaseVo findAllByElevator(ElevatorBaseElement elevatorBaseElement) {

        List<ElevatorBaseElementVo>list=elevatorBaseElementDao.findAll(elevatorBaseElement);
        Set<Integer> type= new HashSet();
        list.stream().forEach(c->type.add(c.getCategoryType()));
        List<Map> rsList=new ArrayList<>(type.size());
        Iterator  a=type.iterator();
         while (a.hasNext()){
             Map <String, Object> rs=new HashMap();
             int aaa=Integer.valueOf(String.valueOf(a.next()));
             rs.put("categoryType",aaa);
             List<ElevatorBaseElement>elements=new ArrayList<>(list.size());
             for(ElevatorBaseElement aa:list){
                 if (aa.getCategoryType().equals(aaa)){
                     elements.add(aa);
                 }
             }
             rs.put("elements",elements);
             rsList.add(rs);
         }
         BaseVo<List> vo= new BaseVo();
         vo.setData(rsList);
        return vo;
    }
}
