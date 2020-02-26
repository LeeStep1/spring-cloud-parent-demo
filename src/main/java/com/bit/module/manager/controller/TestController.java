package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.User;
import com.bit.module.manager.bean.UserLogin;
import com.bit.module.manager.service.Impl.EquationCacheServiceImpl;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import com.bit.module.manager.service.UserService;
import com.bit.module.manager.vo.PortalUserVo;
import com.bit.module.manager.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:37
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EquationServiceImpl equationService;
    @Autowired
    private EquationCacheServiceImpl equationCacheService;

    @PostMapping(value = "/equation")
    public BaseVo equation(@RequestBody Map map){
        equationService.executeCount(map);
        BaseVo baseVo = new BaseVo();
        baseVo.setData(map);
        return baseVo;
    }

    @PostMapping(value = "/executeCountProjectPrice")
    public BaseVo executeCountProjectPrice(@RequestBody Map map){
        BaseVo baseVo = new BaseVo();
        baseVo.setData(equationService.executeCountProjectPrice(map));
        return baseVo;
    }

    @PostMapping(value = "/countAvgDiscountRate")
    public BaseVo countAvgDiscountRate(@RequestBody Map map){
        BaseVo baseVo = new BaseVo();
        baseVo.setData(equationService.countAvgDiscountRate(map));
        return baseVo;
    }

    @PostMapping(value = "/cleanCache")
    public BaseVo cleanCache(@RequestBody Map map){
        equationCacheService.cleanCache();
        equationCacheService.cleanCache2();
        equationCacheService.cleanCache3();
        return new BaseVo();
    }
}
