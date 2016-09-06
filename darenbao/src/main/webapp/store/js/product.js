var TabBar = function(container){
	container = $(container);
	container.html("");
	var titleContainer = $("<div class=\"buttons-tab order-tab\" style=\"position: absolute;left: 0;width: 100%;top:2.2rem;z-index:99\"></div>");
	var contentContainer = $("<div class=\"tabs\"></div>");
	container.parent().append(titleContainer);
	container.append(contentContainer);
	var tabs = {};
	
	function _addTab(tabId, name, object){
		var tab = {};
		tab.id = tabId;
		tab.name = name;
		tab.object = object;
		tab.title = $("<a href=\"#"+tabId+"\" class=\"tab-link button\">"+name+"</a>");
		tab.content = $("<div id=\""+tabId+"\" class=\"tab\">");
		titleContainer.append(tab.title);
		contentContainer.append(tab.content);
		tabs[tabId] = tab;
		if(object && object.setContainer)
			object.setContainer(tab.content);
	}

	function _active(tabId){
		for(var key in tabs) {
			tabs[key].title.removeClass("active");
			tabs[key].content.removeClass("active");
		}
		tabs[tabId].title.addClass("active");
		tabs[tabId].content.addClass("active");
		if(tabs[tabId].object && tabs[tabId].object.active)
			tabs[tabId].object.active();
	}
	
	titleContainer.on("click", "a", function(){
		_active($(this).attr("href").substring(1));
	});
	
	return {
		addTab: function(tabId, name, object) {
			_addTab(tabId, name, object);
		}, 
		active: function(tabId) {
			_active(tabId);
		}
	}
} 

var Product = function(specsContainer) {
	specsContainer = $(specsContainer);
	specsContainer.html("");
	var container;
	function _setContainer(c){
		container = c;
		container.html("");
	}
	var goodsData;
	var width;
	
	function _initProductShowcase(container){
		var productShowcase = $("<div class='product-showcase'></div>");
		container.append(productShowcase);
		var swiperContainer = $("<div class='swiper-container index-swiper-container' data-space-between='10'></div>");
		productShowcase.append(swiperContainer);
		var swiperWrapper = $("<div class='swiper-wrapper'></div>");
		swiperContainer.append(swiperWrapper);
		var images = goodsData.imagesTitle;
		for(var i=0; i<images.length; i++)
			swiperWrapper.append("<div class='swiper-slide'><img src='"+images[i]+"&width="+parseInt(width*1.5)+"' /></div>");
		swiperContainer.append("<div class='swiper-pagination'></div>");
	}
	
	function _initProductInfo(container){
		var productInfo = $("<div class='product-info'></div>");
		container.append(productInfo);
		productInfo.append("<h3>"+goodsData.title+"</h3>");
		productInfo.append("<p><span class='price-now'>￥"+goodsData.discount+"</span><span class='price-before'>￥"+goodsData.price+"</span></p>");
		productInfo.append("<p class='fg-light-gray'>月销："+goodsData.monthSell+" 库存："+goodsData.stock+" 包邮</p>");
		productInfo.append("<div class='spec-drop-down'> 规格 <span class='icon icon-down'></span></div>");
		var productImages = $("<div class='product-image-gallery'></div>");
		container.append(productImages);
		var details = goodsData.imagesDetail;
		for(var i=0; i<details.length; i++)
			productImages.append("<img src='"+details[i]+"&width="+parseInt(width*1.5)+"' />");
	}
	
	function _initProductSpecs(){
		var productSelectHeader = $("<div class='product-select-header'></div>");
		specsContainer.append(productSelectHeader);
		productSelectHeader.append("<span class='product-select-header-img'><img src='"+goodsData.coverImg+"' /></span>");
		productSelectHeader.append("<h3>"+goodsData.title+"</h3>");
		productSelectHeader.append("<span class='price-now'>￥"+goodsData.discount+"</span>");
		productSelectHeader.append("<span class='price-before'>￥"+goodsData.price+"</span>");
		var selectBody = $("<div class='product-select-body'></div>");
		specsContainer.append(selectBody);
		var specs = goodsData.specs;
		var specGroups = [];
		for(var i=0; i<specs.length; i++){
			var group = $("<div class='product-spec-group'></div>");
			selectBody.append(group);
			specGroups.push(group);
			group.append("<label class='product-spec-group-title'>"+specs[i].name+"：</label>");
			var groupBody = $("<div class='product-spec-group-body'></div>");
			group.append(groupBody);
			var list = specs[i].dataList;
			for(var j=0; j<list.length; j++)
				groupBody.append("<span>"+list[j]+"</span>");
		}
		var countGroup = $("<div class='product-spec-group'>");
		selectBody.append(countGroup);
		countGroup.append("<label class='product-spec-group-title'>数量：</label>");
		var countGroupBody = $("<div class='product-spec-group-body'></div>");
		countGroup.append(countGroupBody);
		var numCalc = $("<div class='number-calc'></div>");
		countGroupBody.append(numCalc);
		var numCalcSub = $("<span class='minus'>-</span>");
		var numCalcAdd = $("<span class='plus'>+</span>");
		var num = $("<span></span>");
		numCalc.append(numCalcSub).append(num).append(numCalcAdd);
		num.append(1);
		numCalcSub.on("click", function(){
			var n = parseInt(num.html());
			if(n>1)
				num.html(n-1);
		});
		numCalcAdd.on("click", function(){
			num.html(parseInt(num.html())+1);
		});
		var footer = $("<div class='product-select-footer'></div>");
		specsContainer.append(footer);
		var buyNow = $("<div class='hover-active'><a class='close-popup'>立即购买</a></div>");
		footer.append(buyNow);
		footer.append("<div><a class='close-popup'>加入购物车</a></div>");
		buyNow.on("click", function(){
			var data = {};
			data.image=goodsData.coverImg;
			data.name=goodsData.title;
			data.num=parseInt(num.html());
			data.price=goodsData.discount;
			data.specs=[];
			for(var i=0; i<specGroups.length; i++){
				var spec=specGroups[i];
				var value=spec.find(".hover-active");
				if(value.length<=0) {
					$.alert("请选择规格");
					return false;
				}
				data.specs.push({name:spec.children(".product-spec-group-title").html(), value:$(value[0]).html()});
			}
			var s = encodeURI(JSON.stringify(data));
			location.href="./sureOrder.do#"+s;
		});
	}
	
	function _load(){
		var callback = function(data) {
			goodsData = data;
			var productDetail = $("<div class='product-detail'></div>");
			container.append(productDetail);
			width = productDetail.width();
			_initProductShowcase(productDetail);
			_initProductInfo(productDetail);
			_initProductSpecs();
			$.init();
		}
		$.post("./getGoodsInfo.do", {goodsId: GetRequest()["goodsId"]}, callback);
	}
	
	function _active(){
		if(!goodsData)
			_load();
	}
	
	return {
		setContainer: function (container){
			_setContainer(container);
		}, 
		active: function(){
			_active();
		}
	}
}

