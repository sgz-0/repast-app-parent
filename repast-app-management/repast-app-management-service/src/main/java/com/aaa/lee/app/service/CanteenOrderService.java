package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.Comment;
import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.domain.OrderItem;
import com.aaa.lee.app.domain.OrderSetting;
import com.aaa.lee.app.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.aaa.lee.app.staticstatus.StaticProperties.*;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/22 23:36
 * @Description
 **/
@Service
public class CanteenOrderService extends BaseService<Order> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Mapper<Order> getMapper() {
        return orderMapper;
    }
    /**
     * @Author SGZ
     * @Description
     *      根据订单编号查询订单信息
     * @Param [order]
     * @Return com.aaa.lee.app.domain.Order
     * @Date 2019/11/23
     */
    public Order getOrderByOrderOrderSn(String orderSn){
        Order order = orderMapper.selectOrderByOrderSn(orderSn);
        if(order != null){
            return order;
        }
        return null;
    }

    /**
     * @Author SGZ
     * @Description
     *      付款成功，根据订单编号修改订单数据
     *      付款成功15分钟后，修改状态为订单完成
     * @Param  * @param order
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean paySuccess(String  orderSn,String date){
        Order order = getOrderByOrderOrderSn(orderSn);
        try {
            Date formatDate = simpleDateFormat.parse(date);
            //修改订单状态为待发货，支付时间，修改时间
            order.setPayType(STATUS_ORDER_WXPAID)//微信支付
                    .setStatus(STATUS_ORDER_PAID)//待发货状态
                    .setPaymentTime(formatDate)//支付时间
                    .setModifyTime(formatDate);//修改时间
            Integer updateResult = super.update(order);
            if(updateResult>0){
                Timer timer = new Timer();
                //设置定时任务,自动确认消费完成
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Order orderByOrderOrderSn = getOrderByOrderOrderSn(orderSn);
                        //判断该订单是否已发货
                        if(orderByOrderOrderSn.getStatus().equals(STATUS_ORDER_PAID)){
                            Date date = new Date();
                            String format = simpleDateFormat.format(date);
                            try {
                                Date formatDate = simpleDateFormat.parse(format);
                                //修改订单状态为已发货，修改时间
                                order.setModifyTime(formatDate)
                                        .setStatus(STATUS_ORDER_FINISHED)//订单完成状态
                                        .setConfirmStatus(STATUS_ORDER_CONFIRM);//确认完成订单
                                Integer updateResult = CanteenOrderService.super.update(order);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },15*60000);
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * @Author SGZ
     * @Description
     *      消费成功后，定时自动好评
     * @Param [order, orderSettingService, commentService, orderItemService]
     * @Return java.lang.Boolean
     * @Date 2019/11/24
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean receiveSuccess(String  orderSn, String date, CanteenOrderSettingService orderSettingService, CanteenCommentService commentService, CanteenOrderItemService canteenOrderItemService){
        Order order = getOrderByOrderOrderSn(orderSn);
        Long orderId = order.getId();
        //判断商品是否确认收货,若是收货,设置自动好评时间
        if(order.getConfirmStatus().equals(STATUS_ORDER_CONFIRM)){
                Long shopId = order.getShopId();
                //获取店铺自动好评时间
                OrderSetting orderSetting = orderSettingService.getOrderSettingByShopId(shopId);
                Integer commentOvertime = orderSetting.getCommentOvertime();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Comment comment = new Comment();
                        comment.setOrderId(orderId).setShopId(shopId);
                        Comment comment1 = commentService.selectComment(comment);
                        //判断该订单是否已进行评论
                        if(comment1 == null){
                            OrderItem orderItem = new OrderItem();
                            orderItem.setOrderId(orderId);
                            List<OrderItem> productByOrderId = canteenOrderItemService.getProductByOrderId(orderItem);
                            //判断该订单是否只有一个商品
                            if(productByOrderId.size() > 0 && productByOrderId.size()==1){
                                //如果该订单只有一个商品，productId等商品信息不为空
                                comment.setProductId(productByOrderId.get(0).getProductId())
                                        .setProductName(productByOrderId.get(0).getProductName())
                                        .setProductAttribute(productByOrderId.get(0).getProductAttr())
                                        .setPics(productByOrderId.get(0).getProductPic());
                            }
                            Date date = new Date();
                            String format = simpleDateFormat.format(date);
                            try {
                                Date formatDate = simpleDateFormat.parse(format);
                                //设置好评，默认展示，好评时间
                                comment.setStar(STATUS_COMMENT_GOOD)
                                        .setShowStatus(STATUS_COMMENT_SHOW)
                                        .setCreateTime(formatDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            commentService.addComment(comment);
                        }
                    }
                },5000);//commentOvertime*86400000
                return true;
            }

        return false;
    }
}
