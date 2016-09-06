<%@page contentType="text/html; charset=utf-8" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>确认订单</title>
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
	<meta name="format-detection" content="telephone=no" />
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/order_01.js"></script>
	<link rel="stylesheet" href="style/cssreset.css">
	<link rel="stylesheet" href="style/order.css">
</head>
<body>
	<p class="ding">确认订单
     <a class="goback" href="javascript:location.href='./index.do';">
      <img src="./images/back_1.png" />
     </a></p>
	<div class="order_data">
		 <ul class="top">
		 	 <li><span></span><em>康伟峰</em></li>
		 	 <li><span></span><em>18750552710</em></li>
		 </ul>
		 <div class="position"><span></span><em>福建省厦门市思明区软件园二期59号之一1楼101B姚明信息科技有限公司</em></div>
	</div>
	<div class="shop_list">
		<div class="img_show">
			<img src="images/order_image/product02.png" alt="">
		</div>
		<div class="shop_item">
			<h3>星巴克拿铁咖啡</h3>
			<div class="color">
				<dl>
					 <dt>颜色:</dt>
					 <dd>灰色</dd>
				</dl>
				<dl class="size">
					 <dt>尺寸:</dt>
					 <dd>XXL</dd>
				</dl>
			</div>
			<div class="bottom">
				<span class="tot_price"></span>
				<ul class="caculate">
					<li class="jian"><span class="active"></span></li>
					<li class="shuzi">0</li>
					<li class="jia"><span></span></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="require">
		<dl>
			 <dt>买家留言</dt>
			 <dd><input type="input" placeholder="选填,可填写与卖家达成一致的要求"></dd>
		</dl>
		<dl class="ways">
			 <dt>发票信息</dt>
			 <dd>纸质发票 个人 明细<span></span></dd>
		</dl>
	</div>
	<div class="sure_price">
		 <div class="total">
		 	 <span>合计:</span>
		 	 <em class="total_price"></em>
		 	 <span class="freight">不含运费</span>       
		 </div> 
		 <a class="sure">确认</a>
		 
	</div>
	<!-- 发票 -->
	<div class="invoice_mask">
		<div class="invoice">
			<div class="top">发票信息</div>
			<div class="invoive_box">
				<h3>发票类型：</h3>
				<ul class="invoice_type">
					<li class="active">纸质发票</li>
					<li>电子发票</li>
					<li>不开发票</li>
				</ul>
				<div class="taitou">
					<h3>发票抬头：</h3>
					<p></p>
					<div class="kuang">个人</div>
				</div>
				<div class="invoice_content">
					<h3>发票内容：</h3>
					<ul>
						<li class="active">
							<input type="checkbox" checked="checked" > 
							<em></em>
							<label for="">明细</label>
						</li>
						<li>
							<input type="checkbox" > 
							<em></em> 
							<label for="">耗材</label>
						</li>
						<li>
							<input type="checkbox" > 
							<em></em> 
							<label for="">电脑配件</label>
						</li>
						<li>
							<input type="checkbox" > 
							<em></em> 
							<label for="">其他</label>
						</li>

					</ul>
				</div>

			</div>
			<div class="sure">确定</div>
		</div>		 
	</div>
  <!-- 支付方式 -->
	<div class="pay_mask">
		<div class="pay_ways">
			 <p>支付方式</p>
			 <div class="alipay zhifubao">
			 	<ul>
			 		<li class="pay_li"><input type="radio" class="pay" name="pay" ><span></span></li>
			 		<li></li>
			 		<li class="zhi">支付宝</li>
			 	</ul>
			 </div>
			 <div class="alipay weixin">
			 	<ul>
			 		<li class="pay_li"><input type="radio" class="pay" name="pay"><span></span></li>
			 		<li></li>
			 		<li class="zhi">微信</li>
			 	</ul>
			 </div>
			 <div class="sure_pay">确认</div>
		</div>

	</div>
	
</body>
<script type="text/javascript">
	$(function() {		                         
		
		
	})
</script>
</html>