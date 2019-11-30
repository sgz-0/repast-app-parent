package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.service.IRepastService;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/22 16:28
 * @Description
 **/
@RestController
@Api(value = "添加订单信息", tags = "订单接口")
public class OrderController extends BaseController {
    @Autowired
    private IRepastService repastService;

    /**
     * @Author Lee
     * @Description
     *   添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    @ApiOperation(value = "添加订单", notes = "执行添加订单")
    public ResultData addOrder(@RequestBody List<OmsOrderVo> omsOrder) {
        Boolean aBoolean = repastService.addOrder(omsOrder);
        System.out.println(aBoolean);
        if (aBoolean) {
            return operateSuccess();
        } else {
            return operateFailed();
        }
    }
    @PostMapping("/selectOrderAndShop")
    @ApiOperation(value = "查询订单", notes = "执行查询订单")
    public ResultData selectOrderAndShop(@RequestParam("memberId") Long memberId ){
        List<OmsOrderAndShopInfoVo> omsOrderAndShopInfoVos = repastService.selectOrderAndShop(memberId);
        if (omsOrderAndShopInfoVos.size()>0){
            return  exist(omsOrderAndShopInfoVos);
        }else {
            return notExist();
        }
    }
    @PostMapping("/selectOrderInfo")
    @ApiOperation(value = "查询订单详情", notes = "执行查询订单详情")
    public ResultData selectOrderInfo(@RequestParam("orderSn") String orderSn){
        OmsOrder omsOrder = repastService.selectOrderInfo(orderSn);
        System.out.println(omsOrder.toString());
        if(omsOrder != null){
            return  exist(omsOrder);
        }else {
            return  notExist();
        }
    }

    @PostMapping("/selectOrderItemInfo")
    @ApiOperation(value = "查询订单商品详情", notes = "执行查询订单商品详情")

    public ResultData selectOrderItemInfo(@RequestParam("orderSn") String orderSn){
        List<OmsOrderItem> omsOrderItems = repastService.selectOrderItemInfo(orderSn);
        if(omsOrderItems.size()>0){
           return  exist(omsOrderItems);
        }else {
            return  notExist();
        }
    }

    @PostMapping("/selectCartItemInfo")
    @ApiOperation(value = "", notes = "执行查询购物车商品详情")
    public ResultData selectCartItemInfo(@RequestParam("memberId") Long memberId, @RequestParam("shopId") Long shopId){
        List<CartItemAndOrderVO> cartItemAndOrderVOS = repastService.selectCartItemInfo(memberId,shopId);
        if(cartItemAndOrderVOS.size()>0){
            return  exist(cartItemAndOrderVOS);
        }else {
            return  notExist();
        }
    }


}
