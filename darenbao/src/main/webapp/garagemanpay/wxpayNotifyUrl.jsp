<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@page import="com.club.web.alipay.util.SpringContextWrapper"%>
<%@ page import="com.club.web.wxpay.client.RequestHandler"%>
<%@ page import="com.club.web.wxpay.client.ResponseHandler"%>
<%@ page import="com.club.web.wxpay.client.ClientResponseHandler"%>
<%@ page import="com.club.web.wxpay.client.TenpayHttpClient"%>
<%@page import="com.club.web.alipay.service.AlipayService"%>
<%@ page import="com.club.web.wxpay.util.*"%>
<%@ page import="com.club.web.alipay.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Map<String, String> requestMap = MessageUtil.parseXml(request);
	String out_trade_no = requestMap.get("out_trade_no");//订单号
	String result_code = requestMap.get("result_code");	//订单信息状态
	//Double total_fee = Double.parseDouble(requestMap.get("total_fee"));
	String total_fee = requestMap.get("total_fee");	//总金额
	String time_end = requestMap.get("time_end");	//支付完成时间
	String body = requestMap.get("attach");	//自定义参数
	String trade_no = requestMap.get("transaction_id");	//微信支付订单号
	String mch_id = requestMap.get("mch_id");	//商户号
	String openid = requestMap.get("openid");	//用户标识

	// 如果支付成功，则更新状态
	AlipayService alipayService = SpringContextWrapper.getBean(AlipayService.class);
	int i = alipayService.findByOrderNum(out_trade_no);
	if (i == 0) {
		if (result_code.equals("SUCCESS")) {
			alipayService.updateOrderState(body,total_fee,openid,out_trade_no,
					trade_no,result_code,openid,mch_id,"1");
			out.println("success"); //请不要修改或删除
		} else {
			System.out.println("支付失败订单->>>" + out_trade_no);
		}
	} else {
		System.out.println("支付订单处理过->>>" + out_trade_no);
	}
%>