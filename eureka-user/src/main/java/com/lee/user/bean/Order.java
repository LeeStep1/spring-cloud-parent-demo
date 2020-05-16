package com.lee.user.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class Order {

    private Long id;

    private Long userId;

    private String productName;
}
