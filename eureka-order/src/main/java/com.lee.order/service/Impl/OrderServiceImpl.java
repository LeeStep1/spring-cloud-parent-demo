package com.lee.order.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.order.bean.Order;
import com.lee.order.dao.OrderDao;
import com.lee.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao,Order> implements OrderService {

    @Autowired
    private OrderDao dao;

    @Override
    public Order getOrderById(Long id) {
        Order order = dao.selectOrderById(id);
        return order;
    }

    @Override
    public void updateOrderById(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setProductName("修改");
        updateById(order);
    }

    @Override
    public boolean updateOrderByUserId(@RequestBody Order order) {
        Boolean b = dao.updateOrderByUserId(order);
        System.out.println("order 改完了........");
        return b;
    }
}