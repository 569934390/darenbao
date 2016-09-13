Ext.define('component.dcc.view.TestDccMessageTreePanel',{
	extend:'Ext.panel.Panel',
	requires:['component.public.AjaxComboBox','component.public.CookieComboBox'],
	config:{
		uuid:null,
		connectState:false,
		socketType:0,
		dccContent:null,
		testDccLogPanel:null
	},
	constructor:function(config){
		var me=this;
		me.config=config||{};
		me.searchForm=me.createForm();
		me.messageDetailViewer=me.createTree();
		me.sessionId="0";
		Ext.applyIf(config,{
			layout : 'border',
			items : [me.searchForm,me.messageDetailViewer]
		
		});
		me.callParent([config]);
		me.setUuid(Math.uuidCompact());
		me.bindEvent();
	},
	bindEvent:function(){
		  var me=this;
		  var interval=Number(appconfig.socket_clear_time)/2;
		  me.down('ajaxComboBox[name=dccMessageType]').on('change',Ext.bind(me.messgeTypeChange,me));
		  me.down('button[name=connectBtn]').on('click',Ext.bind(me.socketOperate,me,['connect'],0));
		  me.down('button[name=listenBtn]').on('click',Ext.bind(me.socketOperate,me,['listen'],0));
		  me.down('button[name=sendBtn]').on('click',Ext.bind(me.socketOperate,me,['send'],0));
		  me.down('button[name=editDccBtn]').on('click',Ext.bind(me.editDcc,me));
		  me.down('button[name=advancedConfigBtn]').on('click',Ext.bind(me.advancedConfigHandler,me));
		  me.task=Ext.TaskManager.newTask({
				run : function() {
					if(me.getConnectState()===true){
						Ext.Ajax.request({
								url : ctx + '/debug/refreshConnect.do',
								params : {
									uuid : me.uuid,
									socketType:me.socketType
								}
							})
					}
					
				},
				interval : 1000 * interval
		  });
		  me.task.start();
		  me.on('destroy',function(){
		  		var me=this;
		  		if(me.task){
		  			me.task.stop();
		  		}
		  })
	},
	messgeTypeChange:function(combo,newValue,oldValue){
		var me=this;
		var record=combo.getStore().findRecord(combo.valueField,newValue);
		if(!Ext.isEmpty(record)){
			me.messageDetailViewer.getStore().proxy.extraParams.parentId=combo.dccParentId;
//			var strArr=record.get('id').split('&');
//			me.messageDetailViewer.getStore().proxy.extraParams.id=strArr[0];
//			me.messageDetailViewer.getStore().proxy.extraParams.key=strArr[1];
//			me.messageDetailViewer.getStore().proxy.extraParams.name=encodeURI(strArr[2]);
			me.messageDetailViewer.getStore().proxy.extraParams.dccId=combo.dccId;
			me.messageDetailViewer.getStore().proxy.extraParams.content=record.raw['content'];
			me.dcc=record.raw;
			me.messageDetailViewer.getStore().load();
		}
	},
	advancedConfigHandler:function(){
		var me=this;
		if(!me.configTreeWindow){
			me.configTreeWindow=Ext.create('component.dcc.view.ConfigTreeWindow');
		}
		me.configTreeWindow.show();
	},
	getDcc:function(){
		var me=this;
		var dccNode=me.messageDetailViewer.getRootNode().childNodes[0];
		var dcc=new Object();
		var dccMessageType=me.down('ajaxComboBox[name=dccMessageType]');
		dcc.dccId=dccMessageType.dccId;
		dcc.name=dccMessageType.getRawValue();
		var requestNode=dccNode.childNodes[0];
		var requestDiameterNode=requestNode.childNodes[0];
		var diameter=new Object();
		Ext.each(requestDiameterNode.childNodes,function(childNode,index){
			diameter[childNode.raw['text']]=childNode.raw['avpValue'];
		});
		var avps=[];
		var requestAvpNode=requestNode.childNodes[1];
		var requestAvpList=me.getAvpList(requestAvpNode,avps,dcc.name);
		var request={diameter:diameter,avps:avps};
		Ext.apply(dcc,{
			request:request
		});
		return dcc;
//		var responseNode=dccNode.childNodes[1];
//		var responseDiameterNode=responseNode.childNodes[0];
//		var responseAvpNode=responseNode.childNodes[1];
//		var responseAvpList=me.getAvpList(responseAvpNode);
	},
	getAvpList:function(avpListNode,avps,name){
		var me=this;
		Ext.each(avpListNode.childNodes,function(childNode,index){
			var data=childNode.data;
			var avp={
				name:data['text'],
				code:data['code'],
				type:data['type'],
				expression:data['expression'],
				alias:data['alias'],
				customize:data['customize'],
				option:data['option'],
				flags:data['flags'],
				vendorId:data['vendorId'],
				src:data['src'],
				avpValue:data['avpValue'],
				desc:data['desc']
			};
			if(avp['name']=="Session-Id"&&name!="初始包"&&name!="短信包"&&me.sessionId!="0"){
				avp['avpValue']=me.sessionId;
			}
			avps.push(avp);
			if(!Ext.isEmpty(childNode.childNodes)){
				avp['avp']=[];
				me.getAvpList(childNode,avp['avp']);
			}
		});
		return avps;
	},
	socketOperate:function(socketOperate){
		var me=this;
		me.down('[name=ip]').fireEvent('addCookie');
		me.down('[name=port]').fireEvent('addCookie');
		if(socketOperate=='listen'){
			me.setSocketType(1);
			var text=me.down('button[name=listenBtn]').getText();
			if(text=="断开监听"){
				socketOperate='breakListen'
			}

		}
		if(socketOperate=='send'&&me.getConnectState()==false){
			return Ext.Msg.alert('提示','请先连接');
		}
		if(socketOperate=="connect"){
			var text=me.down('button[name=connectBtn]').getText();
			if(text=="断开连接"){
				socketOperate='breakConnect';
			}
		}

		var params=new Object();
		params=Ext.getFormValues(me.searchForm);
		if(Ext.isEmpty(params.ip))return Ext.Msg.alert('提示','IP不能为空');
		if(Ext.isEmpty(params.port))return Ext.Msg.alert('提示','端口不能为空');
		params.uuid=me.getUuid();
//		var strArr=params.dccMessageType.split("&");
//		params.id=strArr[0];
//		params.key=strArr[1];
//		params.name=strArr[2];
		params.socketOperate=socketOperate;
		params.socketType=me.getSocketType();
		var dcc=me.getDcc();
		if(socketOperate=='send'){
				params.dcc=Ext.encode(dcc);
		}
		console.info(params);
		Ext.Ajax.request({
			url:ctx+'/debug/socketOperate.do',
			params:params,
			success:function(response){
				var result = Ext.decode(response.responseText);
				if(result.success){
					me.getTestDccLogPanel().setType(me.getSocketType());
					if(socketOperate=="connect"){
						me.setConnectState(true);
						me.down('button[name=connectBtn]').setText('断开连接');
						me.down('button[name=listenBtn]').disable();
					}else if(socketOperate=='listen'){
						me.setConnectState(true);
						me.down('button[name=connectBtn]').disable();
						me.down('button[name=listenBtn]').setText('断开监听');
					}else if(socketOperate=='breakConnect'){
						me.setConnectState(false);
						me.down('button[name=connectBtn]').setText('连接');
						me.down('button[name=listenBtn]').enable();
					}else if(socketOperate=='breakListen'){
						me.setConnectState(false);
						me.down('button[name=connectBtn]').enable();
						me.down('button[name=listenBtn]').setText('监听');
					}
					else{
						me.sessionId=result.avpVo;
						setTimeout(function(){
							me.testDccLogPanel.refreshLog();
						},1000);
					}
				}else{
					return Ext.Msg.alert('操作失败',result.msg);
				}
			},
			failure:function(){
				return Ext.Msg.alert('提示','操作失败');
			}
		});
	},
	editDcc:function(){
		var me=this;
		if(!me.editDccWindow){
			me.editDccWindow=Ext.create('component.dcc.view.EditSingleDccWindow');
		}
		me.editDccWindow.setDcc(me.dcc);
		me.editDccWindow.setComBo(me.down('ajaxComboBox[name=dccMessageType]'));
		me.editDccWindow.show();
	},
	createForm:function(){
		var me=this;
		return Ext.create('Ext.form.Panel',{
			region:'north',
			xtype : 'form',
			layout:'column',
			bodyStyle : 'background-color : #dfe8f6',border:false,
			defaults:{
				labelAlign:'right',
				style:'margin-right:20px',
				labelWidth:30
			},
			items : [{
					xtype : 'cookieComboBox',
					fieldLabel : 'IP',
					name:'ip',
					vtype:'ip',
					style:'margin-top:1px;',
					plugins:Ext.create('component.public.ComboSelectFirstPlugin')
				},{
					xtype : 'cookieComboBox',
					fieldLabel : '端口',
//					minValue:1,
//					maxValue:65535,
					maskRe:/^\d+$/,
					labelWidth:30,
					width:105,
					name:'port',
					plugins:Ext.create('component.public.ComboSelectFirstPlugin')
				},{
					xtype : 'button',
					text : '连接',
					style:'margin-top:1px;',
					name:'connectBtn'
				},{
					xtype : 'button',
					text : '监听',
					style:'margin-top:1px;margin-left:10px;',
					name :'listenBtn'
//				},{
//					xtype : 'button',
//					text : '全屏窗口',
//					name:'maxBtn'
				},{
					style:'margin-top:1px;',
					xtype : 'ajaxComboBox',
					queryMode : 'remote',
					sqlKey:'DopTestDcc.selectListByDccIdWithContent',
					paramMap:{dcc_id:-1,dccFlag:true},
					valueField:'dcc_id',
    				displayField:'text',
					name : 'dccMessageType',
					labelWidth:70,
					fieldLabel : '选择消息包',
					plugins:Ext.create('component.public.ComboSelectFirstPlugin')
				},{
					xtype : 'button',
					text : '发送',
					style:'margin-top:1px;margin-left:10px;',
					name:'sendBtn'
				},{
					xtype : 'button',
					text : '编辑消息包',
					style:'margin-top:1px;margin-left:10px;',
					name:'editDccBtn'
				},{
					xtype : 'button',
					text : '高级配置',
					style:'margin-top:1px;margin-left:10px;',
					name:'advancedConfigBtn'
				}]
		});
	},
	createTree:function(){
		return Ext.create('component.dcc.view.MessageDetailEditor',{region:'center',hideFlag:'root',editable:true});
	}
});