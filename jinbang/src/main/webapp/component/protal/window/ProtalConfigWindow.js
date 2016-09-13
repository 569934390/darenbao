Ext.define('component.protal.window.ProtalConfigWindow',{
	extend:'Ext.window.Window',
	constructor:function(config){
		config=config||{};
		var me=this;
		me.detailViewer=me.createdetailViewer();
		Ext.applyIf(config,{
			title:'系统健康报告配置',
			layout:'fit',
			draggable:false,
			items:[me.messageDetailViewer]
		});
		me.callParent([config]);
	},
	createdetailViewer:function(){
		return Ext.create('Ext.panel.Panel',{
			
		});
	}
});