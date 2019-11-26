package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.OrderInfo;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface OrderInfoMapper extends Mapper<OrderInfo> {
    /**
     * @Author sgz
     * @Description
     * 根据订单的主键id查询订单的详情
     * @Param
     * @Date 2019/11/21 19:39
     * @Return  
     * @Throws 
     **/
    List<OrderInfo> getOrderById(Long id);

    BigDecimal getPayAmountByOrderId(Long id);
    int updateIntegrationAndGrowth(Member member);

}