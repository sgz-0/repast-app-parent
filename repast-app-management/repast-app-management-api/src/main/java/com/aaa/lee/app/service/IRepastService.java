package com.aaa.lee.app.service;

import com.aaa.lee.app.domain.*;
import com.aaa.lee.app.fallback.RepastFallBackFactory;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.ShopInfoVo;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/20 11:40
 * @Description
 *      当使用feign进行传参的时候，如果是对象,包装类型,实体类...必须要使用@RequestBody，并且这个@RequestBody只能在该方法中出现一次
 *          ResultData selectUsersCondition(@RequestBody User user, @RequestBody UserInfo userInfo);---->错误
 *      当传递的参数是简单类型(String, Integer....8种基本类型+String)，必须要使用@RequestParam("")，这个@RequestPara注解可以出现多个
 *          ResultData selectUsersCondition(@RquestPara("username") String username, @RequestParam("age") Integer age);---->正确
 *
 **/
@FeignClient(value = "userinfo-interface-provider", fallbackFactory = RepastFallBackFactory.class)
public interface IRepastService {

    /**
     * @author SGZ
     * @description
     *      执行登录操作
     * @param [member]
     * @date 2019/11/21
     * @return java.lang.Boolean
     * @throws 
    **/
    @PostMapping("/login")
    Boolean doLogin(@RequestBody Member member);

    /**
     * @author SGZ
     * @description
     *      根据会员id获取会员收获地址列表
     * @param []
     * @date 2019/11/21
     * @return java.util.List<com.aaa.lee.app.domain.MemberReceiveAddress>
     * @throws 
    **/
    @GetMapping("/receive")
    List<MemberReceiveAddress> getMemberReceiveAddress();

    /**
     * @author SGZ
     * @description
     *      通过主键查询店铺信息
     * @param [shopId]
     * @date 2019/11/21
     * @return java.lang.String
     * @throws 
    **/
    @GetMapping("/getById")
    ShopInfoVo getShopById(@RequestParam("shopId") Long shopId);

    /**
     * @author SGZ
     * @description
     *      通过店铺主键查询商品类目列表
     * @param [shopId]
     * @date 2019/11/21
     * @return com.aaa.lee.app.base.ResultData
     * @throws 
    **/
    @GetMapping("/getCatByShopId")
    List<ProductCat> getCategoryByShopId(@RequestParam("shopId")Long shopId);

    /**
     * @author SGZ
     * @description
     *      通过店铺主键查询商品列表
     * @param [shopId]
     * @date 2019/11/21
     * @return java.util.List<com.aaa.lee.app.domain.Product>
     * @throws 
    **/
    @GetMapping("/getProductByShopId")
    List<Product> getProductByShopId(@RequestParam("shopId")Long shopId);

    @GetMapping("/getOrderByOrderOrderSn")
    Order getOrderByOrderOrderSn(@RequestParam("orderSn")String orderSn);

    @PostMapping("/paySuccess")
    Boolean paySuccess(@RequestParam("orderSn") String orderSn, @RequestParam("date") String date);

    /**
     * 通过店铺id和用户id获取当前用户所选购当前商家的商品
     * @param shopId
     * @param memberId
     * @return
     */
    @PostMapping("/getSubmitOrder")
    List<SubmitOrderVO> getSubmitOrder(@RequestParam("memberId") Long memberId,@RequestParam("shopId") Long shopId);

    /**
     * 通过页面信息创建订单
     */
    @PostMapping("/setOrder")
    int setOrder(@RequestBody List<SubmitOrderVO>  submitOrderVO);

    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    PmsProductVO getIntegralGoods(@RequestParam(value = "productId") Long productId);

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    int setIntegralOrder(@RequestBody PmsProductVO productVO);
    /**
     * @Author Lee
     * @Description
     *      添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    Boolean addOrder(@RequestBody List<OmsOrderVo> omsOrder);

    /**
     * @Author Lee
     * @Description
     * 根据用户id查询购物车
     * @Param  * @param id
     * @Return
     * @Date 2019/11/26
     */
    @PostMapping("/selectOrderAndShop")
    List<OmsOrderAndShopInfoVo>  selectOrderAndShop(@RequestParam("memberId") Long memberId);
    /**
     * @Author Lee
     * @Description
     *  根据订单编号查询订单详情
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.domain.OmsOrder
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderInfo")
    OmsOrder selectOrderInfo(@RequestParam("orderSn") String  orderSn);

    /**
     * @Author Lee
     * @Description
     * 根据订单编号查询订单商品详情
     * @Param  * @param orderSn
     * @Return java.util.List<com.aaa.lee.app.domain.OmsOrderItem>
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderItemInfo")
    List<OmsOrderItem> selectOrderItemInfo(@RequestParam("orderSn") String orderSn);
    /**
     * @Author Lee
     * @Description
     * 根据用户id和店铺id查询购物车商品详情
     * @Param  * @param memberId
     * @param shopId
     * @Return java.util.List<com.aaa.lee.app.vo.CartItemAndOrderVO>
     * @Date 2019/11/29
     */
    @PostMapping("/selectCartItemInfo")
    List<CartItemAndOrderVO> selectCartItemInfo(@RequestParam("memberId") Long memberId, @RequestParam("shopId") Long shopId);
}
