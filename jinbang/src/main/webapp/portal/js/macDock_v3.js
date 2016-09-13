/**
 * 原理在此贴中http://rsj217.diandian.com/post/2012-09-18/40039914176
 * @吕衍辉 
 */
Portal = {};
Portal.Task={};
Portal.Task.timers=[];
/**
 * 添加任务，UI控制任务进程，用户感知UI优先
 * @param {} task
 * @return {}
 */
Portal.Task.add=function(task){
	var timer ;
	if(task.type=='one'){
		timer = setTimeout(task.callback,task.sleep);
	}
	else{
		timer = setInterval(task.callback,task.sleep);
	}
	Portal.Task.timers.push({id:task.id,type:task.type,timer:timer,callback:task.callback,sleep:task.sleep});
	return timer;
}

/**
 * 任务定时器
 * @param {} i
 */
Portal.Task.stopTimer = function(i){
	if(!Portal.Task.timers[i])return;
	if(Portal.Task.timers[i].type=='one'){
		if(Portal.Task.timers[i].timer){
			clearTimeout(Portal.Task.timers[i].timer);
		}
	}
	else{
		if(Portal.Task.timers[i].timer){
			clearInterval(Portal.Task.timers[i].timer);
		}
	}
	Portal.Task.timers[i].timer = null;
	delete Portal.Task.timers[i];
}
/**
 * 停止任务调度
 * @param {} id
 */
Portal.Task.stop = function(id){
	
	for(var i in Portal.Task.timers){
		if(id){
			if(Portal.Task.timers[i].id==id){
				Portal.Task.stopTimer(i);
			}
		}
		else{
			Portal.Task.stopTimer(i);
		}
	}
}
/**
 * 暂停任务调度
 */
Portal.Task.pause = function(){
	console.info('pause')
	if(Portal.Task.pauses&&Portal.Task.pauses.length>0)return;
	Portal.Task.pauses = [];
	console.info(Portal.Task.timers)
	for(var i in Portal.Task.timers){
		Portal.Task.pauses.push($.extend(true, {}, Portal.Task.timers[i]));
	}
	Portal.Task.stop();
}
/**
 * 恢复任务
 * @param {} index
 */
Portal.Task.continues = function(index){
	if(!Portal.Task.pauses||Portal.Task.pauses.length==0)return;
	for(var i=0;i<Portal.Task.pauses.length;i++){
		Portal.Task.add(Portal.Task.pauses[i]);
	}
	Portal.Task.pauses = null;
}

Portal.dock={};
Portal.dock.imageCache = new Image();	//图片缓存
Portal.dock.prefx = webRoot+'portal/images/icons/'; //图片服务器前缀
/**
 * 标签显示控制
 */
Portal.dock.updateLabel = function(index){
	if($.isIE8m()){
		Portal.dock.$label.css({display:'none'});
	}
	else{
		Portal.dock.$label.fadeOut();
	}
}
/**
 * 图片缓存
 * @param {} name
 */
Portal.dock.initCache = function(name){
	Portal.dock.imageCache.src = Portal.dock.prefx+name+'.png';
	Portal.dock.imageCache.src = Portal.dock.prefx+name+'_max.png';
	Portal.dock.imageCache.src = Portal.dock.prefx+name+'_min.png';
}

