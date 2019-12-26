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
      * @description:  获得相关的选项下拉
      * @author liyujun
      * @date 2019-12-25
      * @param optionType : 填写的类型
      * @param elevatorId: 电梯类型的id
      * @param orderId:  电梯订单的id
      * @return : null
      */

     List<Options> getOptions( Integer optionType,Long elevatorId,Long orderId);



     /**
      * @description: 获得相关的选项下拉
      * @author liyujun
      * @date 2019-12-25
      * @param optionType : 填写的类型
      * @param elevatorId: 电梯类型的id
      * @param orderBaseInfos:  订单基本信息
      * @return : List
      */

     List<Options> getOptions( Integer optionType,Long elevatorId,List<ProjectEleOrderBaseInfo> orderBaseInfos);


     /**
      * @description:  微信端添加报价
      * @author liyujun
      * @date 2019-12-19
      * @param vo :
      * @return : void
      */
     Map wxAddReportInfo(ReportInfoVO vo);


     /**
      * @description:  微信端修改单条的报价
      * @author liyujun
      * @date 2019-12-19
      * @param vo :
      * @return : void
      */
    // void  wxUpdateReportInfo(ReportInfoVO vo);

     /**
      * @description:  计算按钮，其实只是计算草稿的
      * @author liyujun
      * @date 2019-12-19
      * @param projectId
      * @return : void
      */
     Map pojectPriceTest(Long projectId);


     /**
      * @description:  转正是版本
      * @author liyujun
      * @date 2019-12-19
      * @param projectId
      * @return : void
      */
     BaseVo proPriceToVersion(Long projectId);

     /**
      * @description:  修改前复制数据
      * @author liyujun
      * @date 2019-12-19
      * @param projectId
      * @return : void
      */
     Map<String, Object>  copyData(Long projectId,Long versionId);

}
