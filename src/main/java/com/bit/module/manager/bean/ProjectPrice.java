package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
@TableName(value = "t_project_price")
public class ProjectPrice {


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 关联的项目ID
     */
    private Long projectId;
    /**
     * 总价格
     */
    private String totalPrice;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 创建人员
	 */
    private Long createUserId;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 是否标准，1：标准，0非标
     */
    private Integer standard;
    /**
     * 标准名称1：标准，0非标
     */
    private String standardName;
    /**
     * 1：常规 0实施
     */
    private Integer stage;
    /**
     * 常规，实施
     */
    private String stageName;
    /**
     * 是否选择安装费 0-否 1-是
     */
    private Integer installFlag;
    /**
     * 是否选择运输费 0-否 1-是
     */
    private Integer transportFlag;

    /**
     * 非标审批状态:0：无需审批，1:待提交 2：待审核，3：通过,-1 :撤回
     */
    private Integer nonStandardApplyStatus;
	/**
	 * 乐观锁
	 */
	private Integer positiveLock;

	/*新增字段*/
    /**
     * 议价审批状态：0：未提交审批，1：审批中，2：审批通过，3：审批拒绝，4，撤销
     */
    private Integer enquiryApplyStatus;
    /**
     * 无优惠的总价
     */
    private String nonRateTotalPrice;
    /**
     * 平均下浮率
     */
    private Double averageRate;
    /**
     * 最大下浮率
     */
    private Double maxRate;
    /**
     * 议价流程节点的审批人
     */
    private Long enquiryAuditUserId;
    /**
     * 议价的环节点的审批人所属公司id
     */
    private Long enquiryAuditUserCompanyId;
    /**
     * 提交议价的时间
     */
    private Date enquiryApplyTime;
    /**
     * 提交议价的期望价格
     */
    private String inquiryPrice;
    /**
     * 成本总价
     */
    private String costTotalPrice;


}
