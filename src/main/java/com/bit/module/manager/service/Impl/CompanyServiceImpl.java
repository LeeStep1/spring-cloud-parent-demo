package com.bit.module.manager.service.Impl;

import com.bit.base.service.BaseService;
import com.bit.base.vo.BaseVo;
import com.bit.module.manager.bean.Company;
import com.bit.module.manager.dao.CompanyDao;
import com.bit.module.manager.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-01-03
 **/
@Service("companyService")
public class CompanyServiceImpl  extends BaseService implements CompanyService {

    @Autowired
    private CompanyDao companyDao;


    @Override
    public BaseVo getCompany() {
       List<Company>list =companyDao.selectList(null);
       BaseVo rs= new BaseVo();
        rs.setData(list);
        return rs;
    }
}
