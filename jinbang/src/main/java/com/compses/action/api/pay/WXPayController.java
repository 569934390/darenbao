package com.compses.action.api.pay;

import com.compses.constants.PayConstants;
import com.compses.model.OrderInfo;
import com.compses.service.api.order.OrderInfoService;
import com.compses.util.PayUtil;
import com.compses.util.TenpayUtil;
import com.compses.util.WXUtil;
import com.compses.util.XMLUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by jocelynsuebb on 2016/3/11.
 */
@Controller
@RequestMapping("pay")
public class WXPayController {

    public static Logger logger = LoggerFactory.getLogger(WXPayController.class);

//    @Autowired
//    private PayService payService;

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "webchatPrePay.do")
    @ResponseBody
    public Map<String ,Object> webchatPrePay(HttpServletRequest request , HttpServletResponse response){
        String outTradeNo = request.getParameter("outTradeNo");//订单号
        String subject = request.getParameter("subject");//商品名称
        String body = request.getParameter("body");  //商品描述
        String price = request.getParameter("price");//价格
//
        double dprice = Double.parseDouble(price)*100;
        price = (int)dprice+"";
//        price = String.valueOf(Integer.parseInt(p));

        //TODO 需要传订单号价格
/*        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
        String out_trade_no = strReq;*/

        Map<String, Object> result = new HashMap<String, Object>();
        String responString = null;
        String noncestr = WXUtil.getNonceStr();
        String timestamp = WXUtil.getTimeStamp();
        try {
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", PayConstants.WEBCHAT_APP_ID);
            parameters.put("mch_id", PayConstants.WEBCHAT_MCH_ID);
            parameters.put("nonce_str", noncestr);
            parameters.put("body", subject);//购买支付信息
            parameters.put("out_trade_no", outTradeNo);//订单号
            parameters.put("total_fee", price);// 总金额单位为分
            parameters.put("spbill_create_ip", "120.32.188.50");
            parameters.put("notify_url", PayConstants.WEBCHAT_NOTIFY_URL);
            parameters.put("trade_type", PayConstants.WEBCHAT_TRADE_TYPE);
//        parameters.put("openid", openId);
            String sign = PayUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            String requestXML = PayUtil.getRequestXml(parameters);
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(PayConstants.WEBCHAT_PRE_URL);
            postMethod.setRequestEntity(new StringRequestEntity(requestXML, "text/xml", "UTF-8"));
            int statusCoede = httpClient.executeMethod(postMethod);
            if (statusCoede == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(postMethod.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responString = new String(strByte, 0, strByte.length, "UTF-8");
                bos.close();
                bis.close();
            }
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
            Map map = XMLUtil.doXMLParse(responString);
            if(null != map){
                if (null != map.get("prepay_id") && ""!= map.get("prepay_id")){
                        SortedMap<Object, Object> params = new TreeMap<Object, Object>();
                        params.put("appid",PayConstants.WEBCHAT_APP_ID);
                        params.put("partnerid", PayConstants.WEBCHAT_MCH_ID);
                        params.put("prepayid", map.get("prepay_id"));
                        params.put("package", PayConstants.WEBCHAT_PACKAGE);
//                        params.put("appkey",PayConstants.WEBCHAT_APP_KEY);
                        params.put("noncestr",noncestr);
                        params.put("timestamp",timestamp);
                        params.put("sign", PayUtil.createSign("UTF-8",params));
                        result.put("success",true);
                        result.put("result",params);
                    }else{
                        result.put("success", false);
                        result.put("msg", map.get("err_code_des"));
                }
            }else{
                result.put("success", false);
                result.put("msg", "支付失败");
            }
        }catch (Exception e){
            result.put("success", false);
            result.put("msg", "支付失败");
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "webchatNotify.do" , method = RequestMethod.POST)
    public void webchatNotify(HttpServletRequest request, HttpServletResponse response){
        //TODO 更改订单状态
        String notifyXML = "";
        String inputLine = "";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXML += inputLine;
            }
            logger.debug(notifyXML);
            request.getReader().close();
            Map map = XMLUtil.doXMLParse(notifyXML);
            if("SUCCESS".equals(map.get("return_code").toString())){
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                String transactionId = map.get("transaction_id").toString();  //微信支付订单号
                String orderCode = map.get("out_trade_no").toString();     //订单号
                OrderInfo orderInfo = orderInfoService.getByOrderCode(orderCode);
                orderInfo.setPayCode(transactionId);
                orderInfoService.update(orderInfo);

            }
            else{
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            logger.debug("微信++++++++++++++++"+resXml);
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "wxPayRefund.do")
    @ResponseBody
    public void wxPayRefund(OrderInfo orderInfo,HttpServletRequest request , HttpServletResponse response){
        try{
//            boolean flag = payService.wxPayRefund(orderInfo);
/*        String outTradeNo = request.getParameter("outTradeNo");//订单号
        String price = request.getParameter("price");//价格
        Map<String, Object> result = new HashMap<String, Object>();
        String responString = null;
        String noncestr = WXUtil.getNonceStr();
        String timestamp = WXUtil.getTimeStamp();
        double dprice = Double.parseDouble(price)*100;
        price = (int)dprice+"";

        try{
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", PayConstants.WEBCHAT_APP_ID);
            parameters.put("mch_id", PayConstants.WEBCHAT_MCH_ID);
            parameters.put("nonce_str", noncestr);
            parameters.put("op_user_id",PayConstants.WEBCHAT_MCH_ID);
            parameters.put("out_refund_no","xzb1111111111110");
            parameters.put("out_trade_no", outTradeNo);//订单号
            parameters.put("refund_fee",price);
            parameters.put("total_fee", price);// 总金额单位为分
            String sign = PayUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            String requestXML = PayUtil.getRequestXml(parameters);
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(PayConstants.WEBCHAT_REFUND_URL);
            postMethod.setRequestEntity(new StringRequestEntity(requestXML, "text/xml", "UTF-8"));
            int statusCoede = httpClient.executeMethod(postMethod);
            if (statusCoede == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(postMethod.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responString = new String(strByte, 0, strByte.length, "UTF-8");
                bos.close();
                bis.close();
            }
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
            Map map = XMLUtil.doXMLParse(responString);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
