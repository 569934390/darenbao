$(function () {
    $('#back').attr('href',localStorage.getItem('searchPage'));
    localStorage.setItem('orderPage', 'html/customer/details/details.html?id='+Util.common.getParameter("id"));
    customer.details.init();
    customer.details.initSc();
    customer.details.getCartCount();
    //customer.details.getAddInfo();
    //customer.details.getPostage();
    localStorage.removeItem('one_location');
    //customer.details.wxInit();
    $('#detailCommentTabs').on('click', '.tabs a', function () {
        $(this).parent().parent().find('.ui-btn-active').removeClass('ui-btn-active');
        $(this).addClass('ui-btn-active');
        if ($(this).find('span').html() == '买家评论') {
            $('#commentTab').show();
            $('#detailTab').hide();
        }
        if ($(this).find('span').html() == '商品详情') {
            $('#commentTab').hide();
            $('#detailTab').show();
        }
    });
});

var timeCounter = function(){
	console.log("111");
    var $timer  = $("#timer-out");
    var startTime=$("#startDate_time").val();
     var ednTime=$("#endDate_time").val();
    startTime=startTime.replace(/\-/g,"/");
    ednTime=ednTime.replace(/\-/g,"/");
    console.log(startTime+"   "+ednTime);
    var curTimer=new Date().getTime();
    var startTimer=new Date(startTime).getTime();;
    var endTimer=new Date(ednTime).getTime();;
    var t=0;
    if(startTimer>curTimer ){
        $timer.find('.title-show').html("距开始:");
    	t=startTimer-curTimer;
    	var d=Math.floor(t/1000/60/60/24);
        var h=Math.floor(t/1000/60/60%24);
        var m=Math.floor(t/1000/60%60);
        var s=Math.floor(t/1000%60);
        var hours=h+d*24;
        if(hours<10&&hours >=0){
	        $timer.find('.hour').html('0'+hours);
        }else{
        	$timer.find('.hour').html(hours);
        }
        if(m<10&&m >=0){
	        $timer.find('.min').html('0'+m);
        }else{
        	$timer.find('.min').html(m);
        }
        if(s<10 && s>=0){
	        $timer.find('.sec').html('0'+s);
        }else{
        	$timer.find('.sec').html(s);
        }

    }else if(startTimer<curTimer && curTimer<endTimer){
    	$timer.find('.title-show').html("距结束:");
    	t=endTimer-curTimer;
    	var d=Math.floor(t/1000/60/60/24);
        var h=Math.floor(t/1000/60/60%24);
        var m=Math.floor(t/1000/60%60);
        var s=Math.floor(t/1000%60);
        var hours=h+d*24;
        if(hours<10&&hours >=0){
	        $timer.find('.hour').html('0'+hours);
        }else{
        	$timer.find('.hour').html(hours);
        }
        if(m<10&&m >=0){
	        $timer.find('.min').html('0'+m);
        }else{
        	$timer.find('.min').html(m);
        }
        if(s<10 && s>=0){
	        $timer.find('.sec').html('0'+s);
        }else{
        	$timer.find('.sec').html(s);
        }
    }else{
    	$timer.find('.title-show').html("已抢完");
    	$timer.find('.timer').hide();
    }
    $timer.show();
};
var customer = customer || {};
customer.details = {
    conditionStr: {},
    goodInfoObj:null,
    goMessagePage:function(){
    	if(customer.details.goodInfoObj){
    		localStorage.setItem('goodInfoObj', JSON.stringify(customer.details.goodInfoObj));
    		setTimeout(
    			document.location.href = "html/customer/details/message.html?goodId=" + Util.common.getParameter('id')
    			,1000);
    		
    	}
    },
    wxInit: function () {
        var url = Util.common.baseUrl + "/weixin/weixinConfig/verification.do";
        var currentUrl = "weixinfront/html/customer/details/details.html?id=" + Util.common.getParameter("id");
        var param = {"url": currentUrl};
        Util.common.executeAjaxCallback(url, param, function (data) {
            if (data.success) {
                wx.config({
                    debug: false,
                    appId: data.msg.appId,
                    timestamp: data.msg.timestamp,
                    nonceStr: data.msg.nonceStr,
                    signature: data.msg.signature,
                    jsApiList: [
                        // 所有要调用的 API 都要加到这个列表中
                        'checkJsApi',
                        'onMenuShareTimeline',
                        'onMenuShareAppMessage'
                    ]
                });
                wx.ready(function () {
                    var imageSrc = $(".slides").find('li').eq(0).find('img').attr('src');
                    var shareUrl = Util.common.baseUrl + 'weixin/weixinClient/index.do?from=weixinfront/html/customer/details/details.html?id=' + Util.common.getParameter("id");
                    var title = $("#goodsName").text();
                    var desc = $("#goodsName").text();
                    $("#wxShareLink").on("click", function () {
                        wx.onMenuShareAppMessage({
                            title: $("#goodsName").text(), // 分享标题
                            desc: $("#goodsName").text(), // 分享描述
                            link: shareUrl, // 分享链接
                            imgUrl: imageSrc, // 分享图标
                            type: 'link', // 分享类型,music、video或link，不填默认为link
                            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                            success: function () {
                                // 用户确认分享后执行的回调函数
                                Util.msg.show("msgId", "分享成功");
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                                Util.msg.show("msgId", "分享失败");
                            }
                        });
                    });
                    $("#wxCircleShareLink").on("click", function () {
                        wx.onMenuShareTimeline({
                            title: $("#goodsName").text(), // 分享标题
                            link: shareUrl, // 分享链接
                            imgUrl: imageSrc, // 分享图标
                            success: function () {
                                // 用户确认分享后执行的回调函数
                                Util.msg.show("msgId", "分享成功");
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                                Util.msg.show("msgId", "分享失败");
                            }
                        });
                    })
                    var shareData = {
                        title: title,
                        desc: desc,
                        link: shareUrl,
                        imgUrl: imageSrc
                    };
                    wx.onMenuShareAppMessage(shareData);
                    wx.onMenuShareTimeline(shareData);

                });
            }
        })
    },
    /*getAddInfo:function(){
        var data = {};
        data.provinceName = localStorage.getItem('fName');
        data.cityName = localStorage.getItem('sName');
        data.areaName = localStorage.getItem('aName');
        if( data.provinceName==null){
           // alert("哈哈")
            data.provinceName ="北京";
            data.cityName ="北京市";
            data.areaName ="东城区";
            localStorage.setItem('fName','北京');
            localStorage.setItem('sName','北京市');
            localStorage.setItem('aName','东城区');
        }
        console.log(data);
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        $("#addressInfo").html($("#addressInfo_t").tmpl(data));
        customer.details.initCityPick();
    },*/
    init: function () {
        //分享出来的详情页重新获取商铺名称
        var shareStoreId = Util.common.getParameter("storeId");
        var shareUserId = Util.common.getParameter("userId");
        if(shareUserId!=null){
            localStorage.setItem("userid",shareUserId);
        }
        if(shareStoreId!=null){
            localStorage.setItem("shopId",shareStoreId);
            var loadShopUrl = Util.common.baseUrl+"/weixin/store/subbranch/loadInfo.do";
            var param = {"id": shareStoreId};
            //var param = {"id": "248474721296064512"};
            Util.common.executeAjaxCallback(loadShopUrl, param, function (store) {
                localStorage.setItem('store',JSON.stringify(store));
                localStorage.setItem('my_name',store.name);
            });
        }
        var url = Util.common.baseUrl + "/weixin/good/getGoodDetail.do";
        var param = {"goodId": Util.common.getParameter("id")};

        localStorage.setItem('goodId', Util.common.getParameter("id"));
        Util.common.executeAjaxCallback(url, param, function (data) {
            localStorage.setItem('skuId', data.goodSkuList[0].id);
            //localStorage.setItem('postId', data.postId);
            var goodInfoObj={
            	name:data.name,
            	imgUrl:data.smallImage,
            	salePrice:data.salePrice,
            	cargoNo:data.cargoNo,
            	id:Util.common.getParameter("id")
            }
            customer.details.goodInfoObj=goodInfoObj;
//          localStorage.setItem('goodInfoObj', JSON.stringify(jsonobj));
            //图片轮播
            $("#slider-content").html($("#goodsDetail_showImage_t").tmpl(data.showImages));
            //商品描述
            data.shopName = localStorage.getItem('my_name');
            localStorage.setItem('salePrice', data.salePrice);
            $("#sp-desc-id .sp-desc-a").html($("#sp-desc-t").tmpl(data));
            $("#detail-flag").html($("#detail-flag-show").tmpl(data));
            if(data.startDate !=""&& data.startDate !=null && data.endDate !=""&&data.endDate !=null){
            	setInterval(timeCounter, 1000);
            }
            //商品描述(运费,收藏,月销量)
            $("#sp-desc-ysy").html($("#sp-desc-ysy-t").tmpl(data));
            //查价格窗口
            //规格
            var new_skuList = [];
            var items_1 = {}, items_2 = {}, items_3 = {};
            var item_value_1 = [], item_value_2 = [], item_value_3 = [];
            for (var i = 0; i < data.goodSkuList.length; i++) {
                var item_1 = data.goodSkuList[i].skuLong.split('!!');
                for (var j = 0; j < item_1.length; j += 3) {
                    var item_2 = item_1[j].split("~");
                    items_1.type = item_2[0];
                    item_value_1.push(item_2[2]);
                }
                for (var j = 1; j < item_1.length; j += 3) {
                    var item_2 = item_1[j].split("~");
                    items_2.type = item_2[0];
                    item_value_2.push(item_2[2]);
                }
                for (var j = 2; j < item_1.length; j += 3) {
                    var item_2 = item_1[j].split("~");
                    items_3.type = item_2[0];
                    item_value_3.push(item_2[2]);
                }
            }
            var result_1 = [], hash_1 = {};
            var result_2 = [], hash_2 = {};
            var result_3 = [], hash_3 = {};
            for (var n = 0, elem; (elem = item_value_1[n]) != null; n++) {
                if (!hash_1[elem]) {
                    result_1.push(elem);
                    hash_1[elem] = true;
                }
            }
            for (var n = 0, elem; (elem = item_value_2[n]) != null; n++) {
                if (!hash_2[elem]) {
                    result_2.push(elem);
                    hash_2[elem] = true;
                }
            }
            for (var n = 0, elem; (elem = item_value_3[n]) != null; n++) {
                if (!hash_3[elem]) {
                    result_3.push(elem);
                    hash_3[elem] = true;
                }
            }
            items_1.value = result_1;
            items_2.value = result_2;
            items_3.value = result_3;
            new_skuList.push(items_1);
            new_skuList.push(items_2);
            new_skuList.push(items_3);
            console.log(new_skuList);
            data.new_skuList = new_skuList;

            $("#goods-cass-div-box").html($("#goods-cass-div-t").tmpl(data));
            //详情图片
            $("#detailTab").html($("#detailTab_t").tmpl(data.detailImages.images));
            //买家评论
            $("#commentTab").html($("#commentTab_t").tmpl(data.goodEvaluationList));

            if (data.goodEvaluationList == null || data.goodEvaluationList == '') {
                $('#commentTab').html('<p style="text-align: center;padding: 10px 0;letter-spacing: 1px;">暂无评价</p>');
            }

            localStorage.setItem('nums', data.kucun);

            customer.details.initEvent(data);

            var wxUrl=window.location.href;
	        var imageSrc = $(".slides").find('li').eq(0).find('img').attr('src');
	        var shareUrl = 'fromshanguo=weixinfront/html/customer/details/details.html?id=' + Util.common.getParameter("id")+'&storeId='+ localStorage.getItem('shopId') + '&type=weixinIndex';
	        var title = $("#goodsName").text();
	        var desc = $("#goodsName").text();
	        Util.weixinMenu.share(wxUrl,title,shareUrl,imageSrc,desc);
            customer.details.getPostage();
        });
        var queryurl = Util.common.baseUrl + "/weixin/addr/query.do";
        var queryparam = {"userId": localStorage.getItem('userid')};
        Util.common.executeAjaxCallback(queryurl, queryparam, function (data) {
            for(var i=0;i< data.length;i++) {
                if(data[i].status == 1) {
                    $("input[name=provinceName]").val(data[i].provinceName);
                    $("input[name=cityName]").val(data[i].cityName);
                    $("input[name=areaName]").val(data[i].areaName);
                    var provinceCode =data[i].provinceCode;
                    var cityCode = data[i].cityCode;
                    var areaCode = data[i].areaCode;
                    $("#addressInfo").html($("#addressInfo_t").tmpl(data[i]));
                    $("input[name=provinceName]").attr("data-cs",provinceCode);
                    $("input[name=cityName]").attr("data-cs",cityCode);
                    $("input[name=areaName]").attr("data-cs",areaCode);
                    customer.details.initCityPick();
                }
            }
        });
        var data = {};
        data.provinceName = "北京";
        data.cityName = "北京市";
        data.areaName = "东城区";
        $("#addressInfo").html($("#addressInfo_t").tmpl(data));
        $("input[name=provinceName]").attr("data-cs","110000");
        $("input[name=cityName]").attr("data-cs","110100");
        $("input[name=areaName]").attr("data-cs","110101");
        customer.details.initCityPick();

    },
    //计算邮费
    getPostage:function () {
        var url = Util.common.baseUrl + "/weixin/common/getCarriageById.do";
        var param = {"carriageId": $("input[name=postId]").val()};
        Util.common.executeAjaxCallback(url, param, function (data) {
            $(".templateName").html(data.templateName);
            var quantity = $("#quantity").val();
            var salePrice = localStorage.getItem("salePrice");
            var areaCode = $("input[name=areaName]").attr("data-cs");
            if(areaCode==""){
                areaCode = $("input[name=cityName]").attr("data-cs");
            }
            $(".detailfreight").html(data.carriage);
            var datadetail = data.detail;
            for(var i=0; i < datadetail.length; i++){
                //console.log(datadetail[i].deliverRegion);
                var indentMoneyFull = datadetail[i].indentMoneyFull;
                if(datadetail[i].deliverRegion.indexOf(areaCode)>=0 && salePrice*quantity >= indentMoneyFull){
                   // alert("含有"+areaCode);
                    $(".detailfreight").html(datadetail[i].carriageFull);
                }else
                if(datadetail[i].deliverRegion.indexOf(areaCode) >= 0 && salePrice*quantity < indentMoneyFull){
                    $(".detailfreight").html(datadetail[i].carriageNotFull);
                }
                /*for(var key in datadetail[i]){
                    //console.log(key+':'+datadetail[i][key]);
                }*/
            }

            //$(".detailfreight").html(data);
        });
    },
    //选择地区
    initCityPick:function(){
        if ($.mobileCityPicker) {
            $.mobileCityPicker({
                id: 'demo_treelist',//容器id
                inputClass: 'cityPickerInput',//
                inputClick: true,
                option: {
                    defaultValue: [0, 0, 0],//默认选项
                    label: ['province', 'city', 'district'],
                    theme: 'android-holo-light',
                    mode: 'scroller',
                    inputClass: 'hidden',
                    display: 'bottom',
                    lang: 'zh',
                },
                callback: function (val, citys, citysid) {
                    var vals = val.split(' ');
                    $("input[name=provinceCode]").val(vals[0]);
                    $("input[name=cityCode]").val(vals[1]);
                    if (vals.length > 1) {
                        $("input[name=areaCode]").val(vals[2]);
                    }
                    $("#pca_value").html(citys.join('-'));
                    localStorage.setItem('fName',citys[0]);
                    localStorage.setItem('sName',citys[1]);
                    localStorage.setItem('aName',citys[2]);
                    if($("input[name=areaCode]").val()==""){
                        $("input[name=areaCode]").val("");
                        $("input[name=areaName]").val("");
                        $("input[name=areaName]").attr("data-id","");
                        $("input[name=areaName]").attr("data-cs","");
                    }
                    customer.details.getPostage();
                    //localStorage.setItem('aNameid',citysid[2]);
                    //console.log(citysid+"区编号");
                    //console.log(citysid[2]+"区编号");
                    console.log(val);
                    console.log(citys);
                },
            });
        }
    },
    initEvent: function (data) {
        /*图片轮播*/
        $("#slider-content").swiper({
            slidesPerView: 1,
            autoplay: 2500,
            loop: true,
            effect: 'fade',
            fade: {
                crossFade: true
            },
            speed: 1000,
            autoplayDisableOnInteraction: false,
            pagination: '.swiper-pagination',
            spaceBetween: 0
        });
    },
    //获取收藏状态
    initSc: function () {
        var url = Util.common.baseUrl + "/weixin/store/queryStore.do";
        var param = {"userId": localStorage.getItem("userid"), "shopId": localStorage.getItem("shopId")};
        var this_id = Util.common.getParameter('id');
        Util.common.executeAjaxCallback(url, param, function (data) {
            for (var i = 0; i < data.length; i++) {
                var temp = data[i].goodId;
                if (this_id == temp) {
                    $('#spxq_sc_dj_id').addClass('sc-share-active');
                }
            }
        });
    },
    //添加到购物车
    addCart: function () {
        var url = Util.common.baseUrl + "/weixin/cart/addCart.do";
        var param = {
            "userId": localStorage.getItem("userid"),
            "goodsId": localStorage.getItem("skuId"),
            "shopId": localStorage.getItem("shopId"),
            "count": $("#quantity").val()
        };
        Util.common.executeAjaxCallback(url, param, function (data) {
            //{"msg":"操作成功","code":"000000"}
            //Util.msg.show("msgId",data.msg);
            if (data.code == "000000") {
                $("#join-cart-popup").popup("open").css({'visibility': 'visible'});
                $("#join-cart-popup-popup").css({'top': '30%'});
                customer.details.getCartCount();
            }
        });
    },
    addFav: function () {
        var url = Util.common.baseUrl + "/weixin/store/addStore.do";
        var favdelurl = Util.common.baseUrl +"/weixin/store/delStore.do";
        var param = {
            "userId": localStorage.getItem("userid"),
            "goodsId": localStorage.getItem("goodId"),
            "shopId": localStorage.getItem("shopId"),
            "goodsIds": localStorage.getItem("goodId"),
            "count": $("#quantity").val()
        };
       if($('#spxq_sc_dj_id').hasClass('sc-share-active')){
            Util.common.executeAjaxCallback(favdelurl ,param,function(data){
                //{"msg":"操作成功","code":"000000"}
                if(data.code =='000000'){
                    $('#spxq_sc_dj_id').removeClass('sc-share-active');
                    Util.msg.show("msgId", "取消收藏");
                    //window.location.reload()
                }
            });
        }else{
            Util.common.executeAjaxCallback(url, param, function (data) {
                //{"msg":"操作成功","code":"000000"}
                if (data.msg == "操作成功") {
                    $('#spxq_sc_dj_id').addClass('sc-share-active');
                    Util.msg.show("msgId", "收藏成功");
                    //window.location.reload()
                }
            });
        }

    },
    //buy self
    goBuy: function () {
        var shopCartInfo = {};
        var shopCarts = [];
        var count = 1;
        var amount = 0;
        var shopCart = {};
        shopCart.goodImgUrl = $("#slider-content ul li:first-child > img").attr('src');
        shopCart.goodTitle = $("input[name=goodName]").val();
        shopCart.salePrice = $("input[name=salePrice]").val();
        shopCart.goodCount = $("input[name=goodCount]").val() * $('#quantity').val();
        shopCart.skuItem = $("input[name=skuItem]").val();
        shopCart.skuId = $("input[name=skuId]").val();
        shopCart.goodsId = $("input[name=goodsId]").val();
        shopCarts.push(shopCart);
        count += parseFloat(shopCart.goodCount);
        amount += parseFloat(shopCart.salePrice);
        shopCartInfo.shopCarts = shopCarts;
        shopCartInfo.count = shopCart.goodCount;
        shopCartInfo.amount = amount;
        shopCartInfo.all = amount * $('#quantity').val();
        document.location.href = "html/customer/order/order-detail-submit.html?buyType=0&&goodId=" + Util.common.getParameter('id') + "&&shopCartInfo=" + JSON.stringify(shopCartInfo);
    },
    //buy gift
    //我要送人
    goBuyFor: function () {
        var shopCartInfo = {};
        var shopCarts = [];
        var count = 1;
        var amount = 0;
        var shopCart = {};
        shopCart.goodImgUrl = $("#slider-content ul li:first-child > img").attr('src');
        shopCart.goodTitle = $("input[name=goodName]").val();
        shopCart.salePrice = $("input[name=salePrice]").val();
        shopCart.goodCount = $("input[name=goodCount]").val() * $('#quantity').val();
        shopCart.skuItem = $("input[name=skuItem]").val();
        shopCart.skuId = $("input[name=skuId]").val();
        shopCart.goodsId = $("input[name=goodsId]").val();
        shopCarts.push(shopCart);
        count += parseFloat(shopCart.goodCount);
        amount += parseFloat(shopCart.salePrice);
        shopCartInfo.shopCarts = shopCarts;
        shopCartInfo.count = shopCart.goodCount;
        shopCartInfo.amount = amount;
        shopCartInfo.all = amount * $('#quantity').val();

        //save cartInfo
        localStorage.setItem('buyType', '1');
        localStorage.setItem('goodId', Util.common.getParameter("id"));
        localStorage.setItem('shopCartInfo', JSON.stringify(shopCartInfo));

        document.location.href = "html/customer/address/edit.html?type=add&&use=now&buyType=1&&goodId=" + Util.common.getParameter('id') + "&&shopCartInfo=" + JSON.stringify(shopCartInfo);
        //document.location.href = "html/customer/order/order-detail-submit.html?buyType=1&&goodId=" + Util.common.getParameter('id') + "&&shopCartInfo=" + JSON.stringify(shopCartInfo);
    },
    //查询购物车商品数量
    getCartCount: function () {
        var url = Util.common.baseUrl + "/weixin/cart/getCartCount.do";
        var param = {"userId": localStorage.getItem("userid"), "shopId": localStorage.getItem("shopId")};
        var $cart_num_detail = $('<span id="cart_num_detail" class="shop-cart-span"></span>');

        Util.common.executeAjaxCallback(url, param, function (data) {
            console.log(data);
            if(data != '' && data != null) {
				$('#cart_num_detail').remove();
                $('.shop-cart-css').append($cart_num_detail);
                $('#cart_num_detail').html(data);
            } else {
                $('#cart_num_detail').remove();
            }
        });
    }
}
/*规格弹出窗口*/
$("#ggdialog").on({
    popupafteropen: function () {
        $("#ggdialog-popup").css({
            "top": ($(window).height() - $("#ggdialog-popup").height()),
            "left": "0",
            "width": "100%"
        });
        $("body").css({overflow: "hidden"});    //禁用滚动条
    }
});

