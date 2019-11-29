package com.aaa.lee.app.fallback;

import com.aaa.lee.app.domain.*;
import com.aaa.lee.app.service.IRepastService;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.ShopInfoVo;
import feign.Param;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/20 11:41
 * @Description
 **/
@Component
public class RepastFallBackFactory implements FallbackFactory<IRepastService> {

    @Override
    public IRepastService create(Throwable throwable) {
        IRepastService repastService = new IRepastService() {
            @Override
            public Boolean doLogin(Member member) {
                System.out.println("测试登录熔断数据");
                return null;
            }

            @Override
            public List<MemberReceiveAddress> getMemberReceiveAddress() {
                System.out.println("测试收获地址列表熔断数据");
                return null;
            }

            @Override
            public ShopInfoVo getShopById(Long shopId) {
                System.out.println("测试店铺信息熔断数据");
                return null;
            }

            @Override
            public List<ProductCat> getCategoryByShopId(Long shopId) {
                System.out.println("测试商品类目熔断数据");
                return null;
            }

            @Override
            public List<Product> getProductByShopId(Long shopId) {
                return null;
            }

            @Override
            public Order getOrderByOrderOrderSn(String orderSn) {
                return null;
            }

            @Override
            public Boolean paySuccess(String orderSn, String date) {
                return null;
            }

            @Override
            public List<SubmitOrderVO> getSubmitOrder(@Param("memberId") Long memberId, @Param("shopId") Long shopId) {
                System.out.println("测试中转提交订单页面熔断数据");
                return null;
            }

            @Override
            public int setOrder(List<SubmitOrderVO> submitOrderVO) {
                System.out.println("测试创建订单页面熔断数据");
                return 0;
            }

            @Override
            public PmsProductVO getIntegralGoods(Long productId) {
                System.out.println("测试商品id获取积分商城具体商品数据");
                return null;
            }

            @Override
            public int setIntegralOrder(PmsProductVO productVO) {
                System.out.println("生成积分订单熔断测试");
                return 0;
            }

            @Override
            public Boolean addOrder( Map<String,Object> data) {
                System.out.println("添加订单熔断数据");
                return null;
            }

            @Override
            public List<OmsOrderAndShopInfoVo>  selectOrderAndShop(Long id) {
                System.out.println("查询订单熔断");
                return null;
            }

            @Override
            public OmsOrder selectOrderInfo(String orderSn) {
                System.out.println("查询订单详情");
                return null;
            }

            @Override
            public List<OmsOrderItem> selectOrderItemInfo(String OrderSn) {
                System.out.println("查询订单商品详情");
                return null;
            }

            @Override
            public List<CartItemAndOrderVO> selectCartItemInfo(Long memberId, Long shopId) {
                System.out.println("查询购物车商品详情");
                return null;
            }


        };
        return repastService;
    }
}
