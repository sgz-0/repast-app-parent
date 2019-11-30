package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OmsCartItem;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.mapper.OmsCartItemMapper;
import com.aaa.lee.app.mapper.OmsOrderItemMapper;
import com.aaa.lee.app.mapper.OmsOrderMapper;
import com.aaa.lee.app.utils.JSONUtil;
import com.aaa.lee.app.utils.OrderCodeFactory;

import com.aaa.lee.app.vo.OmsOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.common.Mapper;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/22 16:40
 * @Description
 **/
@Service
public class OrderService extends BaseService<OmsOrder> {
    @Autowired
    private  OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;
    @Autowired
    private OmsCartItemMapper omsCartItemMapper;

    @Override
    public Mapper<OmsOrder> getMapper() {
        return omsOrderMapper;
    }



    /**
     * @Author Lee
     * @Description
     * /**
     *      创建订单
     *      在此页面需要获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
     *      status（订单状态）：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭
     *      pay_type（支付方式）：0->未支付；1->支付宝；2->微信     获取支付方式是为了退款的时候原路返回
     *     同时在此页面还应该改变购物车的状态码由待清空状态 ：0
     *     转换为清空用户不可见状态：1  （更改为1是为了顾客在次进行购买的时候还能显示这些商品）
     * @Param  * @param omsOrder
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @Transactional
    public  Boolean addOrder(  List<OmsOrderVo> omsOrder){


        OmsOrder  omsOrder1 =new OmsOrder();
        OmsOrderItem omsOrderItem =  new OmsOrderItem();
        OmsCartItem omsCartItem =new OmsCartItem();
        try{
            Date date = new Date();
            long lTime = date.getTime();
            String orderCode = OrderCodeFactory.getOrderCode(lTime);
            for (OmsOrderVo omsor :omsOrder){
                /**
             获取订单中所包含的商品的信息，存入到数据库中
                订单编号，商品id，商品图片，商品名字，销售价格，购买数量
             */
                BigDecimal price =omsor.getProductPrice();
                String productName =omsor.getProductName();
                Integer productQuantity = omsor.getProductQuantity();
                Long productId = omsor.getProductId();
                omsOrderItem.setProductId(productId)
                        .setProductName(productName)
                        .setProductPrice(price)
                        .setProductQuantity(productQuantity)
                        .setOrderSn(orderCode);
                int insert = omsOrderItemMapper.insert(omsOrderItem);
            }

            Date date1 = new Date();
            String formatDate = null;
            //HH表示24小时制；
            DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatDate = dFormat.format(date1);
            SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date strD = null;
            try {
                strD = lsdStrFormat.parse(formatDate);
                System.out.println(strD+"===============");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (OmsOrderVo omsor1 : omsOrder){
                //获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
                Long shopId = omsor1.getShopId();
                Long productId = omsor1.getProductId();
                Long memberId = omsor1.getMemberId();
                BigDecimal payAmount = omsor1.getPayAmount();
                BigDecimal totalAmount = omsor1.getTotalAmount();
                Long cartId = omsor1.getCartId();
                String receiverName = omsor1.getReceiverName();
                String receiverPhone = omsor1.getReceiverPhone();
                omsOrder1.setShopId(shopId)
                        .setMemberId(memberId)
                        .setOrderSn(orderCode)
                        .setPayAmount(payAmount)
                        .setCreateTime(strD)
                        .setReceiverName(receiverName)
                        .setReceiverPhone(receiverPhone)
                        .setDeleteStatus(0)
                        .setTotalAmount(totalAmount);
                int insert = omsOrderMapper.insert(omsOrder1);
                if(insert>0){
                    omsCartItem.setMemberId(memberId);
                    omsCartItem.setId(cartId);
                    omsCartItem.setShopId(shopId);
                    omsCartItem.setProductId(productId);
                    omsCartItem.setDeleteStatus(1);
                    int updaCart = omsCartItemMapper.updateCartStatus(omsCartItem);
                    if(updaCart>0){
                        return  true;
                    }else {
                        return  false;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  false;
    }


}
