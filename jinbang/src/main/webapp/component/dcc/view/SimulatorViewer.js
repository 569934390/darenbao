Ext.define('component.dcc.view.SimulatorViewer',{
	extend:'Ext.panel.Panel',
	requires : ['component.public.AjaxComboBox'],
	config:{
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
		var filters = Ext.state.Manager.get('filters')||['127.0.0.1'];
		var port = Ext.state.Manager.get('port')||'1111';
		var flag = true;
		for(var i=0;i<filters.length;i++){
			if(filters[i]==ip){
				flag = false;
				break;
			}
		}
		if(flag)
			filters.push(ip);
		var ips = [];
		for(var i=0;i<filters.length;i++){
			ips.push([filters[i],filters[i]]	);
		}
		Ext.state.Manager.set('filters',filters);
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
			iconCls:'toolbar-tools',
        	handler:function(){
        		Ext.Msg.alert('提示','请数据库手动添加代理');
        	}
	    },'-',{
			xtype:'ajaxComboBox',
			editable : true,
			queryMode:'local',
			fieldLabel : 'IP',
			name:'ip',
			displayField: 'name',
			valueField: 'value',
			labelAlign:'right',
			labelWidth:15,
			data:ips,
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
	    		me.sendCommand('send');
	    	}
	    }];
		me.callParent([config]);
		me.commandView.on('itemclick',function(view, record, item, index, e, eOpts){
			if(e.target.className.indexOf('tip')!=-1){
				var code = e.target.className.split('_')[1],tip='';
				tip+=code+"["+avpCodes[code].avpValue+"]:"+avpCodes[code].avpDesc;
				Ext.create('Ext.tip.ToolTip', {
					anchor: 'left',
		            html: tip
				}).showAt([e.getXY()[0]+20,e.getXY()[1]-15]);
			}
		});
		me.m1 = MessageUtils.bind('dccdop.server.web.simulator.recelive',Ext.bind(me.messageReceived,me));
