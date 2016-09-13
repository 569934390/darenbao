Ext.define('component.public.LocalSelectWindow', {
	extend:'Ext.window.Window',
	alias:'widget.localSelectWindow',
	constructor:function(config){
		var me=this;
		config=config||{};
		Ext.applyIf(config,{
			closeAction:'hide',
			modal:true,
			title : '经纬度选择器',
			width : 600,
			height:500,
			resizable:false,
			frame : true,
			html:'<iframe id="main_frame" src="'+ctx+'/map/map.jsp" frameborder="no" width="100%" height="100%"   border="no"></iframe>',
			dockedItems: [{
				xtype: 'toolbar',
				dock: 'bottom',
				ui:'footer',
				frame:true,
				layout:{
					pack:'center'
				},
				items: [{
					text : '提交',
					name : 'submitBtn'
				}, {
					text : '取消',
					name : 'canceltBtn'
				}]
			}]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.down('[name=submitBtn]').on('click',Ext.bind(me.submitHandler,me));
		me.down('[name=canceltBtn]').on('click',Ext.bind(me.cancelHandler,me));
	},
	submitHandler:function(){
		var me=this;
		var main_frame = document.getElementById('main_frame');
		if(Ext.isFunction(me.callback)){
			me.callback(main_frame.contentWindow.localInfo);
		}else{
			me.close();
		}
	},
	cancelHandler:function(){
		this.close();
	}
});