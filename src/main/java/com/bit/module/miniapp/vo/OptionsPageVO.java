package com.bit.module.miniapp.vo;

import com.bit.base.vo.BasePageVo;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2019-12-25
 **/
@Data
public class OptionsPageVO extends BasePageVo{

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String ocode;
    /**
     * 选项名称
     */
    private String optionsName;
    /**
     * 描述
     */
    private String description;
    /**
     * 1：电梯装潢可选项 2：客梯功能配置可选项 3.客梯常用非标可选项，4：货梯非标，5：自动扶梯、人行道非标
     */
    private Integer secOptionType;
    /**
     * 输入方式：1，选择，2手输入.
     */
    private Integer inputType;
    /**
     * 分项大类：1：装潢，2：功能（可选项），3：非标可选项
     */
    private Integer optionType;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 单位
     */
    private String unit;
    /**
     * 后期扩展大类的计算key值
     */
    private String groupValue;
    /**
     * 数据属性1:有且唯一，2：可多选
     */
    private Integer rule;
    /**
     * 元素属性 0为分组名称1为实际选项
     */
    private Integer itemType;




}
