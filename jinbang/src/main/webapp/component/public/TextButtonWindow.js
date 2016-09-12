/**
 * 报表统计
 */
Ext.define('component.public.TextButtonWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.textButtonWindow',
	constructor : function(config) {
		config = config || {};
		var me = this;
		Ext.applyIf(config, {
			layout : 'fit',
			height : 500,
			width : 500,
			frame : true,
			closeAction : 'hide',
			title : '选择窗口',
			modal:true,
			dockedItems : [{// 菜单栏
				xtype : 'toolbar',
				dock : 'bottom',
				ui : 'footer',
				layout : {
					pack : 'center'
				},
				items : [{
					text : '确定',
					width:50,
					name : 'btnOk'
				}, {
					text : '取消',
					width:50,
					name : 'btnCancel'
				}]
			}]
		});
		me.callParent([config]);
	},
	bindEvent:function(){
		var me=this;
		me.down('button[name=btnOk]').on('click',Ext.bind(me.okHandler,me));
		me.down('button[name=btnCancel]').on('click',Ext.bind(me.cancelHandler,me));
		if(Ext.isFunction(me.getSearchBtn)){
			me.getSearchBtn().on('click',Ext.bind(me.search,me));
		}
		if(Ext.isFunction(me.getStore)){
			me.on('show',Ext.bind(me.search,me));
		}
		if(me.mainPanel){
			me.mainPanel.on('itemdblclick',Ext.bind(me.okHandler,me));
		}
	},
	search : function() {
		var me = this;
		if (!Ext.isEmpty(this.down('form'))) {
			var form = this.down('form').getForm();
			if (form.isValid()) {
				var param = form.getValues();
				// for(var key in param){
				// param[key]=encodeURIComponent(param[key]);
				// }
				me.getStore().proxy.extraParams = param;
			}
		}
		me.getStore().load();
	},
	okHandler:function(){
		var me=this;
		if(me.callback){
			me.callback(me);
		}else if(me.mainPanel&&me.targetField){
			var record=Ext.gridSelectCheck(me.mainPanel);
			if(record===false) return;
			if(Ext.isFunction(me.validation)){
				if(!me.validation(record)) return;
			}
			me.targetField.setValue(record.raw[me.displayField]+","+record.raw[me.valueField]);
			me.close();
		}
	},
	cancelHandler:function(){
		var me=this;
		me.close();
	}
});