Ext.define('component.dcc.store.MessageDetailEditStore', {
	extend : 'Ext.data.TreeStore',
	requires:['component.dcc.model.MessageDetail'],
	constructor : function(config) {
		var me=this,config=config||{};
		Ext.applyIf(config, {
			model : 'component.dcc.model.MessageDetail',
			proxy : {
				type : 'ajax',
				getMethod: function(){ return 'POST'; },
				reader : 'json',
				url : config.url||(webRoot + 'debug/loadTestDcc.do')
			}
		});
		delete config.url;
        me.callParent([config]);
	}
});
