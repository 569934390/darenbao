Ext.define('component.business.UserTree', {
	createTree : function(obj) {
		var tree = Ext.create('component.tree.GeneralTree',Ext.apply({
			width:200,
			rootDraggable : true,
			viewConfig : {
				plugins : {
					ddGroup : 'tree-to-form',
					enableDrop : false,
					ptype : 'treeviewdragdrop' // 可以拖拽
				}
			}
		},obj));
		return tree;
	}
});