Ext.define("component.public.ComboSetStylePlugin", {
	extend : 'Ext.AbstractPlugin',
	alias : 'plugin.comboSetStylePlugin',
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
					var length = store.getRange().length;
					if(length>0){
						for(var i=0;i<length;i++){
							var data=store.getRange()[i].data;
							if(data[combo.valueField]==combo.value){
								combo.setValue(combo.value?combo.value:(store.getRange()[i].data[combo.valueField]));
								if(data[combo.displaySet]==2){
									combo.setFieldStyle('color:red;');//background:#87CEEB
								}else{
									combo.setFieldStyle('color:green');
								}
							}
						}
					}
				}
			});
//			combo.on("change", function(store,newVal,val){
//				var length = combo.store.getRange().length;
//				if(length>0){
//					for(var i=0;i<length;i++){
//						var data=combo.store.getRange()[i].data;
//						if(data[combo.valueField]==newVal){
//							combo.setValue(combo.store.getRange()[i].data[combo.valueField]);
//							if(data[combo.displaySet]==2){
//								combo.setFieldStyle('color:red;');//background:#87CEEB
//							}else{
//								combo.setFieldStyle('color:green');//background:#87CEEB
//							}
//						}
//					}
//				}
//			});
		}
		this.callParent(arguments);
	}
});
