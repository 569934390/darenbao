Ext.define('component.business.SelectorWindow', {
	extend : 'component.public.CustomGridEditorWindowComponent',
	constructor : function(config) {
		var me = this;
		me.dropViewer = Ext.create(config.dropView).createDropView(Ext.apply({
			region : 'center'
		}, config.dropViewCfg))
		me.selectTree = Ext.create(config.selector).createTree(Ext.apply({
			region : 'west',
			cls:'select_tree'
		}, config.selectorCfg));
		me.items = [me.selectTree, me.dropViewer];
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
	},
	clear : function() {
		var me = this;
		me.dropViewer.getStore().removeAll();
	},
	deleteSelect : function() {
		this.dropViewer.getStore().remove(this.dropViewer.getSelectionModel().getSelection());
	},
	getStore : function() {
		return this.dropViewer.getStore();
	},
	layout : 'border'
});