Ext.define('component.dcc.view.TestDccLogPanel',{
	extend:'Ext.panel.Panel',
	alias:'widget.testDccLogPanel',
	constructor:function(config){
		var me=this;
		config=config||{};
		me.logTreePanel=me.createLogTreePanel();
		me.logDetailTreePanel=me.createLogDetailTreePanel();
		me.logTextPanel=me.createLogTextPanel();
		me.logXmlPanel=me.createLogXmlPanel();
		Ext.applyIf(config,{
			layout:'border',
			items:[me.logTreePanel,{
				xtype:'tabpanel',
				region:'center',
				border:false,
				items:[me.logDetailTreePanel,me.logTextPanel,me.logXmlPanel]
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
		        },{
		            text: '全屏窗口',
		            name:'maxPanel'
		        },'->',{
		            text: '重组消息包',
		            name:'recombineMessage'
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
		me.down('button[name=maxPanel]').on('click',Ext.bind(me.maxPanel,me));
		me.down('button[name=recombineMessage]').on('click',Ext.bind(me.recombineMessage,me));
	},
	deleteTestDccLog:function(params){
		var me=this;
		Ext.Ajax.request({
		    url: ctx+'/debug/deleteTestDccLog.do',
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
	maxPanel:function(){
		var me=this;
		if(me.getHeight()!=me.ownerCt.getHeight()){
			me.orginalHeight=me.getHeight();
			me.down('button[name=maxPanel]').setText('恢复窗口');
			me.setHeight(me.ownerCt.getHeight());
		}else{
			me.down('button[name=maxPanel]').setText('全屏窗口');
			me.setHeight(me.orginalHeight);
		}
	},
	recombineMessage:function(){
		var me=this;
		me.messageDetailPanel.getStore().getProxy().extraParams.action="recombineMessage";
		me.messageDetailPanel.getStore().getProxy().extraParams.messageByteContent=me.messageBytePanel.down('[name=messageByteContent]').getValue();
		me.updateTestDccLog();
	},
	updateTestDccLog:function(){
		var me=this;
		me.messageDetailPanel.getStore().load({callback:function(records,operation){
			var responseText=Ext.ResponseDecode(operation.response);
			me.messageBytePanel.down('[name=messageByteContent]').setValue(responseText.messageByteContent);
			me.logXmlPanel.down('[name=messageXmlContent]').setValue(responseText.messageXmlContent);
			if(!Ext.isEmpty(responseText.requestTxt)){
				me.logTextPanel.update(responseText.requestTxt);
			}else{
				me.logTextPanel.update(responseText.responseTxt);
			}
		}});
		
	},
	logTreeItemClick:function(){
		var me=this;
		var record=me.logTreePanel.getSelectionModel().getSelection()[0];
		if(Ext.isEmpty(record.get('log_id'))) return;
		me.messageDetailPanel.getStore().getProxy().extraParams.logId=record.get('log_id');
		me.messageDetailPanel.getStore().getProxy().extraParams.action="loadTestDccLog";
		me.updateTestDccLog();
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
	 	return Ext.create('component.dcc.view.TestDccLogTreePanel', {
			region : 'west',split:true,
			border:false
		});
	},
	createLogDetailTreePanel:function(){
		var me=this;
		me.messageDetailPanel=Ext.create('component.dcc.view.MessageDetailEditor',{region:'center',hideFlag:'msg',url:ctx+'/debug/loadTestDccLog.do'});
		me.messageBytePanel = Ext.create('Ext.form.Panel', {
			region : 'north',
			height : '30%',
			layout : 'fit',
			border :false,
			items : [{
						xtype : 'textarea',
						name : 'messageByteContent'
					}]
		});
		return Ext.create('Ext.panel.Panel',{
			layout:'border',
			title:'消息包',
			items:[me.messageBytePanel,me.messageDetailPanel]
		})
	},
	createLogTextPanel:function(){
		return Ext.create('Ext.panel.Panel', {
			title:'文本内容'
		});
	},
	createLogXmlPanel:function(){
		return Ext.create('Ext.panel.Panel', {
			title : 'xml内容',
			layout:'fit',
			items : [{
						xtype : 'textarea',
						name : 'messageXmlContent'
					}]
		});
	}
});