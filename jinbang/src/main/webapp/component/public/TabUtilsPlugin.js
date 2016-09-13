Ext.define("component.public.TabUtilsPlugin", {
	extend : 'Ext.AbstractPlugin',
	alias : 'plugin.tabUtilsPlugin',
	init : function(tab) {
		tab.findItemByTitle=function(title){
			var items = tab.items.items;
			 for(var i = 0;i<items.length;i++){
				 if(items[i].title==title)
					 return items[i];
			 }
			 return null;
		}
		this.callParent([arguments]);
	}
});
