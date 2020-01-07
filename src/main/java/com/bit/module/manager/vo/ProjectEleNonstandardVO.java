package com.bit.module.manager.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:  订单非标项
 * @Author: liyujun
 * @Date: 2020-01-04
 **/
@Data
public class ProjectEleNonstandardVO {

    /**
     * id
     **/
    private Long id;



    /**
     * 非标描述内容
     **/
    private String content;

    /**
     * 创建标识
     **/
    private Integer sysType;

    /**
     * 单价格（目前无用，仅为后期扩展）
     **/
    private String  signalPrice;

    /**
     * 总价格（目前无用，仅为后期扩展）
     **/
    private String  totalPrice;

    /**
     * 审批备注
     **/
    private String  auditRemark;

    /**
     * 是否能做 0-否 1-是
     **/
    private Integer  productionFlag;
    /**
     * 版本号
     */
    private Integer version;
	/**
	 * 报价id
	 */
	private Long priceId;
	/**
	 * 非标审批状态:-1：撤销 0：无需审批，1：非标的待提交，2：待审核，3：通过
	 */
	private Integer nonStandardApplyStatus;
	/**
	 * 项目id
	 */
	private Long projectId;

}
