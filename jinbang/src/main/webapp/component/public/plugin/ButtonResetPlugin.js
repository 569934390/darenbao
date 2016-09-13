Ext.define('component.public.plugin.ButtonResetPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.buttonResetPlugin',
	init : function(btn) {
		var me=this;
		btn.on('click',Ext.bind(me.resetHandler,btn));
	},
	resetHandler:function(){
		this.up('form').getForm().reset();
	}
});
