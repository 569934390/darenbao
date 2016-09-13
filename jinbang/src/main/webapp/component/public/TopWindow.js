Ext.define('component.public.TopWindow', {
	constructor : function(condition) {
		return window.top.Ext.create('Ext.window.Window',Ext.apply({
			modal:true,
			closeAction:'hide',
			layout:'fit',
			items:[window.top.Ext.create('Ext.ux.IFrame',{})]
		},condition));
	}
});
