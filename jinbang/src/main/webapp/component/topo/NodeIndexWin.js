/**
 * 网元端口信息列表
 */
Ext.define('component.topo.NodeIndexWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.nodeIndexWin',
	constructor:function(condition){
		var me = this;
		me.curNode = condition.curNode;
		if(Ext.isEmpty(me.curNode.raw.attributes)){
			me.nodeId= me.curNode.raw.nodeId;
		}else{
			var attributes = Ext.JSON.decode(me.curNode.raw.attributes);
			me.nodeId=attributes.nodeId;
		}
		me.createIndexTree();
		me.createNetIndexGrid();
		me.createNetToMediaGrid();
		me.createNetEndVlanGrid();
		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'border',
			items:[me.nodeIndexTree,me.indexGrid,me.netToMediaGrid,me.netEndVlanGrid]
		});
		
		me.callParent([config]);
	},
	createIndexTree : function(){
		var me = this;
		var store = Ext.create('Ext.data.TreeStore', {
		    root: {
		        expanded: true,
		        children: [{
		        	text: "端口信息列表", expanded: true, 
		        	children: [{
			        	id : '1',text: "接口信息", leaf: true
			        },{
			        	id : '2',text: "ARP信息", leaf: true
			        },{
			        	id : '3',text: "VLAN信息", leaf: true
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
			   	  		me.indexGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}else if(node.data.id==2) {
			   	  		me.indexGrid.hide();
			   	  		me.netToMediaGrid.show();
			   	  		me.netEndVlanGrid.hide();
			   	  		me.netToMediaGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}else if(node.data.id==3) {
	   	  				me.indexGrid.hide();
	   	  				me.netToMediaGrid.hide();
	   	  				me.netEndVlanGrid.show();
			   	  		me.netEndVlanGrid.getStore().load({params:{start:0, limit:100,nodeId:me.nodeId}});
	   	  			}
	 			}
	 		}
		});
	},
	createNetIndexGrid : function(){
		var me = this;

		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/ipIfIdex/findIpIfIndexPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
	        fields: [
	             'ipAdEntIfIndex', 'ipAdEntAddr','ipAdEntMask','ipAdEntType','ipAdEntDesc',
	             'ipAdEntState','ipAdEntVlanId','ipAdEntMac', 'stateDate'
	        ],
		    pageSize: 100
		});
		store.load({params:{start:0, limit:100,nodeId:me.nodeId}});

		me.indexGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
		    store: store,
		    forceFit : true,
			multiSelect: true,
		    columns: [{ 
		    	header: "端口索引",dataIndex: 'ipAdEntIfIndex',width:60
		    },{ 
		    	header: "端口IP",dataIndex: 'ipAdEntAddr',sortable: true
		    },{ 
		    	header: "端口MASK",dataIndex: 'ipAdEntMask',sortable: true
		    },{ 
		    	header: "端口MAC",dataIndex: 'ipAdEntMac',sortable: true
		    },{ 
		    	header: "端口类型",dataIndex: 'ipAdEntType',width:60
		    },{ 
		    	header: "端口状态",dataIndex: 'ipAdEntState',width:60,
		    	renderer:function(value, meta, record) {
	        		if(value=='1') {
	        			return "接口正常";
	        		}else if(value=='2') {
	        			return "接口异常";
	        		}else if(value=='3') {
	        			return "接口测试中";
	        		}
	        	}
		    },{ 
		    	header: "端口描述",dataIndex: 'ipAdEntDesc',sortable: true
		    },{ 
		    	header: "Vlan号",dataIndex: 'ipAdEntVlanId',width:60
		    },{
	            header: "采集时间",dataIndex: 'stateDate',hidden:true,
	            align:'center',format : 'Y-m-d H:i:s'
	        }],
			bbar : new Ext.PagingToolbar({
	            pageSize: 100,
				displayMsg : 'Displaying{0} - {1} of {2}',
	            store: store,
	            displayInfo: true
	        })
		});
	},
	createNetToMediaGrid : function(){
		var me = this;
		
		var store = new Ext.data.JsonStore({
			proxy: {
				type: 'ajax',
				url: webRoot + '/ipIfIdex/findNetToMediaPage.do',
				reader: {
					type: 'json',
					root: 'result',
					totalProperty : 'totalCount'
				}
			},
			fields: [
		         'ipNetToMediaIfIndex', 'ipNetToMediaNetAddress', 'ipNetToMediaPhysAddress', 
		         'ipNetToMediaType','ipNetToMediaNodeType','stateDate'
	        ],
	        pageSize: 100
		});
		
		me.netToMediaGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
			store: store,
			hidden : true,
			forceFit : true,
			multiSelect: true,
			columns: [{ 
				header: "端口索引",dataIndex: 'ipNetToMediaIfIndex',width:60, sortable: true
			},{ 
				header: "对端IP",dataIndex: 'ipNetToMediaNetAddress',width:80,sortable: true
			},{ 
				header: "对端物理地址",dataIndex: 'ipNetToMediaPhysAddress',sortable: true
			},{ 
				header: "地址映射类型",dataIndex: 'ipNetToMediaType',width:80,sortable: true
			},{
				header: "对端节点类型",dataIndex: 'ipNetToMediaNodeType',width:80,
		    	renderer:function(value, meta, record) {
	        		if(value=='1') {
	        			return "端口";
	        		}else if(value=='2') {
	        			return "设备";
	        		}
	        	}
		    },{
	            header: "采集时间",dataIndex: 'stateDate',align:'center',format : 'Y-m-d H:i:s'
			}],
			bbar : new Ext.PagingToolbar({
				pageSize: 100,
				displayMsg : 'Displaying{0} - {1} of {2}',
				store: store,
				displayInfo: true
			})
		});
	},
	createNetEndVlanGrid : function(){
		var me = this;
		
		var store = new Ext.data.JsonStore({
			proxy: {
				type: 'ajax',
				url: webRoot + '/ipIfIdex/findNetVlanPage.do',
				reader: {
					type: 'json',
					root: 'result',
					totalProperty : 'totalCount'
				}
			},
			fields: [
			         'ipAdEntIfIndex', 'ipAdEntVlanId', 'ipAdEntVlanIfIndex', 'stateDate'
	         ],
	         pageSize: 100
		});
		
		me.netEndVlanGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
			store: store,
			hidden : true,
			forceFit : true,
			multiSelect: true,
			columns: [{ 
				header: "端口索引",dataIndex: 'ipAdEntIfIndex', sortable: true
			},{ 
				header: "VlanID号",dataIndex: 'ipAdEntVlanId',sortable: true
			},{ 
				header: "VLANID索引",dataIndex: 'ipAdEntVlanIfIndex',sortable: true
		    },{
	            header: "采集时间",dataIndex: 'stateDate',align:'center',format : 'Y-m-d H:i:s'
			}],
			bbar : new Ext.PagingToolbar({
				pageSize: 100,
				displayMsg : 'Displaying{0} - {1} of {2}',
				store: store,
				displayInfo: true
			})
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