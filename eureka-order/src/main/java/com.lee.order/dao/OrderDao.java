package com.lee.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.order.bean.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends BaseMapper<Order> {

    Order selectOrderById(@Param("id") Long id);
}
