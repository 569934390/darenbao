Ext.onReady(function(){
	Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[Ext.create('component.dcc.view.BusinessStaffTestPanel')]
	})
});