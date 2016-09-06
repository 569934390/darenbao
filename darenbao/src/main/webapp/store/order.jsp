<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>首页</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <link rel="stylesheet" href="./style/index.css">

</head>
<body>
<div class="page-group">
    <div class="page page-current">
<!--         <header class="bar bar-nav bg-base"> -->
<!--             <h1 class="title dark-gray">我的订单</h1> -->
<!--             <a class="button button-link button-nav pull-left back" href="/demos"> -->
<!--                 <span class="icon icon-left"></span> -->
<!--             </a> -->
<!--         </header> -->
        <div class="content native-scroll"></div>
    </div>
</div>
<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='./js/order.js' charset='utf-8'></script>
<script>
	$(document).ready(function(){
		new ContentTab(".content", [
			{id: "tab_all", name: "全部", url: "./getOrders.do", status: 0}, 
			{id: "tab_waiting_pay", name: "待付款", url: "./getOrders.do", status: 1}, 
			{id: "tab_waiting_delivery", name: "待发货", url: "./getOrders.do", status: 2}, 
			{id: "tab_waiting_receive", name: "待收货", url: "./getOrders.do", status: 3}, 
			{id: "tab_waiting_rate", name: "待评价", url: "./getOrders.do", status: 4},
		]);
	});
</script>
</body>
</html>