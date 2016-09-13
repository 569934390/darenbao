define(['app/dock'],function (dock,tab,sb) {
	Drop={};//拖放
    Drop.move=function(offset){
    	if(!Drop.$m){
    		Drop.$bg = $('<div class="drop-bg"></div>').appendTo(document.body);
    		Drop.$m = $('<div class="drop"></div>').css({backgroundImage:'url("'+constans.image.prefx+Drop.$target.data('item').icon+'.png'+'")'}).appendTo(document.body);
    		if(!$.isIE8m()){
    			Drop.$m.css({opacity:0.5});
    		}
    	}
    	if(Drop.startXY.y-offset.y<50){
	    	var sub = offset.x-Drop.startXY.x-(dock.c.config.minimumSize>>1);
	    	var index = parseInt((sub/dock.c.config.minimumSize).toFixed(0));
	    	if(Drop.overIndex!=index){
	    		Drop.overIndex = index;
	    		dock.beforeInsert(Drop.overIndex+Drop.nowIndex+1,Drop.nowIndex);
	    	}
    	}
    	Drop.$m.css({left:offset.x-27,top:offset.y-35});
    };//拖放
    
    function hideItem(){
    	Drop.$target.show();
		Drop.$m.remove();
		Drop.$bg.remove();
		Drop.$m = null;
    }
    
    $(document.body).bind('mousedown', function(e){
    	if(e.which!=1)return;
    	if(!$(e.target).data('item'))return;
    	Drop.nowIndex = dock.c.index(e.target);
		Drop.moving=true;
		Drop.startXY = {x:e.clientX,y:e.clientY};
		Drop.$target=$(e.target);
		return false;
	}).bind('mousemove',function(e){
		if(e.which!=1)return;
		if(Drop.moving&&Math.abs(e.clientY-Drop.startXY.y)+Math.abs(e.clientX-Drop.startXY.x)>15){
			Drop.$target.hide();
			Drop.move({x:e.clientX,y:e.clientY});
			if(!Drop.movingTimer){
				Drop.movingTimer = setTimeout(function(){
					$('.stackback').remove();
					dock.c.unbindEvent();
				},100);
			}
			return false;
		}
	}).bind('mouseup',function(e){
		if(e.which!=1)return;
		Drop.moving=false;
		if(Drop.movingTimer){
			clearTimeout(Drop.movingTimer);
			Drop.movingTimer = null;
		}
		if(Drop.$m&&Drop.$bg){
			Drop.endXY = {x:e.clientX,y:e.clientY};
			if(Drop.startXY.y-Drop.endXY.y<50){
				var item = Drop.$target.data('item');
		    	if(dock.insert(item,Drop.overIndex+Drop.nowIndex+1)){
			    	dock.removeAt(Drop.nowIndex+(Drop.overIndex<0?1:0));
			    	hideItem();
					dock.c.bindEvent();
			    	return;
		    	}
	    	}
			Drop.$m.animate({left:Drop.startXY.x-27,top:Drop.startXY.y-15},hideItem);
		}
	});
});
