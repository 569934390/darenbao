/**
 * 站点同步信息列表
 */
Ext.define('component.resource.StationSyncPanel',{
	extend : 'Ext.panel.Panel',
	alias : 'stationSyncPanel',
	constructor:function(condition){
		condition=condition||{};
		var me = this;
		me.nodeId=1;
		me.createIndexTree();
		me.createStationSyncInfoGrid();
		me.createStaionSyncResultGrid();
		me.createDeviceStationAddPanel();

		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'border',
			items:[me.nodeIndexTree,me.stationSyncInfoGrid,me.stationSyncResultGrid,me.deviceStationAddPanel]
		});
		me.callParent([config]);
		me.on('render',function(){
			me.nodeIndexTree.getRootNode().cascadeBy(function(node){
				if(!Ext.isEmpty(node.data.id)&&node.data.id==1){
					me.nodeIndexTree.getSelectionModel().select(node);
					me.stationSyncInfoGrid.show();
			  		me.stationSyncResultGrid.hide();
				}
			});
		});
		me.down("[name=stationSync]").on('click',Ext.bind(me.stationSync,me));
	},
	stationSync:function(){
		var me=this;
//		var records=Ext.gridSelectCheck(me.stationSyncInfoGrid.getGrid(),true);
//		if(!records) return;
		var inputXML=new Object();
		var preQueryTime=Ext.Date.format(new Date(),Date.patterns.ISO8601LongString);
		inputXML.preQueryTime=preQueryTime;
		Ext.Ajax.request({
    		url : ctx+'/stationSync/getGpmsInfo.do',
    		method : 'POST',
    		params : {inputXML:Ext.encode(inputXML),method:'GetStationList'},
    		success : function(resp, action) {
    			me.stationSyncInfoGrid.getGrid().getStore().reload();
    		},
    		failure : function(resp,action) {
    			Ext.Msg.alert('提示','操作失败');
    		}
    	});
	
	},
	createIndexTree : function(){
		var me = this;
		var store = Ext.create('Ext.data.TreeStore', {
		    root: {
		        expanded: true,
		        children: [{
		        	id : '1',text: "站点同步信息列表", leaf: true
		        },{
		        	id : '2',text: "站点同步结果列表", leaf: true
		        },{
		        	id : '3',text: "设备站点添加", leaf: true
		        }]
		    }
		});
		
		me.nodeIndexTree = Ext.create('Ext.tree.Panel', {
			region : 'west',
			title : '站点同步',
			useArrows : true,
		    width: 150,
		    store: store,
		    rootVisible: false,
		    split:true,
	        collapsible: true,
		    listeners: {
	   	  		itemclick: function(view, node, item, index,e,eOpts){
	   	  			if(node.data.id==1) {
			   	  		me.stationSyncInfoGrid.show();
			   	  		me.stationSyncResultGrid.hide();
			   	  		me.deviceStationAddPanel.hide();
	   	  			}else if(node.data.id==2) {
			   	  		me.stationSyncInfoGrid.hide();
			   	  		me.stationSyncResultGrid.show();
			   	  		me.deviceStationAddPanel.hide();
	   	  			}else if(node.data.id==3) {
			   	  		me.stationSyncInfoGrid.hide();
			   	  		me.stationSyncResultGrid.hide();
			   	  		me.deviceStationAddPanel.show();
	   	  			}
	 			}
	 		}
		});
	},
	createStationSyncInfoGrid : function(){
		var me = this;
		me.stationSyncInfoGrid = Ext.create('component.resource.StationSyncInfoGrid', {
			region : 'center'
		});
	},
	createStaionSyncResultGrid : function(){
		var me = this;
		
		me.stationSyncResultGrid = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'STATION_SYNC_RESULT',
			title : '站点同步结果列表',
			pageSize : 25,
			loadMask:true,
			region : 'center',
			displayFunctions : [ {
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state,value, 1);
				}
			} ],
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
	createDeviceStationAddPanel:function(){
		var me = this;
		me.deviceStationAddGrid = Ext.create('Ext.grid.Panel', {
			title:'设备站点信息',
			region:'center',
		    forceFit : true,
		    store:Ext.create('component.public.AjaxStore',{
		    	url:ctx+'/stationSync/getDeviceStationList.do',
		    	fields:['nodeId','nodeName','stationId','stationName']
		    }),
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    columns: [{ 
		    	header: "网元标识",dataIndex: 'nodeId'
		    },{ 
		    	header: "网元名称",dataIndex: 'nodeName'
		    },{ 
		    	header: "站点标识",dataIndex: 'stationId'
		    },{ 
		    	header: "站点名称",dataIndex: 'stationName'
		    }],
			dockedItems:[{
				xtype:'toolbar',
				dock:'top',
				items:[{
						text : '查询',
						iconCls : 'toolbar-view',
						handler : Ext.bind(me.getDeviceStationList,me)
					},'-',{
						text : '修改',
						iconCls : 'toolbar-edit',
						handler : Ext.bind(me.modifyDeviceStationList,me)
					},'-',{
						text : '删除',
						iconCls : 'toolbar-delete',
						handler :Ext.bind(Ext.showConfirmWindow,null,[me,me.deleteDeviceStationList,common.constant.buttonOperate.remove],0)
					}]
				}]
			
		});
		me.nodeTreePanel = Ext.create('component.business.NodeTreePanel', {
					region:'west',
					checkable:true,
					listeners:{
						checkchange:function(){
							me.getNodeIdList();
						}
					},
					width : 250
		});
		me.deviceStationAddPanel=Ext.create('Ext.panel.Panel',{
			title:'站点设备添加',
			hidden:true,
			layout:'border',
			region:'center',
			items:[me.nodeTreePanel,me.deviceStationAddGrid]
		});
	
	},
	getDeviceStationList:function(){
		var me=this;
		var nodeIdList=me.getNodeIdList();
		me.deviceStationAddGrid.getStore().getProxy().extraParams.nodeIdList=nodeIdList;
		me.deviceStationAddGrid.getStore().load();
	},
	modifyDeviceStationList:function(){
		var me=this;
		if(!me.deviceStationWindow){
			me.deviceStationWindow=Ext.create('Ext.window.Window',{
				width:800,
				height:450,
				closeAction:'hide',
				title:'编辑设备站点信息',
				layout:'fit',
				items:[Ext.create('component.resource.StationSyncInfoGrid',{name:'stationInfoGrid'})],
				listeners:{
					beforeshow:function(){
						var nodeRecords=Ext.gridSelectCheck(me.deviceStationAddGrid,true);
						if(!nodeRecords) return false;
						this.setNodeRecords(nodeRecords);
					}
				},
				buttonAlign:'center',
				buttons:[{
					name:'saveBtn',
					text:'确定',
				},{
					name:'cancelBtn',
					text:'取消',
				}],
				setNodeRecords:function(nodeRecords){
					this.nodeRecords=nodeRecords;
				},
				getNodeRecords:function(){
					return this.nodeRecords;
				}
			});
			me.deviceStationWindow.down('[name=saveBtn]').on('click',Ext.bind(Ext.showConfirmWindow,null,[me,me.saveOrUpdateDeviceStation,common.constant.buttonOperate.saveOrUpdate],0));
			me.deviceStationWindow.down('[name=cancelBtn]').on('click',function(){
				me.deviceStationWindow.close();
			});
		}
		me.deviceStationWindow.show();
	},
	saveOrUpdateDeviceStation:function(){
		var me=this;
		var grid=me.deviceStationWindow.down('[name=stationInfoGrid]').getGrid();
		var stationRecord=Ext.gridSelectCheck(grid, false);
		if(!stationRecord) return;
		var deviceStationInfoList=[];
		Ext.each(me.deviceStationWindow.getNodeRecords(),function(record,index){
			var deviceStationInfo=new Object();
			deviceStationInfo.nodeId=record.get('nodeId');
			deviceStationInfo.attrValue=stationRecord.get('STATION_NAME')+'('+stationRecord.get('STATION_ID')+')';
			deviceStationInfo.nodeTypeAttrId=19;
			deviceStationInfo.updateTime=Ext.Date.format(new Date(),Date.patterns.ISO8601Long);
			deviceStationInfoList.push(deviceStationInfo);
		});
		me.saveOrDeleteDeviceStationList('save',deviceStationInfoList);
	},
	deleteDeviceStationList:function(){
		var me=this;
		var nodeRecords=Ext.gridSelectCheck(me.deviceStationAddGrid,true);
		if(!nodeRecords) return;
		var deviceStationInfoList=[];
		Ext.each(nodeRecords,function(record,index){
			var deviceStationInfo=new Object();
			deviceStationInfo.nodeId=record.get('nodeId');
			deviceStationInfo.nodeTypeAttrId=19;
			deviceStationInfoList.push(deviceStationInfo);
		});
		me.saveOrDeleteDeviceStationList('delete',deviceStationInfoList);
	},
	saveOrDeleteDeviceStationList:function(operate,deviceStationInfoList){
		var me=this;
		Ext.Ajax.request({
        	url: webRoot+"stationSync/modifyDeficeStationList.do",
        	method:'POST',
        	params: {
        		operate:operate,
        		deviceStationInfoList:Ext.encode(deviceStationInfoList)
        	},
        	success: function(response,opts){
        		me.deviceStationWindow.close();
        		me.deviceStationAddGrid.getStore().reload();
        	},
        	failure: function(){
        		
        	}
           
        });
	},
	getNodeIdList:function(){
		var me=this;
		var records=me.nodeTreePanel.getChecked();
		var nodeIdList=[];
		Ext.each(records,function(record,index){
			var attributes=Ext.decode(record.raw.attributes);
			if(Number(attributes.symbolStyle)>=3){
				nodeIdList.push(attributes.nodeId);
			}
			console.info(attributes.nodeId);
		});
		return nodeIdList.join(",");
	}
});