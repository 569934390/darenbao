Ext.define('component.graph.CanvasViewer', {
    extend: 'Ext.form.Panel',
    alias: 'widget.canvasViewer',
    requires:['component.socket.jq'],
    mixins: {
    },
	html:'<canvas id="holder"></canvas>',
    initComponent: function() {
    	var me = this;
        this.callParent(arguments);
        me.on('afterrender',Ext.bind(me.init,me));
    },
    init:function(){
    	var me = this,width=Ext.getBody().getWidth(),height = me.getWidth();
		var nodes = [{name:'PC机',type:'pcs'},{name:'服务器',type:'server'},{name:'数据库',type:'dbs'},{name:'网络',type:'grid'}],y = (height>>1)-82;
		
		for(var i=0,length=nodes.length;i<length;i++){
			nodes[i].x=100+(width/length)*i;
			nodes[i].y=y;
			nodes[i].width=64;
			nodes[i].height=64;
			nodes[i]=me.initNode(nodes[i]);
		}
		
		var canvas;
		canvas = document.getElementById("holder");
	    canvas.setAttribute('width',width);
		canvas.setAttribute('height', height);
		$(canvas).bind('click',function(e){
		 	p = me.getEventPosition(e);  
            console.info(arguments);
            console.info(p)
		});
		if (Ext.isIE8m) {
	        G_vmlCanvasManager.initElement(canvas);
	    }
	    var ctx = canvas.getContext("2d");
	    var i=0,timer=-1,lines=[];
//	    lines.push([{index:0,type:'ccr'},{index:1,type:'ccr'},{index:2,type:'ccr'},{}]);
//	    lines.push([{},{index:1,type:'cca'},{index:2,type:'cca'},{index:3,type:'cca'}]);
	    lines.push([{index:0,type:'ccr'},{index:1,type:'ccr'},{index:2,type:'ccr'},{index:3,type:'cca'},{index:2,type:'cca'},{index:1,type:'cca'}]);
	    var timer1 = setInterval(function(){
	    	timer++;
	    	if(timer>=lines[0].length){
	    		timer=0;
	    		if(lines.length==1){
		    		lines.pop();
		    		lines.push([{index:0,type:'ccr'},{index:1,type:'ccr'},{index:2,type:'ccr'},{}]);
		    		lines.push([{},{index:1,type:'cca'},{index:2,type:'cca'},{index:3,type:'cca'}]);
	    		}
	    		else{
	    			lines.pop();
	    			lines.pop();
	    			lines.push([{index:0,type:'ccr'},{index:1,type:'ccr'},{index:2,type:'ccr'},{index:3,type:'cca'},{index:2,type:'cca'},{index:1,type:'cca'}]);
	    		}
//	    		clearInterval(timer1);
//	    		clearInterval(timer2);
//	    		setTimeout(function(){
//	    			ctx.clearRect(0,0,width,height);
//		    		me.draw(ctx,nodes,i++,lines,timer,false);
//	    		},20);
	    	}
	    },3000);
		var timer2 = setInterval(function(){
			ctx.clearRect(0,0,width,height);
			me.draw(ctx,nodes,i++,lines,timer,true);
		},10);
    },
    draw:function(ctx,nodes,index,lines,timer,circle){
    	for(var i=0,length=nodes.length;i<length;i++){
			ctx.drawImage(nodes[i].img,nodes[i].x,nodes[i].y,nodes[i].width,nodes[i].height);
			if(i<length-1){
				ctx.beginPath();
				ctx.strokeStyle = "green";
//				ctx.lineWidth = 5;
				ctx.moveTo(nodes[i].x+64, nodes[i].y+(nodes[i].height>>1)-12);
				ctx.lineTo(nodes[i+1].x, nodes[i+1].y+(nodes[i].height>>1)-12);
				ctx.closePath();
				ctx.stroke();
				
				ctx.beginPath();
				ctx.strokeStyle = "blue";
				ctx.moveTo(nodes[i+1].x, nodes[i+1].y+(nodes[i].height>>1)+12);
				ctx.lineTo(nodes[i].x+64, nodes[i].y+(nodes[i].height>>1)+12);
				ctx.closePath();
				ctx.stroke();
			}
			if(circle){
				for(var j=0,linesLength = lines.length;j<linesLength;j++){
					if(!lines[j][timer]) continue;
					if(lines[j][timer].index==i){
						if(lines[j][timer].type==='ccr'){
							var lineLength = nodes[i+1].x-nodes[i].x -64,x=0;
							ctx.beginPath();
							if(10*index>lineLength){
								x = nodes[i].x+64+(10*index)%lineLength;
							}
							else
								x = nodes[i].x+64+10*index;
							ctx.arc(x, nodes[i].y+(nodes[i].height>>1)-12,5,0,Math.PI*2,false);
							ctx.fillStyle='#1c94c4';
							ctx.fill();
						}
						else{
							var lineLength = nodes[i].x-nodes[i-1].x -64,x=0;
							ctx.beginPath();
							if(10*index>lineLength){
								x = nodes[i].x-(10*index)%lineLength;
							}
							else
								x = nodes[i].x-10*index;
							ctx.arc(x, nodes[i].y+(nodes[i].height>>1)+12,5,0,Math.PI*2,false);
							ctx.fillStyle='red';
							ctx.fill();
						}
					}
				}
			}
		}
    },
    initNode:function(node){
    	node.img = new Image;
		node.img.src = webRoot+'common/dccImages/'+node.type+'.png';
		return node;
    },//得到点击的坐标  
	getEventPosition : function(ev){  
	    var x, y;  
	    if (ev.layerX || ev.layerX == 0) {  
	        x = ev.layerX;  
	        y = ev.layerY;  
	    }else if (ev.offsetX || ev.offsetX == 0) { // Opera  
	        x = ev.offsetX;  
	        y = ev.offsetY;  
	    }  
	    return {x: x, y: y};  
	}
});