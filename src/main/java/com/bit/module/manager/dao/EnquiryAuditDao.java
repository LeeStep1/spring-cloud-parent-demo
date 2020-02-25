package com.bit.module.manager.dao;

import com.bit.module.manager.bean.EnquiryAudit;

import java.util.List;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-02-25
 **/
public interface EnquiryAuditDao {

    void batchAdd(List<EnquiryAudit> list );
}
