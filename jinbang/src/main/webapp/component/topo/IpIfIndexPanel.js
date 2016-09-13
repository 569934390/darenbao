/**
 * 网元端口信息列表
 */
Ext.define('component.topo.IpIfIndexPanel',{
	extend : 'Ext.panel.Panel',
	alias : 'ipIfIndexPanel',
	constructor:function(condition){
		condition=condition||{};
		var me = this;
		me.nodeId=1;
		me.createIndexTree();
		me.createNetIndexGrid();
		me.createNetToMediaGrid();
		me.createNetEndVlanGrid();
		me.createRouteGrid();

		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'border',
			items:[me.nodeIndexTree,me.indexGrid,me.netToMediaGrid,me.netEndVlanGrid,me.routeGrid]
		});
		me.callParent([config]);
		me.on('render',function(){
			me.nodeIndexTree.getRootNode().cascadeBy(function(node){
				if(!Ext.isEmpty(node.data.id)&&node.data.id==1){
					me.nodeIndexTree.getSelectionModel().select(node);
					me.indexGrid.show();
			  		me.netToMediaGrid.hide();
			  		me.netEndVlanGrid.hide();
			  		me.routeGrid.hide();
				}
			});
		});
	},
	createIndexTree : function(){
		var me = this;
		var store = Ext.create('Ext.data.TreeStore', {
		    root: {
		        expanded: true,
		        children: [{
		        	text: "端口信息列表", expanded: true, 
		        	children: [{
			        	id : '1',text: "接口列表", leaf: true
			        },{
			        	id : '2',text: "ARP列表", leaf: true
			        },{
			        	id : '3',text: "VLAN列表", leaf: true
			        },{
			        	id : '4',text: "路由列表", leaf: true
			        }]
		        }]
		    }
		});
		
		me.nodeIndexTree = Ext.create('Ext.tree.Panel', {
			region : 'west',
			title : '端口信息列表',
			useArrows : true,
		    width: 120,
		    store: store,
		    rootVisible: false,
		    split:true,
	        collapsible: true,
		    listeners: {
	   	  		itemclick: function(view, node, item, index,e,eOpts){
	   	  			if(node.data.id==1) {
			   	  		me.indexGrid.show();
			   	  		me.netToMediaGrid.hide();
			   	  		me.netEndVlanGrid.hide();
			   	  		me.routeGrid.hide();
//			   	  		me.indexGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}else if(node.data.id==2) {
			   	  		me.indexGrid.hide();
			   	  		me.netToMediaGrid.show();
			   	  		me.netEndVlanGrid.hide();
			   	  		me.routeGrid.hide();
//			   	  		me.netToMediaGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}else if(node.data.id==3) {
	   	  				me.indexGrid.hide();
	   	  				me.netToMediaGrid.hide();
	   	  				me.netEndVlanGrid.show();
	   	  				me.routeGrid.hide();
//			   	  		me.netEndVlanGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}else if(node.data.id==4) {
	   	  				me.indexGrid.hide();
	   	  				me.netToMediaGrid.hide();
	   	  				me.netEndVlanGrid.hide();
	   	  				me.routeGrid.show();
//			   	  		me.netEndVlanGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}
	 			}
	 		}
		});
	},
	createNetIndexGrid : function(){
		var me = this;
		me.indexGrid = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'NMS_IP_IF_INDEX_INFO',
			title : '接口列表',
			pageSize : 25,
			region : 'center',
			displayFunctions : [
			{
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state,
							value, 1);
				}
			}],
