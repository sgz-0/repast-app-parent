package com.aaa.lee.app.controller;

import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.service.CanteenCommentService;

import com.aaa.lee.app.service.CanteenOrderItemService;
import com.aaa.lee.app.service.CanteenOrderService;
import com.aaa.lee.app.service.CanteenOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/23 00:01
 * @Description
 **/
@RestController
public class CanteenOrderController {
    @Autowired
    private CanteenCommentService canteenCommentService;
    @Autowired
    private CanteenOrderItemService canteenOrderItemService;
    @Autowired
    private CanteenOrderSettingService canteenOrderSettingService;
    @Autowired
    private CanteenOrderService canteenOrderService;
    /**
     * @Author SGZ
     * @Description
     *      根据订单主键查询订单信息
     * @Param  * @param order
     * @Return com.aaa.lee.app.domain.Order
     * @Date 2019/11/23
     */
    @GetMapping("/getOrderByOrderOrderSn")
    public Order getOrderByOrderOrderSn(@RequestParam("orderSn") String orderSn){
        return canteenOrderService.getOrderByOrderOrderSn(orderSn);
    };
    /**
     * @Author SGZ
     * @Description
     *      付款成功，根据订单编号修改订单数据
     * @Param  * @param order
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/paySuccess")
    public Boolean paySuccess(@RequestParam("orderSn") String  orderSn,@RequestParam("date") String date){
        Boolean aBoolean = canteenOrderService.paySuccess(orderSn,date);
        System.out.println(aBoolean);
        return aBoolean;
    };

    /**
     * @Author SGZ
     * @Description
     *      消费完成之后设置自动确认收货
     * @Param [order]
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/consumeSuccess")
    public Boolean consumeSuccess(@RequestParam("orderSn") String  orderSn,@RequestParam("date") String date){
       return canteenOrderService.receiveSuccess(orderSn,date,canteenOrderSettingService,canteenCommentService,canteenOrderItemService);
    }



}
