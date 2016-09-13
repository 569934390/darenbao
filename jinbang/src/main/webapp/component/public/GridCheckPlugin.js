Ext.define('component.public.GridCheckPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.gridCheckPlugin',
	init : function(grid) {
		var me = this;
		grid.on('cellclick',Ext.bind(me.selectEvn,me));
		grid.on('cellcontextmenu',Ext.bind(me.rightClickEvn,me));
		grid.on('itemdblclick',Ext.bind(me.itemdblclickEvn,me));
		me.callParent(arguments);
	},
	rightClickEvn:function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
		var me =this;
		var selected = grid.getSelectionModel().getSelection();
		selected.push(record);
		grid.getSelectionModel().select(selected);
	},
	itemdblclickEvn:function(grid, record, item, index, e, eOpts){
		var me =this;
		var selected = grid.getSelectionModel().getSelection();
		selected.push(record);
		grid.getSelectionModel().select(selected);
	},
	selectEvn:function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts){
		var me =this;
		var selected = grid.getSelectionModel().getSelection();
		if(cellIndex>0&&grid.getSelectionModel().isSelected(record)){
			grid.getSelectionModel().deselect(record);
		}
		else{
			selected.push(record);
			grid.getSelectionModel().select(selected);
		}
	}
});
