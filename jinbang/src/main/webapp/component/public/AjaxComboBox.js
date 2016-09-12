Ext.define("component.public.AjaxComboBox", {
			extend : 'Ext.form.field.ComboBox',
			alias : 'widget.ajaxComboBox',
			constructor : function(condition) {
//				var condition=arguments;
				var me = this;
				var store = null;
				if (condition.queryMode == "local"||condition.mode=="local"||!Ext.isEmpty(condition.data)) {
					condition.queryMode='local',
					store = Ext.create("Ext.data.ArrayStore", {
								fields : condition.fields?condition.fields:(condition.valueField?[condition.displayField,condition.valueField]:['label', 'value']),
								data : condition.data
							});
				} else {
					store = Ext.create("Ext.data.JsonStore",{
								proxy : {
									type : 'ajax',
									url : condition.url||(ctx + '/comboBox/selectList.do'),
									reader : {
										type : 'json'
									}
								},
								fields : condition.fields?condition.fields:(condition.valueField?[condition.displayField,condition.valueField]:['label', 'value']),
								autoLoad : true
							});
					store.on("beforeload", function(store, records, options) {
								store.proxy.extraParams.sqlKey = config.sqlKey;
								store.proxy.extraParams.paramMap = Ext.JSON.encode(me.paramMap||{});
							});
				}
				var config = Ext.applyIf(condition, {
							editable : false,
							typeAhead : true,
							displayField : 'label',
							valueField:'value',
							triggerAction : 'all',
							store : store
						});
				if(config.clear){
					config.trigger2Cls=Ext.baseCSSPrefix + 'form-clear-trigger';
				}
				me.callParent([config]);
			},
			onTrigger2Click : function(){
		    	this.setRawValue("");
		    	this.setValue("");
		    }
		});
