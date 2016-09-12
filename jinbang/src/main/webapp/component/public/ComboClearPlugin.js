Ext.define("component.public.ComboClearPlugin", {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.comboClearPlugin',
	init : function(combo) {
		Ext.apply(combo, {
		    trigger2Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
			emptyText: '--请选择--',
		    onTrigger2Click : function(){
		    	combo.setValue('');
		    }
		});
	}
});