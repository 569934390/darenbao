Ext.define('component.view.win.PerfTestDatasWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.perfTestDatasWin',
	requires:[],
	closeAction:'hide',
	maximizable:true,
	modal:true,
	draggable:false,
	resizable:false,
	width:Ext.getBody().getWidth()*0.9,
	height:Ext.getBody().getHeight()*0.9,
	layout:'border',
	config:{
	},
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		me.items=[me.createPerfTestGird()];
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me = this;
	},
	createPerfTestGird:function(){
		var perfTestGird;
		perfTestGird = Ext.create('component.view.grid.PerfTestGrid',{region:'center'});
		return perfTestGird;
	}
});