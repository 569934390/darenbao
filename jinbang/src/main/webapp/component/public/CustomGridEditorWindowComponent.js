//弹窗
Ext.define('component.public.CustomGridEditorWindowComponent', {
	extend :'Ext.window.Window',
    closeAction: 'hide',
    constrain: true,
    plain: true,
    modal: true,
    layout: 'fit',
	initComponent: function() {
		var me = this;
		me.callParent();
	}
});