/**
 * 使用的例子
 */
/**
var alarmKpiWindow = Ext.create("component.public.ItemSelectorWindow", {
				width : 400,
				height : 400,
				title : '告警kpi',
				panelConfig:{
					selectorConfig:{
						queryMode : 'local',
						data : [['11', '11'], ['22', '22']]
					},
					searchPanel : {
						xtype : 'form',
						frame : true,
						buttons : [{
									text : '查询'
								}, {
									text : '重置'
								}],
						items : [{
									xtype : 'textfield',
									fieldLabel : '节点类型',
									name : 'nodeType'
								}, {
									xtype : 'textfield',
									fieldLabel : 'kpi名称',
									name : 'kpiName'
								}]
					}
				}
			});
alarmKpiWindow.show();
**/
Ext.define('component.public.ItemSelectorForm',{
	extend:'Ext.form.Panel',
	alias:'widget.itemSelectorForm',
	resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
			panel.down('itemselector').setWidth(width);
			panel.down('itemSelector').setHeight(height);
	},
	constructor : function(config){
		var me = this;
		var store = null;
		if (config.queryMode == "local"||config.mode=='local'||config.data) {
			config.fields = config.displayField?[config.displayField,config.valueField]:config.fields || ['label', 'value'];
			store = config.store||Ext.create("Ext.data.ArrayStore", {
						fields : config.fields,
						data : config.data
					});
		} else {
			config.fields=config.fields||[config.displayField,config.valueField];
			store = config.store||Ext.create('Ext.data.JsonStore',{
				proxy : {
					type : 'ajax',
					url : webRoot + 'comboBox/selectList.do',
					reader : {
						type : 'json'
					}
				},
				fields : config.fields,
				autoLoad : true
			});
			store.on("beforeload", function(store, records, options) {
				 store.proxy.extraParams.sqlKey =config.sqlKey;
				 store.proxy.extraParams.paramMap = Ext.encode(config.paramMap);
			});
		}
		var itemSelector=Ext.create('Ext.ux.form.ItemSelector',{
				name : 'itemselector',
				region:'center',
				imagePath : ctx+ '/public/ext-4.2.1/examples/ux/images/',
				body : 0,
				store : store,
				height : config.selectheight||'300',
				displayField : config.fields[0],
				valueField : config.fields[1],
				allowBlank : false,
				border : false,
				msgTarget : 'side',
				fromTitle : '可选节点',
				toTitle : '已选节点'
			
		});
		me.itemSelector=itemSelector;
		config.items=[];
		config.items[config.items.length] = itemSelector;
		Ext.applyIf(config,{
			region:'center',
			layout:'border'
		})
		me.callParent([config]);
	}
})
Ext.define("component.public.ItemSelectorPanel", {
		extend : 'Ext.panel.Panel',
		alias : 'widget.itemSelectorPanel',
		resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
		},
		getItemSelector:function(){
			return this.down("itemselector");
		},
		constructor : function(config) {
			var me=this;
			Ext.applyIf(config.selectorConfig,{
				region:'center',
				layout:'border'
			});
			var itemSelectForm = Ext.create('component.public.ItemSelectorForm',config.selectorConfig);
			me.itemSelectForm=itemSelectForm;
			config.searchPanel.region='north',
			config.items=[];
			config.items[0] = config.searchPanel;
			config.items[1] = itemSelectForm;
			me.callParent([config]);
			delete config.selectorConfig;
			delete config.searchPanel;
			me.on("resize",me.resize);
		}
});
Ext.define("component.public.ItemSelectorWindow", {
	extend : 'Ext.window.Window',
	alias : 'widget.itemSelectorWindow',
	resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
	},
	buttons:[{text:'确定'},{text:'取消'}],
	buttonAlign:'center',
	constructor : function(config) {
		var me=this
		var panelConfig=config.panelConfig;
		Ext.applyIf(config,{
			layout:'border'
		});
		Ext.applyIf(panelConfig,{
			region:'center',
			layout:'border'
		});
		me.itemSelectorPanel=Ext.create('component.public.ItemSelectorPanel',panelConfig);
		delete config.panelConfig;
		config.items=me.itemSelectorPanel;
		me.callParent([config]);
		me.on("resize",me.resize);
	}
});