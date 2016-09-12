Ext.define('component.dcc.view.SimulatorViewer',{
	extend:'Ext.panel.Panel',
	requires : ['component.public.AjaxComboBox'],
	config:{
		uuid:Math.uuidCompact(),
		connectState:false,
		socketType:0,
		testDccLogPanel:null
	},
	constructor:function(config){
		var me=this;
		me.situationId = config.situationId;
		me.config=config||{};
		me.graph=me.createGraph();
		me.messageDetailViewer=me.createTabPanel();
		Ext.applyIf(config,{
			layout : 'border',
			items : [me.graph,me.messageDetailViewer]
		});
		var dccAgent = Ext.state.Manager.get('dccAgent')||'127.0.0.1';
		var ip = Ext.state.Manager.get('ip')||'127.0.0.1';
		var port = Ext.state.Manager.get('port')||'1111';
		me.tbar=[{
			xtype:'ajaxComboBox',
			editable : true,
			fieldLabel:'代理',
			name:'dccAgent',
			displayField: 'avp_code',
			valueField: 'avp_value',
			url:ctx+'/base/getList.do?sqlKey=AvpCodeDcc.selectAgent',
			queryMode:'remote',
			labelAlign:'right',
			labelWidth:30,
			autoLoad:false,
			value:dccAgent
		},{
	    	icon:webRoot+'common/formsImages/tool.gif',
        	handler:function(){
        		Ext.Msg.alert('提示','请数据库手动添加代理');
        	}
	    },'-',{
			xtype : 'textfield',
			fieldLabel : 'IP',
			name:'ip',
			labelAlign:'right',
			labelWidth:15,
			value:ip//'10.45.44.216'
		},{
			xtype : 'numberfield',
			fieldLabel : '端口',
			minValue:1,
			maxValue:65535,
			name:'port',
			labelAlign:'right',
			labelWidth:30,
			width:105,
			value:port//5151
		},'-',{
	    	iconCls:'toolbar-play',
	    	tooltip:'提交代理执行批量发包任务',
	    	handler:function(){
	    		me.clearDccLog();
	    		me.socketOperate('send');
	    	}
	    }];
		me.callParent([config]);
		me.m1 = MessageUtils.bind('dcc_package',function(message){
			console.info(message+new Date());
		});
		console.info(MessageUtils.handlers);
//		MessageUtils.unbind('dcc_package',me.m1);
	},
	reloadData:function(){
		var me = this;
		Ext.Ajax.request({
		    url: webRoot+'debug/selectListByStaffId.do',
		    params: {
		        staffId : session.user.id,
				logType:'0'
		    },
		    success: function(response){
		        var result = Ext.JSON.decode(response.responseText),send = 0,received=0;
		        for(var i=0,length = result.length;i<length;i++){
		        	if(result[i].msgType==0){
		        		send++;
		        	}
		        	else {
		        		received++;
		        	}
		        }
		        me.resultMsg = result;
		        var host = ctx.substring(ctx.indexOf('//')+2,ctx.lastIndexOf(':'));
		        me.graph.setMsg(0,send,function(tip){
		        	var num = tip.data('index'),msg = tip.data('msg');
		        	var datas = [];
		        	for(var i=0;i<this.resultMsg.length;i++){
		        		if(this.resultMsg[i].msgType==0){
			        		datas.push({
			        			logId:this.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:this.title,
			        			message:this.resultMsg[i].name,
			        			src:host,
			        			target:this.resultMsg[i].ipAndPort,
			        			sendTime:this.resultMsg[i].createTime
			        		});
		        		}
		        	}
		        	this.tableView.getStore().loadData(datas);
		        },me);
		        me.graph.setMsg(1,received,function(tip){
		        	var num = tip.data('index'),msg = tip.data('msg');
		        	var datas = [];
		        	for(var i=0;i<this.resultMsg.length;i++){
		        		if(this.resultMsg[i].msgType==1){
			        		datas.push({
			        			logId:this.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:this.title,
			        			message:this.resultMsg[i].name,
			        			target:host,
			        			src:this.resultMsg[i].ipAndPort,
			        			sendTime:this.resultMsg[i].createTime
			        		});
		        		}
		        	}
		        	this.tableView.getStore().loadData(datas);
		        },me);
		    }
		});
	},
	createGraph:function(){
		return Ext.create('component.graph.RaphaelViewer',{
			height:100,
			region:'north'
		});
	},
	createTabPanel:function(){
		var me = this;
		return Ext.create('Ext.tab.Panel',{
			region:'center',border:false,
			layout:'border',
			items:[me.tableView = me.createTable(),
				me.createLogPanel()]
		});
	},
	createTable:function(){
		var me=this;
		var store = Ext.create('Ext.data.Store', {
		    fields:['logId','sys', 'situation', 'src', 'target', 'message', 'sendTime', 'receiveTime'],
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
		});
		
		var tableView =  Ext.create('Ext.grid.Panel',{
			region : 'center',border:false,
			title:'消息列表',
			store: store,
		    columns: [
		        { text: '系统',  dataIndex: 'sys' },
		        { text: '场景名称', dataIndex: 'situation' },
		        { text: '源', dataIndex: 'src' ,width:150},
		        { text: '目标', dataIndex: 'target' ,width:150},
		        { text: '消息包', dataIndex: 'message' , flex: 1},
		        { text: '发送时间', dataIndex: 'sendTime',width:150},
		        { text: '到达时间', dataIndex: 'receiveTime' ,width:150}
		    ],
		    tbar:[{
		    	text:'全部',
		    	handler:function(){
		    		var datas = [];
		    		if(!me.resultMsg)return;
		    		var host = ctx.substring(ctx.indexOf('//')+2,ctx.lastIndexOf(':'));
		    		for(var i=0;i<me.resultMsg.length;i++){
		        		if(me.resultMsg[i].msgType==1){
			        		datas.push({
			        			logId:me.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:me.title,
			        			message:me.resultMsg[i].name,
			        			src:host,
			        			target:me.resultMsg[i].ipAndPort,
			        			sendTime:me.resultMsg[i].createTime
			        		});
		        		}
		        		else if(me.resultMsg[i].msgType==0){
			        		datas.push({
			        			logId:me.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:me.title,
			        			message:me.resultMsg[i].name,
			        			target:host,
			        			src:me.resultMsg[i].ipAndPort,
			        			sendTime:me.resultMsg[i].createTime
			        		});
		        		}
		        	}
		        	console.info(datas);
		        	console.info(me.resultMsg);
		        	me.tableView.getStore().loadData(datas);
		    	}
		    },{
		    	text:'发送',
		    	handler:function(){
		    		var datas = [];
		    		if(!me.resultMsg)return;
		    		var host = ctx.substring(ctx.indexOf('//')+2,ctx.lastIndexOf(':'));
		    		for(var i=0;i<me.resultMsg.length;i++){
		        		if(me.resultMsg[i].msgType==1){
			        		datas.push({
			        			logId:me.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:me.title,
			        			message:me.resultMsg[i].name,
			        			src:host,
			        			target:me.resultMsg[i].ipAndPort,
			        			sendTime:me.resultMsg[i].createTime
			        		});
		        		}
		        	}
		        	me.tableView.getStore().loadData(datas);
		    	}
		    },{
		    	text:'接收',
		    	handler:function(){
		    		var datas = [];
		    		if(!me.resultMsg)return;
		    		var host = ctx.substring(ctx.indexOf('//')+2,ctx.lastIndexOf(':'));
		    		for(var i=0;i<me.resultMsg.length;i++){
		        		if(me.resultMsg[i].msgType==0){
			        		datas.push({
			        			logId:me.resultMsg[i].logId,
			        			sys:'OCS',
			        			situation:me.title,
			        			message:me.resultMsg[i].name,
			        			target:host,
			        			src:me.resultMsg[i].ipAndPort,
			        			sendTime:me.resultMsg[i].createTime
			        		});
		        		}
		        	}
		        	me.tableView.getStore().loadData(datas);
		    	}
		    }]
		});
		tableView.on('itemdblclick',function(){
			var record=me.tableView.getSelectionModel().getSelection()[0];
			if(Ext.isEmpty(record.get('logId'))) return;
			Ext.create('Ext.window.Window', {
			    title: '消息包详情',
			    maximized:true,
			    layout: 'fit',
			    items: me.logDetailTreePanel = me.createLogDetailTreePanel()
			}).show();
			me.logDetailTreePanel.getStore().getProxy().extraParams.logId=record.get('logId');
			me.logDetailTreePanel.getStore().load();
		});
		return tableView;
	},
	createLogPanel:function(){
		return Ext.create('component.dcc.view.SimulatorLogPanel', {
			region : 'center',border:false,
			title:'执行日志',
			listeners:{
				itemclick:function(tree,record,item,index,e,eOpts){
					
				}
			}
		});
	},
	createLogDetailTreePanel:function(){
		return Ext.create('component.dcc.view.MessageDetailViewer',{title:'消息包',hideFlag:'msg',url:ctx+'/debug/loadTestDccLog.do'});
	},
    socketOperate:function(socketOperate,fun){
		var me=this;
		if(socketOperate=='send'&&me.getConnectState()==false){
			return Ext.Msg.alert('提示','请先连接');
		}
		var params=new Object();
		params.uuid=me.getUuid();
		params.ip = me.down('textfield[name=ip]').getValue();
		params.port = me.down('textfield[name=port]').getValue();
		params.dccAgent = me.down('textfield[name=dccAgent]').getValue();
		params.socketOperate=socketOperate;
		params.socketType=me.getSocketType();
		params.situationId = me.situationId;
		
		Ext.state.Manager.set('ip',params.ip);
		Ext.state.Manager.set('port',params.port);
		Ext.state.Manager.set('dccAgent',params.dccAgent);
		return;
		Ext.Ajax.request({
			url:ctx+'/debug/bathSocketOperate.do',
			params:params,
			success:function(response){
				if(response.responseText=='0'){
					if(socketOperate=='breakConnect'){
						me.graph.removeNodes();
						me.setConnectState(false);
					}
					else if(socketOperate=='connect'){
						me.setConnectState(true);
						me.graph.removeNodes();
						me.graph.addNode({type:'PC',x:100,y:10});
					}
					if(socketOperate=='send'){
						me.graph.removeNodes();
						me.graph.addNode({type:'PC',x:100,y:10});
						me.graph.addNode({type:'SERVER',x:400,y:10});
						me.graph.addNode({type:'DBS',x:650,y:10,display:'virtual'});
						me.graph.addNode({type:'GRID',x:950,y:10,display:'virtual'});
					}
				}
				else if(response.responseText=='1'){
					if(socketOperate=='connect'){
						Ext.MessageBox.confirm('警告','系统检查到已存在另一个未关闭链接，是否关闭该链接？',function(pass){
								if(pass=='yes'){
									if(fun&&Ext.isFunction(fun))
										fun.call(this,'breakConnect');
								}
								console.info(arguments);
							}
						);
					}
				}
				else{
					Ext.Msg.alert('提示',response.responseText);
					if(fun&&Ext.isFunction(fun))
						fun.call(this);
				}
			},
			failure:function(){
				return Ext.Msg.alert('提示','操作失败');
			}
		});
	},
	clearDccLog:function(){
		var me=this;
		var params=new Object();
		params.staffId=session.user.id;
		Ext.Ajax.request({
		    url: ctx+'/debug/deleteTestDccLog.do',
		    params:params,
		    success: function(response){
		    }
		});
	}
});