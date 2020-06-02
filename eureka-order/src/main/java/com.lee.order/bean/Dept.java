package com.lee.order.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description:
 * @author: liyang
 * @date: 2020-05-18
 **/
@TableName("t_dept")
@Data
public class Dept {
    private Long id;

    private String dept;

    private int age;
}
