function initHelper(){
	constans.helpPanel = $('<div id="mask"></div>')
					.css({display:'none',width:constans.width,height:constans.height});
	$(document.body).append(constans.helpPanel);
	constans.helpPanel.click(function(){
		constans.helpPanel.hide();
		constans.helper.hide();
	}).bind('keyup',hideHelper);
	constans.helper = $('<div id="helper"></div>').appendTo(document.body);
	constans.helper.mouseout(changeTabStyle);
}
function changeTabStyle(id){
	constans.helpPanel.html('');
	if(id&&parseInt(id)>0){
		$helpTip = $('<div></div>').css({width:constans.width,height:constans.height,background:'url("'+webRoot+'protal/images/helps/'+id+'.png") no-repeat'})
		constans.helpPanel.append($helpTip);
	}
	constans.helper.find('.helperTask').css({border:'4px solid #d3d3d3'}).eq(constans.helper.openIndex).css({border:'4px solid #fff'});
}
function showHelper(e){
	if(constans.helperTimer)
		return;
	if(e.keyCode==16){
		if(!constans.helpPanel){
			initHelper();
		}
		constans.helperTimer = setTimeout(function(){
			constans.helper.html('');
			var width = 0;
			$('iframe').each(function(i,item){
				var index = findTabIndex($(item).attr('id'));
				if($(this).css('display')=='inline'){
					constans.helper.openId = $(item).attr('id');
					constans.helper.openIndex = i;
				}
				if(taskMenus[index-constans.pageSize]){
					var task = taskMenus[index-constans.pageSize];
					var $taskDom = $('<div class="helperTask"></div>').css({width:'81px',height:'81px','background-image':'url("'+webRoot+task.icon+'")'});
					$taskDom.data('task',task);
					if(task.id==constans.helper.openId){
						$taskDom.css({border:'4px solid #fff'});
					}
					constans.helper.append($taskDom);
					width +=99;
				}
			});
			constans.helpPanel.show();
			constans.helper.find('.helperTask').hover(function(){
				constans.helper.find('.helperTask').css({border:'4px solid #d3d3d3'});
				$(this).css({border:'4px solid #fff'});
				showTab($(this).data('task').id,$(this).data('task'));
			},function(){
				$(this).css({border:'4px solid #d3d3d3'});
			}).click(function(e){
				showTab($(this).data('task').id,$(this).data('task'));
				hideHelper(e);
			});
			
			constans.helper.css({width:width,left:(constans.width-width)/2,top:constans.height/2-50}).show();
		},200);
	}
}
function hideHelper(e){
	clearTimeout(constans.helperTimer);
	constans.helperTimer = null;
	constans.helpPanel.hide();
	constans.helper.hide();
	if(constans.helper.openId){
//		showTab(constans.helper.openId);
	}
}
function hideDock(e){
	if(e.clientY<constans.height-150||
		e.clientX<constans.width/2-$('.dock-container').width()/2-100||
		e.clientX>constans.width/2+$('.dock-container').width()/2+100) return;
	if(constans.dockIsHide) return;
	constans.dockIsHide = true;
	$('#jqDock').css({bottom:'-500px'});
	var clones = $('.dockItem>img').clone();
	clones.appendTo(document.body);
	clones.each(function(index,item){
		$(item).css({position:'absolute',left:constans.width/2+$('#dockContainer').width()/2-46*(clones.length-index),bottom:'14px',width:'40px',height:'40px'});
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
	
}
function showDock(){
	if(!constans.dockIsHide)
		return;
	constans.$collectSet.unbind('mouseenter');
	constans.dockIsHide = false;
	var clones = $('.dockItem>img').clone();
	clones.appendTo(document.body);
	clones.css({position:'absolute',left:constans.width/2-22,bottom:'14px',width:'46px',height:'46px'});
	clones.each(function(index,item){
		$(item).animate({left:constans.width/2+$('#dockContainer').width()/2-46*(clones.length-index),bottom:'14px'},function(){
			clones.remove();
			$('#jqDock').css({bottom:'0px'});
			constans.$collectSet.hide();
		});
	});
}
function unbindEvent(){
	$(document.body).unbind('mousewheel', mousewheel);
	$('.dockItem').unbind('click',itemClick);
	$('.dockItem').unbind('contextmenu');
}

function bindEvent(){
	$(document.body).bind('mousewheel', mousewheel); 
	$('.dockItem').bind('click',itemClick);
	$('.dockItem').bind('contextmenu',contextmenuClick);
}
function contextmenuClick(e){
	var index = $('.dockItem').index(this);
	constans.$contextment.css({top:e.clientY-constans.$contextment.height()-5,left:e.clientX-10}).show();
	constans.contextIndex = index;
	e.stopPropagation();
	$('#jqDock').jqDock('freeze');
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
	$('.menu-task').eq(constans.pageNo-1).addClass('menu-task-selected');
}
function mousewheel(event, delta) {
	if(event.target&&event.target.tagName&&event.target.tagName.toUpperCase()=='IFRAME'){
		return;
	}
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
function showTab(id,task){
	if(!constans.helper){
		initHelper();
	}
	constans.helper.openId = id;
	$('iframe').each(function(i,item){
		if($(item).attr('id')==id){
			constans.helper.openIndex = i;
		}
	});
	changeTabStyle(id);
	constans.mainPanel.find('iframe').hide();
	constans.mainPanel.find('#'+id).show();
}
function openUrl(id,url){
	$('.box').css({top:'1500px'});
	var index = findTabIndex(id);
	if(index!=-1){
		$('.menu-task').eq(index).addClass('menu-task-selected');
	}
	if(!constans.mainPanel){
		constans.mainPanel = $('<div class="mainPanel"></div>').css({
			height:constans.height-60,
			width:constans.width-20
		});
		constans.pageLoading = $('<div class="pageLoading"><div class="loadingTips">长按shift切换任务</div></div>').css({top:constans.height/2-100,left:constans.width/2-70});
		constans.mainPanel.append(constans.pageLoading);
		constans.mainPanel.appendTo(document.body);
	}
	constans.mainPanel.show();
	var offset = constans.mainPanel.offset();
	if(constans.mainPanel.find('#'+id).length==0){
		constans.mainPanel.find('.pageLoading').show();
		var $iframe = $('<iframe class="main_frame"  id="'+id+'" ></iframe>');
		constans.mainPanel.append($iframe);
		$iframe.attr('src',webRoot+taskMenus[index-constans.pageSize].url);
		$iframe.load(function(){
			$(this).css({width: '100%',height: '100%'});
			constans.pageLoading.hide();
			$(this.contentWindow.document.body).bind('keydown',showHelper).bind('keyup',hideHelper);
		});
//		$.get(webRoot+url,{},function(data){
//			var $iframe = $('<iframe class="main_frame"  id="'+id+'" ></iframe>').css({display:'none'});
//			constans.mainPanel.append($iframe);
//			var doc = $iframe.get(0).contentWindow.document;
//			setTimeout(function(){
//				doc.open();
//				doc.write(data);
//				doc.close();
//			},500);
//			constans.mainPanel.find('iframe').load(function(){
//				$(this).show();
//				$(this.contentWindow.document.body).bind('keydown',showHelper).bind('keyup',hideHelper);
//			});
//		});
		
	}
	showTab(id);
}
function itemClick(e){
	var index = $('.dockItem').index(this);
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
//	$( ".dock-item" ).eq(index).before( $('<a class="dock-item" ><span class="textcontext"><span>'+item.title+'</span></span><img src="'+webRoot+item.icon+'" alt="home" /><div class="menu-task'+(index==0?' menu-task-selected':'')+'"></div></a> ') );
	$('#dockContainer ul li').eq(index).before('<li><a class="dockItem"><img src="'+webRoot+item.icon+'" alt="history" title="'+item.title+'" /></a></li>');
//	$.jqdock.init($('#dockContainer>li'))
//	var conv = $.jqdock.convert($('<ul><li><a class="dockItem" href="#"><img src="'+webRoot+item.icon+'" alt="history" title="'+item.title+'" /></a></li></ul>'));
//	console.info(conv);
	
//	$(conv).jqDock(constans.jqDockOpts);

//	var $one = $('.jqDockItem').eq(0);
//	$('.jqDock').append($one.clone(true));
//	$('#jqDock').jqDock('destroy');
//	$('#jqDock').jqDock(constans.jqDockOpts);
	unbindEvent();
	bindEvent();
}

$(function(){
	constans.$collectSet = $('<img class="collect" src="'+webRoot+'protal/images/collect.png" />').appendTo(document.body);
	constans.$collectSet.bind('mouseout',function(){
		if(!constans.dockIsHide) return;
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
	}},{title:'<input type="checkbox" style="padding:0;margin-left:-15px" />隐藏任务栏',handler:function(index){
		hideDock({clientX:constans.width/2,clientY:constans.height-100});
	}}];
	var chartMenus = '';
	for(var i=0;i<menus.length;i++){
		chartMenus+='<li><a class="dockItem"><img src="'+webRoot+menus[i].icon+'" alt="history" title="'+menus[i].title+'" /></a></li>';
	}
	for(var i=0;i<taskMenus.length;i++){
		chartMenus+='<li><a class="dockItem"><img src="'+webRoot+taskMenus[i].icon+'" alt="history" title="'+taskMenus[i].title+'" /></a></li>';
	}
	var $dockContainer = $('<div id="dockContainer">'+
			'<ul id="jqDock">'+
				chartMenus+
			'</ul> '+
	     '</div>');
     $(document.body).append($dockContainer);
//   $('#dock').initMacDock({
//		maxWidth: 40,
//		items: 'a',
//		itemsText: '.textcontext',
//		container: '.dock-container',
//		itemWidth: 40,
//		proximity: 90,
//		alignment : 'left',
//		valign: 'bottom',
//		halign : 'center'
//	});
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
			$('#jqDock').jqDock('idle').jqDock('nudge');
		});
	}).contextmenu(function(e){
		e.stopPropagation();
		return false;
	});
	$level1.children().click(function(e){
		console.info(e.target.className)
		if(e.target.className=='toolbar_level1_item'){
			rights[$level1.children().index(this)].handler.call(this,constans.contextIndex);
			closeItem();//关闭当前页
		}
	});
	constans.$contextment = $level1;
	
	constans.jqDockOpts = {align: 'bottom',step:13, duration: 150, labels: 'tc', size: 46,sizeMax:90, distance: 90,inDock:0,onReady:function(){
		$('.jqDock').css({height:'50px',background: 'url("'+webRoot+'protal/images/toolbar-middle.png")'})
			.append('<div class="dock-left"></div>')
			.append('<div class="dock-right"></div>');
		$('#dockContainer').css({left:constans.width/2-$('#dockContainer').width()/2}).show();
		$('.dockItem').append('<div class="menu-task"></div>');
		$('.menu-task').eq(0).addClass('menu-task-selected');
	}};
	
	$(document.body).bind('keydown',showHelper).bind('keyup',hideHelper);
})