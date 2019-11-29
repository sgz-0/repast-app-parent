package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.Order;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {

    Order selectOrderByOrderSn(String orderSn);

}