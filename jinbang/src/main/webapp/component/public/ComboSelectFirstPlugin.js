Ext.define("component.public.ComboSelectFirstPlugin", {
	extend : 'Ext.AbstractPlugin',
	alias : 'plugin.comboSelectFirstPlugin',
	init : function(combo) {
		if (combo.queryMode == "local") {
			combo.on("afterrender", function() {
				if(!Ext.isEmpty(combo.value)){
					combo.setValue(combo.value);
				}
				else if (!Ext.isEmpty(combo.store.getRange()[0])) {
					combo.setValue(combo.store.getRange()[0].data[combo.valueField]);
				}
			});
		} else {
			combo.store.on("load", function(store, records, options) {
						if (!Ext.isEmpty(store.getRange()[0])) {
							combo.setValue(combo.value?combo.value:(store.getRange()[0].data[combo.valueField]));
						}
//						console.info(combo.value);
//						combo.setValue('931');
//						combo.setRawValue('甘肃');
					});
		
		}
		this.callParent(arguments);
	}
});
