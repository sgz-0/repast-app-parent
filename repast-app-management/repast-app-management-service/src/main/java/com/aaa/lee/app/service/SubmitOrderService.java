package com.aaa.lee.app.service;

import com.aaa.lee.app.code.OrderCodeFactory;
import com.aaa.lee.app.domain.OmsCartItem;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.domain.SubmitOrderVO;
import com.aaa.lee.app.mapper.OmsCartItemMapper;
import com.aaa.lee.app.mapper.OmsOrderItemMapper;
import com.aaa.lee.app.mapper.OmsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @data 2019/11/21 23:34
 * @project repast-app-parent
 * @declaration:
 */
@Service
public class SubmitOrderService{

    @Autowired
    private OmsCartItemMapper omsCartItemMapper;

    @Autowired
    private OmsOrderItemMapper orderItemMapper;

    @Autowired
    private OmsOrderMapper omsOrderMapper;

    /**
     * 通过店铺id和用户id获取当前用户所选购当前商家的商品
     * @return
     */
    public List<SubmitOrderVO> getSubmitOrder(Long memberId,Long shopId){
        List<SubmitOrderVO> omsCartltem = omsCartItemMapper.getOmsCartltem(memberId,shopId);
        if (omsCartltem.size() > 0){
            return omsCartltem;
        }
        return null;
    }

    /**
     * 创建订单
     * 在此页面需要获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
     * status（订单状态）：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭
     * pay_type（支付方式）：0->未支付；1->支付宝；2->微信     获取支付方式是为了退款的时候原路返回
     * 同时在此页面还应该改变购物车的状态码由待清空状态 ：0
     *                          转换为清空用户不可见状态：1  （更改为1是为了顾客在次进行购买的时候还能显示这些商品）
     *
     */
    @Transactional
    public int  setOrder(List<SubmitOrderVO> submitOrderVO){

        //获取32位订单时的时间戳时间格式
        Date date = new Date();
        long lTime = date.getTime();
        String orderCode = OrderCodeFactory.getOrderCode(lTime);
        OmsOrderItem omsOrderItem = new OmsOrderItem();

        //存入数据库时的订单提交时间格式
        Date date1 = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
        String formatDate = dFormat.format(date1);
        SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strD = null;
        try {
            try {
                strD = lsdStrFormat.parse(formatDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*
               生成订单表时的数据
                获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
              */
            OmsOrder omsOrder = new OmsOrder();
            for (SubmitOrderVO orderVO1 : submitOrderVO) {
                //获取用户id
                Long memberId = orderVO1.getMemberId();
                //获取商铺id
                Long shopId = orderVO1.getShopId();
                //获取用户名
                String memberUsername = orderVO1.getMemberUsername();
                //获取总价格
                BigDecimal totalAmount = orderVO1.getTotalAmount();
                //获取应付金额
                BigDecimal payAmount = orderVO1.getPayAmount();
                //将获取到的数据放入到对象中，并且传入数据库中进行生成订单
                omsOrder.setMemberId(memberId)
                        .setShopId(shopId)
                        .setOrderSn(orderCode)
                        .setCreateTime(strD)
                        .setMemberUsername(memberUsername)
                        .setTotalAmount(totalAmount)
                        .setPayAmount(payAmount);
                int i = omsOrderMapper.setOmsOrder(omsOrder);
                if (i > 0){
                    //订单生成后会改变购物车中的状态码
                    OmsCartItem omsCartItem = new OmsCartItem();
                    omsCartItem.setMemberId(memberId);
                    omsCartItem.setShopId(shopId);
                    omsCartItem.setDeleteStatus(1);
                    omsCartItemMapper.updataDeleteStatus(omsCartItem);
                    return i;
                }
                break;
            }
            /*
             获取订单中所包含的商品的信息，存入到数据库中
                订单编号，商品id，商品图片，商品名字，销售价格，购买数量
             */
            for (SubmitOrderVO orderVO : submitOrderVO) {
                BigDecimal price = orderVO.getPrice();//商品价格
                String productName = orderVO.getProductName();//商品名字
                Long productId = orderVO.getProductId();//商品id
                String productPic = orderVO.getProductPic();//商品图片路径
                Integer quantity = orderVO.getQuantity();//购买数量
                omsOrderItem.setProductId(productId).setProductPic(productPic).setProductName(productName)
                        .setProductPrice(price).setProductQuantity(quantity).setOrderSn(orderCode);
                orderItemMapper.setOrderItem(omsOrderItem);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return 0;
    }
}
