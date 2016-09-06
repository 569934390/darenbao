var ContentTab = function(container, items){
	container = $(container);
	var titles = $("<div class=\"buttons-tab order-tab\"></div>");
	container.append(titles);
	var contents = $("<div class=\"tabs\"></div>");
	container.append(contents);
	var tabs = {};
	for(var i=0; i<items.length; i++) {
		tabs[items[i].id] = new TabItem(titles, contents, items[i]);
	}
	if(items.length>0)
		tabs[items[0].id].active();
	return {
		get: function(id){
			return tabs[id];
		}
		
	};
}

var TabItem = function(titleContainer, contentContainer, item){
	var title = $("<a href=\"#"+item.id+"\" class=\"tab-link button\">"+item.name+"</a>");
	titleContainer.append(title);
	var content = $("<div id=\""+item.id+"\" class=\"tab\"></div>");
	contentContainer.append(content);
	
	var totalprice = 0;
	var totalcount = 0;
	function goodsItem(data){
		var goods = $("<div class=\"order-item\"></div>");
		goods.append("<div class=\"order-item-img\"><img src=\""+data.coverImg+"\" /></div>");
		var goodsInfo = $("<div class=\"order-item-info\"></div>");
		goods.append(goodsInfo);
		goodsInfo.append("<h4>"+data.title+"</h4>");
		var spec = $("<p class=\"fg-light-gray order-item-describe\"></p>");
		goodsInfo.append(spec);
		for(var i=0; i<data.specs.length; i++){
			var specItem = data.specs[i];
			spec.append(specItem.name+": <span>"+specItem.value+"</span> ");
		}
		goodsInfo.append("<p class=\"order-item-price\"><span class=\"price-now\">￥"+data.price+"</span> <span class=\"order-counter fg-light-gray\">x"+data.count+"</span></p>");
		totalprice += data.price*data.count;
		totalcount += data.count;
		return goods;
	}
	
	function totalBar(data) {
		var bar = $("<div class=\"order-summarize\"></div>");
		switch(data.statusCode) {
		case 1: bar.append("<img src=\"images/pay.png\" />"); break;
		case 2: bar.append("<img src=\"images/wait.png\" />"); break;
		case 3: bar.append("<img src=\"images/Inbound.png\" />"); break;
		case 4: bar.append("<img src=\"images/comments.png\" />"); break;
		}
		bar.append("<span>共"+totalcount+"件商品</span>");
		bar.append("<span class=\"fg-light-gray\"> 合计:<span class=\"price-now\">  ￥"+totalprice+"  </span>不含运费</span>");
		return bar;
	}
	
	function orderFooter(data) {
		var footer = $("<div class=\"order-operate\"></div>");
		switch(data.statusCode) {
		case 1: 
			footer.append("<a href=\"#\" class=\"button operation-button \">取消订单</a>");
			footer.append("<a href=\"#\" class=\"button operation-button hit-orange\">去付款</a>");
			break;
		case 2: 
			footer.append("<a href=\"#\" class=\"button operation-button hit-orange\">提醒发货</a>");
			break;
		case 3: 
			footer.append("<a href=\"#\" class=\"button operation-button hit-orange\">确认收货</a>"); 
			break;
		case 4: 
			footer.append("<a href=\"#\" class=\"button operation-button hit-orange\">去评价</a>");
			break;
		}
		return footer;
	}
	
	function orderItem(data){
		totalprice = 0;
		totalcount = 0;
		var item = $("<div class=\"order-showcase\"></div>");
		for(var i=0; i<data.list.length; i++)
			item.append(goodsItem(data.list[i]));
		item.append(totalBar(data));
		item.append(orderFooter(data));
		return item;
	}
	
	var nextPage = 1;
	function _load(){
		var callback = function(data) {
			for(var i=0; i<data.length; i++) {
				content.append(orderItem(data[i]));
			}
		}
		$.post(item.url, {orderStatus:item.status, page: nextPage}, callback);
	}
	
	function _active(){
		titleContainer.children("a.tab-link").removeClass("active");
		contentContainer.children("div.tab").removeClass("active");
		title.addClass("active");
		content.addClass("active");
		content.html("");
		nextPage = 1;
		_load();
	}
	
	title.on("click", function(){
		_active();
	});
	
	return {
		active: function(){
			_active();
		},
		nextPage: function(){
			_load();
		}
	};
}


//<div class="tabs">
//    <div id="tab1" class="tab active">
//        <div class="order-showcase">
//            <div class="order-item">
//                <div class="order-item-img">
//                    <img src="./images/product pic little.png" alt="">
//                </div>
//                <div class="order-item-info">
//                    <h4>星巴克拿铁咖啡</h4>
//                    <p class="fg-light-gray order-item-describe">颜色: <span>灰色</span> 尺寸: <span>XXL</span></p>
//                    <p class="order-item-price"><span class="price-now">$598</span> <span class="order-counter fg-light-gray">x1</span></p>
//                </div>
//            </div>
//            <div class="order-summarize">
//                <img src="images/Inbound.png" alt="">
//                <span>共1件商品</span>
//                <span class="fg-light-gray">
//                    合计:<span class="price-now">  $958  </span>不含运费
//                </span>
//            </div>
//            <div class="order-operate">
//                <a href="#" class="button operation-button hit-orange">提醒收货</a>
//            </div>
//        </div>
//    </div>
//    <div id="tab2" class="tab">
//        <div class="content-block">
//            <p>This is tab 2 content</p>
//        </div>
//    </div>
//    <div id="tab3" class="tab">
//        <div class="content-block">
//            <p>This is tab 3 content</p>
//        </div>
//    </div>
//    <div id="tab4" class="tab">
//        <div class="content-block">
//            <p>This is tab 4 content</p>
//        </div>
//    </div>
//    <div id="tab5" class="tab">
//        <div class="content-block">
//            <p>This is tab 5 content</p>
//        </div>
//    </div>
//</div>