Ext.define('component.view.grid.GeneralGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.generalGrid',
	requires:[],
	config:{
	},
	viewConfig:{
		enableTextSelection:true
	},
	selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
	constructor : function(config) {
		var me = this;
		config=config||{};
		if(config.template.storeInstance){
			me.store = config.template.storeInstance;
		}
		else {
			var store = Ext.create('Ext.data.Store', {
				fields: config.template.store.fields,
				proxy: {
					type: 'ajax',
					url: ctx + '/base/getPage.do',
					reader: {
						type: 'json',
						root: 'result',
						totalProperty: 'totalCount'
					},
					extraParams: {
						conditionsStr: Ext.encode({staffId: session.user.id}),
						sqlKey: config.template.store.sqlKey,
						type: config.template.store.type
					}
				},
				pageSize: 50,
				autoLoad: true
			});
			me.store = store;
		}
		var plugin = [Ext.create('component.public.GridToolTipPlugin', {
			toolTipRenderer: function (grid, record, value, label, cellIndex, cellElement, dataIndex) {
				if (cellIndex == 15 || cellIndex == 16) {
					return value;
				}
				return false;
			}
		})];
		//me.plugins = plugin;
		if(Ext.isArray(config.template.plugins)){
			me.plugins.concat(config.template.plugins	);
		}
	    if(config.template.grid){me.columns = config.template.grid}
    	me.dockedItems = [{
	        xtype: 'pagingtoolbar',
	        store: store,
	        dock: 'bottom',
	        displayInfo: true
	    }];
		me.callParent([config]);
	}
});