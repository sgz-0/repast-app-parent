package com.aaa.lee.app.service;

import com.aaa.lee.app.code.OrderCodeFactory;
import com.aaa.lee.app.domain.*;
import com.aaa.lee.app.mapper.IntegralGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @data 2019/11/25 22:17
 * @project repast-app-parent
 * @declaration:
 */
@Service
public class IntegralGoodsService {

    @Autowired
    private IntegralGoodsMapper goodsMapper;
    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    public PmsProductVO getPmsProductVO(Long productId){
        PmsProductVO productVO = goodsMapper.getPmsProductVO(productId);
        if (null != productId){
            return productVO;
        }
        return null;
    }

    /**
     * 提交积分物品，生成积分订单
     *  第一个方法解决          生成订单
     *          用户id，商户id，订单编号，提交时间，用户名，实际支付积分，订单状态
     *  第二个方法解决          跟订单相关联的表的商品数据
     *          订单编号，商品id，购买数量，积分价格，图片路径，商品名称
     *  第三个方法解决          生成积分变化历史记录表
     *          用户id，商铺id，创建时间，积分变化类型，商品积分价格
     *
     */
    @Transactional
    public int setIntegralOrder(PmsProductVO productVO, RedisService redisService){
        /*
            获取32位订单时的时间戳时间格式
         */
        Date date = new Date();
        long lTime = date.getTime();
        String orderCode = OrderCodeFactory.getOrderCode(lTime);

        /*
        存入数据库时的订单提交时间格式
         */
        Date date1 = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
        String formatDate = dFormat.format(date1);
        SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strD = null;

        /*
         *从页面获取的所有商品的数据
         * 用户id，商铺id，商品名字，商品价格，图片路径
         */
        Long shopId = productVO.getShopId();//店铺id
        String productName = productVO.getUsername();//商品名
        BigDecimal price = productVO.getPrice();//商品价格
        String pic = productVO.getPic();//商品图片路径
        Long productId = productVO.getPmsId();//商品id
        Long memberId = productVO.getMemberId();//用户id
        Member memberName = goodsMapper.getMemberNameById(memberId);//用户名
        String username = memberName.getUsername();
        try {
            try {
                strD = lsdStrFormat.parse(formatDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*
            生成积分订单方法
            需要存入：用户id,用户名，商店id，订单编号，提交时间，实际支付积分,订单状态
             */
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setMemberId(memberId)
                    .setShopId(shopId)
                    .setOrderSn(orderCode)
                    .setCreateTime(strD)
                    .setPayAmount(price)
                    .setStatus(0)
                    .setMemberUsername(username);
            int i = goodsMapper.setIntegralOrder(omsOrder);
            if (i > 0){
                /*
                生成积分与商品关联表的方法
                需要存入：订单编号，商品id，购买数量，积分价格，图片路径，商品名称
                 */
                OmsOrderItem orderItem = new OmsOrderItem();
               orderItem.setOrderSn(orderCode)
                       .setProductId(productId)
                       .setProductQuantity(1)
                       .setProductPrice(price)
                       .setProductPic(pic)
                       .setProductName(productName);
               OmsOrderItem omsOrderItem = new OmsOrderItem();
                int i1 = goodsMapper.setIntegralOrderItem(omsOrderItem);
                /*
                生成积分变化历史记录表
                需要存入：用户id，商铺id，创建时间，积分变化类型，商品积分价格
                 */
                UmsIntegrationChangeHistory ich = new UmsIntegrationChangeHistory();
                     ich.setMemberId(memberId)
                        .setShopId(shopId)
                        .setCreateTime(strD)
                        .setChangeType(4)
                        .setChangeCount(Integer.valueOf(price.intValue()));
                int i2 = goodsMapper.setIntegralChangeHistory(ich);
                return i2;
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return 0;
    }

}
