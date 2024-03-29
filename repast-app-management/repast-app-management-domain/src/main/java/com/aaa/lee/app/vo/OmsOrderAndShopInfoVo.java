package com.aaa.lee.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/26 15:32
 * @Description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OmsOrderAndShopInfoVo implements Serializable {
    /**
     * 订单编号
     */
    private  String OrderSn;
    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     *
     */
    private BigDecimal payAmount;

    /**
     * 支付方式：0->未支付；1->支付宝；2->微信
     */
    private Integer payType;


    /**
     * 店铺名字
     */
    private  String name;

    /**
     * 商家主图
     */
    private String images;
    /**
     * 商家联系方式
     */
    private String phone;


}
