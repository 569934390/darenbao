Ext.define('component.dcc.view.SimulatorLogPanel',{
	extend:'Ext.panel.Panel',
	alias:'widget.simulatorLogPanel',
	constructor:function(config){
		var me=this;
		config=config||{};
		me.logTreePanel=me.createLogTreePanel();
		me.logDetailTreePanel=me.createLogDetailTreePanel();
		me.logTextPanel=me.createLogTextPanel();
		Ext.applyIf(config,{
			layout:'border',
			items:[me.logTreePanel,{
				xtype:'tabpanel',
				region:'center',
				items:[me.logDetailTreePanel,me.logTextPanel]
			}],
			dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'top',
		        items: [{
		            text: '删除记录',
		            name:'deleteLog'
		        },{
		            text: '清空日志',
		            name:'clearLog'
		        },{
		            text: '刷新日志',
		            name:'refreshLog'
		        }]
		    }]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	setType:function(type){
		var me=this;
		this.type=type;
		me.logTreePanel.paramMap.logType=type;
	},
	getType:function(){
	 	return this.type;
	},
	bindEvent:function(){
		var me=this;
		me.logTreePanel.on('itemclick',Ext.bind(me.logTreeItemClick,me));
		me.down('button[name=deleteLog]').on('click',Ext.bind(me.deleteLog,me));
		me.down('button[name=clearLog]').on('click',Ext.bind(me.clearLog,me));
		me.down('button[name=refreshLog]').on('click',Ext.bind(me.refreshLog,me));
	},
	deleteTestDccLog:function(params){
		var me=this;
		Ext.Ajax.request({
		    url: ctx+'/simulator/deleteDccLog.do',
		    params:params,
		    success: function(response){
		        me.logTreePanel.getStore().load();
		    }
		});
	},
	deleteLog:function(){
		var me=this;
		var params=new Object();
		var record=Ext.gridSelectCheck(me.logTreePanel,false);
		if(!record) return;
		if(Ext.isEmpty(record.get('log_id'))) return Ext.Msg.alert('提示','请选择记录!');
		params.logId=record.get('log_id');
		params.staffId=session.user.id;
		me.deleteTestDccLog(params);
	},
	clearLog:function(){
		var me=this;
		var params=new Object();
		params.staffId=session.user.id;
		me.deleteTestDccLog(params);
	},
	refreshLog:function(){
		var me=this;
		me.logTreePanel.getStore().getProxy().extraParams.paramMap=Ext.encode(me.logTreePanel.paramMap);
		me.logTreePanel.getStore().reload();
	},
	logTreeItemClick:function(){
		var me=this;
		var record=me.logTreePanel.getSelectionModel().getSelection()[0];
		if(Ext.isEmpty(record.get('log_id'))) return;
		me.logDetailTreePanel.getStore().getProxy().extraParams.logId=record.get('log_id');
		me.logDetailTreePanel.getStore().load({callback:function(records,operation){
			var responseText=Ext.decode(operation.response.responseText);
			if(!Ext.isEmpty(responseText.requestTxt)){
				console.info(responseText.requestTxt);
				me.logTextPanel.update(responseText.requestTxt);
			}else{
				me.logTextPanel.update(responseText.responseTxt);
			}
		}});
//		Ext.ajax.request({
//			url:'debug/loadTestDccLog.do',
//			params:{
//				logId:record.get('log_id')
//			},
//			success:function(response){
//				me.logTextPanel.updateInfo(response.responseText.logText);
//			}
//		});
	},
	createLogTreePanel:function(){
	 return Ext.create('component.dcc.view.SimulatorLogTreePanel', {
	 		border:false,
	 		split:true,
			region : 'west'
		});
	},
	createLogDetailTreePanel:function(){
		return Ext.create('component.dcc.view.MessageDetailEditor',{title:'消息包',border:false,	split:true,hideFlag:'msg',url:ctx+'/simulator/loadDccLog.do'});
	},
	createLogTextPanel:function(){
		return Ext.create('Ext.panel.Panel', {
			title:'文本内容'
		});
	}
});