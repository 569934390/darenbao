function hideDock(e){
	console.info(e.clientY,constans.height);
	if(e.clientY<constans.height-150||
		e.clientX<constans.width/2-$('.dock-container').width()/2-100||
		e.clientX>constans.width/2+$('.dock-container').width()/2+100) return;
	if(constans.dockIsHide) return;
	constans.dockIsHide = true;
	$('#dock').css({bottom:'-500px'});
	var clones = $('.dock-item').clone();
	clones.appendTo(document.body);
	clones.each(function(index,item){
		$(item).css({left:$(item).offset().left+constans.width/2-40*clones.length/2});
	});
	clones.animate({top:constans.height-40,left:constans.width/2-20,width:'40px',height:'40px'},function(){
		clones.remove();
	});
	constans.$collectSet.css({top:constans.height-128,left:constans.width/2-64,width:'128px',height:'128px'}).stop().animate({top:constans.height-40,left:constans.width/2-20,width:'40px',height:'40px'}).animate({top:constans.height-30}).show();
	setTimeout(function(){
		constans.$collectSet.bind('mouseenter',function(e){
			$(this).stop().animate({top:constans.height-96,left:constans.width/2-48,width:'96px',height:'96px'},function(){
				showDock(e);
			});
		});
	},500);
}
function resetDock(e){
	var offset = {clientX:e.clientX,clientY:e.clientY};
	jQuery.macDock.over(offset);
}
function showDock(){
	if(!constans.dockIsHide)
		return;
	constans.$collectSet.unbind('mouseenter');
	constans.dockIsHide = false;
	var clones = $('.dock-item').clone();
	clones.appendTo(document.body);
	clones.css({top:constans.height-40,left:constans.width/2-20,width:'40px',height:'40px'});
	clones.each(function(index,item){
		$(item).animate({left:constans.width/2+100-40*index},function(){
			clones.remove();
			$('#dock').css({bottom:'44px'});
			constans.$collectSet.hide();
		});
	});
}
function unbindEvent(){
	$(document.body).unbind('mousewheel', mousewheel); 
	$('.dock-item').unbind('click',itemClick);
	$('.dock-item').unbind('contextmenu');
}

