package com.bit.module.manager.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: liyujun
 * @Date: 2020-03-04
 **/
@Data
@TableName("t_category_head")
public class CategoryHead {

    /**
     *数据id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 列显示名称
     */
    private String colName;
    /**
     * 类别
     */
    private String category;
    /**
     * 字段名称
     */
    private String colCode;

}