// dockedItems:[{//菜单栏
//	 				xtype: 'toolbar',
//	 				dock: 'top',
//	 				items:[{
//		   	  	    	text: '查看',iconCls : 'toolbar-view',name:'roleView'
//	   	 			},'-',{
//		   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//	   	 			},'-',{
//		   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//	   	 			},'-',{
//		   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//	   	 			},'-',{
//		   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
//	   	 			}]
//			}],
			after : function(grid) {
				// alert(grid);//对读出数据的处理过程均可写在这里
			},
			gridCfg: {
	   	 		columnLines: true,
	   	 		multiSelect: false,
	   	 		selModel: {
	   	 			selType:'checkboxmodel'
	   	 		}
   	 		},
			init : function(panel, toolPanel, grid) {
			}
		
    	});
	},
	createNetToMediaGrid : function(){
		var me = this;
		
		me.netToMediaGrid = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'NMS_IP_NET_TO_MEDIA',
			title : 'ARP列表',
			pageSize : 25,
			region : 'center',
			displayFunctions : [ {
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state,
							value, 1);
				}
			} ],
// dockedItems:[{//菜单栏
// xtype: 'toolbar',
// dock: 'top',
//	 				items:[{
//		   	  	    	text: '查看',iconCls : 'toolbar-view',name:'roleView'
//	   	 			},'-',{
//		   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//	   	 			},'-',{
//		   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//	   	 			},'-',{
//		   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//	   	 			},'-',{
//		   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
//	   	 			}]
//			}],
			after : function(grid) {
				// alert(grid);//对读出数据的处理过程均可写在这里
			},
			gridCfg: {
	   	 		columnLines: true,
	   	 		multiSelect: false,
	   	 		selModel: {
	   	 			selType:'checkboxmodel'
	   	 		}
   	 		},
			init : function(panel, toolPanel, grid) {
			}
		
    	});
	},
	createNetEndVlanGrid : function(){
		var me = this;
		
		
		me.netEndVlanGrid = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'NMS_IP_AD_ENT_VLAN',
			title : 'VLAN列表',
			pageSize : 25,
			region : 'center',
			displayFunctions : [ {
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state,
							value, 1);
				}
			} ],
// dockedItems:[{//菜单栏
// xtype: 'toolbar',
// dock: 'top',
//	 				items:[{
//		   	  	    	text: '查看',iconCls : 'toolbar-view',name:'roleView'
//	   	 			},'-',{
//		   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//	   	 			},'-',{
//		   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//	   	 			},'-',{
//		   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//	   	 			},'-',{
//		   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
//	   	 			}]
//			}],
			after : function(grid) {
				// alert(grid);//对读出数据的处理过程均可写在这里
			},
			gridCfg: {
	   	 		columnLines: true,
	   	 		multiSelect: false,
	   	 		selModel: {
	   	 			selType:'checkboxmodel'
	   	 		}
   	 		},
			init : function(panel, toolPanel, grid) {
			}
		
    	});
	},
	createRouteGrid : function(){
		var me = this;
		
		
		me.routeGrid = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'NMS_IP_ROUTE',
			title : '路由列表',
			pageSize : 25,
			region : 'center',
			displayFunctions : [ {
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state, value, 1);
				}
			} ],
// dockedItems:[{//菜单栏
// xtype: 'toolbar',
// dock: 'top',
// items:[{
//		   	  	    	text: '查看',iconCls : 'toolbar-view',name:'roleView'
//	   	 			},'-',{
//		   	 			text: '新增',iconCls : 'toolbar-add',name:'roleAdd'
//	   	 			},'-',{
//		   	 			text: '修改',iconCls : 'toolbar-edit',name:'roleEdit',resourceId:6
//	   	 			},'-',{
//		   	 			text: '删除',iconCls : 'toolbar-delete',name:'roleDelete'
//	   	 			},'-',{
//		   	 			text: '权限配置',iconCls : 'alarmRelateInfo',name:'privCfg'
//	   	 			}]
//			}],
			after : function(grid) {
				// alert(grid);//对读出数据的处理过程均可写在这里
			},
			gridCfg: {
	   	 		columnLines: true,
	   	 		multiSelect: false,
	   	 		selModel: {
	   	 			selType:'checkboxmodel'
	   	 		}
   	 		},
			init : function(panel, toolPanel, grid) {
			}
		
    	});
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'nodeSync':
				me.nodeSync();
				break;
		}
	}
});