function bindEvent(){
	$(document.body).bind('mousewheel', mousewheel); 
	$('.dock-item').bind('click',itemClick);
	$('.dock-item').bind('contextmenu',contextmenuClick);
}
function contextmenuClick(e){
	jQuery(document).unbind('mousemove');
	var index = $('.dock-item').index(this);
	constans.$contextment.css({top:e.clientY-constans.$contextment.height()-5,left:e.clientX-10}).show();
	constans.contextIndex = index;
	var offset = {clientX:e.clientX,clientY:e.clientY};
	jQuery.macDock.over(offset);
	e.stopPropagation();
	return false;
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
	$('.menu-task:lt('+constans.pageSize+')').removeClass('menu-task-selected');
	$('.menu-task').eq(constans.pageNo-1).addClass('menu-task-selected').css({opacity:0.8});
}
function mousewheel(event, delta) {
	console.info(event.target.tagName.toUpperCase())
	if(event.target&&event.target.tagName&&event.target.tagName.toUpperCase()=='IFRAME')
		return;
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
function findTabIndex(id){
	for(var i=0;i<taskMenus.length;i++){
		if(taskMenus[i].id==id){
			return i+constans.pageSize;
		}
	}
	return -1;
}
function openUrl(id,url){
	$('.box').css({top:'1500px'});
	var index = findTabIndex(id);
	if(index!=-1){
		$('.menu-task').eq(index).addClass('menu-task-selected').css({opacity:0.8});
	}
	if(!constans.mainPanel){
		constans.mainPanel = $('<div class="mainPanel"></div>').css({
			height:constans.height-60,
			width:constans.width-20
		});
		constans.mainPanel.appendTo(document.body);
	}
	constans.mainPanel.show();
	constans.mainPanel.children().hide();
	var offset = constans.mainPanel.offset();
	if(constans.mainPanel.find('#'+id).length>0){
		constans.mainPanel.find('#'+id).show();
	}
	else{
		var $iframe = $('<iframe class="main_frame"  id="'+id+'" src="'+webRoot+url+'"></iframe>');
		constans.mainPanel.append($iframe);
		constans.mainPanel.find('iframe').load(function(){
			$(this.contentWindow.document.body).bind('mousemove',function(e){
				var x = e.clientX+offset.left,y = e.clientY+offset.top;
				jQuery.macDock.over({clientX:x,clientY:y});
			});
//			.bind('click',function(e){
//				var x = e.clientX+offset.left,y = e.clientY+offset.top;
//				hideDock({clientX:x,clientY:y});
//			});
		});
	}
}
function itemClick(e){
	var index = $('.dock-item').index(this);
	if(index>=menus.length){
		if(window.top.window.Ext){
			window.top.window.Ext.mainPanel.loadTab(taskMenus[index-menus.length]);
		}
		else{
			openUrl(taskMenus[index-menus.length].id,taskMenus[index-menus.length].url);
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
function insertItem(item,index){
	taskMenus.push({id:item.id,title:item.title,url:item.url,icon:item.icon});
	$( ".dock-item" ).eq(index).before( $('<a class="dock-item" ><span class="textcontext"><span>'+item.title+'</span></span><img src="'+webRoot+item.icon+'" alt="home" /><div class="menu-task'+(index==0?' menu-task-selected':'')+'"></div></a> ') );
	$('#dock').initMacDock({
		maxWidth: 50,
		items: 'a',
		itemsText: '.textcontext',
		container: '.dock-container',
		itemWidth: 40,
		proximity: 90,
		alignment : 'left',
		valign: 'bottom',
		halign : 'center'
	});
	unbindEvent();
	bindEvent();
}

$(function(){
	constans.$collectSet = $('<img class="collect" src="'+webRoot+'protal/images/collect.png" />').appendTo(document.body);
	constans.$collectSet.bind('mouseout',function(){
		$(this).stop().css({left:constans.width/2-20,width:'40px',height:'40px',top:constans.height-30});
	}).click(function(e){
		showDock(e);
	});
	var rights = [{title:'新窗口打开',handler:function(index){
		if(index>=constans.pageSize){
			window.open(webRoot+taskMenus[index-constans.pageSize].url);
		}
	}},{title:'关闭当前页',handler:function(index){
		$('iframe').each(function(i,item){
			if($(item).attr('id')==taskMenus[index-constans.pageSize].id){
				$('.menu-task').eq(index).removeClass('menu-task-selected');
				$(item).attr('src','');
				$(item).remove();
				if(i-1>=0){
					$('iframe').eq(i-1).show();
				}
				else{
					if(constans.mainPanel)
						constans.mainPanel.hide();
					$('.box').css({top:'30px'});
				}
				return false;
			}
		});
	}},{title:'刷新当前页',handler:function(index){
		if(index>=constans.pageSize){
			$('iframe').each(function(i,item){
				if($(item).attr('id')==taskMenus[index-constans.pageSize].id){
					$(item).attr('src',webRoot+taskMenus[index-constans.pageSize].url);
					return false;
				}
			});
		}
	}},{title:'隐藏任务栏',handler:function(index){
		hideDock({clientX:constans.width/2,clientY:constans.height-100});
	}}];
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
//	$(document.body).mouseout(function(e){
//		console.info(e);
//		if(e.toElement&&e.toElement.className=="toolbar_level1_item"){
//			
//		}
//		else{
//			resetDock({clientX:0,clientY:0});
//		}
//	}).animate(function(){
//		console.info(arguments);
//	},100);
//	$(document.body)
////	.mouseout(function(e){
////		if(constans.height-e.clientY<15)
////			showDock();
////	})
//	.click(function(e){
//		if(e.target.tagName&&e.target.tagName.toLowerCase().indexOf('canvas')!=-1){
//			hideDock(e);
//		}
//		else{
////			showDock();
//		}
//		e.stopPropagation();
//	});
	bindEvent();
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
			jQuery(document).bind('mousemove',  jQuery.macDock.over);
//			jQuery.macDock.over({clientX:0,clientY:0});
		});
	}).contextmenu(function(e){
		e.stopPropagation();
		return false;
	});
	$level1.children().click(function(){
		rights[$level1.children().index(this)].handler.call(this,constans.contextIndex);
		closeItem();//关闭当前页
	});
	constans.$contextment = $level1;
	
	var jqDockOpts = {align: 'bottom', duration: 200, labels: 'tc', size: 40, distance: 80};
	$('#jqDock').jqDock(jqDockOpts);
})