/*var num_add="";*/
/*商品数量+1*/
function numAdd() {
    var num_add = parseInt($("#quantity").val()) + 1;
    if ($("#quantity").val() == "") {
        num_add = 1;
    }
    $("#quantity").val(num_add);
    localStorage.setItem('num',num_add);
    customer.details.getPostage();
}
/*商品数量-1*/
function numDec() {
    var num_dec = parseInt($("#quantity").val()) - 1;
    if (num_dec < 1) {
        $("#quantity").val(0);
        localStorage.setItem('num',0);
    } else {
        $("#quantity").val(num_dec);
        localStorage.setItem('num',num_dec);
    }
    customer.details.getPostage();

}
/**
 * 分享开始
 */
$(".share-bottom-bar-a").on("click", function () {
    $(".my-ui-share-bottom").show();
    $("body").css({"overflow": "hidden"});
});
$(".my-ui-share-bottom").on("click", function (e) {
    var target = $(e.target);
    if (target.closest(".share-content").length == 0) {
        $(this).hide();
        $("body").css({"overflow": "visible"});
    }
});
/**
 * 分享开始
 */
$(".share-bottom-bar-a").on("click", function () {
    $(".my-ui-share-bottom").show();
    $("body").css({"overflow": "hidden"});
});
$(".my-ui-share-bottom,.dialog_close").on("click", function (e) {
    var target = $(e.target);
    if (target.closest(".share-content").length == 0) {
        $(this).hide();
        $("body").css({"overflow": "visible"});
    }
});
function showGoodSize(type) {
    var nums = localStorage.getItem('nums');

    if (type == 1) {
        if (nums == 0) {
            $("#no-goods-popup").show().on('click', '.ok-order-btn, .dialog_close' ,function(){
                $("#no-goods-popup").hide();
            });
            return;
        } else {
            $("#goods-cass-div-box").show();
            $('#buyNow').on('click', function () {
                customer.details.goBuyFor();
            });
        }
    } else if (type == 2) {
        if (nums == 0) {
            $("#no-goods-popup").show().on('click', '.ok-order-btn, .dialog_close' ,function(){
                $("#no-goods-popup").hide();
            });
            return;
        } else {
            $("#goods-cass-div-box").show();
            $('#buyNow').on('click', function () {
                customer.details.goBuy();
            });
        }
    } else if (type == 3) {
        if (nums == 0) {
            $("#no-goods-popup").show().on('click', '.ok-order-btn, .dialog_close' ,function(){
                $("#no-goods-popup").hide();
            });
            return;
        } else {
            $("#goods-cass-div-box").show();
            $('#buyNow').on('click', function () {
                customer.details.goBuy();
            });
        }
    }

    $('#addCart').on('click', function () {
        if (nums == 0) {
			$('#cart_num_detail').remove();
            $("#no-goods-popup").show().popup("open");
        } else {
            customer.details.addCart();
        }
    });
    $('#weight-0-0').attr('checked', 'true');
    $('#weight-1-0').attr('checked', 'true');
    $('#weight-2-0').attr('checked', 'true');

    $("body").css({"overflow": "hidden"});
}
function closeGoodSize() {
    $("#goods-cass-div-box").hide();
    $("body").css({"overflow": "visible"});
    //document.location.href = "/weixinfront/html/customer/details/details.html?id=" + Util.common.getParameter("id");
}

