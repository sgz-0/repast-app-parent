package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.PmsProductVO;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @data 2019/11/25 23:08
 * @project repast-app-parent
 * @declaration:
 */
@RestController
@Api(value = "积分商城商品数据信息", tags = "积分商城商城信息接口")
public class IntegralGoodsController  extends BaseController {

    @Autowired
    private IRepastService repastService;

    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    @ApiOperation(value = "积分商城商品数据信息", notes = "进行查询操作")
    public ResultData getIntegralGoods(Long productId){
        PmsProductVO integralGoods = repastService.getIntegralGoods(productId);
        if(null != integralGoods) {
            return success(integralGoods);
        } else {
            return failed();
        }
    }

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    @ApiOperation(value = "积分订单",notes = "通过vo实体类数据添加积分订单")
    public ResultData setIntegralOrder(PmsProductVO productVO){
        int i = repastService.setIntegralOrder(productVO);
        if (i > 0){
            return success();
        }
        return failed();
    }

}
