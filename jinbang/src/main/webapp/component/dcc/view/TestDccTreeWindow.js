/**
 * 报表统计
 */
Ext.define('component.dcc.view.TestDccTreeWindow', {
	extend : 'component.public.TextButtonWindow',
	alias : 'widget.textButtonWindow',
	constructor : function(config) {
		config = config || {};
		var me = this;
		me.mainPanel=me.createMainPanel();
		Ext.applyIf(config, {
			title:'dcc消息',
			displayField:'text',
			valueField:'id',
			items:[me.mainPanel]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.mainPanel.getStore().on('load',function(){
			me.mainPanel.getView().refresh();
		});
		this.callParent();
	},
	getStore:function(){
		return this.mainPanel.getStore();
	},
	createMainPanel:function(){
		return Ext.create('component.tree.TestDccTree',{
			collapsible: false,
			headerPosition : 'top',
			split:false
		});
	}
});