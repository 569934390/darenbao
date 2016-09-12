package com.compses.constants;

/**
 * Created by jocelynsuebb on 2016/3/11.
 */
//TODO xbb 微信配置已改,支付宝未改
public class PayConstants {

//    public static final String WEBCHAT_APP_KEY = "JINGPINKONG1234567890ABCDEFGHIJK";

    public static final String WEBCHAT_APP_KEY = "junnuoxiamen20150511ljx123456789";

    public static final String WEBCHAT_PRE_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String WEBCHAT_REFUND_URL="https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String WEBCHAT_TRADE_TYPE="APP";

    public static final String WEBCHAT_APP_ID="wx16f24a7d30f3a278";

    public static final String WEBCHAT_MCH_ID="1241036902";

    public static final String WEBCHAT_PACKAGE="Sign=WXPay";

    public static final String WEBCHAT_NOTIFY_URL="http://120.26.241.152:38080/jinbang/pay/webchatNotify.do";


    public static final String ALI_APP_ID="";

    // 商户PID
    public static final String ALI_PARTNER = "2088811102504866";
    // 商户收款账号
    public static final String ALI_SELLER = "junnuoxm@126.com";
    // 商户私钥，pkcs8格式
    public static final String ALI_RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM5ygwNPAaqlynnC/stVD/m8v39VVAmtlM+oaf7KFk10HArpWbtJion8REcz4Ld7+SZ2M4RJ86lDy0ufnA9xMChPWAjrg0eWswrU2gPKLgN9u56kcnjSsd8+f1N0wfV6FE408XMRpWVRG9PuvQFBHHcPpz3FBoxDbkyziG9yf4hDAgMBAAECgYEAju9r1BucfMkjAeDHRoKTeAZ906yBbd33vTrOgHDsth2g2NEtpmVJfTXjFCVP/dng7AdEreAByvy8QZS1mV91xgPnnxByjlEmf32YJ4h3aTyMxUEpfpkL2Bsa2qvmsOGlIGIvniQtjiD0JjjCC0eCdNqDDxzRojHHqyq8i+jma2ECQQD2V6PwOgr9bfK4Hy3dCznERsDZo0hHZveqY0N5wjpGavMQLgVaZjXP72tEMkFkx5d2uKKiefFLS+nmOnQNJeHTAkEA1op5TaAvWPzIyXaizrQ1fXX2Rc8pq1SOlu5HpnQURhfvxAWt4zuKB/13rJDgVnrGAv0NIqWGgm8wZpzkKP9J0QJAJ/Pc1chEIggj9nKwGIqKiufCvUl7HoF3p33D4sp6cxNDaptcKOYs0hWNexj/fB3W1d1qGxnV5ZVhUQKn8b30GwJAd2W/VqU0VIjyUbS1rOX78uPIeFdvsEsxw+u+cnOdqNi/dk9W0+SZ0BKPHLnQMDDJrHYablLChirNO/5KuEpDUQJAOHicG85LCdACRnBTcDpme/xnoEFbYgMYcSihgPTxNfx//ul/XdpVkjPv1T0EOj4JaDuwoWpJcn4WHkiA1taD8w==";
    // 支付宝公钥
    public static final String ALI_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final int ALI_SDK_PAY_FLAG = 1;

    public static final String ALI_NOTIFY_URL="http://120.26.241.152:38080/jinbang/alipay/aliNotify.do";

    // 调用的接口名，无需修改
    public static String ALI_REFUND_SERVICE = "refund_fastpay_by_platform_pwd";

}

