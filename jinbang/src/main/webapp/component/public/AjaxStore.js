Ext.define('component.public.AjaxStore', {
			extend : 'Ext.data.JsonStore',
			alias : 'ajaxStore',
			constructor : function(config) {
				config = config || {};
				if(config.model){
					Ext.syncRequire(config.model);
				}
				if (config.pageSize) {
					Ext.applyIf(config, {
						proxy : {
							type : 'ajax',
							url : config.url ? config.url : null,
							reader : {
								type : 'json',
								root : config.root||'root',
								totalProperty : config.totalProperty||'totalCount'
							},
							getMethod: function(){return 'POST';}//亮点，设置请求方式,默认为GET   
						}
					});
				} else {
					Ext.applyIf(config, {
						proxy : {
							type : 'ajax',
							url : config.url ? config.url : null,
							reader : {
								type : 'json'
							},
							getMethod: function(){return 'POST';}//亮点，设置请求方式,默认为GET  
						}
					});
				}
				delete config.url;
				delete config.root;
				delete config.totalProperty;
				this.callParent([config]);
			}
		});