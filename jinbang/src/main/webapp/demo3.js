Ext.onReady(function() {
	var textfield=Ext.create('component.public.TextField',{value:1});
//	textfield.setValue('1');
	Ext.create('Ext.container.Viewport',{
		layout:'form',
		items:[textfield]
	});
	textfield.reset();
});