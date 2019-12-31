package com.bit.module.miniapp.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import com.bit.module.miniapp.bean.Options;
import com.bit.module.miniapp.vo.ReportInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-24
 **/
public interface WxElevatorService {


	/**
	 * @param optionType  : 填写的类型
	 * @param elevatorId: 电梯类型的id
	 * @param orderId:    电梯订单的id
	 * @return : null
	 * @description: 获得相关的选项下拉
	 * @author liyujun
	 * @date 2019-12-25
	 */

	List<Options> getOptions(Integer optionType, Long elevatorId, Long orderId);


	/**
	 * @param optionType      : 填写的类型
	 * @param elevatorId:     电梯类型的id
	 * @param orderBaseInfos: 订单基本信息
	 * @return : List
	 * @description: 获得相关的选项下拉
	 * @author liyujun
	 * @date 2019-12-25
	 */

	List<Options> getOptions(Integer optionType, Long elevatorId, List<ProjectEleOrderBaseInfo> orderBaseInfos);


	/**
	 * @param vo :
	 * @return : void
	 * @description: 微信端添加报价
	 * @author liyujun
	 * @date 2019-12-19
	 */
	Map wxAddReportInfo(ReportInfoVO vo);

	/**
	 * @param vo :
	 * @return : void
	 * @description: 微信端更新报价
	 * @author liyujun
	 * @date 2019-12-19
	 */
	Map wxUpdateReportInfo(ReportInfoVO vo);


	/**
	 * @description: 微信端修改单条的报价
	 * @author liyujun
	 * @date 2019-12-19
	 * @param vo :
	 * @return : void
	 */
	// void  wxUpdateReportInfo(ReportInfoVO vo);

	/**
	 * @param projectId
	 * @return : void
	 * @description: 计算按钮，其实只是计算草稿的
	 * @author liyujun
	 * @date 2019-12-19
	 */
	Map pojectPriceTest(Long projectId);


	/**
	 * @param projectId
	 * @param proPriceToVersion (1,实施，2运费)
	 * @return : void
	 * @description: 转正式版本
	 * @author liyujun
	 * @date 2019-12-19
	 */
	BaseVo proPriceToVersion(Long projectId, List<Integer> proPriceToVersion);

	/**
	 * @param projectId
	 * @return : void
	 * @description: 修改前复制数据
	 * @author liyujun
	 * @date 2019-12-19
	 */
	BaseVo updateProjectPrice(Long projectId, Long projectPriceId);

	/**
	 * 根据订单id删除订单
	 *
	 * @param orderId
	 * @return
	 */
	BaseVo delOrderByOrderId(Long orderId);

	/**
	 * 更新订单
	 *
	 * @param vo
	 * @return
	 */
	BaseVo updateOrder(ReportInfoVO vo);

}
