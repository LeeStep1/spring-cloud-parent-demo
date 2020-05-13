package com.lee.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.order.bean.Order;

public interface OrderService extends IService<Order> {

    Order getOrderById(Long id);

    void updateOrderById(Long id);
}
