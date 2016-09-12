Ext.define('component.public.FormButton', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.formButton',
	constructor : function(config) {
		config = config || {};
		var me = this;
		Ext.applyIf(config, {
			border:false,
			height:27,
			width:140,
			buttons:[{
				xtype:'button',
				text:'搜索',
				maxWidth:55,
				iconCls:'toolbar-search',
	        	name:'searchBtn'
			},{
				text:'重置',
		        iconCls:'toolbar-delete',
		        name:'delete',
				maxWidth:55,
		        handler: function() {
		            this.up('form').getForm().reset();
		        }
			}]
		});
		me.callParent([config]);
	}
});