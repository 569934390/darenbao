Ext.define('component.alarm.AlarmRuleConfigPanel',{
	extend:'Ext.panel.Panel',
	alias:'widget.alarmRuleConfigPanel',
	requires:['component.business.NodeTreePanel','component.business.KpiTreePanel'],
	constructor:function(condition){
		var me = this;
		me.nodeGrid=me.initNodeGrid(condition);
		me.kpiGrid=me.initKpiGrid(condition);
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.nodeGrid,me.kpiGrid]
		});
		me.callParent([config]);
		me.on("resize",me.panelResize.createDelegate(me));
		me.on("show",me.panelShow.createDelegate(me));
	},
	panelResize:function(panel,width,height){
		this.nodeGrid.setHeight(height>>1);
	},
	panelShow:function(){
		if(this.showCallBack){
			this.showCallBack.apply(this,[this]);
		}
	},
	initNodeGrid:function(condition){
		var me = this;
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot+'ruleInfoManager/loadRuleInfoNodeConfig.do'
		    },
		   	fields: ['nodeId', 'symbolName1','nodeTypeId', 'topoTypeDisplayName']
		});
		store.load();
		
		me.nodeId=Ext.id();
		me.nodeName=Ext.id();
		var grid = Ext.create('Ext.grid.Panel',{
			title:'针对节点对象',
		    store: store,
		    region:'north',
		    loadMask:true,
		    columnLines: true,
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    tbar:[{text:"添加",handler:me.buttonEvent.createDelegate(me,['addNode'],0)},"-",
		          {text:"删除",handler:me.buttonEvent.createDelegate(me,['deleteNode'],0)},"-",
		          "节点名称：",{xtype:'textfield',id:me.nodeName},{xtype:'tbspacer',width:10},
		          {text:"查询",handler:me.buttonEvent.createDelegate(me,['queryNode'],0)}
		          ],
		    columns: {
		        defaults: {
		            width: 120,
		            sortable: true
		        },
		        items: [
		            {header: '节点标识',dataIndex: 'nodeId',width:120},
		            {header: '节点名称',dataIndex: 'symbolName1',width:120},
		            {header: '节点类型',dataIndex: 'topoTypeDisplayName',width:120},
		            {header: '节点类型',dataIndex: 'nodeTypeId',width:120,hidden:true,hideable:false}

		        ]
		    },
	        forceFit: true
		});
		return grid;		
	},
	initKpiGrid:function(condition){
		var me = this;
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot+'ruleInfoManager/loadRuleInfoKpiConfig.do'
		    },
		   	fields:['kpiId','kpiName']
		});
		store.load();

		me.kpiId=Ext.id();
		me.kpiName=Ext.id();
		var grid = Ext.create('Ext.grid.Panel',{
			title:'针对告警指标',
			store: store,
			region:'center',
			loadMask:true,
			columnLines: true,
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
			tbar:[{text:"添加",handler:me.buttonEvent.createDelegate(me,['addKpi'],0)},"-",
		          {text:"删除",handler:me.buttonEvent.createDelegate(me,['deleteKpi'],0)},"-",
		          "告警名称：",{xtype:'textfield',id:me.kpiName},{xtype:'tbspacer',width:10},
		          {text:"查询",handler:me.buttonEvent.createDelegate(me,['queryKpi'],0)
            }],
		    columns:{
		        defaults: {
		            width: 120,
		            sortable: true
		        },
		        items: [
		            {header: '告警标识',dataIndex: 'kpiId',width:120},
		            {header: '告警名称',dataIndex: 'kpiName',width:120}
		        ]
		    },
	        forceFit: true
		});
		return grid;
	},
	getNodeEditWin:function(){
		var me=this;
		if(!me.nodeEditWin){
			me.nodeEditWin=Ext.create('component.business.NodeTreePanelWindow',{
				checkable:true,
				callback:function(nodeTreeNodes){
					return me.addGridData(me.nodeGrid,nodeTreeNodes,"nodeId","symbolName1");
				},
				showCallBack:function(){
		    		me.treeReload(me.nodeEditWin.getNodeTreePanel());
				}
			});
		}
		return me.nodeEditWin;
	},
	getKpiEditWin:function(){
		var me=this;
		if(!me.kpiEditWin){
			me.kpiEditWin=Ext.create('component.business.KpiTreePanelWindow',{
				width : 400,
				callback:function(kpiTreeNodes){
					return me.addGridData(me.kpiGrid,kpiTreeNodes,"kpiId","kpiName");
				},
				showCallBack:function(){
					me.kpiEditWin.getKpiTreePanel().getRootNode().removeAll();
		    		me.treeReload(me.kpiEditWin.getNodeTreePanel());
				}
			});
		}
		return me.kpiEditWin;
	},
	setState:function(state){
		this.state=state;
	},
	getState:function(state){
		return this.state;
	},
	setRuleId:function(ruleId){
		this.ruleId=ruleId;
	},
	treeReload:function(tree){

	},
	joinKpi:function(records){
		if(records.length==0){
			return null;
		}
		var array=[];
		for ( var int = 0; int < records.length; int++) {
			array.push("AlarmInfo.kpi_id=="+records[int].get("kpiId"));
		}
		return array.join(" or ");
	},
	joinRuleApplyToObject:function(records,ruleId){
		if(records.length==0){
			return null;
		}
		var array=[];
		for ( var int = 0; int < records.length; int++) {
			var obj=new Object();
			obj.ruleId=ruleId;
			obj.objectType=2;
			obj.objectId=records[int].get("nodeId");
			array.push(obj);
		}
		return array;
	},
	getNodeIdSet:function(){
		var me=this;
		me.nodeGrid.getStore().clearFilter();
		var ruleApplyObjectParams=me.joinRuleApplyToObject(me.nodeGrid.getStore().getRange(0,me.nodeGrid.getStore().getCount()-1),me.ruleId);
		if(Ext.isEmpty(ruleApplyObjectParams)){
			return "";
		}
		var arr=[];
		for ( var int = 0; int < ruleApplyObjectParams.length; int++) {
			arr.push(ruleApplyObjectParams[int].objectId);
		}
		return arr.join(",");
	},
	saveGridData:function(){
		var me=this;
		var ruleInfoParam=new Object();
		ruleInfoParam.ruleId=me.ruleId;
		ruleInfoParam.objectType=2;
		me.kpiGrid.getStore().clearFilter();
		if(0==me.kpiGrid.getStore().getCount()){
			Ext.Msg.alert("提示","请填写针对告警指标");
			return false;
		}
		ruleInfoParam.condition=me.joinKpi(me.kpiGrid.getStore().getRange(0,me.kpiGrid.getStore().getCount()-1));
		Ext.Ajax.request({
        	url: webRoot+"ruleInfoManager/saveRuleInfoConfig.do",
        	method:'POST',
        	params: {
        			ruleInfo:Ext.encode(ruleInfoParam),
        			state:me.state
        	},
        	success: function(response,opts){
        		me.loadGridData();
        	},
        	failure: function(){
        		
        	}
           
        });
	},
	loadGridData:function(){
		if(this.firstLoad){
			this.kpiGrid.getStore().removeAll();
			this.nodeGrid.getStore().removeAll();
			if("edit"==this.state){
				this.kpiGrid.getStore().proxy.extraParams.ruleId=this.ruleId;
				this.nodeGrid.getStore().proxy.extraParams.ruleId=this.ruleId;
				this.kpiGrid.getStore().load();
				this.nodeGrid.getStore().load();
			}
			this.firstLoad=false;
		}
		
	},
	setFirstLoad:function(firstLoad){
		this.firstLoad=firstLoad;
	},
	addGridData:function(grid,nodes,idKey,nameKey){
		if(nodes.length==0){
			Ext.Msg.alert("请选择一条记录");
			return false;
		}
		var returnFlag=true;
		var RecordType=grid.getStore().model;
		for ( var int = 0; int < nodes.length; int++) {
			var attributes=Ext.decode(nodes[int].raw.attributes);
			if(!attributes) continue;
			var index=grid.getStore().find(idKey,attributes[idKey]);
			if(index>=0){
				Ext.Msg.alert("提示","存在重复的记录:"+attributes.symbolName1);
				returnFlag=false;
			}
		}
		if(returnFlag===false){
			return returnFlag;
		}
		var records=[];
		for ( var int = 0; int < nodes.length; int++) {
			var object=new Object();
			var attributes=Ext.decode(nodes[int].raw.attributes);
			Ext.apply(object,attributes);
			if(Ext.isEmpty(object.kpiId)&&Ext.isEmpty(object.nodeId)){
				continue;
			}
			var p = new RecordType(object);
			records.push(p);
		}
		if(Ext.isEmpty(records)){
			return Ext.Msg.alert("提示","没有可添加的节点对象");
		}
		grid.getStore().add(records);

	},
	removeGridData:function(grid){
		var records = grid.getSelectionModel().getSelection();
		if(Ext.isEmpty(records)){
			return Ext.Msg.alert("提示","请选择一条记录");
		}
		Ext.Msg.confirm("提示", "确定要删除改记录?", function(btn) {
			if(btn=='yes'){
				grid.getStore().remove(records);
			}
		});
	},
	filterKpiGridData:function(){
		var me=this;
		var value=Ext.getCmp(me.kpiName).getValue();
		me.kpiGrid.getStore().suspendEvents();
		me.kpiGrid.getStore().clearFilter();
		me.kpiGrid.getStore().resumeEvents();
		me.kpiGrid.getStore().filter([{
            fn: function(record) {
            	if(Ext.isEmpty(value))return true;
               return record.get('kpiName').indexOf(value)!=-1;
            }
        }]);
	},
	kpiFilter:function(record){
		var me=this;
		var value=Ext.getCmp(me.kpiName).getValue();
		return record.get('kpiName').indexOf(value)!=-1;
	},
	nodeFilter:function(record){
		var me=this;
		var value=Ext.getCmp(me.nodeName).getValue();
		return record.get('symbolName1').indexOf(value)!=-1;
	},
	filterNodeGridData:function(){
		var me=this;
	    var value = Ext.getCmp(me.nodeName).getValue();
		me.nodeGrid.getStore().suspendEvents();
		me.nodeGrid.getStore().clearFilter();
		me.nodeGrid.getStore().resumeEvents();
		me.nodeGrid.getStore().filter([{
            fn: function(record) {
            	if(Ext.isEmpty(value))return true;
               return record.get('symbolName1').indexOf(value)!=-1;
            }
        }]);
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'addNode':
				me.getNodeEditWin().show();
				break;
			case 'deleteNode':
				me.removeGridData(me.nodeGrid);
				break;
			case 'queryNode':
				me.filterNodeGridData();
				break;
			case 'addKpi':
				me.getKpiEditWin().show();
				break;
			case 'deleteKpi':
				me.removeGridData(me.kpiGrid);
				break;
			case 'queryKpi':
				me.filterKpiGridData();
				break;
		}
	}
});
