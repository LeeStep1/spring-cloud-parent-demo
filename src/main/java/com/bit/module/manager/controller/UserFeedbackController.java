package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.UserFeedbackService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserFeedbackPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-27
 **/
@RestController
@RequestMapping("/manager/feedback")
public class UserFeedbackController {


    @Autowired
    private UserFeedbackService userFeedbackService;

    /***
     * @description:  分页
     * @author liyujun
     * @date 2020-02-27
     * @param userFeedbackPageVO :
     * @return : com.bit.base.vo.BaseVo
     */
    @PostMapping("/listPage")
    public BaseVo findAll(@RequestBody UserFeedbackPageVO userFeedbackPageVO) {
        return userFeedbackService.listPage(userFeedbackPageVO);
    }

    /***
     * @description:  详情
     * @author liyujun
     * @date 2020-02-27
     * @param id :
     * @return : com.bit.base.vo.BaseVo
     */
    @GetMapping("/info/{id}")
    public BaseVo findbyId(@PathVariable Long id) {
        return userFeedbackService.findById(id);
    }

    /**
     * @description:  删除
     * @author liyujun
     * @date 2020-02-27
     * @param id : 
     * @return : com.bit.base.vo.BaseVo
     */
    @DeleteMapping("/info/{id}")
    public BaseVo delete(@PathVariable Long id){
        return userFeedbackService.delete(id);
    }

}