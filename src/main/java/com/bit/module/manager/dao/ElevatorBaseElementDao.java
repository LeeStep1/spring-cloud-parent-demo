package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.vo.ElevatorBaseElementPageVO;
import com.bit.module.manager.vo.ElevatorBaseElementVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Repository
public interface ElevatorBaseElementDao extends BaseMapper<ElevatorBaseElement> {




    List<ElevatorBaseElementVO> findAll(ElevatorBaseElement a);

    /**
     * 根据id单查记录
     * @param id
     */
    ElevatorBaseElement getElevatorBaseElementById(Long id);


    /**
     * 多参数查询
     * @return
     */
    List<ElevatorBaseElement> findByParam(ElevatorBaseElement elevatorBaseElement);

    /**
     * 新增记录
     */
    void addElevatorBaseElement(ElevatorBaseElement elevatorBaseElement);

    /**
     * 编辑记录
     */
    void updateElevatorBaseElement(ElevatorBaseElement elevatorBaseElement);

    /**
     * 删除记录
     */
    void delElevatorBaseElementById(Long id);

    /**
     * 分页查询
     */
    IPage<ElevatorBaseElementVO> listPage(@Param("pg")Page<ElevatorBaseElementVO> page, @Param("elevatorBaseElementPageVO") ElevatorBaseElementPageVO elevatorBaseElementPageVO);

}