//function goodsWxShare() {
//  var imageSrc = $(".slides").find('li').eq(0).find('img').attr('src');
//  alert($("#goodsName").text());
//  var shareUrl = Util.common.baseUrl + 'weixin/weixinClient/index.do?from=weixinfront/html/customer/details/details.html?id=' + Util.common.getParameter("id");
//  wx.onMenuShareAppMessage({
//      title: $("#goodsName").text(), // 分享标题
//      desc: $("#goodsName").text(), // 分享描述
//      link: shareUrl, // 分享链接
//      imgUrl: imageSrc, // 分享图标
//      type: 'link', // 分享类型,music、video或link，不填默认为link
//      dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
//      success: function () {
//          // 用户确认分享后执行的回调函数
//          Util.msg.show("msgId", "分享成功");
//      },
//      cancel: function () {
//          // 用户取消分享后执行的回调函数
//          Util.msg.show("msgId", "分享失败");
//      }
//  });
//}
//
//function goodsWxCircleShare() {
//  var imageSrc = $(".slides").find('li').eq(0).find('img').attr('src');
//  alert($("#goodsName").text());
//  wx.onMenuShareTimeline({
//      title: $("#goodsName").text(), // 分享标题
//      link: window.location.url, // 分享链接
//      imgUrl: imageSrc, // 分享图标
//      success: function () {
//          // 用户确认分享后执行的回调函数
//          Util.msg.show("msgId", "分享成功");
//      },
//      cancel: function () {
//          // 用户取消分享后执行的回调函数
//          Util.msg.show("msgId", "分享失败");
//      }
//  });
//}