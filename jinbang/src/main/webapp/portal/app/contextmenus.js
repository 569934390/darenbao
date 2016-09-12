define(['app/dock','app/tabpanel','app/style'],function (dock,tab,sb) {
	var task,renderItems,items = [
	{text:'新窗口打开',handler:function(e){
		window.open(webRoot+task.url);
	}},{text:'刷新',handler:function(e){
		$('iframe').each(function(i,item){
			if($(item).attr('id')==task.id){
				$(item).attr('src',webRoot+task.url);
				return false;
			}
		});
	}},{text:'关闭',handler:function(e){
		$('iframe').each(function(i,item){
			if($(item).attr('id')==task.id){
				dock.closeItem(task.id);
				$(item).attr('src','');
				$(item).remove();
				return false;
			}
		});
	}},{text:'隐藏任务栏',handler:function(){
		dock.hide();
	}}];
	var w=170,h;
	function menu(width,height){
		var menu = $('<div class="stackback-c"></div>').css({width:width,height:height});
		var itemc = $('<div class="stackback-items"></div>').css({width:width+40,height:height+40}).appendTo(menu);
		for(var i = 0;i<renderItems.length;i++){
			if(renderItems[i].text)
				$('<div class="stackback-item">'+renderItems[i].text+'</div>').css({width:width-20}).hover(function(){
			$(this).css({background: 'url("'+webRoot+'portal/images/level1-bg.png")'});
				},function(){
					$(this).css({background: 'transparent'});
				}).appendTo(itemc);
		}
		return menu;
	}
	function renderMenu(dom,task){
		if(!dock.itemStatus(task.id)){
			renderItems = [{text:'打开',handler:function(e){
				tab.openUrl(task.id,task.url,function(){});
			}},items[0],items[items.length-1]];
		}
		else{
			renderItems = items.concat();
		}
		if(task.id=='charts'){
			renderItems.unshift({text:'OCS',handler:function(){}});
			renderItems.unshift({text:'ABM',handler:function(){}});
		}
		if(task.mores!=null&&task.mores.length>0){
			for(var i=0;i<task.mores.length;i++){
				(function(){
					var id = task.mores[i].id,url = task.mores[i].url;
					renderItems.unshift({
						text:task.mores[i].title,
						handler:function(){
							console.info(id,url);
							if(url)
								tab.openUrl(id,url,function(){});
						}
					});				
				})();
			}
		}
		h=renderItems.length*22+70,w=170;
		for(var i = 0;i<renderItems.length;i++){
			if(renderItems[i].text.length*14>w-100) w = renderItems[i].text.length*14+100;
		}
		var offset = $(dom.target).offset();
		var top = offset.top-h+10;
		var template = '<div class="stackback{0}"></div>';
		$(template.format('')).css({
			width:w,
			height:h
		}).contextmenu(function(e){
			e.stopPropagation();
			return false;
		})
		.append(template.format('-tl'))
		.append($(template.format('-tm')).css({width:w-80}))
		.append(template.format('-tr'))
		.append($(template.format('-l')).css({height:h-80}))
		.append(menu(w-80,h-80))
		.append($(template.format('-r')).css({height:h-80}))
		.append(template.format('-bl'))
		.append($(template.format('-bm')).css({width:(w-124)/2}))
		.append(template.format('-bt'))
		.append($(template.format('-bm')).css({width:((w-124)/2-1)}))
		.append(template.format('-br'))
		.css({left:offset.left-(w-$(dom.target).width())/2,top:top}).appendTo(document.body);
	}
	$('#dock_container').contextmenu(function(e){
		e.stopPropagation();
		if(dock.$contextMenuBg)
			dock.$contextMenuBg.remove();
		task = $(e.target).data('item');
		$('.task-label').hide();
		$('.stackback').remove();
		renderMenu(e,task);
		dock.pause();
		dock.$contextMenuBg = $('<div class="drop-bg"></div>').css({zIndex:888}).appendTo(document.body).click(continues);
		return false;
	});
	function continues(e){
		e.stopPropagation();
		if(e.target.className=='stackback-item'){
			var index = $('.stackback-item').index(e.target);
			if(renderItems[index].handler){
				renderItems[index].handler.call(this,e);
			}
		}
		else if($(e.target).data('item')){
			return false;
		}
		dock.continues();
		$('.stackback').remove();
		if(dock.$contextMenuBg)
			dock.$contextMenuBg.remove();
		return true;
	};
	$(document.body).click(continues);
	constans.continues = continues;
	return {
		continues:continues
	}
});
