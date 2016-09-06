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
            <h1 class="title dark-gray" >最特色</h1>
            <img src="images/menu.png" class="pull-left" style="position: absolute;" >
            <img src="images/shopping.png" class="pull-right">
            <img src="images/search.png" class="pull-right">
        </header>
        <div class="content">
            <!-- Slider -->
            <div class="swiper-container index-swiper-container" data-space-between='10'>
                <div class="swiper-wrapper">
                    <div class="swiper-slide"><img
                            src="images/swiper/banner01.png"
                            alt=""></div>
                    <div class="swiper-slide"><img
                            src="images/swiper/banner02.png"
                            alt=""></div>
                    <div class="swiper-slide"><img
                            src="images/swiper/banner03.png"
                            alt=""></div>
                </div>
                <div class="swiper-pagination"></div>
            </div> <!-- END SWIPER -->

            <div class="entrance"></div><!-- END ENTRANCE -->

            <div class="promotion"></div><!--END PROMOTION-->

            <div class="recommend-container recommend"></div>
            
            <div class="recommend-container hot-commondity"></div>
            
            <div class="recommend-container private-custom-leather"></div>

            <div class="recommend-container product-list"></div>

        </div>
    </div>
</div>

<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<!-- <script type='text/javascript' src='./js/common.js' charset='utf-8'></script> -->
<script type='text/javascript' src='./js/index.js' charset='utf-8'></script>
<script>
	$(document).ready(function(){
        initEntrance(".entrance");
        initPromotion(".promotion");
        var recommend = new CommonBar(".recommend", "./getRecommendGoods.do", "今日推荐", "Recommend", "#");
        var hotCommondity = new CommonBar(".hot-commondity", "./getHotGoods.do", "热门商品", "Hot commodity", "#");
        var privateCustomLeather = new CommonBar(".private-custom-leather", "./getProvateCustomLeather.do", "私人皮具定制", "Private custom leather", "#");
        var productList = new CommonBar(".product-list", "./getGoodsList.do", "商品列表", "Product list", "#");
        $("header > img.pull-left").click(function(){
	 		if(window.app && app.getContent)
	 			app.getContent();
		});
    	var callback = function(bridge) {
    		$("header > img.pull-left").click(function(){
    			if(bridge)
    				bridge.callHandler('showLeftMenuAction', {'Menu': 'HmomeMenuAction'}, function(response) {});
    		});
    	}
		function setupWebViewJavascriptBridge(callback) {
   	        if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
   	        if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
   	        window.WVJBCallbacks = [callback];
   	        var WVJBIframe = document.createElement('iframe');
   	        WVJBIframe.style.display = 'none';
   	        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
   	        document.documentElement.appendChild(WVJBIframe);
   	        setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
   	    }
		if(!window.app)
   	    	setupWebViewJavascriptBridge(callback);
		$.init();
	});
	

	
	
</script>
</body>
</html>