//		MessageUtils.unbind('dcc_package',me.m1);
		me.on('afterrender',Ext.bind(me.init,me));
	},
	messageReceived : function(data){
		var me = this;
		var l = me.commandView.getStore().getCount(), store = me.commandView.getStore(), result = Ext.decode(data);
		if(result.success){
			for (var i = 0; i < l; i++) {
				var record = store.getAt(i);
				if (result.uuid == record.get('nodeId')) {
					if (result.message==true) {
						record.set('src', webRoot+ 'common/topoImages/16x16/ok.png');
						record.set('success', true);
						record.set('statusTime',Ext.util.Format.date(new Date(),'Y-m-d H:i:s'));
						record.set('successColor','color:green;');
						var sessionIds = result.sessionId&&result.sessionId.split(','),sessionId;
						if(sessionIds!=null){
							for(var si = 0;si<sessionIds.length;si++){
								if(!Ext.isEmpty(sessionIds[si])){
									sessionId = sessionIds[si];
								}
							}
						}
						record.set('result', '执行完毕!'+'<a href="'+webRoot+'debug/debug.jsp?sessionId='+sessionId+'&theme=classic" target="_blank">查看端口镜像包</a>');
					}else {
						var msg = result.message;
						if(result.type=='receive'||result.type=='send'){
							var total = record.get('look').length;
							msg+='<img class="look l_'+total+'" src="'+webRoot+'common/images/16x16/look.png" ></img>';
							record.get('look').push({callback:function(msg,index){
								var toString = msg.get('look')[index].toString,dccJson = msg.get('look')[index].dcc;
								me.getPackageWin(toString,dccJson);
							},toString:result.toString,dcc:result.dcc,index:total});
						}
						record.set('src',webRoot+ 'common/topoImages/16x16/sync.gif');
						record.set('result', result.message);
						record.set('successColor','color:#blue;');
						record.set('statusTime',Ext.util.Format.date(new Date(),'Y-m-d H:i:s'));
						record.get('logger').push({debug:msg});
					}
				}
			}
		}
		else{
			if(result.message)
				Ext.Msg.alert('提示','代理服务器发生错误，请联系管理员:\n'+result.message);
		}
	},
	getPackageWin:function(toString,dccJson){
		var me = this;
		toString = toString.replace(/</g,"{");
		toString = toString.replace(/>/g,"}")
		var texts = toString.split('\n'),textStr='<div style="padding-left:10px;overflow:auto;width:100%;height:100%;">';
		for(var i=0;i<texts.length;i++){
			var proper = texts[i].split('=');
			textStr+= '<p style="font-size: 12px;"><span style="font-weight: bold;">'+proper[0]+'</span>=[<span style="color:red">'+proper[1]+' </span>]</p>';
		}
		textStr+='</div>';
		var viewer = Ext.create('component.dcc.view.MessageDetailEditor',{title:'',border:false,hideFlag:'msg',url:ctx+'/simulator/convertDccLog.do'});
		viewer.getStore().getProxy().extraParams.jsonstr=dccJson;
		viewer.getStore().load();
		Ext.create('Ext.window.Window',{
			title:'消息包详情',
			layout:'border',
			closeAction:'destroy',
			width:Ext.getBody().getWidth()-50,
			height:Ext.getBody().getHeight()-50,
			items:[{
				region:'center',
				border:false,
				xtype:'tabpanel',
				items:[{
					xtpe:'panel',
					title:'格式化内容',
					layout:'fit',
					items:viewer
				},{
					xtpe:'panel',
					title:'XML内容',
					layout:'fit',
					items:[{
						xtype:'box',
						html:'safas',
						listeners:{
							afterrender:function(box){
								Ext.Ajax.request({
									url:ctx+'/simulator/convertDccXML.do',
									params:{jsonstr:dccJson},
									success:function(response){
										if(!Ext.isEmpty(response.responseText)){
											var result = Ext.decode(response.responseText).xml;
											result = result.replace(/\\n/g,'\n');
											box.update('<textarea style="width:100%;height:100%;">'+result+'</textarea>');
											var editor = CodeMirror.fromTextArea(box.getEl().dom.childNodes[0], {
										        mode: "text/html",
										        lineNumbers: true
										    });
										}
									},
									failure:function(){
										return Ext.Msg.alert('提示','操作失败');
									}
								});
							}
						}
					}]
				},{
					xtpe:'panel',
					title:'文本内容',
					layout:'fit',
					items:[{
						xtype:'box',
						html:textStr
					}]
				}]
			}]
		}).show();
	},
	init:function(){
		var me = this;
		setTimeout(function(){
			me.initGraph();
		},100);
	},
	initGraph:function(){
		var me = this;
		me.graph.removeNodes();
		me.graph.addNode({type:'PC',x:100,y:10,name:'代理'});
		me.graph.addNode({type:'SERVER',x:400,y:10,name:'服务器'});
		me.graph.addNode({type:'DBS',x:650,y:10,display:'virtual'});
		me.graph.addNode({type:'GRID',x:950,y:10,display:'virtual'});
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
		me.createMessageLog();
		return Ext.create('Ext.tab.Panel',{
			region:'center',border:false,
			layout:'border',
			items:[me.commandView,
				me.logPanel = me.createLogPanel()],
			listeners:{
				tabchange:function( tabPanel, newCard, oldCard, eOpts ){
					me.logPanel.logTreePanel.getRootNode().expand();
				}
			}
		});
	},
	createMessageLog:function(){
		var me = this;
		me.commandView = Ext.create('component.business.CommandView', {
			title:'代理服务器日志',
			region : 'center'
		});
	},
	createLogPanel:function(){
		return Ext.create('component.dcc.view.SimulatorLogPanel', {
			region : 'center',border:false,
			title:'历史记录',
			listeners:{
				itemclick:function(tree,record,item,index,e,eOpts){
					
				}
			}
		});
	},
	createLogDetailTreePanel:function(){
		return Ext.create('component.dcc.view.MessageDetailViewer',{title:'消息包',hideFlag:'msg',url:ctx+'/debug/loadTestDccLog.do'});
	},
    sendCommand:function(){
		var me=this;
		var params=new Object();
		params.ip = me.down('textfield[name=ip]').getValue();
		params.port = me.down('textfield[name=port]').getValue();
		params.dccAgent = me.down('textfield[name=dccAgent]').getValue();
		params.situationId = me.situationId;


		if(Ext.isEmpty(params.ip))return Ext.Msg.alert('提示','IP不能为空');
		if(Ext.isEmpty(params.port))return Ext.Msg.alert('提示','端口不能为空');
		if(Ext.isEmpty(params.dccAgent))return Ext.Msg.alert('提示','代理不能为空');

		Ext.state.Manager.set('ip',params.ip);
		Ext.state.Manager.set('port',params.port);
		Ext.state.Manager.set('dccAgent',params.dccAgent);
		Ext.Ajax.request({
			url:ctx+'/simulator/doCmd.do',
			params:params,
			success:function(response){
				var text = response.responseText;
		        var data = Ext.JSON.decode(text);
		        if (data.success) {
					me.commandView.getStore().insert(0, {
							nodeId : data.uuid,
							value : ' 执行控制命令 "' + params.ip + '" ',
							src : webRoot+ 'common/topoImages/16x16/sync.gif',
							success : false,
							result : '',
							ip:params.ip,
							statusTime:Ext.util.Format.date(new Date(),'Y-m-d H:i:s'),
							logger:[],
							look:[]
						});
					(function(record) {
						setTimeout(function() {
							if (record.get('success') == false) {
								record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
								record.set('success', true);
								record.set('result', '控制命令执行超时！');
							}
						}, 60000);
					})(me.commandView.getStore().getAt(0));
				} else {
					Ext.Msg.alert('提示', data.msg);
				}
		    },
		 	failure: function(response,opts){
		 		Ext.Msg.alert('提示','执行超时，请检查网络');
		 	}
		});
	}
});