var Comment = function(){
	var container;
	function _setContainer(c){
		container = c;
		container.html("");
	}

	
	var commentData;
	
	function _rate(container, rate){
		var count = 0;
		for(var i=0; i<rate; i++) {
			container.append("<img src=\"images/star_little_select.png\" />");
			count++;
		}
	}
	
	function _spec(container, specs){
		for(var i=0; i<specs.length; i++){
			container.append(specs[i].name+": <span>"+specs[i].value+"</span> ");
		}
	}
	
	function _init(container){
		var summary = $("<div class='user-evaluation-summary fg-light-gray'></div>");
		container.append(summary);
		summary.append("商品满意度:&nbsp;");
		_rate(summary, commentData.rate);
		summary.append("<span class='star-rate'>"+commentData.rate+"</span>");
		var data = commentData.list;
		
		for(var i=0; i<data.length; i++) {
			var item = $("<div class='user-evaluation-item'></div>")
			container.append(item);
			var itemInfo = $("<div class='user-evaluation-item-info'></div>");
			item.append(itemInfo);
			itemInfo.append("<div class='user-avatar-evaluation'><img src='"+data[i].headImage+"'></div>");
			var userInfo = $("<div class='user-info-evaluation'></div>");
			itemInfo.append(userInfo);
			userInfo.append("<h5 class='user-info-name fg-light-gray'>"+data[i].name+"</h5>");
			var rate = $("<div class='user-info-evaluation-star'></div>");
			userInfo.append(rate);
			_rate(rate, data[i].rate);
			itemInfo.append("<div class='user-evaluation-date fg-light-gray'>"+data[i].datetime+"</div>");
			item.append("<div class='user-evaluation-item-content'>"+data[i].comment+"</div>");
			var footer = $("<div class='user-evaluation-item-footer fg-light-gray'></div>");
			item.append(footer);
			_spec(footer, data[i].specs);
			
		}

	}
	
	function _load(){
		var callback = function(data) {
			commentData = data;
			var commentContainer = $("<div class='user-evaluation'></div>");
			container.append(commentContainer);
			_init(commentContainer);
		}
		$.post("./getComment.do", {goodsId: GetRequest()["goodsId"]}, callback);
	}
	
	function _active(){
		if(!commentData)
			_load();
	}
	
	return {
		setContainer: function (container){
			_setContainer(container);
		}, 
		active: function(){
			_active();
		}
	}
}

function GetRequest() { 
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
		strs = str.split("&"); 
		for(var i = 0; i < strs.length; i ++) { 
			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
		} 
	} 
	return theRequest; 
}