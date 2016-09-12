/**
 * jquery 滑动类
 * 
 * @吕衍辉  qq:569934930 
 */
function unbindEvent(){
	$(document.body).unbind('mousewheel', mousewheel); 
	$('.menu-item').unbind('click',itemClick);
}

function bindEvent(){
	$(document.body).bind('mousewheel', mousewheel); 
	$('.menu-item').bind('click',itemClick);
}

function changePage(direction){
	unbindEvent();
	$('.box').stop().each(function(index,box){
		$(box).stop().animate({top:$(box).offset().top+direction*constans.height}, '800',function(){
			if(index==0){
				bindEvent();
			}
		});
	});
	$('.menu-item').removeClass('menu-item-selected');
	$('.menu-item').eq(constans.pageNo-1).addClass('menu-item-selected');
}
function mousewheel(event, delta) {
	unbindEvent();
	constans.pageNo+=(delta>0?-1:1);
	if(constans.pageNo<=0){
		constans.pageNo=1;
		bindEvent();
		return true;
	}
	if(constans.pageNo>constans.pageSize){
		constans.pageNo=constans.pageSize;
		bindEvent();
		return true;
	}
	changePage((delta>0?1:-1));
	return true;  
}
function itemClick(e){
	var index = $('.menu-item').index(this);
	var direction = constans.pageNo-index-1;
	if(direction==0) return true;
	constans.pageNo=index+1;
	unbindEvent();
	for(var i=0;i<Math.abs(direction);i++)
		changePage(direction);
	return true;
}

$(function(){
	//初始化常量参数
	constans.width = $(document.body).width();
	constans.height = $(document.body).height();
	
	var menus = [{id:1,title:'OCS',charts:[{
		title:'OCS系统实时监控',
		type:'heartChart',
		autoflush:true
	},{
		title:'业务变化量',
		type:'businessChart'
	},{
		title:'最近10天返回码统计',
		type:'returnCodeChart'
	},{
		title:'消息时延',
		type:'delayChart'
	}]},{id:2,title:'ABM',charts:[{
		title:'ABM系统实时监控',
		type:'heartChart',
		autoflush:true
	},{
		title:'业务变化量',
		type:'businessChart'
	},{
		title:'最近10天返回码统计',
		type:'returnCodeChart'
	},{
		title:'消息时延',
		type:'delayChart'
	}]},{id:3,title:'HB',charts:[{
		title:'HB系统实时监控',
		type:'heartChart',
		autoflush:true
	},{
		title:'业务变化量',
		type:'businessChart'
	},{
		title:'最近10天返回码统计',
		type:'returnCodeChart'
	},{
		title:'消息时延',
		type:'delayChart'
	}]}];
	//菜单
	
	$menu = $('<div class="menu"></div>').css({
		height:constans.height-20
	});
	$(document.body).append($menu);
	constans.pageSize = menus.length;
	for(var i=0;i<menus.length;i++){
		$menuItem = $('<div class="menu-item'+((i==0)?' menu-item-selected':'')+'">'+menus[i].title+'</div>');
		$menuItem.appendTo($('.menu'));
	}
	for(var i=0;i<constans.pageSize;i++){
		var $box = $('<div class="box"></div>').css({
			height:constans.height-20,
			width:constans.width-$menu.width()-30,
			top:(constans.height)*i+10
		});
		$(document.body).append($box);
		
		for(var j=0;j<4;j++){
			var $chart = $('<div class="chart"></div>').css({
				height:(constans.height-40)/2,
				width:(constans.width-$menu.width()-50)/2
			});
			$box.append($chart);
		}
	}
	bindEvent();
	chartInit(menus);//初始化图表
});