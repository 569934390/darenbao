Ext.define("component.public.SilderPanel", {
	extend : 'Ext.panel.Panel',
	alias : 'widget.silderPanel',
	constructor : function(condition) {
		var me = this,maxWidth=0;
		me.target = condition.target;
		me.silderItems = condition.target.query('panel[silderable=true]');
		Ext.Array.each(me.silderItems,function(panel,index){
			maxWidth+=panel.width;
		});
		me.target.width=maxWidth+1000;
		var config = {
			xtype:'border',
			items:[{
					region:'center',
					border:false,
					items:[me.target]
				},{
					region:'south',
					xtype:'slider',
	    			width:'100%',
				    minValue: condition.minValue||0,
				    maxValue: condition.maxVlaue||100,
//				    increment:condition.increment||20,
	    			value:0,
	    			tipText: function(thumb){
	    				var val = thumb.value;
	    				if(val<=0){
	    					return '请选择节点';
	    				}
	    				else if(val>=100){
	    					return '请按"保存"键提交';
	    				}
			            return Ext.String.format('<b>{0}% 完成进度</b>',val);
			        },
	    			listeners:{
	    				change:function(silder,value){
	    					$(me.target.body.dom).stop().animate({left:value*-0.01*(me.lefts[me.lefts.length-2])}, '100');
	    				}
	    			}
			}]
		};
		Ext.apply(config,condition);
		this.callParent([config]);
		this.on('resize',Ext.bind(me.autoResize,me));
		this.on('afterrender',Ext.bind(me.init,me));
	},
	resizeLefts:function(panel,subWidth){
		var me = this,begin = 999;
		Ext.Array.each(this.silderItems,function(item,index){
			if(panel.id==item.id){
				begin=index;
			}
			if(index>begin){
				me.lefts[index] = me.lefts[index]-subWidth;
				item.body.un('click');
				item.body.on('click',Ext.Function.bind(me.itemClick,me,[item,index,me.lefts],true));
			}
		});
	},
	init:function(){
		var me = this,lefts = me.lefts||[];
		Ext.Array.each(me.silderItems,function(panel,index){
			lefts[index]=$(panel.body.dom).offset().left;
			lefts[index]-=lefts[0];
			panel.body.un('click');
			panel.body.on('click',Ext.Function.bind(me.itemClick,me,[panel,index,lefts],true));
			panel.on('beforecollapse',me.itemBeforecollapse,me);
			panel.on('expand',me.itemExpand,me);
		});
		me.lefts = lefts;
	},
	itemExpand:function(p, eOpts){
		var me = this;
		me.resizeLefts(p,-p.getWidth());
	},
	itemBeforecollapse:function(p, direction, animate, eOpts){
		var me = this;
		me.resizeLefts(p,p.getWidth());
	},
	itemClick:function(e,panel,obj,pan,index,lefts){
		if(e.target.childElementCount==0)
			return;
		var me =this,per = index-1<0?0:index-1,maxWidth=0;
		Ext.Array.each(me.silderItems,function(panel,index){
			maxWidth+=(panel.width||27);
		});
		var sub = maxWidth-Ext.getBody().getWidth();
		var margin = lefts[per]>sub?sub:lefts[per];
		if(sub<=0)
			margin=lefts[0];
		me.down('slider').setValue((index)/lefts.length*100);
		$(me.target.body.dom).stop().animate({left:-margin}, '800');
	},
	autoResize:function( panel, width, height, oldWidth, oldHeight, eOpts){
		var me = this;
		if(!Ext.isNumber(oldHeight))
			return;
		Ext.Array.each(this.query('panel[silderable=true]'),function(panel,index){
			panel.setHeight(me.target.getHeight()+height-oldHeight);
		});
		me.target.setHeight(me.target.getHeight()+height-oldHeight);
	}
});
