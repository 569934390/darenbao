var ASC = '0',DESC = '1';
var myScroll;// 引用滑动分页需定义此固定变量myScroll，下拉时刷新使用
/**
 * 下拉刷新 （自定义实现此方法）
 * 此处用延迟模拟数据，
 */
function pullDownAction() {
    /**
     *此处填写加载后台数据代码
     * 结束处记得要调用刷新myScroll.refresh();
     **/
    myScroll.refresh();
}
/**
 * 滚动翻页 （自定义实现此方法）
 */
function pullUpAction() {
    $('.empty-list').hide();
    customer.search.loadMore();
}
//初始化绑定iScroll控件
//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
//document.addEventListener('DOMContentLoaded', function(){loaded("good-grid-id")}, false);
//$(document).bind('mobileinit',function(){
//    $.mobile.changePage.defaults.changeHash = false;
//    $.mobile.hashListeningEnabled = false;
//    $.mobile.pushStateEnabled = false;
//});
$(function () {
    customer.search.init();
    var href = 'html/customer/search/search-special.html?'+document.location.href.split('?')[1];
    console.info(href);
    localStorage.setItem('searchPage', href);
    $('#back').attr('href','html/customer/index.html?storeId='+localStorage.getItem("shopId")+'&type=weixinIndex&userId='+localStorage.getItem("userid"));
});
var customer = customer || {};
//初始化搜索条件
var conditionStr={
    "shopId":localStorage.getItem('shopId'),
    "goodName":Util.common.getParameter('searchText'),
    "pageSize":"",
    "pageNum":1,
    "classifyId":Util.common.getParameter('classify'),
    "saleNumSort":"0",
    "startPrice":"",
    "endPrice":"",
    "brandId":Util.common.getParameter('brandId'),
    "columnId":Util.common.getParameter('columnId'),
    "labelId": Util.common.getParameter('labelId')
};
var Loader = {
    isLoading:false,
    request:function(url,data,cb){
        var loader = this;
        if(loader.isLoading){
            return;
        }
        loader.isLoading = true;
        Util.common.executeAjaxCallback(url,data,function(){
            loader.isLoading = false;
            cb.apply(null,arguments);
        });
    }
};
var timeCounter = function(){
    var timers = $('.good-grid-info');
    for(var i=0;i<timers.length;i++){
        var $timer  = $(timers[i]);
        
        var curTimer=new Date().getTime();
        var startTimer=$timer.find('.startTimer').val();
        var endTimer=$timer.find('.endTimer').val();
        var t=0;
        if(startTimer>curTimer ){
	        $timer.find('.timer-title').html("距开始:");
	        $timer.find('.img-bottom-div').html("即将开始");
        	t=startTimer-curTimer;
        	var d=Math.floor(t/1000/60/60/24);
	        var h=Math.floor(t/1000/60/60%24);
	        var m=Math.floor(t/1000/60%60);
	        var s=Math.floor(t/1000%60);
	        $timer.find('.hour').html(( h+d*24));
	        $timer.find('.min').html(m);
	        $timer.find('.sec').html(s);
	       
        }else if(startTimer<curTimer && curTimer<endTimer){
        	$timer.find('.timer-title').html("距结束:");
        	$timer.find('.img-bottom-div').html("秒杀中");
        	t=endTimer-curTimer;
        	var d=Math.floor(t/1000/60/60/24);
	        var h=Math.floor(t/1000/60/60%24);
	        var m=Math.floor(t/1000/60%60);
	        var s=Math.floor(t/1000%60);
	        $timer.find('.hour').html(( h+d*24));
	        $timer.find('.min').html(m);
	        $timer.find('.sec').html(s);
        }else{
        	$timer.find('.timer-title').html("已被抢:");
        	$timer.find('.img-bottom-div').html("已抢完");
        	$timer.find('.timer-show').hide();
        	$timer.find('.sales-num-show').show();
        }
        
    }
};
var StringToDate=function(DateStr){
    console.log(DateStr);
	if(typeof DateStr=="date")
		return DateStr;
	var converted = Date.parse(DateStr);
	var myDate = new Date(converted);
	if(isNaN(myDate)){
		DateStr=DateStr.replace(/:/g,"-");
		DateStr=DateStr.replace(" ","-");
		DateStr=DateStr.replace(".","-");
		var arys= DateStr.split('-');
		switch(arys.length){
			case 7 : myDate = new Date(arys[0],--arys[1],arys[2],arys[3],arys[4],arys[5],arys[6]);
                break;
			case 6 : myDate = new Date(arys[0],--arys[1],arys[2],arys[3],arys[4],arys[5]);
                break;
			default: myDate = new Date(arys[0],--arys[1],arys[2]);
			}
		};
		return myDate;
};
customer.search = {
    init: function () {
    	Util.weixinMenu.shareIndex();
        this.initParameter();
        this.initGoodList();
        this.loadCartNumber();
        setInterval(timeCounter, 1000);
        loaded('good-grid-id-special');
        setTimeout(function(){
            //hot fix. 2016-6-14
            var PADDING_TOP = 0;
            var top = document.querySelector('.ui-content').getBoundingClientRect().top;
            document.querySelector('#good-grid-id-special').style.top = top + PADDING_TOP + 'px';
        },0);
    },
    //初始化加载购物车数目
    loadCartNumber: function () {
        var url = Util.common.baseUrl + "/weixin/cart/getCartCount.do";
        var param = {"userId": localStorage.getItem("userid"), "shopId": localStorage.getItem("shopId")};
        var $cart_num = $('<span id="cart_num"></span>');
        Util.common.executeAjaxCallback(url, param, function (data) {
            console.log(data);
            if(data != '' && data != null) {
                $('.my-goods-cart-search-a').append($cart_num);
                $('#cart_num').html(data);
            } else {
                $('#cart_num').hide();
            }
        });
    },
    //商品分类跳转过来参数初始化 参数title=茶叶&classify=238986851953229824&type=classify
    initParameter: function () {
        var title = Util.common.getParameter("title");
        if (title != null && title != '') {
            $("#pageTitle").html(title);
            $("#classifyId").html(title);
            Util.common.setWxTitle(title);
        }
        var classify = Util.common.getParameter("classify");
        if (classify != null && classify != '') {
            $("#classifyId").attr('classify', classify);
            //保存选择的分类id,默认为链接过来的父分类ID
            $("#selectClassifyId").val(classify);
        }
        var searchText = Util.common.getParameter("searchText");
        if (searchText != null && searchText != '') {
            $("#goodSearchId").val(searchText);
        }
    },
    conditionsIsGet: false,
    conditionsDataIsGet:false,
    //分类下拉
    showClassify: function (obj) {
        $("#searchFilterPopup").hide();
        $('#searchFilterId').css("color", "#000");
        if ($("#teaNavbarPopup").css("display") == 'block') {
            $('#classifyId').css("color", "#000");
            $("#teaNavbarPopup").hide();
            return;
        } else {
            $('#classifyId').css("color", "#ff6e82");
            $("#teaNavbarPopup").show();
        }
        if(!this.conditionsDataIsGet){
        	var url = Util.common.baseUrl+ "/weixin/good/goodColumn/findAllGoodColumns.do";
	        var param = {};
	        Util.common.executeAjaxCallback(url, param, function (data) {
	        	 var data=data.rule;
	        	 $("#menu-a").html($("#search_classify_a_t").tmpl(data));
	        	 $("#teaNavbarPopup").show();
	        });
	        this.conditionsDataIsGet=true;
        }else{
        	$("#teaNavbarPopup").show();
        }
        
    },

    changeClassify: function (obj) {
        document.location.href = 'html/customer/search/search-special.html?title=' + $(obj).attr('title') + '&columnId=' + $(obj).attr('classify') + '&type=column';
    },
    initGoodList:function(){
        var url = Util.common.baseUrl+ "/weixin/good/getGoodList.do";
        var param = {"conditionStr":JSON.stringify(conditionStr)};
        Loader.request(url, param, function (data) {
            if (data == '') {
                var $empty = $('<div class="empty-list"><img src="images/xxdpi/kzt_sp.png" alt=""><p>暂无此类商品</p><p>客官逛逛其他商品吧~</p></div>');
                $('#good-grid-id-special').empty();
                $('#good-grid-id-special').append($empty);
            } else {
                $('.empty-list').hide();
                customer.search.loadTemplate("#thelist", "#search_goodlist_t", data);
            }
            myScroll.refresh();		//调用刷新页面myScroll.refresh();
        });
    },
    //切换排序
    changeToAsc: function (obj) {
        conditionStr.pageNum = 1;
        $('.empty-list').hide();
        var sortField = obj.dataset.sortField;
        var sale, price;
        if ($(obj).hasClass("ui-icon-splb-xx")) { // 降序->升序
            $(obj).removeClass("ui-icon-splb-xx");
            $(obj).addClass("ui-icon-splb-xs");
            //$(obj).attr("value", DESC);
            if($(obj).hasClass('saleSort')) {
                conditionStr.saleNumSort = "1";
                conditionStr.priceSort = null;
            } else {
                conditionStr.saleNumSort = null;
                conditionStr.priceSort = "1";
            }
        } else { // 升序->降序
            $(obj).removeClass("ui-icon-splb-xs");
            $(obj).addClass("ui-icon-splb-xx");
            //$(obj).attr("value", ASC);
            if($(obj).hasClass('saleSort')) {
                conditionStr.saleNumSort = "0";
                conditionStr.priceSort = null;
            } else {
                conditionStr.saleNumSort = null;
                conditionStr.priceSort = "0";
            }
        }
        var url = Util.common.baseUrl+ "/weixin/good/getGoodList.do";

        //change search condition
        $.extend(conditionStr,{
            //"saleNumSort": sale,
            //"priceSort": price
        });

        var param = {"conditionStr":JSON.stringify(conditionStr)};
        Loader.request(url, param, function (data) {
            if (data == '') {
                var $empty = $('<div class="empty-list"><img src="images/xxdpi/kzt_sp.png" alt=""><p>暂无此类商品</p><p>客官逛逛其他商品吧~</p></div>');
                $('#thelist').empty();
                $('#good-grid-id-special').append($empty);
            } else {
                $('.empty-list').hide();
                customer.search.loadTemplate("#thelist", "#search_goodlist_t", data);
                myScroll.refresh();
            }
        });
    },
    //弹出筛选框
    showSearchFilter: function (obj) {
        $("#teaNavbarPopup").hide();
        $('#classifyId').css("color", "#000");
        if ($("#searchFilterPopup").css("display") == 'block') {
            $('#searchFilterId').css("color", "#000");
            $("#searchFilterPopup").hide();
            return;
        } else {
            $('#searchFilterId').css("color", "#ff6e82");
            $("#searchFilterPopup").show();
        }
        if (!this.conditionsIsGet) {
            var url = Util.common.baseUrl + "/weixin/good/goodLabels/findAll.do";
            var param = {};
            this.conditionsIsGet = true;
            Util.common.executeAjaxCallback(url, param, function (data) {
                var datas = {"datas": data};
                //customer.search.loadTemplate("#purpose-select", "#purpose-select_t", datas);
                $("#purpose-select").html($("#purpose-select_t").tmpl(datas));
            });
            //
            var url2 = Util.common.baseUrl + "/weixin/cargo/brand/findAll.do";
            var param2 = {};
            Util.common.executeAjaxCallback(url2, param2, function (data) {
                var datas = {"datas": data};
                customer.search.loadTemplate("#brand-select", "#brand-select_t", datas);
            });
        }
        //
    },
    btnReset: function () {
        $('.empty-list').hide();
        customer.search.initGoodList();
        $('#searchFilterId').css("color", "#000");
        $("#searchFilterPopup").hide();
    },
    loadMore:function(){
        conditionStr.pageNum++;
        $('.empty-list').hide();
        var url = Util.common.baseUrl+ "/weixin/good/getGoodList.do";
        var param = {"conditionStr":JSON.stringify(conditionStr)};
        console.log(param);
        Loader.request(url, param, function (data) {
            if (data == '') {
                console.log('true');
                $('.empty-list').show();
            } else {
                console.info(data );
                for (var i = 0; i < data.length; i++) {
		            console.info(data[i].startDate);
					console.info(data[i].endDate);
					var startDate=data[i].startDate;
					var endDate=data[i].endDate;
					var curTimer=new Date().getTime();
					var startTimer = new Date(StringToDate(startDate)).getTime();
					var endTimer = new Date(StringToDate(endDate)).getTime();
					//alert(startDate+"-----"+StringToDate(startDate));
					//alert(curTimer);
					var start=startTimer-curTimer;
					var end=endTimer-curTimer;
					data[i].starttimer = startTimer;
					data[i].endtimer = endTimer;
					//alert(startTimer);
					//alert(endTimer);
					var d, h, m,s;
					if(start > 0 && end > 0) {
						data[i].cTimer = data[i].starttimer;
						d = Math.floor(start / 1000 / 60 / 60 / 24);
						h = Math.floor(start / 1000 / 60 / 60 % 24);
						m = Math.floor(start / 1000 / 60 % 60);
						s = Math.floor(start / 1000 % 60);
						data[i].timeTitle = '距开始:';
						data[i].hour =( h+d*24);
						data[i].min = m;
						data[i].sec = s;
					} else if(start < 0 && end > 0) {
						data[i].cTimer = data[i].endtimer;
						d = Math.floor(end / 1000 / 60 / 60 / 24);
						h = Math.floor(end / 1000 / 60 / 60 % 24);
						m = Math.floor(end / 1000 / 60 % 60);
						s = Math.floor(end / 1000 % 60);
						data[i].timeTitle = '距结束:';
						data[i].hour =( h+d*24);
						data[i].min = m;
						data[i].sec = s;
					} else if(start < 0 && end < 0) {
						data[i].timeTitle = '已被抢!';
						data[i].hour = 0;
						data[i].min = 0;
						data[i].sec = 0;
						//clearInterval(timeCounter);
					}
					console.log(data[i].hour + ':' + data[i].min + ':' + data[i].sec);
				}
                var tpl = $("#search_goodlist_t").tmpl(data);
                console.info(tpl);
                $("#thelist").append(tpl);
                //customer.search.loadTemplate("#thelist", "#search_goodlist_t", data);
            }
            myScroll.refresh();		//调用刷新页面myScroll.refresh();
        });
    },
    //筛选框确定事件
    sureBtnFilter:function(){
        $('#searchFilterId').css("color","#000");
        $("#searchFilterPopup").hide();
        var url = Util.common.baseUrl+ "/weixin/good/getGoodList.do";
        //change search condition
        $.extend(conditionStr,{
            "goodName":Util.common.getParameter('searchText') || "",
            "pageNum":1,
            "startPrice":$('#minPrice').val(),
            "endPrice":$('#maxPrice').val(),
            "brandId":$('input[name=brand]:checked').val() || "",
            "labelId":$('input[name=purpose]:checked').val() || "",
        });
        var param = {"conditionStr":JSON.stringify(conditionStr)};
        Loader.request(url, param, function (data) {
            if (data == '') {
                console.log('true');
                $('.empty-list').show();
            } else {
                customer.search.loadTemplate("#thelist", "#search_goodlist_t", data);
                myScroll.refresh();
                console.log(data);
            }
        });
    },
    executeAjax: function (url, param, render, templateId) {
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            dataType: 'json',
            success: function (result) {
                this.loadTemplate(render, templateId, result);
            }
        });
    },
    loadTemplate:function(render ,templateId ,data ){
        for (var i = 0; i < data.length; i++) {
            console.log(data+'我是data');
            console.info(data[i].startDate);
            console.info(data[i].endDate);
			var startDate=data[i].startDate;
			var endDate=data[i].endDate;
            var curTimer=new Date().getTime();
            var startTimer = new Date(StringToDate(startDate)).getTime();
            var endTimer = new Date(StringToDate(endDate)).getTime();
			//alert(startDate+"-----"+StringToDate(startDate));
            //alert(curTimer);
			var start=startTimer-curTimer;
			var end=endTimer-curTimer;
            data[i].starttimer = startTimer;
            data[i].endtimer = endTimer;
			//alert(startTimer);
			//alert(endTimer);
			var d, h, m,s;
            if(start > 0 && end > 0) {
                data[i].cTimer = data[i].starttimer;
                d = Math.floor(start / 1000 / 60 / 60 / 24);
                h = Math.floor(start / 1000 / 60 / 60 % 24);
                m = Math.floor(start / 1000 / 60 % 60);
                s = Math.floor(start / 1000 % 60);
                data[i].timeTitle = '距开始:';
                data[i].hour =( h+d*24);
                data[i].min = m;
                data[i].sec = s;
            } else if(start < 0 && end > 0) {
                data[i].cTimer = data[i].endtimer;
                d = Math.floor(end / 1000 / 60 / 60 / 24);
                h = Math.floor(end / 1000 / 60 / 60 % 24);
                m = Math.floor(end / 1000 / 60 % 60);
                s = Math.floor(end / 1000 % 60);
                data[i].timeTitle = '距结束:';
                data[i].hour =( h+d*24);
                data[i].min = m;
                data[i].sec = s;
            } else if(start < 0 && end < 0) {
                data[i].timeTitle = '已被抢!';
                data[i].hour = 0;
                data[i].min = 0;
                data[i].sec = 0;
                //clearInterval(timeCounter);
            }
            console.log(data[i].hour + ':' + data[i].min + ':' + data[i].sec);
        }
        $(render).html($(templateId).tmpl(data));
    },
    //获取元素的纵坐标
    getTop: function (e) {
        var offset = e.offsetTop;
        if (e.offsetParent != null) offset += getTop(e.offsetParent);
        return offset;
    },
    //获取元素的横坐标
    getLeft: function (e) {
        var offset = e.offsetLeft;
        if (e.offsetParent != null) offset += getLeft(e.offsetParent);
        return offset;
    },
    //搜索框跳转
    searchEven:function(){
        setTimeout(function(){
            document.location.href="html/customer/search/search.html?searchContent=";
        }, 300);
    }
};