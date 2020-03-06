package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;

import com.bit.common.wxenum.ResultCode;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.dao.ElevatorBaseElementDao;
import com.bit.module.manager.dao.ElevatorTypeDao;
import com.bit.module.manager.service.ElevatorBaseElementService;
import com.bit.module.manager.vo.ElevatorBaseElementPageVO;
import com.bit.module.manager.vo.ElevatorBaseElementVO;
import com.bit.module.miniapp.bean.ElevatorType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
public class ElevatorBaseElementImpl extends BaseService implements ElevatorBaseElementService {


    @Autowired
    private ElevatorBaseElementDao elevatorBaseElementDao;
    @Autowired
    private ElevatorTypeDao elevatorTypeDao;

    /**
     * @description:  根据电梯类型得到基本信息填写模板
     * @author liyujun
     * @date 2019-12-24
     * @param elevatorBaseElement :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    public BaseVo findAllByElevator(ElevatorBaseElement elevatorBaseElement) {

        List<ElevatorBaseElementVO> list=elevatorBaseElementDao.findAll(elevatorBaseElement);
        Set<Integer> type= new HashSet();
        list.stream().forEach(c->type.add(c.getCategoryType()));
        List<Map> rsList=new ArrayList<>(type.size());
        Iterator  a=type.iterator();
        while (a.hasNext()){
            Map <String, Object> rs=new HashMap();
            int aaa=Integer.valueOf(String.valueOf(a.next()));
            rs.put("categoryType",aaa);
            List<ElevatorBaseElementVO> elements=new ArrayList<>(list.size());
            for(ElevatorBaseElementVO aa:list){
                if (aa.getCategoryType().equals(aaa)){
                     elements.add(aa);
                }
            }
            rs.put("elements", elements);
            rsList.add(rs);
        }
        BaseVo<List> vo= new BaseVo();
        vo.setData(rsList);
        return vo;
    }


    /**
     * 新增数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     */
    @Override
    @Transactional
    public BaseVo add(ElevatorBaseElement elevatorBaseElement){
        elevatorBaseElementDao.addElevatorBaseElement(elevatorBaseElement);
        return successVo();
    }

    /**
     * 编辑数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     */
    @Override
    @Transactional
    public BaseVo update(ElevatorBaseElement elevatorBaseElement) {
        elevatorBaseElementDao.updateElevatorBaseElement(elevatorBaseElement);
        return successVo();
    }

    /**
     * 删除数据
     * @param id
     * @author chenduo
     * @since ${date}
     */
    @Override
    @Transactional
    public BaseVo delete(Long id) {
        elevatorBaseElementDao.delElevatorBaseElementById(id);
        return successVo();
    }

    /**
     * 多参数查询数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     * @return List<ElevatorBaseElement>
     */
    @Override
    public BaseVo findByParam(ElevatorBaseElement elevatorBaseElement) {
        List<ElevatorBaseElement> elevatorBaseElementList = elevatorBaseElementDao.findByParam(elevatorBaseElement);
        BaseVo baseVo = new BaseVo();
        baseVo.setData(elevatorBaseElementList);
        return baseVo;
    }

    /**
     * 参数验重
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     * @return List<ElevatorBaseElement>
     */
    @Override
    public BaseVo distinctParam(ElevatorBaseElement elevatorBaseElement) {
        List<ElevatorBaseElement> elevatorBaseElementList = elevatorBaseElementDao.findByParam(elevatorBaseElement);
        BaseVo baseVo = new BaseVo();
        if (CollectionUtils.isNotEmpty(elevatorBaseElementList)){
            baseVo.setData(ResultCode.PARAMS_KEY_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_EXIST.getInfo());
        }else {
            baseVo.setData(ResultCode.PARAMS_KEY_NOT_EXIST.getCode());
			baseVo.setMsg(ResultCode.PARAMS_KEY_NOT_EXIST.getInfo());
        }
        return baseVo;
    }

    /**
     * 单查数据
     * @param id
     * @author chenduo
     * @since ${date}
     * @return ElevatorBaseElement
     */
    @Override
    public BaseVo reflectById(Long id) {
        ElevatorBaseElement elevatorBaseElement = elevatorBaseElementDao.getElevatorBaseElementById(id);
        ElevatorBaseElementVO elevatorBaseElementVO = new ElevatorBaseElementVO();
        BeanUtils.copyProperties(elevatorBaseElement,elevatorBaseElementVO);
        ElevatorType elevatorTypeById = elevatorTypeDao.getElevatorTypeById(elevatorBaseElement.getElevatorTypeId());
        if (elevatorTypeById!=null){
            elevatorBaseElementVO.setElevatorTypeName(elevatorTypeById.getTypeName());
        }
        BaseVo baseVo = new BaseVo();
        baseVo.setData(elevatorBaseElementVO);
        return baseVo;
    }
    /**
     * 分页查询
     * @param elevatorBaseElementPageVO
     * @return
     */
    @Override
    public BaseVo listPage(ElevatorBaseElementPageVO elevatorBaseElementPageVO) {
		Page<ElevatorBaseElementVO> page = new Page<>(elevatorBaseElementPageVO.getPageNum(), elevatorBaseElementPageVO.getPageSize());
		IPage<ElevatorBaseElementVO> elementVOIPage = elevatorBaseElementDao.listPage(page, elevatorBaseElementPageVO);
		BaseVo baseVo = new BaseVo();
		baseVo.setData(elementVOIPage);
        return baseVo;
    }
}
