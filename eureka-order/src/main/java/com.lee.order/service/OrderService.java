package com.lee.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.order.bean.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService extends IService<Order> {

    Order getOrderById(Long id);

    void updateOrderById(Long id);

    boolean updateOrderByUserId(Order order);
}
