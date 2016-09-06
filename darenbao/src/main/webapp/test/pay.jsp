<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta charset="utf-8">
<title>确认交易</title>
<script type="text/javascript"
	src="/shanguoyinyi/js/jquery/jquery-1.7.1.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<style>
body {
	padding: 0;
	margin: 0;
	font-family: "微软雅黑";
	font-size: 15px;
	background-color: #f3f3f3;
}

.top {
	padding: 20px 0 20px 0;
	width: 100%;
	overflow: hidden;
}

.top div {
	font-size: 16px;
	text-align: center;
	width: 100%;
	margin: 0;
	padding: 5px 0 5px 0;
}

.shop {
	background-color: #fff;
	width: 100%;
	padding: 10px 0 10px 0;
	overflow: hidden;
}

.shop div {
	width: 46%;
	float: left;
}

.shop-left {
	text-align: left;
	color: #ccc;
	padding-left: 2%;
}

.shop-rigth {
	text-align: right;
	padding-right: 2%;
}

.button {
	width: 100%;
	text-align: center;
	margin: 0 auto;
	padding: 20px 0 0 0;
}

.button input {
	width: 90%;
	margin: 0 auto;
	border: 0;
	color: #fff;
	background-color: green;
	padding: 10px;
}
</style>

<body>
	<div class="top">
		<div>支付测试</div>
		<input id="orderId" type="text" value="237068918100140032" />
	</div>

	<div class="button">
		<input type="button" value="立即支付" id="onBridgeReady" onclick="onBridgeReady()" /> 
		<input type="button" value="取消订单" id="closeOrder" onclick="closeOrder()" />
		<input type="button" value="立即退款" id="Re" onclick="Re()" />
		<input type="button" value="立即分享" id="onMenuShareTimeline" onclick="onMenuShareTimeline()" />
	</div>

</body>
<script>
	/* $.ajax({
		type : "GET",
		url : "/shanguoyinyi/weixin/weixinConfig/verification.do",
		data : {
			"url" : "weixin/weixinPay/testpay.do"
		},
		dataType : "json",
		success : function(data) {
			wx.config({
				debug : true,
				appId : data.msg.appId,
				timestamp : data.msg.timestamp,
				nonceStr : data.msg.nonceStr,
				signature : data.msg.signature,
				jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
						'onMenuShareAppMessage', 'onMenuShareQQ',
						'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems',
						'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
						'translateVoice', 'startRecord', 'stopRecord',
						'onRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice',
						'uploadVoice', 'downloadVoice', 'chooseImage',
						'previewImage', 'uploadImage', 'downloadImage',
						'getNetworkType', 'openLocation', 'getLocation',
						'hideOptionMenu', 'showOptionMenu', 'closeWindow',
						'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
						'addCard', 'chooseCard', 'openCard' ]
			});
		}
	}); */
</script>
<script type="text/javascript">
/* 	wx.ready(function() {
		 document.querySelector('#onBridgeReady').onclick = function () {
			var orderId = $("#orderId").val();
			$.ajax({
				type : "GET",
				url : "/shanguoyinyi/weixin/weixinPay/wxPay.do",
				data : {
					orderId : orderId
				},
				dataType : "json",
				success : function(data) {
					console.log(data);
					if (data.code) {
						var param = data.msg;
						var appId = param.appId;
						var timeStamp = param.timeStamp;
						var nonceStr = param.nonceStr;
						var packageValue = param.package;
						var paySign = param.sign;
						WeixinJSBridge.invoke('getBrandWCPayRequest',{
								"appId" : appId,
								"timeStamp" : timeStamp,
								"nonceStr" : nonceStr,
								"package" : packageValue,
								"signType" : "MD5",
								"paySign" : paySign
							},
							function(res) {
								alert(res.err_msg);
								if (res.err_msg == "get_brand_wcpay_request:ok") {
									alert("微信支付成功!");
								} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
									alert("用户取消支付!");
								} else {
									alert("支付失败!");
								}
							});
							}
						}
					});
		}
		 document.querySelector('#Re').onclick = function () {
			var orderId = $("#orderId").val();
			$.ajax({
				type : "GET",
				url : "/shanguoyinyi/weixin/weixinPay/wxRePay.do",
				data : {
					orderId : orderId
				},
				dataType : "json",
				success : function(data) {
					console.log(data);
				}
			});
		}
		 document.querySelector('#onMenuShareTimeline').onclick = function () {

			  wx.onMenuShareTimeline({  //例如分享到朋友圈的API  
				   title: '方倍工作室 微信JS-SDK DEMO', // 分享标题
				   link: 'http://www.cnblogs.com/txw1958/', // 分享链接
				   imgUrl: 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0', // 分享图标
				   success: function () {
				       // 用户确认分享后执行的回调函数
				   },
				   cancel: function () {
				       // 用户取消分享后执行的回调函数
				   }
				});
		}
	});
	wx.error(function(res) {
		alert(res.errMsg);
	}); */
	function onBridgeReady(){
		var orderId = $("#orderId").val();
		$.ajax({
			type : "GET",
			url : "/shanguoyinyi/weixin/weixinPay/wxPay.do",
			data : {
				orderId : orderId
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
				if (data.code) {
					var param = data.msg;
					var appId = param.appId;
					var timeStamp = param.timeStamp;
					var nonceStr = param.nonceStr; 
					var packageValue = param.package;
					var paySign = param.sign;
					WeixinJSBridge.invoke('getBrandWCPayRequest',{
							"appId" : appId,
							"timeStamp" : timeStamp,
							"nonceStr" : nonceStr,
							"package" : packageValue,
							"signType" : "MD5",
							"paySign" : paySign
						},
						function(res) {
							alert(res.err_msg);
							if (res.err_msg == "get_brand_wcpay_request:ok") {
								alert("微信支付成功!");
							} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
								alert("用户取消支付!");
							} else {
								alert("支付失败!");
							}
						});
						}
					}
				});
	}
	function closeOrder(){
		var orderId = $("#orderId").val();
		console.log(orderId);
		$.ajax({
			type : "GET",
			url : "/shanguoyinyi/weixin/weixinPay/wxCloseOrder.do",
			data : {
				orderId : orderId
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
			}
		});
	}
	function Re(){
		var orderId = $("#orderId").val();
		var orderId = $("#orderId").val();
		$.ajax({
			type : "GET",
			url : "/shanguoyinyi/weixin/weixinPay/wxRePay.do",
			data : {
				orderId : orderId
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
			}
		});
	}
</script>
</html>