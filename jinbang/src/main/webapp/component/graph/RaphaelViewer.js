Ext.define('component.graph.RaphaelViewer', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.raphaelViewer',
    requires:[''],
    mixins: {
    },
//	html:'<div id="holder" style="-moz-user-select: none;height:100%;width:100%;overflow:hidden;z-index:0;background:#fff"></div>',
    initComponent: function() {
    	var me = this;
        this.callParent(arguments);
        me.on('afterrender',Ext.bind(me.init,me));
    },
    addNode:function(node){
    	var me = this;
    	node = me.dictNode(node);
    	me.nodeSet.push(
    		me.paper.image(node.image.src, node.x, node.y, node.image.width, node.image.height).data('attributes',node)
    	);
    	if(node.name)
    		me.paper.text(node.x+node.image.width/2,node.y+node.image.height+10,node.name);
    	me.updateLines();
    },
    setMsg:function(num,text,handler,scop){
    	var me = this;
    	me.lineSet.forEach(function(line,index){
    		if(num==index){
    			var bbox = line.getBBox(),offsetx = 50;
    			if(num==0)
    				offsetx=bbox.width-50;
    			me.tips.push(
    				me.paper.circle(bbox.x+bbox.width-offsetx,bbox.y,10).attr({"fill":"red",'stroke':'#f5f5f5','stroke-width':2,'cursor':'pointer'}).data({'index':num,'msg':text}).click( function(){
    				if(handler!=null){
    					handler.call(scop||this,this);
    				}
    			}),
    				me.paper.text(bbox.x+bbox.width-offsetx,bbox.y,text).attr({'stroke':'#ffffff','cursor':'pointer'}).data({'index':num,'msg':text}).click( function(){
    				if(handler!=null){
    					handler.call(scop||this,this);
    				}
    			})
    			);
    			return false;
    		}
    	});
    },
    removeNodes:function(){
    	var me = this;
    	me.nodeSet.forEach(function(node,index){
    		node.remove();
    	});
    	me.lineSet.forEach(function(line,index){
    		line.remove();
    	});
    	me.tips.forEach(function(tip,index){
    		tip.remove();
    	});
    	me.tips.remove();
    	me.tips = me.paper.set();
    	me.nodeSet.remove();
    	me.lineSet.remove();
    	me.nodeSet = me.paper.set();
    	me.lineSet = me.paper.set();
    },
    updateLines:function(){
    	var me = this;
    	me.lineSet.forEach(function(line,index){
    		line.remove();
    	});
    	me.tips.forEach(function(tip,index){
    		tip.remove();
    	});
    	me.tips.remove();
    	me.tips = me.paper.set();
    	me.lineSet.remove();
    	me.lineSet = me.paper.set();
    	var prevNode;
    	me.nodeSet.forEach(function(node,index){
    		var attributes = node.data('attributes');
    		if(index!=0){
	    		me.lineSet.push(
		    		me.paper.path(['M',node.attr('x'),node.attr('y')+(node.attr('height')>>1)+10,'L',
		    			prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)+10
		    		]).data('attributes',attributes),
		    		me.paper.path(['M',prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)-10,'L',
		    			node.attr('x'),node.attr('y')+(node.attr('height')>>1)-10
		    		]).data('attributes',attributes)
	    		);
    		}
    		prevNode = node;
    	});
    	me.lineSet.attr({fill: "none","fill-opacity": 0,'stroke-dasharray':'.', "stroke-width": 4, cursor: "move",'arrow-end':'classic-wide-long',stroke:'green'});
    	var items = [];
    	me.lineSet.forEach(function(line,index){
    		items.push(line);
    	});
    	for(var i=items.length-1;i>=0;i--){
    		var line = items[i];
    		var attributes = line.data('attributes');
    		if(attributes.display=='virtual')
    			line.attr({stroke:'red','arrow-end':'classic'});
    		else
    			line.attr({stroke:'green'});
    	}
    	
    },
    dictNode:function(node){
    	if(node.type=='PC'){
    		node.image = {src:webRoot+"common/dccImages/pcs.png",width:64,height:64};
    	}
    	else if(node.type=='SERVER'){
    		node.image = {src:webRoot+"common/dccImages/server.png",width:64,height:64};
    	}
    	else if(node.type=='DBS'){
    		node.image = {src:webRoot+"common/dccImages/dbs.png",width:64,height:64};
    	}
    	else if(node.type=='GRID'){
    		node.image = {src:webRoot+"common/dccImages/grid.png",width:64,height:64};
    	}
    	return node;
    },
    init:function(panel){
    	var me = this;
		setTimeout(function(){
			me.paper= Raphael(panel.body.id, '100%','100%');
			var lineSet = me.paper.set(),nodeSet = me.paper.set();
			var y = 10;
			me.lineSet = lineSet;
			me.nodeSet = nodeSet;
			me.tips = me.paper.set();
		},100);

//    	nodeSet.push(
//    		me.paper.image(webRoot+"common/dccImages/pcs.png", 100, y, 64, 64),
//    		me.paper.image(webRoot+"common/dccImages/server.png", 400, y, 64, 64),
//    		me.paper.image(webRoot+"common/dccImages/dbs.png", 650, y, 64, 64),
//	    	me.paper.image(webRoot+"common/dccImages/grid.png", 950, y, 64, 64)
//    	);
//    	var prevNode;
//    	nodeSet.forEach(function(node,index){
//    		if(index==2){
//    			lineSet.push(
//		    		me.paper.path(['M',prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)-10,'L',
//		    			node.attr('x'),node.attr('y')+(node.attr('height')>>1)-10
//		    		])
//	    		);
//	    		me.paper.path(['M',node.attr('x'),node.attr('y')+(node.attr('height')>>1)+10,'L',
//		    			prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)+10
//		    		]).attr({'arrow-end':'none',stroke: 'red', "stroke-width": 4,'stroke-dasharray':'- .'});
//		    	me.paper.path(['M',node.attr('x')-100,node.attr('y')+(node.attr('height')>>1)+20,'L',
//		    		node.attr('x')-120,node.attr('y')+(node.attr('height')>>1)
//		    	]).attr({'arrow-end':'none',stroke: 'red', "stroke-width": 4,'stroke-dasharray':'-'});
//    		}
//    		else if(index!=0){
//	    		lineSet.push(
//		    		me.paper.path(['M',prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)-10,'L',
//		    			node.attr('x'),node.attr('y')+(node.attr('height')>>1)-10
//		    		]),
//		    		me.paper.path(['M',node.attr('x'),node.attr('y')+(node.attr('height')>>1)+10,'L',
//		    			prevNode.attr('x')+prevNode.attr('width'),prevNode.attr('y')+(prevNode.attr('height')>>1)+10
//		    		])
//	    		);
//    		}
//    		prevNode = node;
//    	});
//    	lineSet.hide();
//    	lineSet.attr({fill: "none",stroke: 'green', "fill-opacity": 0,'stroke-dasharray':'.', "stroke-width": 4, cursor: "move",'arrow-end':'classic-wide-long'});
//    	lineSet.forEach(function(line,index){
//	    		var color = "blue";
//	    		var e = me.paper.ellipse(0, 0, 7, 3).attr({stroke: "none", fill: color}).onAnimation(function () {
//	                    var t = this.attr("transform");
//	                });
//	    		function run(flag) {
//	    			var len = line.getTotalLength();
//	    			var v = 1;
//	            	var end = line.getPointAtLength(v * len);
//	            	var start = line.getPointAtLength(0);
//	            	e.attr({transform: "t" + [start.x, start.y] + "r" + start.alpha,rx:7,ry:3});
//	                e.animate({transform: "t" + [end.x, end.y] + "r" + end.alpha}, 1300,'<>', function () {
//	                	e.animate({transform:"t" + [end.x, end.y] +'s1.3',rx:7,ry:7},200,'>',function(){
//	                		e.animate({transform:"t" + [end.x, end.y]},200,'>',function(){
//		                    	run(flag);
//	                		});
//	                	});
//	                });
//	            }
//		        run(true);
//    	});
    }
});