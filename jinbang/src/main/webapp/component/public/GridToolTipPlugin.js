Ext.define('component.public.GridToolTipPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.gridToolTipPlugin',
	init : function(grid) {
		var me = this;
		grid.on('afterrender', Ext.bind(me.initToolTip,me,[grid],0));
		me.callParent(arguments);
	},
	updateToolTip:function(tip,eOpts,grid,columns,cellIndex){
		var me=this;
		var label=columns[cellIndex]['text']+":</br>";
		var dataIndex=columns[cellIndex]['dataIndex'];
		var cellElement=tip.triggerElement;
		var rowElement=cellElement.parentNode;
		var record=grid.getView().getRecord(cellElement.parentNode);
		var rowIndex=grid.getView().indexInStore(rowElement);
		var value=null;
		if(Ext.isFunction(me.toolTipRenderer)){
			value=me.toolTipRenderer.apply(me||me.scope,[grid,record,record.get(dataIndex),label,cellIndex,rowIndex,cellElement,rowElement,dataIndex,eOpts]);
		}else{
			value=record&&record.get(dataIndex)||"";
		}
		if(Ext.isEmpty(value)||value===false){
			return false;
		}
		tip.update(label+value);
	},
	initToolTip : function(grid) {
		var me=this;
		var view = grid.getView();
		var columns=view.getHeaderCt().getGridColumns();
		var tip = Ext.create('Ext.tip.ToolTip', {
					// The overall target element.
					target : view.el,
					// Each grid row causes its own separate show and hide.
					delegate : 'td .x-grid-cell',
					dismissDelay:10000,
//					deletate : view.cellSelector,
					// Moving within the row should not hide the tip.
					trackMouse : true,
					// Render immediately so that tip.body can be referenced
					// prior to the first show.
					renderTo : Ext.getBody(),
					listeners : {
						// Change content dynamically depending on which element
						// triggered the show.
						beforeshow : function updateTipBody(tip,eOpts) {
							var cellIndex = Number(tip.triggerElement.cellIndex);
							if(Ext.isFunction(me.toolTipRenderer)||Ext.isEmpty(me.cellIndex)){
								if(Ext.isEmpty(columns[cellIndex]['text'])){
									return false;
								}
								return me.updateToolTip(tip,eOpts,grid,columns,cellIndex);						
							}else{
								if(me.cellIndex.indexOf(cellIndex)>-1){
									return me.updateToolTip(tip,eOpts,grid,columns,cellIndex);
								}else{
									return false;
								}
							}
						}
					}
				});

	}
});
