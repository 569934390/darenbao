Ext.require(['component.view.grid.AutomationTestGrid','component.view.grid.AutomationGrid','component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.win.ResourceWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.resourceWin',
	requires:[],
	closeAction:'hide',
	maximizable:true,
	modal:true,
	width:Ext.getBody().getWidth()*0.95,
	height:Ext.getBody().getHeight()*0.95,
	layout:'border',
	config:{
	},
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		me.items=[me.resourcePanel = me.createResourcePanel()];
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me = this;
	},
	createResourcePanel:function(){
		return Ext.create('component.view.panel.ResourcePanel',{region:'center'});
	}
});