package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 审批
 * @Author: liyujun
 * @Date: 2020-02-25
 **/
@Data
@TableName("t_enquiry_audit")
public class EnquiryAudit {


    /**
     * 电梯类型id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * '对应表t_project_price表ID'
     */
    private Long projectPriceId;
    /**
     * '操作类型 1-提交 2-转交审批 3-审批'
     */
    private Integer auditType;
    /**
     * 操作名称
     */
    private String auditTypeName;
    /**
     * '操作人id'
     */
    private Long auditUserId;
    /**
     * '操作时间'
     */
    private Date auditTime;
    /**
     * '操作人名称'
     */
    private String auditUserName;
    /**
     * '议价的时指定的下浮率使用json存储例如：[{"orderId":1,"rate":"0.1"}]'
     */
    private String rateList;



}
