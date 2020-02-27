package com.bit.module.manager.service;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.UserFeedback;
import com.bit.module.manager.vo.UserFeedbackPageVO;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
public interface UserFeedbackService {

    /***
     * @description:
     * @author liyujun
     * @date 2020-02-27
     * @param userFeedback :
     * @return : com.bit.base.vo.BaseVo
     */
    BaseVo addFeedBack(UserFeedback userFeedback);

    /***
     * @description:
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    BaseVo findById(Long id);

    /***
     * @description:
     * @author liyujun
     * @date 2020-02-27
     * @param userFeedbackPageVO :
     * @return : com.bit.base.vo.BaseVo
     */
    BaseVo listPage(UserFeedbackPageVO userFeedbackPageVO);


    /***
     * @description:  删除
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    BaseVo delete(Long id);

}
