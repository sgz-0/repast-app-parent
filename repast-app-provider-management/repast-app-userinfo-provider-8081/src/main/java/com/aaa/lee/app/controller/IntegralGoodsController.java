package com.aaa.lee.app.controller;

import com.aaa.lee.app.domain.PmsProductVO;
import com.aaa.lee.app.service.IntegralGoodsService;
import com.aaa.lee.app.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @data 2019/11/25 22:13
 * @project repast-app-parent
 * @declaration:
 * 积分商城：
 *      1、获取页面方法
 *      2、操作订单方法
 */
@RestController
public class IntegralGoodsController {

    /**
     * @TODO
     */
    @Autowired
    private IntegralGoodsService goodsService;

    private RedisService redisService;
    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    public PmsProductVO getIntegralGoods(@RequestParam Long productId){
        PmsProductVO pmsProductVO = goodsService.getPmsProductVO(productId);
        return pmsProductVO;
    }

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    public int setIntegralOrder(@RequestBody PmsProductVO productVO){
        int i = goodsService.setIntegralOrder(productVO, redisService);
        return i;
    }
}
