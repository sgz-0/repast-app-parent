package com.aaa.lee.app.staticstatus;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/21 9:58
 * @Description
 **/
public class StaticProperties {

    public static final String OK = "OK";
    public static final String REDIS_KEY = "member";
    //支付方式为微信支付
    public static final Integer STATUS_ORDER_WXPAID =2;
    //待发货状态
    public static final Integer STATUS_ORDER_PAID =1;
    //已发货状态
    public static final Integer STATUS_ORDER_SENT =2;
    //已完成状态
    public static final Integer STATUS_ORDER_FINISHED =3;
    //关闭订单状态
    public static final Integer STATUS_ORDER_CLOSE=4;
    //确认收货状态
    public static final Integer STATUS_ORDER_CONFIRM=1;
    //未确认收货
    public static final Integer STATUS_ORDER_NOT_CONFIRM=0;

    public static final Integer STATUS_COMMENT_SHOW=1;
    public static final Integer STATUS_COMMENT_GOOD=5;

}
