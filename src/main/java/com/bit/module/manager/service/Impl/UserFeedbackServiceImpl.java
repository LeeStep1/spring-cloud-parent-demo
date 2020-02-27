package com.bit.module.manager.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.UserFeedback;
import com.bit.module.manager.dao.UserFeedbackDao;
import com.bit.module.manager.vo.UserFeedbackPageVO;
import com.bit.module.manager.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:  客户意见
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
@Service
public class UserFeedbackServiceImpl extends BaseService implements UserFeedbackService {


    @Autowired
    private UserFeedbackDao userFeedbackDao;

    /***
     * @description: 添加
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    @Transactional
    public BaseVo addFeedBack(UserFeedback userFeedback) {
        userFeedback.setCreateUserId(getCurrentUserInfo().getId());
        userFeedback.setCreateRealName(getCurrentUserInfo().getRealName());
        userFeedback.setCreateTime(new Date());
        userFeedbackDao.insert(userFeedback);
        return  successVo();

    }
    /***
     * @description: 返显数据
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    public BaseVo findById(Long id) {
        BaseVo<UserFeedback> rs=new BaseVo<>();
        rs.setData(userFeedbackDao.selectById(id));
        return rs;
    }



    /***
     * @description:  分页 支持上报人查询
     * @author liyujun
     * @date 2020-02-27
     * @param userFeedbackPageVO :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    public BaseVo listPage(UserFeedbackPageVO userFeedbackPageVO) {
        Page<UserFeedback> page = new Page<>(userFeedbackPageVO.getPageNum(), userFeedbackPageVO.getPageSize());
        IPage<UserFeedback> pageRs = userFeedbackDao.listPage(page, userFeedbackPageVO);

        BaseVo baseVo = new BaseVo();
        baseVo.setData(pageRs);
        return baseVo;
    }


    /***
     * @description:删除
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    @Override
    @Transactional
    public BaseVo delete(Long id){
        userFeedbackDao.deleteById(id);
        return successVo();
    }
}
