package com.compses.action.api.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.compses.constants.PayConstants;
import com.compses.model.OrderInfo;
import com.compses.service.api.order.OrderInfoService;
import com.compses.util.*;
import org.apache.openjpa.persistence.jest.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jocelynsuebb on 2016/3/14.
 */
@Controller
@RequestMapping("alipay")
public class ALIPayController {

//    @Autowired
//    private PayService payService;

    @Autowired
    private OrderInfoService orderInfoService;


    public void verifyWg(HttpServletRequest request , HttpServletResponse response){

    }

    @RequestMapping(value = "getOrderInfo.do")
    @ResponseBody
    public Map<String,Object> getOrderInfo(HttpServletRequest request , HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
//            String outTradeNo = TradeNoUtil.getTradeNo();
            String outTradeNo = request.getParameter("outTradeNo");//订单号
            String subject = request.getParameter("subject");//商品名称
            String body = request.getParameter("body");  //商品描述
            String price = request.getParameter("price");//价格
            String orderInfo = getOrderInfo(subject, body, price, outTradeNo);
            String sign = SignUtils.sign(orderInfo,PayConstants.ALI_RSA_PRIVATE);
            result.put("orderInfo",orderInfo);
            result.put("sign",sign);
            result.put("success",true);
        }catch (Exception e){
            e.getStackTrace();
            result.put("success", false);
        }
        return result;


    }

    @RequestMapping(value = "aliNotify.do" , method = RequestMethod.POST)
    public void aliNotify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("支付宝回调成功................................");
        AlipayCore.logResult("支付宝回调成功................................");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号	String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号	String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            if(AlipayNotify.verify(params)){//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                //交易成功且结束，即不可再做任何操作。
                if(trade_status.equals("TRADE_FINISHED")){
                    AlipayCore.logResult("交易成功且结束，即不可再做任何操作。................................");
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
                    String orderCode = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                    OrderInfo orderInfo = orderInfoService.getByOrderCode(orderCode);
                    orderInfo.setPayCode(tradeNo);
                    orderInfoService.update(orderInfo);
                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                } else if (trade_status.equals("TRADE_SUCCESS")){
                    AlipayCore.logResult("交易成功，且可对该交易做操作，如：多级分润、退款等。................................");
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
                    String orderCode = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                    OrderInfo orderInfo = orderInfoService.getByOrderCode(orderCode);
                    orderInfo.setPayCode(tradeNo);
                    orderInfoService.update(orderInfo);
                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                AlipayCore.logResult("返回success................................");
                out.println("success");	//请不要修改或删除

                //////////////////////////////////////////////////////////////////////////////////////////
            }else{//验证失败
                out.println("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.println("fail");
        }

    }

    private String getOrderInfo(String subject, String body, String price,String outTradeNo) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PayConstants.ALI_PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + PayConstants.ALI_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + PayConstants.ALI_NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"15m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    @RequestMapping(value = "aliPayRefund.do")
    @ResponseBody
    public void aliPayRefund(OrderInfo orderInfo){
        Map<String,String> bizContentMap = new HashMap<String, String>();
        bizContentMap.put("out_trade_no",orderInfo.getOrderCode());
        bizContentMap.put("trade_no","2016052121001004920259970990");
        bizContentMap.put("refund_amount","0.01");
        bizContentMap.put("refund_reason","正常退款");
//        bizContentMap.put("out_request_no","xzb0000001tk");
//        bizContentMap.put("operator_id","xbb");
        orderInfo.setOrderPrice(0.01);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",PayConstants.ALI_APP_ID,PayConstants.ALI_RSA_PRIVATE,"json","utf-8",PayConstants.ALI_RSA_PUBLIC);
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        String bizContent = JsonUtils.toJson(bizContentMap);
        bizContent = "{\"out_trade_no\":\""+orderInfo.getOrderCode()+"\",\"trade_no\":\"2016052121001004920259970990\",\""+orderInfo.getOrderPrice()+"\"}";
        request.setBizContent(bizContent);
        try {
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }


    public String getRefundOrder(){
        String refundOrder= "app_id=" + "\"" + PayConstants.ALI_APP_ID + "\"";

        refundOrder += "&method=\"alipay.trade.refund\"";

        refundOrder += "&charset=\"utf-8\"";

        refundOrder += "&sign_type=\"RSA\"";

        refundOrder += "&timestamp=" + "\"" + DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss") + "\"";

        refundOrder += "&version=\"1.0\"";
        return refundOrder;
    }

    @RequestMapping(value = "aliRefund.do")
    @ResponseBody
    public void aliRefund(HttpServletRequest request , HttpServletResponse response){
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", PayConstants.ALI_REFUND_SERVICE);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("notify_url", "http://www.baidu.com");
        sParaTemp.put("seller_user_id", PayConstants.ALI_SELLER);
        sParaTemp.put("refund_date", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        sParaTemp.put("batch_no", "201605220425231");
        sParaTemp.put("batch_num", "1");
        sParaTemp.put("detail_data", "2016052121001004920259970990^0.01^tk");
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","OK");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(sHtmlText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
