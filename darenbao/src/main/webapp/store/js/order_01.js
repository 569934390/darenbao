
$(function() {
	var count;
	var text;
	var dan;
	var s = location.href;
		var ss = s.substring(s.indexOf('#')+1);
		var sss=decodeURI(ss);
		var obj = JSON.parse(sss);
		$('.img_show img').attr('src',obj.image);
		$('.shop_item h3').html(obj.name);
		$('.size dt').html(obj.specs[0].name);
		$('.size dd').html(obj.specs[0].value);
		$('.total_price').html("￥"+obj.price*obj.num);
		$('.shuzi').html(obj.num);
		count=obj.num;
		dan=obj.price;
    //窗口调整
	$(window).on('resize', function() {
		resize();
	});
	$('.tot_price').html('￥'+dan);

	function resize() {
      document.getElementsByTagName('html')[0].style.fontSize = $(window).innerWidth() /3.75 + 'px';
	}
	resize();

	//发票
	$('.invoice_type li').on('touchend',function(e){
		e.stopPropagation();
        $(this).addClass('active').siblings('li').removeClass('active'); 
	});
	//发票内容
	$('.invoice_content ul li input').on('touchend',function(e){
		e.stopPropagation();
         if(!$(this).prop('checked')){
         	$(this).closest('li').addClass('active');
         }else{
         	$(this).closest('li').removeClass('active');
         }
	});
	//配送
	$('.require .ways').on('touchend',function(e){
		e.stopPropagation();
          $('.invoice_mask').css('display','block');
          $('.invoice').stop().animate({'height':4.3+'rem'},100)
	});
	$('.invoice_mask').on('touchend',function(e){
		if (e.target.className == 'invoice_mask') {
			$('.invoice').stop().animate({
				'height': 0
			}, 100, function() {
				$('.invoice_mask').css('display', 'none');
			})
		}
		
	});
	
	//支付宝/微信
	$('.pay_ways .pay').on('touchend',function(e){
		e.stopPropagation();
		console.log($(this).prop('checked'))
        if(!$(this).prop('checked')){
            $(this).closest('li').addClass('active').closest('div').siblings('div').find('li').removeClass('active')	
        	text=$(this).closest('li').siblings('.zhi').text();
        }else{
        	 $(this).closest('li').addClass('active').closest('div').siblings('div').find('li').removeClass('active')	
        }
       
	});
	//订单确认
	$('.sure_price a').on('touchend',function(){
		console.log("BB");
  	  if(window.app && app.Alipay)
  		  app.Alipay(price, obj.name);
	});

	var callback = function(bridge) {
		$('.sure_price a').click(function(){
			if(bridge)
				bridge.callHandler('payAction', {price: price, name:obj.name}, function(response) {});
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
	
	
	$('.pay_mask').on('touchend',function(e){
		if(e.target.className=='pay_mask'){
			$('.pay_ways').stop().animate({'height':0+'rem'},100,function(){
				   $('.pay_mask').css('display','none');
			});
		}
     
	});
	// $('.pay_ways').on('touchend',function(e){
 //      e.stopPropagation();
	// })
//	$('.pay_ways .sure_pay').on('click',function(e){
//      if(text=="微信"){
//      	 alert("微信支付通道尚未开通");
//      }else if(text=="支付宝"){
//      }
//	});
	//数量/价格初始化;
	function total(count){
		if(count==0){
			// price=count*60;
	  //        $('.total_price').html('￥'+price);
			$('.jian span').addClass('active');
		}else{
			price=count*dan;
			$('.shuzi').text(count);
		
			$('.jian span').removeClass('active');
		}
	}
	total(count);
	//数量加减
	$('.jia').on('touchend',function(){
         count++;
         $('.jian span').removeClass('active');
         $('.shuzi').text(count);
         price=count*dan;
         $('.total_price').html('￥'+price);
	})
	$('.jian').on('touchend',function(){
         
         if(count>1){
         	count--
	         price=count*dan;
	         $('.total_price').html('￥'+price);
         }else{
         	count=0;
         	$('.jian span').addClass('active');
         	 price=count*dan;
	         $('.total_price').html('￥'+price);
         }
         $('.shuzi').text(count)
	});
	$('.invoice').on('touchend',function(e){
      e.stopPropagation();
	});

});