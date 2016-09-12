Ext.define('component.public.ComboCheckPlugin', {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.comboCheckPlugin',
	init : function(combo) {
		var treeid = Math.random() + '';
		var me = this;
		if(combo.queryMode=='local') {
			me.initGrid(combo);
			combo.tpl = Ext.String.format('<div id="{0}" style="height:{1}px;"></div>', treeid, 20 * combo.getStore().getCount() + 12);
			combo.multiSelect = true;
			combo.on('expand', function () {
				if (!me.grid.rendered) {
					me.grid.render(treeid);
				}
			});
		}
		else{
			//combo.getStore().load();
			//combo.getStore().on('load',function(){
			//	me.initGrid(combo);
			//	combo.tpl = Ext.String.format('<div id="{0}" style="height:{1}px;"></div>', treeid, 20 * combo.getStore().getCount() + 12);
			//	combo.multiSelect = true;
			//	//combo.on('expand', function () {
			//		if (!me.grid.rendered) {
			//			me.grid.render(treeid);
			//		}
			//	//});
			//});
		}
		me.callParent(arguments);
	},
	initGrid:function(combo){
		var grid = Ext.create('Ext.grid.Panel', {
			border:false,hideHeaders:true,forceFit: true,split: true,
			selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
		    store: combo.getStore(),
		    columns: [
		        { text: '全选', dataIndex: combo.displayField}
		    ]
//			,tbar:[{
//		    	xtype:'checkbox',
//		    	height:20,
//		    	style:'margin-left:3px',
//		    	listeners:{
//		    		change:function(checkbox,value,oldValue,e){
//		    			if(value){
//		    				grid.getSelectionModel().selectAll();
//		    			}
//		    			else{
//		    				grid.getSelectionModel().deselectAll();
//		    			}
//		    		}
//		    	}
//		    }]
		});
		grid.on('cellclick',function( grid, td, cellIndex, record, tr, rowIndex, e, eOpts ){
			var selected = grid.getSelectionModel().getSelection();
			if(cellIndex>0&&grid.getSelectionModel().isSelected(record)){
				grid.getSelectionModel().deselect(record);
			}
			else{
				selected.push(record);
				grid.getSelectionModel().select(selected);
			}
			var records = grid.getSelectionModel().getSelection(),rawValue='',value=[];
			for(var i=0;i<records.length;i++){
				rawValue += records[i].get(combo.displayField);
				value.push(records[i].get(combo.valueField));
				if(i<records.length-1){
					rawValue+=',';
				}
			}
			combo.setMutiValue(value);
			
			combo.setRawValue(rawValue);
		});
		combo.setMutiValue=function(val){
			combo.mutilValue = val;
		}
		combo.getValue = function(){
			return combo.mutilValue;
		}
		combo.setValue = function(value){
			if(Ext.isEmpty(value)||!Ext.isEmpty(value.type))
				return;
			combo.setMutiValue(value);
			grid.getStore().load({
				callback:function(records){
					grid.selectRecords = [];
					var displayValue=[],vals = value.split(',');
					for(var r in records){
						for(var val in vals){
							if(records[r].get(combo.valueField)==parseInt(vals[val])){
								displayValue.push(records[r].get(combo.displayField));
								grid.selectRecords.push(records[r]);
							}
						}
					}
					combo.setRawValue(displayValue.join(','));
				}
			});
			combo.on('expand',function(){
				var selectMode = grid.getSelectionModel();
				selectMode.select(grid.selectRecords);
			});
		}
		this.grid = grid;
	}
});
