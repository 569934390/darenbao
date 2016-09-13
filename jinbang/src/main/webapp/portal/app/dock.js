define(['jq','./utils'],function () {
	var Portal = {};
    Portal.dock={};
	Portal.dock.imageCache = [];	//图片缓存
	constans.image = {};
	constans.image.prefx = webRoot+'portal/images/icons/'; //图片服务器前缀
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
	Portal.dock.showLabel = function(index){
		if($.isIE8m()){
			Portal.dock.$label.css({display:'inline'});
		}
		else{
			Portal.dock.$label.fadeIn(100);
		}
	    Portal.dock.updateIconProperties(false,index);
	}
	/**
	 * 图片缓存
	 * @param {} name
	 */
	Portal.dock.initCache = function(name){
		var img = new Image();
		img.src = constans.image.prefx+name+'.png';
		Portal.dock.imageCache.push(img);
		var maxImg = new Image();
		maxImg.src = constans.image.prefx+name+'_max.png';
		Portal.dock.imageCache.push(maxImg);
		var minImg = new Image();
		minImg.src = constans.image.prefx+name+'_min.png';
		Portal.dock.imageCache.push(minImg);
	}
	
	Portal.dock.index = function(item){
		return Portal.dock.$img.index(item);
	}
	
	Portal.dock.removeAt=function(index){
		ArrayUtils.removeAt(Portal.dock.config.items,index);
		Portal.dock.$dockBar.find('img').eq(index).remove();
		$('.menu-task').eq(index).remove();
		ArrayUtils.removeAt(Portal.dock.iconNodes,index);
		ArrayUtils.removeAt(Portal.dock.iconSizes,index);
		ArrayUtils.removeAt(Portal.dock.status,index);
		Portal.dock.labels=[];
		Portal.dock.unbindEvent().bindEvent();
		Portal.dock.updateIconProperties();
	}
	
	Portal.dock.insert=function(item,index,fn,scope){
		if(index>=Portal.dock.$img.length)return false;
		var $img,append=false;
		if(!index&&index!=0){
			index = Portal.dock.config.items.length-1;
			append = true;
		}
		ArrayUtils.insertAt(Portal.dock.config.items,index,item);
		
		Portal.dock.initCache(item.icon);
		$img = $('<img ondrag="return false"></img>').css({width:Portal.dock.config.minimumSize,position: 'absolute','z-index':99999}).data('item',item)
			.attr('src',constans.image.prefx+item.icon+'_max.png');
		Portal.dock.$dockBar.find('img').eq(index).before($img);
		ArrayUtils.insertAt(Portal.dock.iconNodes,index,$img);
		ArrayUtils.insertAt(Portal.dock.iconSizes,index,Portal.dock.config.minimumSize);
		var $status = $('<div class="menu-task"></div>').data('item',item);
		if(item.status)$status.addClass('menu-task-selected');
		ArrayUtils.insertAt(Portal.dock.status,index,$status);
		Portal.dock.$dockBar.find('.menu-task').eq(index).before($status);
		Portal.dock.updateIconProperties();
		if(append){
			Portal.dock.unbindEvent();
			$img.attr('src',constans.image.prefx+item.icon+'.png')
				.animate({top:'-=15'},function(){
					if(fn)
						fn.call(scope||this);
				}).animate({top:'+=15'},function(){
					Portal.dock.bindEvent();
					$img.attr('src',constans.image.prefx+item.icon+'_min.png');
				});
		}
		else{
			Portal.dock.unbindEvent().bindEvent();
		}
		return $img;
	}
	Portal.dock.unbindEvent = function(){
		Portal.dock.processMouseOut();
		Portal.dock.pause();
		return Portal.dock;
	}
	Portal.dock.pause = function(){
		Portal.dock.$img.unbind('mousemove').unbind('mouseout').unbind('mouseup');
		return Portal.dock;
	}
	Portal.dock.continues = function(){
		Portal.dock.bindEvent();
		Portal.dock.processMouseOut();
		return Portal.dock;
	}
	Portal.dock.bindEvent = function(){
		Portal.dock.$img = Portal.dock.$dockBar.find('img');
		Portal.dock.$dockBar.find('img').unbind('mousemove').unbind('mouseout').unbind('mouseup')
			.bind('mousemove',Portal.dock.processMouseMove)
			.bind('mouseout', Portal.dock.processMouseOut)
		  	.bind('mouseup',Portal.dock.mouseup = function(e){
		  		if(e.which!=1)return;
		  		var item = $(this).data('item');
		  		if(!item)return;
		  		require(['app/tabpanel'],function(tab){
					tab.openUrl(item.id,item.url,function(){
						item.status=true;
		  				$(this).data('item',item);
					});
				});
				return;
		  	});
		return Portal.dock;
	}
	Portal.dock.hide = function(){
		Portal.dock.$label.hide();
		Portal.dock.$dockBar.hide();
	}
	Portal.dock.closeItem = function(id){
		for(var i=0;i<Portal.dock.status.length;i++){
			var status = Portal.dock.status[i];
			if(status.data('item').id==id){
				status.removeClass('menu-task-selected');
			}
		}
	}
	Portal.dock.itemStatus = function(id){
		for(var i=0;i<Portal.dock.status.length;i++){
			var status = Portal.dock.status[i];
			if(status.data('item').id==id){
				return status.hasClass('menu-task-selected');
			}
		}
	}
	Portal.dock.updateIconProperties = function(min,index){
		var barWidth = 0;
		for (var j = 0; j < Portal.dock.iconNodes.length; j++) {
			if(Portal.dock.iconSizes[j]<Portal.dock.config.minimumSize+(Portal.dock.config.maximumSize-Portal.dock.config.minimumSize)/4||min){
				if(Portal.dock.iconNodes[j].attr('src').indexOf('max')>0)
					Portal.dock.iconNodes[j].attr('src',constans.image.prefx+Portal.dock.config.items[j].icon+'_min.png');
			}
			else{
				if(Portal.dock.iconNodes[j].attr('src').indexOf('min')>0)
					Portal.dock.iconNodes[j].attr('src',constans.image.prefx+Portal.dock.config.items[j].icon+'_max.png');
			}
			var size = Portal.dock.config.minimumSize + Portal.dock.scale* (Portal.dock.iconSizes[j] - Portal.dock.config.minimumSize);
			Portal.dock.iconNodes[j].css({width:size,height:size*1.26,left:barWidth,top:-size*0.9+Portal.dock.config.minimumSize-Portal.dock.offset});
			Portal.dock.status[j].css({left:barWidth+(size>>1)-15});
			if(index==j){
				Portal.dock.$label.text(Portal.dock.config.items[j].title);
				if(!Portal.dock.labels[j]){
					Portal.dock.labels[j]={widthb2:Portal.dock.$label.width()/2};
				}
				Portal.dock.$label.css({left:barWidth-Portal.dock.labels[j].widthb2-10+(size>>1),top:-size*0.9+Portal.dock.config.minimumSize-40});
			}
			barWidth+=size;
		}
		Portal.dock.$dockBar.css({width:barWidth,left:(constans.width-barWidth)>>1});
	}
	Portal.dock.beforeInsert = function(overIndex,moveIndex){
		if(overIndex<0)return;
		var size = Portal.dock.config.minimumSize;
		var barWidth = 0;
		for (var j = 0; j < Portal.dock.iconNodes.length; j++) {
			if(j==overIndex){barWidth+=size}
			if(j==moveIndex)continue;
			Portal.dock.iconNodes[j].stop().animate({left:barWidth},200);
			Portal.dock.status[j].stop().animate({left:barWidth+(size>>1)-15},200);
			barWidth+=size;
		}
//		Portal.dock.$dockBar.stop().animate({overflow:'auto',width:barWidth,left:(constans.width-barWidth)>>1});
	}
	Portal.dock.processMouseMove = function(e){
		var index = Portal.dock.$img.index(this);
		if(!Portal.dock.animateTimer)Portal.dock.animateTimer=1;
	    window.clearTimeout(Portal.dock.closeTimeout);
	    Portal.dock.closeTimeout = null;
	    window.clearInterval(Portal.dock.closeInterval);
	    Portal.dock.closeInterval = null;
	    if (Portal.dock.scale != 1 && !Portal.dock.openInterval){
	    	Portal.dock.nowTime = new Date().getTime();
		    Portal.dock.openInterval = window.setInterval(
	          function(){
	          	var subTime = new Date().getTime()-Portal.dock.nowTime;
	            if (Portal.dock.scale < 1) Portal.dock.scale += subTime/400;
	            if (Portal.dock.scale >= 1){
	              Portal.dock.scale = 1;
	              window.clearInterval(Portal.dock.openInterval);
	              Portal.dock.openInterval = null;
	              Portal.dock.showLabel(index);
	            }
	           	Portal.dock.updateIconProperties();
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
			Portal.dock.updateIconProperties(false,index);
		}
	}
	Portal.dock.processMouseOut = function(e){
	    if (!Portal.dock.closeTimeout && !Portal.dock.closeInterval){
	    	var index = Portal.dock.$img.index(this);
	      	Portal.dock.closeTimeout = window.setTimeout(
	          function(){
	          	Portal.dock.updateLabel(index);
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
	                 	Portal.dock.updateIconProperties(true,index);
	                },
	                13);
	          },
	          50);
	    }
	}
    return {
        init: function(options){
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
			Portal.dock.openInterval,Portal.dock.closeInterval,Portal.dock.closeTimeout,Portal.dock.scale=0,Portal.dock.offset = constans.height<600?0:6,index = 0,Portal.dock.nowTime=new Date().getTime();
			
			for(var i=0;i<Portal.dock.config.items.length;i++){
				Portal.dock.initCache(Portal.dock.config.items[i].icon);
				var $img = $('<img ></img>').css({width:Portal.dock.config.minimumSize,position: 'absolute','z-index':99999}).data('item',Portal.dock.config.items[i])
					.attr('src',constans.image.prefx +Portal.dock.config.items[i].icon+'_max.png');
				Portal.dock.$dockBar.append($img);
				width+=Portal.dock.config.minimumSize;
				Portal.dock.iconNodes.push($img);
				Portal.dock.iconSizes.push(Portal.dock.config.minimumSize);
				var $status = $('<div class="menu-task"></div>').data('item',Portal.dock.config.items[i]);
				Portal.dock.status.push($status);
				Portal.dock.$dockBar.append($status);
			}
			var $label = $('<div class="task-label"></div>').css({opacity:0.8});
			Portal.dock.$label=$label;
			Portal.dock.$dockBar.append($label);
			Portal.dock.updateIconProperties();
			Portal.dock.bindEvent();
		},
		insert:Portal.dock.insert,
		beforeInsert:Portal.dock.beforeInsert,
		removeAt:Portal.dock.removeAt,
    	bindEvent:Portal.dock.bindEvent,
    	unbindEvent:Portal.dock.unbindEvent,
    	pause:Portal.dock.pause,
    	continues:Portal.dock.continues,
    	hide:Portal.dock.hide,
    	closeItem:Portal.dock.closeItem,
    	itemStatus:Portal.dock.itemStatus,
    	c:Portal.dock
    };
});
