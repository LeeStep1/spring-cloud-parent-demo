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

}
