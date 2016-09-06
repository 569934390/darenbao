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
    
</head>
<body>
<div class="page-group">
    <div class="page page-current">
<!--         <header class="bar bar-nav bg-base"> -->
<!--             <h1 class="title dark-gray" >最特色</h1> -->
<!--             <img src="images/menu.png" class="pull-left" style="position: absolute;" > -->
<!--             <img src="images/shopping.png" class="pull-right"> -->
<!--             <img src="images/search.png" class="pull-right"> -->
<!--         </header> -->
        <div class="content">
        	<input class='btn-test' type="button" value="TEST" />
       	</div>
    </div>
</div>

<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script>
	$(document).ready(function(){
		$(".btn-test").click(function(){
			var data = {
				cargoId: 0,
				cargoNo: "AAA",
				cargoName: "上品虱子", 
				classifyId: "1", 
				supplierId: "226048126025674752", 
				brandId: "3453454353", 
				smallImage: {id: 0, url:"/image/goods/536870913/detail_01.jpg"}, 
				showImages: {
					groupId: 0,
					images: [{
						id: 0, url: "/image/goods/536870913/title_01.jpg"
					}, {
						id: 0, url: "/image/goods/536870913/title_02.jpg"
					}]
				}, 
				detailImages: {
					groupId: 0,
					images: [{
						id: 0, url: "/image/goods/536870914/title_01.jpg"
					}, {
						id: 0, url: "/image/goods/536870914/title_02.jpg"
					}]
				},
				skuTypes: [{
					skuTypeId: 510,
					skuItemIds: [511, 512, 513]
				}, {
					skuTypeId: 520,
					skuItemIds: [521, 522]
				}], 
				skuDelete: [601, 602, 603], 
				skuChange: [{
					skuId: 701, 
					skuCode: "牛逼", 
					price: 123.45
				}, {
					skuId: 702, 
					skuCode: "哄哄", 
					price: 234.56
				}], 
				skuAdd: [{
					skuCode: "NAXX", 
					price: 345.67, 
					skuItems: [511, 521]
				}, {
					skuCode: "NBXX", 
					price: 456.78, 
					skuItems: [511, 522]
				}, {
					skuCode: "NCXX", 
					price: 567.89, 
					skuItems: [521, 521]
				}]
			};
			$.post("./save.do", {
				cargo: encodeURI(JSON.stringify(data))
			});
		});
		$.init();
	});
	

	
	
</script>
</body>
</html>