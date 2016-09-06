<%@page import="com.club.web.store.service.RechargeNoteService"%>
<%
	/* *
	 功能：支付宝服务器异步通知页面
	 版本：3.3
	 日期：2012-08-17
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

	 //***********页面功能说明***********
	 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
	 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
	 该页面调试工具请使用写文本函数logResult，该函数在com.vrapp.tools.alipay.util文件夹的AlipayNotify.java类文件中
	 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
	 //********************************
	 * */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.slf4j.Logger"%>
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="com.club.web.alipay.util.*"%>
<%@ page import="com.club.web.alipay.config.*"%>
<%@ page import="com.club.web.alipay.service.*"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%
	//获取支付宝POST过来反馈信息
	Map<String, String> params = new HashMap<String, String>();
	Map requestParams = request.getParameterMap();
	for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
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
	
	//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
	//商户订单号	
	String out_trade_no = new String(request.getParameter(
			"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
	
	//支付宝交易号	
	String trade_no = new String(request.getParameter("trade_no")
			.getBytes("ISO-8859-1"), "UTF-8");

	//交易状态
	String trade_status = new String(request.getParameter(
			"trade_status").getBytes("ISO-8859-1"), "UTF-8");
	
	//交易金额
	String total_fee = new String(request.getParameter(
			"total_fee").getBytes("ISO-8859-1"), "UTF-8");
	
	//买家支付宝用户号
	String buyer_id = new String(request.getParameter(
			"buyer_id").getBytes("ISO-8859-1"), "UTF-8");
	
	//买家支付宝账号
	String buyer_email = new String(request.getParameter(
			"buyer_email").getBytes("ISO-8859-1"), "UTF-8");
	
	//卖家支付宝用户号 
	String seller_id = new String(request.getParameter(
			"seller_id").getBytes("ISO-8859-1"), "UTF-8");
	
	//自定义参数
	String body = new String(request.getParameter(
			"body").getBytes("ISO-8859-1"), "UTF-8");

	if (AlipayNotify.verify(params)) {//验证成功
		RechargeNoteService rechargeNoteService = SpringContextWrapper.getBean(RechargeNoteService.class);
		rechargeNoteService.addRechargeNote(body,out_trade_no,total_fee,"2",buyer_id,buyer_email);
		
		AlipayService alipayService = SpringContextWrapper.getBean(AlipayService.class);
		//AlipayServiceImpl alipayServiceImpl = new AlipayServiceImpl();
		int i = alipayService.findByOrderNum(out_trade_no);
		
		//判断该笔订单是否在商户网站中已经做过处理 
		//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序  
		//如果有做过处理，不执行商户的业务程序  
		if (i == 0) {
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				//更新订单状态
				alipayService.updateOrderState(body,total_fee,buyer_id,out_trade_no,
						trade_no,trade_status,buyer_email,seller_id,"2");
			}
	
			System.out.println("支付成功订单->>>" + out_trade_no);
			out.println("success"); //请不要修改或删除
		}
	} else {//验证失败
		System.out.println("支付失败订单->>>" + out_trade_no);
		out.println("fail");
	}
%>
