function initEntrance(container){
	container = $(container);
	resetEntrance(container);
	container.on("click", ".classify_item", function(){
		var classifyId = $(this).attr("classify");
	});
}

function initPromotion(container) {
	container = $(container);
	resetPromotion(container);
}

function resetEntrance(container) {
	container.html("");
	var callback = function(data) {
		var countForRow = 4;
		var row;
		for(var i=0; i<data.length; i++){
			if(i % countForRow == 0) {
				row = $("<ul></ul>");
				container.append(row);
			}
			var itemData = data[i];
			var item = $("<li class='classify_item' classify='"+itemData.classifyId+"' ></li>");
			item.append("<img src='"+itemData.image+"' />");
			item.append("<h3>"+itemData.title+"</h3>");
			row.append(item);
		}
	}
	$.post("./getClassify.do", callback);
}

function resetPromotion(container){
	container.html("");
	container.on("click", ".promotion-goods-item", function(){
		location.href="./product.do?goodsId="+$(this).attr("goods");
	});
	var callback = function(data){
		initTimer(container, new Date(data.time).getTime());

		var list = data.list;
		var countForRow = 3;
		var row;
		var rowWidth;
		for(var i=0; i<list.length; i++){
			if(i % countForRow == 0) {
				row = $("<div class=\"promotion-showcase\"></div>");
				container.append(row);
				rowWidth = row.width();
			}
			row.append(promotionGood(list[i],i<list.length-1));
		}
	}
	$.post("./getDiscountGoods.do", callback);
}

function promotionGood(data,border){
	var item = $("<div class='promotion-goods-item' goods='"+data.goodsId+"' ></div>");
	item.append("<div class=\"showcase-dividing\" "+(!border?"style=\"border:0;\"":"")+"><img src=\""+data.coverImg+"\"\" /></div>");
	item.append("<p class=\"price-now\">￥"+data.discount+"</p>");
	item.append("<p class=\"price-before\">￥"+data.price+"</p>");
	return item;
}

function initTimer(container, end){
	var clockHour = $("<span class=\"timmer-clock clock-hour\">0</span>");
	var clockMinute = $("<span class=\"timmer-clock clock-minute\">0</span>");
	var clockSecond = $("<span class=\"timmer-clock clock-second\">0</span>");
	var timer = $("<div class=\"timmer\"></div>");
	timer.append($("<img src=\"./images/free.png\" />"));
	timer.append($("<h3>优惠活动</h3>"));
	timer.append(clockHour);
	timer.append(" : ");
	timer.append(clockMinute);
	timer.append(" : ");
	timer.append(clockSecond);
	container.append(timer);

	clearInterval(timerInterval);
	var timerInterval = setInterval(function(){
		var begin = new Date().getTime();
		var diff = end-begin;
		if(diff>0) {
			var timediff = getTimeDiff(diff);
			if(timediff.hour<10)
				clockHour.html("0"+timediff.hour);
			else 
				clockHour.html(timediff.hour);
			if(timediff.minute<10)
				clockMinute.html("0"+timediff.minute);
			else
				clockMinute.html(timediff.minute);
			if(timediff.second<10)
				clockSecond.html("0"+timediff.second);
			else
				clockSecond.html(timediff.second);
		}else{
			clockHour.html(00);
			clockMinute.html(00);
			clockSecond.html(00);
			clearInterval(timerInterval);
		}
	}, 1000);
}

function getTimeDiff(diff) {
	diff = Math.round(diff/1000);
	var days=Math.floor(diff/(24*3600));
	var leave1=diff%(24*3600);
	var hours=Math.floor(leave1/(3600));
	var leave2=leave1%(3600);
	var minutes=Math.floor(leave2/(60));
	var seconds=leave2%(60);
	return {
		hour: days*24+hours,
		minute: minutes,
		second: seconds
	}
}


var CommonBar = function(container, uri, chsName, engName, moreHref) {
	
	container = $(container);
	container.html("");
	
	var bar = $("<div class=\"title-bar\"></div>");
	var title = $("<div></div>");
	title.append("<h3>"+chsName+"</h3>");
	title.append("<p class=\"fg-light-gray font-small\">"+engName+"</p>");
	bar.append(title);
	bar.append("<hr class=\"horizental\">");
	var more = $("<div class=\"load-more\"> 更多 <span><img src=\"images/more@2x.png\" /></span></div>");
	bar.append(more);
	more.on("click", function(){location.href=moreHref;});
	container.append(bar);

	var content = $("<div class=\"card-content\"></div>");
	container.append(content);
	content.on("click", ".goods", function(){
		location.href="./product.do?goodsId="+$(this).attr("goods");
	});
	
	function commonGoods(data) {
		var goods = $("<div class=\"goods\" goods=\""+data.goodsId+"\"></div>");
		goods.append("<div class=\"card-image\"><img class=\"goods-pic\" src=\""+data.coverImg+"\" /></div>");
		var card = $("<div class=\"card-info\"></div>");
		goods.append(card);
		card.append("<h4>"+data.title+"</h4>");
		card.append("<p><span class=\"price-now\">￥"+data.discount+"</span>&nbsp;&nbsp;<span class=\"price-before\">￥"+data.price+"</span></p>");
		var star = $("<p class=\"star-info\"></p>");
		card.append(star);
		var count = 0;
		for(var i=0; i<data.rate; i++) {
			star.append("<img src=\"images/star_medium_select.png\" />");
			count++;
		}
		return goods;
	}
	
	var nextPage = 1;
	function addCommonBody() {
		var callback = function(data) {
			for(var i=0; i<data.length; i++)
				content.append(commonGoods(data[i]));
			nextPage++;
		}
		$.post(uri, {page: nextPage}, callback);
	}
	
	addCommonBody();
	
	return {
		nextPage: function(){
			addCommonBody();
		}
	}
}