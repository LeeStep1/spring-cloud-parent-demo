package com.lee.user.feign;

import com.lee.user.bean.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("eureka-order")
@Component
public interface OrderFeign {

    /**
     * 根据ID修改某个订单
     */
    @RequestMapping(value = "/order/updateOrderByUserId",method = RequestMethod.POST)
    void updateOrderById(@RequestBody Order order);

    /**
     * 增加一条
     * @param order
     * @return
     */
    @RequestMapping(value = "/order/addOne",method = RequestMethod.POST)
    boolean addOne(@RequestBody Order order);
}
