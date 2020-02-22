package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ElevatorBaseElement;
import com.bit.module.manager.vo.ElevatorBaseElementPageVO;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface ElevatorBaseElementService {

    BaseVo findAllByElevator(ElevatorBaseElement elevatorBaseElement);

    /**
     * 新增数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     */
    BaseVo add(ElevatorBaseElement elevatorBaseElement);

    /**
     * 编辑数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     */
    BaseVo update(ElevatorBaseElement elevatorBaseElement);

    /**
     * 删除数据
     * @param id
     * @author chenduo
     * @since ${date}
     */
    BaseVo delete(Long id);


    /**
     * 多参数查询数据
     * @param elevatorBaseElement
     * @author chenduo
     * @since ${date}
     * @return List<ElevatorBaseElement>
     */
    BaseVo findByParam(ElevatorBaseElement elevatorBaseElement);

    /**
     * 单查数据
     * @param id
     * @author chenduo
     * @since ${date}
     * @return ElevatorBaseElement
     */
    BaseVo reflectById(Long id);
	/**
	 * 分页查询
	 * @param elevatorBaseElementPageVO
	 * @return
	 */
    BaseVo listPage(ElevatorBaseElementPageVO elevatorBaseElementPageVO);
}
