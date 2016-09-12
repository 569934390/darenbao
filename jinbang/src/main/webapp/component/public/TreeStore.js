Ext.define('component.public.TreeStore', {
	extend : 'Ext.data.TreeStore',
	alias : 'widget.treeStore',
	constructor : function(config) {
		config=config||{};
		if(config.model){
			Ext.Loader.require(config.model);
		}
		Ext.applyIf(config, {
			proxy : {
				type : 'ajax',
				getMethod: function(){ return 'POST'; },//亮点，设置请求方式,默认为GET 
				url : config.url ? config.url : null,
				reader : {
					type : 'json'
				}
			}
		});
		delete config.url;
		this.callParent([config]);
	}
});