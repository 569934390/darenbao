package com.club.web.alipay.config;


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
    public static String partner = "2088021321729610";
    
	// 商户收款账号
	public static String seller_id = "nameisyedan@163.com";
	
	// 商户的私钥，pkcs8格式
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMUkMea90S5//bTR\n" +
            "TH9xQnHRdXB+oqjPEUvk1KD8ZiBhRp1yGyASLuS24pbPqJKaXDmh1okoOMguivkr\n" +
            "zrRHq4qmMGq1GrwTTv1ZEXbsEjFxqIcQBCquSJ7dqOu/EqtFBhmNF6PkPBHgdQCL\n" +
            "pTefHwgLvmKg29raYGvrkYR1V2ghAgMBAAECgYASrJsNgG2QOg+OUUUtzUy9+1lk\n" +
            "jppZ40OALOz3UvFZn91QjL6FCzPl9m0AXCtbt8OEsg/gbi7yx3XipIOwEBnvnb7J\n" +
            "oeOkM5YlpkbggoedzBSY5fh19PqsmsB4KUY8JU2syNaZuG17in4VPnkjrnzHR63b\n" +
            "DG1PruPtcI3mWvEsAQJBAOGYVnDiYhOZc/IXgpHpXv/CgHZvw2h5pGdSSvEGjYl5\n" +
            "VDv79w5051tn8b/KTfqU0mmIIVnOQBHlz6fnZKuv9yUCQQDftiAdZ5sFOHRvsmq6\n" +
            "J30t104U57SS+nWetNvI1cofbv5zRx2zK/Ntj7U01DnStNu3xfpBbhhM8tge6IUI\n" +
            "BipNAkA+ggZZivCf+6H7z3XXVjGDbnJH3tnnJeVe15d6R1WKZPrpD/y5nbzJP/jl\n" +
            "KnoG15uhsB2Fk7d+/g1Wua/UPpKdAkAOU5aI64qk8fGa+Z0WINNGbURl2FTbGMkR\n" +
            "idmNaguQqcYH0eUYBVeIi6x6HqE5WjW79iTJrehGfULT7+YdFm/9AkEArHQh0HC6\n" +
            "ZI1xuFEPfODE9J5RSuAtKRJ4GJ81DEuFZsksA9PBZno8+Z7IWbwSnK7TczYFni4C\n" +
            "6uJiwQ306FbfKw==";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