Portal.dock.init = function(options){
    Portal.dock.config = {
        items:options.items,
        itemWidth: options.itemWidth,
        minimumSize:options.minimumSize||constans.height<500?30:38,
        maximumSize:options.maximumSize||constans.height<500?70:80,
        range:options.range||2
    };
    Portal.dock.iconNodes = [],
    Portal.dock.status = [],
    Portal.dock.labels = [],
    Portal.dock.iconSizes = [];
    Portal.dock.maximumWidth = 0;
    Portal.dock.$dockBar = $('<div id="dock_container"><div id="dock-left"></div><div id="dock-right"></div></div>');
	Portal.dock.$dockBar.appendTo(options.renderTo);
	var width = 0;
	Portal.dock.openInterval,Portal.dock.closeInterval,Portal.dock.closeTimeout,Portal.dock.scale=0,Portal.dock.offset = constans.height<500?0:6,index = 0,Portal.dock.nowTime=new Date().getTime();
	
	for(var i=0;i<Portal.dock.config.items.length;i++){
		Portal.dock.initCache(Portal.dock.config.items[i].icon);
		var $img = $('<img ></img>').css({width:Portal.dock.config.minimumSize,position: 'absolute','z-index':99999}).data('item',Portal.dock.config.items[i])
			.attr('src',Portal.dock.prefx +Portal.dock.config.items[i].icon+'_max.png');
		Portal.dock.$dockBar.append($img);
		width+=Portal.dock.config.minimumSize;
		Portal.dock.iconNodes.push($img);
		Portal.dock.iconSizes.push(Portal.dock.config.minimumSize);
		var $status = $('<div class="menu-task"></div>');
		Portal.dock.status.push($status);
		Portal.dock.$dockBar.append($status);
		var $label = $('<div class="task-label">'+Portal.dock.config.items[i].title+'</div>');
		if($.isIE8m()){
//			$label.css({background: 'transparent','font-size':'16px',color: '#000','font-weight':'bolder'});
		}
		else{
			$label.css({opacity:0.8});
		}
		Portal.dock.labels.push($label);
		Portal.dock.$dockBar.append($label);
		$label.widthb2=$label.width()/2;
	}
	Portal.dock.updateIconProperties();
	Portal.dock.bindEvent();
};
Portal.dock.insert=function(item,index,fn,scope){
	var $img,append=false;
	if(!index&&index!=0){
		index = Portal.dock.config.items.length-1;
		append = true;
	}
	ArrayUtils.insertAt(Portal.dock.config.items,index,item);
	
	Portal.dock.initCache(item.icon);
	$img = $('<img ></img>').css({width:Portal.dock.config.minimumSize,position: 'absolute','z-index':99999}).data('item',item)
		.attr('src',Portal.dock.prefx+item.icon+'_max.png');
	Portal.dock.$dockBar.find('img').eq(index).before($img);
	ArrayUtils.insertAt(Portal.dock.iconNodes,index,$img);
	ArrayUtils.insertAt(Portal.dock.iconSizes,index,Portal.dock.config.minimumSize);
	var $status = $('<div class="menu-task menu-task-selected"></div>');
	ArrayUtils.insertAt(Portal.dock.status,index,$status);
	Portal.dock.$dockBar.append($status);
	var $label = $('<div class="task-label">'+item.title+'</div>');
	if($.isIE8m()){
//		$label.css({background: 'transparent',color: '#146397','font-weight':'bolder'});
	}
	else{
		$label.css({opacity:0.8});
	}
	ArrayUtils.insertAt(Portal.dock.labels,index,$label);
	Portal.dock.$dockBar.append($label);
	$label.widthb2=$label.width()/2;
	Portal.dock.updateIconProperties();
	if(append){
		Portal.dock.unbindEvent();
		$img.attr('src',Portal.dock.prefx+item.icon+'.png')
			.animate({top:'-=15'},function(){
				if(fn)
					fn.call(scope||this);
			}).animate({top:'+=15'},function(){
				Portal.dock.bindEvent();
				$img.attr('src',Portal.dock.prefx+item.icon+'_min.png');
			});
	}
	else{
		Portal.dock.unbindEvent().bindEvent();
	}
	return $img;
}
Portal.dock.unbindEvent = function(){
	Portal.dock.processMouseOut();
	Portal.dock.$img.unbind('mousemove').unbind('mouseout').unbind('click');
	return Portal.dock;
}
Portal.dock.bindEvent = function(){
	Portal.dock.$label = $('.task-label');
	Portal.dock.$img = Portal.dock.$dockBar.find('img');
	Portal.dock.$dockBar.find('img')
		.bind('mousemove',Portal.dock.processMouseMove)
		.bind('mouseout', Portal.dock.processMouseOut)
	  	.bind('click',function(){
	  		var item = $(this).data('item');
	  		openUrl(item.id,item.url,function(){
			});
	  	});
	return Portal.dock;
}
Portal.dock.updateIconProperties = function(min){
	var barWidth = 0;
	for (var j = 0; j < Portal.dock.iconNodes.length; j++) {
		if(Portal.dock.iconSizes[j]<Portal.dock.config.minimumSize+(Portal.dock.config.maximumSize-Portal.dock.config.minimumSize)/4||min){
			if(Portal.dock.iconNodes[j].attr('src').indexOf('max')>0)
				Portal.dock.iconNodes[j].attr('src',Portal.dock.prefx+Portal.dock.config.items[j].icon+'_min.png');
		}
		else{
			if(Portal.dock.iconNodes[j].attr('src').indexOf('min')>0)
				Portal.dock.iconNodes[j].attr('src',Portal.dock.prefx+Portal.dock.config.items[j].icon+'_max.png');
		}
		var size = Portal.dock.config.minimumSize + Portal.dock.scale* (Portal.dock.iconSizes[j] - Portal.dock.config.minimumSize);
		Portal.dock.iconNodes[j].css({width:size,height:size*1.26,left:barWidth,top:-size*0.9+Portal.dock.config.minimumSize-Portal.dock.offset});
		Portal.dock.status[j].css({left:barWidth+(size>>1)-15});
		Portal.dock.labels[j].css({left:barWidth-Portal.dock.labels[j].widthb2-10+(size>>1),top:-size*0.9+Portal.dock.config.minimumSize-40});
		barWidth+=size;
	}
	Portal.dock.$dockBar.css({width:barWidth,left:(constans.width-barWidth)>>1});
}
Portal.dock.processMouseMove = function(e){
	var index = Portal.dock.$img.index(this);
	Portal.dock.$label.css({display:'none'});
	Portal.dock.labels[index].css({display:'inline'});
	if(!Portal.dock.animateTimer)Portal.dock.animateTimer=1;
//	if(Portal.dock.animateTimer++%1!=0)return;
    window.clearTimeout(Portal.dock.closeTimeout);
    Portal.dock.closeTimeout = null;
    window.clearInterval(Portal.dock.closeInterval);
    Portal.dock.closeInterval = null;
    if (Portal.dock.scale != 1 && !Portal.dock.openInterval){
    	Portal.Task.pause();
    	Portal.dock.nowTime = new Date().getTime();
	    Portal.dock.openInterval = window.setInterval(
          function(){
          	var subTime = new Date().getTime()-Portal.dock.nowTime;
            if (Portal.dock.scale < 1) Portal.dock.scale += subTime/400;
            if (Portal.dock.scale >= 1){
              Portal.dock.scale = 1;
              window.clearInterval(Portal.dock.openInterval);
              Portal.dock.openInterval = null;
            }
           	Portal.dock.updateIconProperties()
          },
          13);
    }
	var index = 0;
	while (Portal.dock.iconNodes[index].get(0) != e.target&&index<100) index++;
	var across = (e.layerX || e.offsetX) / Portal.dock.iconSizes[index];
	if (across){
		var currentWidth = 0;
	    for (var i = 0; i < Portal.dock.iconNodes.length; i++){
	        if (i < index - Portal.dock.config.range || i > index + Portal.dock.config.range){
	          Portal.dock.iconSizes[i] = Portal.dock.config.minimumSize;
	        }else if (i == index){
	          Portal.dock.iconSizes[i] = Portal.dock.config.maximumSize;
	        }else if (i < index){
	          Portal.dock.iconSizes[i] =
	              Portal.dock.config.minimumSize
	              + Math.round(
	                  (Portal.dock.config.maximumSize - Portal.dock.config.minimumSize - 1)
	                  * (
	                      Math.cos(
	                          (i - index - across + 1) / Portal.dock.config.range * Math.PI)
	                      + 1)
	                  >>1);
	          currentWidth += Portal.dock.iconSizes[i];
	        }else{
	          Portal.dock.iconSizes[i] =
	              Portal.dock.config.minimumSize
	              + Math.round(
	                  (Portal.dock.config.maximumSize - Portal.dock.config.minimumSize - 1)
	                  * (
	                      Math.cos(
	                          (i - index - across) / Portal.dock.config.range * Math.PI)
	                      + 1)
	                  >>1);
	          currentWidth += Portal.dock.iconSizes[i];
			}
		}
		if (currentWidth > Portal.dock.maximumWidth) Portal.dock.maximumWidth = currentWidth;
		if (index >= Portal.dock.config.range
	          && index < Portal.dock.iconSizes.length - Portal.dock.config.range
	          && currentWidth < Portal.dock.maximumWidth){
	        Portal.dock.iconSizes[index - Portal.dock.config.range] += Math.floor((Portal.dock.maximumWidth - currentWidth) >>1);
	        Portal.dock.iconSizes[index + Portal.dock.config.range] += Math.ceil((Portal.dock.maximumWidth - currentWidth) >>1);
		}
		Portal.dock.updateIconProperties();
	}
}
Portal.dock.processMouseOut = function(){
    if (!Portal.dock.closeTimeout && !Portal.dock.closeInterval){
    	var index = Portal.dock.$img.index(this);
      	Portal.dock.closeTimeout = window.setTimeout(
          function(){
          	Portal.dock.updateLabel(index);
          	Portal.Task.continues();
            Portal.dock.closeTimeout = null;
            if (Portal.dock.openInterval){
              window.clearInterval(Portal.dock.openInterval);
              Portal.dock.openInterval = null;
            }
            Portal.dock.nowTime = new Date().getTime();
            Portal.dock.closeInterval = window.setInterval(
                function(){
                	var subTime = new Date().getTime()-Portal.dock.nowTime;
                  	if (Portal.dock.scale > 0) Portal.dock.scale -= subTime/400;
                  	if (Portal.dock.scale <= 0){
                   		Portal.dock.scale = 0;
                    	window.clearInterval(Portal.dock.closeInterval);
                    	Portal.dock.closeInterval = null;
                  	}
                 	Portal.dock.updateIconProperties(true);
                },
                13);
          },
          50);
    }
}
