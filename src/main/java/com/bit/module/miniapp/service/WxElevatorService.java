package com.bit.module.miniapp.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.ElevatorRate;
import com.bit.module.manager.bean.Project;
import com.bit.module.manager.bean.ProjectEleOrderBaseInfo;
import com.bit.module.manager.bean.ProjectPrice;
import com.bit.module.manager.vo.ProjectPriceVo;
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
	 * @return : void
	 * @description: 转正式版本
	 * @author liyujun
	 * @date 2019-12-19
	 */
	BaseVo proPriceToVersion(Long projectId);

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

	/**
	 * 更新报价表的运输 和 安装 标识
	 * @param projectPrice
	 * @return
	 */
	BaseVo updateProjectPriceFlag(ProjectPrice projectPrice);


	/**
	 * 生成报价单发送邮件
	 * @param projectPriceId  (id)
	 * @return
	 */
	 BaseVo sendPriceMail(Long projectPriceId,List<String>ccAddress);

	/**
	 * 判断下浮率
	 * @param elevatorRate
	 * @return
	 */
	BaseVo judgeRate(ElevatorRate elevatorRate);


	/**
	 * 根據人員數據和電梯類型，獲取最大下浮率
	 * @param elevatorTypeId
	 * @return
	 */
	Map getRate(Long elevatorTypeId);

	/**
	 * 撤销申请
	 * @param elevatorPriceId
	 * @return
	 */
	BaseVo cancelApply(Long elevatorPriceId);

	/**
	 * 议价审核上报
	 * @param projectPriceVo
	 * @return
	 */
	BaseVo submit(ProjectPriceVo projectPriceVo);

	/**
	 * 通过 or 驳回 询价
	 * @param projectPrice
	 * @return
	 */
	BaseVo passEnquireAudit(ProjectPrice projectPrice);

}
