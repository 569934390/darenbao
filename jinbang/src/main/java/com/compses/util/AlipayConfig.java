package com.compses.util;

/**
 * Created by jocelynsuebb on 2016/4/14.
 */
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088021265404642";
    // 商户的私钥
    public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK/6mVuWDdXsLVNDzdtOb0oAtMpVrEqws1l5oLBuK0Mk6u9AZmcuIiOYn4bv3IdzyAW4ca18EVuzqQD2JwvSDeAON4kcYg4UJikPXIO5cJQxVOYCfaXtfJ9kbaFyN4tRvp3mduXGaE2rMu6d5DgLtDaV+Cf/9rpB6JLiXzuFOdChAgMBAAECgYAFi37jjOHYuL1g30UQQPNuwIDx0ys/mzu7eQKgLIh+cB5a9YuEesammnuEU/B98B8AVyR10+/0FMAhgKAQVkkLldcHDvCrolpUNDro6ITXfr7mEjEZb7WMN8ZGMxMKBJn/2FwsjTAzbYBspFTK98/gPQ/Z3feApERMHWKOASwPMQJBAOmvLKVTSiXbGXCiiRPyH1XpFshY35OMwuBP+pMSiv/7kypjvMMFjW2uZeK4JbWIyATAQkKP5NXrsw4MjhMLvjUCQQDAyLb3xexXrE8l1zgVOQfwE1EjMBot7eML7U5v6btP5nE2zb9YVidMCmepRDV4KDPQD33f8ZsPY5lUTUxpH0Y9AkArTFDaZR9U+k6xZvw9HNyff2vAPW9XmHo3M7p667GjkoqHDSnZfLriurCYHISoKSrebn0Ydi7xUBXCIaNldgSNAkA/sdEp+f3FVcvsr7f64xXpTyiOnLT5mdBbP3Ob7DiUBIpBViczM3vWVtW7Cis0YOwFgSjQlc3qfrjJLqThUpDtAkAC1rDTUJIZhM+aeLgjU5YMI0+OmCcbiponAaphzPJCmEjQlWl8/zQqCkNKjU/R4l/IwpSXpUYI7lSXIyIRIWx6";

    // 支付宝的公钥，无需修改该值
    public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "C:\\Tomcat7\\logs";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 签名方式 不需修改
    public static String sign_type = "RSA";

}

