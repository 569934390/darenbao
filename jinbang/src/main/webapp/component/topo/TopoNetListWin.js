/**
 * 子网列表
 */
Ext.define('component.topo.TopoNetListWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoNetListWin',
	constructor:function(condition){
		var me = this;
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[{
	   	 		xtype: 'customPanel',
	   			region : 'center',
	   			title: '子网列表',
	   	 		aliasName: 'TOPO_NET_GRID',
	   	 		pageSize: 20,
	   	 		init: function(panel, toolPanel, grid) {
		   	 		panel.addDocked([{//菜单栏
		 				xtype: 'toolbar',
		 				dock: 'top',
		 				items:[{
			   	 			text: '定位',
			   	 			iconCls : 'toolbar-location',
		   	 				handler: function(){
			   	 				var length = grid.getSelectionModel().getCount();
			 					if(length <1){
			 						Ext.Msg.alert('提示','请选择一条节点信息！'); 
			 						return;
			 					}
			 					if(length >1){
			 						Ext.Msg.alert('提示','只能选择一条节点信息！'); 
			 						return;
			 					}
		 						var selected = grid.getSelectionModel().getSelection();
		 						var symbolId = selected[0].data['symbolId'];
		 						var treeParentId = selected[0].data['treeParentId'];
		 						var mapParentName = selected[0].data['mapParentName'];

		 						var node = topoTreePanel.selectNode(symbolId,function(node){
		 							var attributes = Ext.JSON.decode(node.raw.attributes);
		 							topoClickLocate(node);
		 						});
		 						me.close();
		   	 				}
		 				}]
					}]);
//		   	 		grid.on("itemdblclick",resourceEdit);
			   	 	// 给控件添加右键菜单触发事件(itemcontextmenu)
			   	 	grid.on("itemcontextmenu",function rule_rowcontextmenu(gd, record, item, index, e, eOpts ){
			   	 		//禁用浏览器的右键相应事件
			   	 		e.preventDefault();
			   	 		e.stopEvent();
			   	 		//禁用浏览器的右键相应事件
			   	 		
			   	 	    var menus = Ext.create('Ext.menu.Menu', {
			   	 	        items: [{
				   	 			text: '定位',
				   	 			iconCls : 'toolbar-edit'
			   	 			}]
			   	 	    });
			   	 	    menus.showAt(e.getXY());
			   	 	});
	   	 		}
	        }]
		});
		me.callParent([config]);
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'saveLayoutSet':
				me.saveTopoSet();
				break;
		}
	}
});