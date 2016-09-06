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
        <header class="bar bar-nav bg-base">
            <h1 class="title dark-gray">商品详情</h1>
            <a style="color: #000;" class="button button-link button-nav pull-left" href="javascript:location.href='./index.do';">
                <span class="icon icon-left"></span>
            </a>
            <img src="images/shopping.png" class="pull-right">
            <img src="images/search.png" class="pull-right">
        </header>
        <div class="content" style="padding-top: 2.2rem;">

        </div>
    </div>
    <!--新增加的规格选择页面-->
    <div class="popup popup-fit popup-product-select" id="popup-product-spec"></div>
</div>
<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='./js/product.js' charset='utf-8'></script>
<script>
	$(document).ready(function(){
		var tabBar = new TabBar(".content");
		tabBar.addTab("goodsDetail", "商品详情", new Product("#popup-product-spec"));
		tabBar.addTab("comment", "买家评论", new Comment());
		tabBar.active("goodsDetail");
		
        $(document).on('click','.product-spec-group-body span',function(){
            var $el = $(this);
            $el.siblings().removeClass('hover-active');
            $el.addClass('hover-active');
        });

        $(document).on('click', '.spec-drop-down', function () {
            $.popup('#popup-product-spec');
        });

        $(document).on('click', function (e) {
            console.info(e.target.className.indexOf("popup-overlay"))
            if(e&& e.target&& e.target.className&&e.target.className.indexOf("popup-overlay")>=0) {
                $.closeModal('#popup-product-spec');
            }
        });
	});
</script>
</body>
</html>