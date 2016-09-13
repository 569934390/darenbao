Ext.define('component.dcc.store.MessageDetailStore', {
	extend : 'Ext.data.TreeStore',
	model : 'component.dcc.model.MessageDetail',
	constructor : function(config) {
		var me=this,config=config||{};
		Ext.applyIf(config, {
			root : Ext.apply({
				expanded : true
			},config.root),
			proxy : {
				type : 'ajax',
				reader : 'json',
				url : webRoot + 'debug/loadDcc.do?node=root&rowKey='+config.rowKey
			},
			autoLoad: false
		});
        this.callParent([config]);
	}
});
