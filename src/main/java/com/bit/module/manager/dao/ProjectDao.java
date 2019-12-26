package com.bit.module.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bit.module.manager.bean.ElementParam;
import com.bit.module.manager.bean.ElevatorTypeNameAndUnitPrice;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectEleOrder;
import com.bit.module.manager.vo.ProjectPriceDetailVO;
import com.bit.module.miniapp.bean.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
@Repository
public interface ProjectDao extends BaseMapper<Project> {
	/**
	 * 根据项目id查询项目信息和报价
	 * @param projectId
	 * @param projectPriceId
	 * @return
	 */
	ProjectPriceDetailVO getProjectDetailById(@Param(value = "projectId")Long projectId,@Param(value = "projectPriceId")Long projectPriceId);

	/**
	 * 根据项目id查询项目下的电梯订单
	 * @param projectId
	 * @return
	 */
	List<ProjectEleOrder> getOrderByProjectId(@Param(value = "projectId")Long projectId);

	/**
	 * 根据订单id查询电梯规格参数 和 井道参数
	 * @param orderId
	 * @return
	 */
	List<ElementParam> getElementParamByOrderId(@Param(value = "orderId")Long orderId);

	/**
	 * 根据项目id查询电梯功能定制选项
	 * @param orderId
	 * @return
	 */
	List<Options> getProjectOptions(@Param(value = "orderId")Long orderId);

	/**
	 * 多参数查询数据
	 * @param project
	 * @return
	 */
	List<Project> findByParam(Project project);
}
