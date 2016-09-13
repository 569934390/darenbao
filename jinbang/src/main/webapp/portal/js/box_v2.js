/**
 * jquery 滑动类
 * 
 * @吕衍辉  qq:569934930 
 */
function unbindEvent(){
	$(document.body).unbind('mousewheel', mousewheel); 
	$('.dock-item').unbind('click',itemClick);
}

function bindEvent(){
	$(document.body).bind('mousewheel', mousewheel); 
	$('.dock-item').bind('click',itemClick);
}

function changePage(direction){
	unbindEvent();
	$('.box').stop().each(function(index,box){
		$(box).stop().animate({left:$(box).offset().left+direction*constans.width}, '800',function(){
			if(index==0){
				bindEvent();
			}
		});
	});
	$('.menu-task:lt(3)').removeClass('menu-task-selected');
	$('.menu-task').eq(constans.pageNo-1).addClass('menu-task-selected').css({opacity:0.8});
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
	var index = $('.dock-item').index(this);
	if(index>=menus.length){
		$('.box').css({top:'1500px'});
		$('.menu-task').eq(index).addClass('menu-task-selected').css({opacity:0.8});
		if(window.top.window.Ext){
			window.top.window.Ext.mainPanel.loadTab(taskMenus[index-menus.length]);
		}
		else{
			if(!constans.mainPanel){
				constans.mainPanel = $('<div class="mainPanel"></div>').css({
					height:constans.height-60,
					width:constans.width-20
				});
				constans.mainPanel.appendTo(document.body);
			}
			constans.mainPanel.show();
			constans.mainPanel.children().hide();
			if(constans.mainPanel.find('#'+taskMenus[index-menus.length].id).length>0){
				constans.mainPanel.find('#'+taskMenus[index-menus.length].id).show();
			}
			else{
				constans.mainPanel.append('<iframe class="main_frame"  id="'+taskMenus[index-menus.length].id+'" src="'+webRoot+taskMenus[index-menus.length].url+'"></iframe>');
			}
		}
		return;
	}
	if(constans.mainPanel)
		constans.mainPanel.hide();
	$('.box').css({top:'30px'});
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
	
//	//菜单
//	var toolbarWidth = 45*4+25;
//	$menu = $('<div class="menu"></div>').css({
//		top:constans.height-44,
//		left:constans.width/2-25-toolbarWidth/2
//	});
//	$(document.body).append($menu);
	constans.pageSize = menus.length;
//	$('<div class="menu-left"></div>').appendTo($('.menu'));
//	$menuMiddle = $('<div class="menu-middle"></div>').css({width:toolbarWidth+'px',height:'43px'});
//	$menuMiddle.appendTo($('.menu'));
//	for(var i=0;i<menus.length;i++){
//		var $menuItem = $('<div class="menu-item'+((i==0)?' menu-item-selected':'')+'">'+menus[i].title+'</div>');//<img class="heart'+((i==0)?' heart-selected':'')+'" src="'+webRoot+menus[i].icon+'"/>'
//		$menuItem.appendTo($menuMiddle);
//	}
//	var $settingItem = $('<div class="menu-item">设置</div>');//<img class="heart" src="'+webRoot+'protal/images/setting.png"/>
//		$settingItem.appendTo($menuMiddle);
//	$('<div class="menu-right"></div>').appendTo($('.menu'));
	for(var i=0;i<constans.pageSize;i++){
		var $box = $('<div class="box"></div>').css({
			height:constans.height-60,
			width:constans.width-20,
			left:(constans.width)*i+10
		});
		$(document.body).append($box);
		
		for(var j=0;j<4;j++){
			var $chart = $('<div class="chart"></div>').css({
				height:(constans.height-80)/2,
				width:(constans.width-40)/2
			});
			$box.append($chart);
		}
	}
	
	var chartMenus = '';
	for(var i=0;i<menus.length;i++){
		chartMenus+='<a class="dock-item" ><span class="textcontext"><span>'+menus[i].title+'</span></span><img src="'+webRoot+menus[i].icon+'" alt="home" /><div class="menu-task'+(i==0?' menu-task-selected':'')+'"></div></a> ';
	}
	for(var i=0;i<taskMenus.length;i++){
		chartMenus+='<a class="dock-item" ><span class="textcontext"><span>'+taskMenus[i].title+'</span></span><img src="'+webRoot+taskMenus[i].icon+'" alt="home" /><div class=menu-task></div></a> ';
	}
	$('<div class="dock" id="dock">'+
         '<div class="dock-container">'+
         	   '<div class="dock-left"></div>'+
	           chartMenus+
         	   '<div class="dock-right"></div>'+
	           '</div> '+
         '</div>').appendTo($(document.body));
   $('#dock').initMacDock({
		maxWidth: 40,
		items: 'a',
		itemsText: '.textcontext',
		container: '.dock-container',
		itemWidth: 40,
		proximity: 90,
		alignment : 'left',
		valign: 'bottom',
		halign : 'center'
	});
	$('#dock').each(function(){
		console.info(this.config);
	})
//	taskMenus.push({id:'performance',title:'性能测试',url:'performance/performance.jsp',icon:'protal/images/performance.png'});
//	$('.dock-container').append($('<a class="dock-item" ><span class="textcontext"><span>asdfsssssssssdsaf</span></span><img src="'+webRoot+'protal/images/performance.png" alt="home" /><div class=menu-task></div></a> '));
//	$('#dock').lyhMacDock({
//		maxWidth: 50,
//		items: 'a',
//		itemsText: '.textcontext',
//		container: '.dock-container',
//		itemWidth: 40,
//		proximity: 90,
//		alignment : 'left',
//		valign: 'bottom',
//		halign : 'center'
//	});
	$(document.body).mouseout(function(e){
		if(constans.height-e.clientY<30)
			$('#dock').css({bottom:'44px'});
	}).click(function(e){
		if(e.target.tagName&&e.target.tagName.toLowerCase().indexOf('canvas')!=-1){
			$('#dock').css({bottom:'-500px'});
		}
		else{
			$('#dock').css({bottom:'44px'});
		}
		e.stopPropagation();
	});
	bindEvent();
	chartInit(menus);//初始化图表
	function closeItem(){
		$level1.hide();
	}
	var $level1 = $('<div class="toolbar_level1"></div>');
	for(var i=0;i<rights.length;i++){
		$level1.append($('<div class="toolbar_level1_item">'+rights[i].title+'</div>'));
	}
	$level1.css({'z-index':99999,display:'none'}).appendTo($(document.body));
	var hideContextmenu;
	$('.toolbar_level1,.toolbar_level1_item').hover(function(){
		if(hideContextmenu){
			clearTimeout(hideContextmenu);
		}
	},function(){
		hideContextmenu = setTimeout(function(){
			$level1.hide();
		});
	});
	$level1.children().click(function(){
		alert(constans.contextIndex+','+$level1.children().index(this));
		closeItem();//关闭当前页
	});
	$('.dock-item').contextmenu(function(e){
		var index = $('.dock-item').index(this);
		$level1.css({top:e.clientY-$level1.height()-5,left:e.clientX-10}).show();
		constans.contextIndex = index;
		e.stopPropagation();
		return false;
	});
	function nowTime(){
		var time = new Date();  
		$('#staffInfo').text(session.user.realName+'('+session.user.name+'),欢迎登陆! '+time.format("MM月dd日 hh:mm"));
	}
	nowTime();
	setInterval(nowTime,60000);
	$('#logout').click(function(){
		$.post(ctx + '/login/loginOut.do',{},function(){
			window.top.location.href=ctx+'/main/login.jsp';
		});
	});
//	var $heart = $('.heart'),clearTimer;
//	setTimeout(function(){
//		var heartWidth = $heart.width();
//		$(document).bind('mousemove',function(e){
//			if(constans.height-e.clientY>140){
//				$heart.css({'margin-top':'-25px','margin-left':'0px',width:'40px',height:'40px'});
//				return;
//			}
//			if(clearTimer){
//				clearTimeout(clearTimer);
//			}
//			var y = Math.pow(constans.height-e.clientY-heartWidth/2, 2);
//			$heart.each(function(index,node){
//				var nodeOffset = $(node).offset();
//				var x = Math.pow(nodeOffset.left-e.clientX+heartWidth/2, 2);
//				var r = Math.sqrt(x+y);
//				if(r>3*heartWidth+10){
////					$(node).css({'margin-top':'-25px','margin-left':'0px',width:'40px',height:'40px'});
//					return true;
//				}
//				console.info(index,r);
//				r = heartWidth-r/3;
//				if(r>heartWidth+10)
//					r = 0;
//				$(node).css({'margin-top':(-25-r)+'px','margin-left':(-10+((nodeOffset.left-e.clientX+heartWidth/2)>0?-1:1)*r/10)+'px',width:(heartWidth+r)+'px',height:(heartWidth+r)+'px'});
//			});
//			return;
////			var hoverIndex = $heart.index(this);
////			var width = $(this).width(),offset;
////			var offsetX = e.offsetX<width/2?e.offsetX:width-e.offsetX;
////			offset = offsetX;
////			$heart.each(function(index,node){
////				if(index<hoverIndex-2||index>hoverIndex+2){
////					$(node).css({'margin-top':'-25px','margin-left':'0px',width:'40px',height:'40px'});
////				}
////				else if(hoverIndex==index){
////					$(node).css({'margin-top':'-55px','margin-left':'-10px',width:'60px',height:'60px'});
////				}
////				else{
////					var sub = Math.abs(hoverIndex-index),raw=50;
////					console.info(offset/sub);
////					if(hoverIndex>=index&&e.offsetX<=width/2||hoverIndex<=index&&e.offsetX>=width/2){
////						raw = 60-offset;
////					}
////					else if(hoverIndex>index&&e.offsetX>width/2||hoverIndex<index&&e.offsetX>width/2){
////						raw = 40+offset;
////					}
////					$(node).css({'margin-top':(-25-offset/sub)+'px','margin-left':'-10px',width:raw/sub+'px',height:raw/sub+'px'});
////				}
////			});
////			var width = $(this).width(),offset;
////			var offsetX = e.offsetX<width/2?e.offsetX:width-e.offsetX,offsetY= e.offsetY;
////			offset = (offsetX+offsetY)/2;
////			if(hoverIndex>0)
////				$heart.eq(hoverIndex-1).css({'margin-top':'-35px','margin-left':'-5px',width:(40+offset/2)+'px',height:(40+offset/2)+'px'});
////			$(this).stop().css({'margin-top':'-55px','margin-left':'-10px',width:(40+offset)+'px',height:(40+offset)+'px'});
////			if(hoverIndex<$heart.length-1)
////				$heart.eq(hoverIndex+1).css({'margin-top':'-35px','margin-left':'5px',width:(40+offset/2)+'px',height:(40+offset/2)+'px'});
//		});
//		$heart.bind('mouseleave',function(){
//			clearTimer = setTimeout(function(){
//				$heart.css({'margin-top':'-25px','margin-left':'0px',width:'40px',height:'40px'});
//			},100);
//		});
//	},500);
});