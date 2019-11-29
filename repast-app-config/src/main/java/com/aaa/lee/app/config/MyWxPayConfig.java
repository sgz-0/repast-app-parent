package com.aaa.lee.app.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/22 10:46
 * @Description
 **/

public class MyWxPayConfig implements WXPayConfig {
    private byte[] certData;

    public MyWxPayConfig() throws Exception{
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/wxpay/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }
    @Override
    public String getAppID() {
        return null;
    }

    @Override
    public String getMchID() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
