package com.aaa.lee.app.myConst;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/26 09:04
 * @Description
 **/
public class WXConst {
    //微信小程序appid
    public static String APPID = "wx95440d8bf94f76b2";
    //微信小程序appsecret
    public static String APPSECRET = "f07cbdd84ee256fb56cd910ee2251a3f";
    //微信支付主体
    public static String TITLE = "test-wxpay";
    //订单号
    public static String ORDERNO = "1";
    //微信商户号
    public static String MCH_ID="1532192611";
    //微信支付的商户密钥
    public static final String KEY = "D7FF70A194598ED8D95126343D6A3B21";
    //获取微信Openid的请求地址
    public static String WX_GETOPENID_URL = "oVxV55KJ_1NRtad8U_F2LmARNHDc";
    //支付成功后的服务器回调url
    public static final String NOTIFY_URL="https://9ec88aa8.ngrok.io/api/wxpay/notice";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款通知地址
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
}
