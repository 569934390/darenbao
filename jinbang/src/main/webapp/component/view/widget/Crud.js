Ext.require(['component.view.grid.AutomationTestGrid','component.view.grid.AutomationGrid','component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:Ext.create('component.view.panel.GeneralManager',{
			region:'center',border:false,frame:false,template:template
		})
	});
});
