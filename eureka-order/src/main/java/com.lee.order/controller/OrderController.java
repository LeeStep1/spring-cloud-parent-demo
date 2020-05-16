package com.lee.order.controller;

import com.lee.order.bean.Order;
import com.lee.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 获取根据ID获取单个user
     * @param id
     * @return
     */
    @RequestMapping("/getOrder/{id}")
    public Order getUserById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);

        return order ;
    }

    /**
     * 批量添加
     * @param orderList
     * @return
     */
    @PostMapping("/batchAdd")
    public boolean batchOrder(@RequestBody List<Order> orderList){

        return orderService.saveBatch(orderList);
    }

    /**
     * 增加一条
     * @param order
     * @return
     */
    @PostMapping("/addOne")
    public boolean addOne(@RequestBody Order order){

        return orderService.save(order);
    }

    /**
     * 根据ID修改某个订单
     * @param id
     */
    @GetMapping("/updateOrderById/{id}")
    public void updateOrderById(@PathVariable long id){

        orderService.updateOrderById(id);

    }

    @PostMapping("/updateOrderByUserId")
    public boolean updateOrderByUserId(@RequestBody Order order){
        return orderService.updateOrderByUserId(order);
    }
}
