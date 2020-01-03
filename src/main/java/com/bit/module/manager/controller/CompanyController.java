package com.bit.module.manager.controller;

import com.bit.base.vo.BaseVo;
import com.bit.module.manager.service.CompanyService;
import com.bit.module.manager.service.Impl.EquationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description:
 * @author: chenduo
 * @create: 2019-05-06 15:37
 */
@RestController
@RequestMapping("/manager/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping(value = "/allCompany")
    public BaseVo allCompany(){

        return companyService.getCompany();
    }


}
