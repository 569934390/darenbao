Ext.define('component.business.PingBugsPortWindow', {
	extend : 'Ext.window.Window',
	modal : false,
	mixins: {
    	kpiDatas:'component.business.KpiDatas'
    },
	requires:['component.public.TextButtonField'],
	constructor : function(config) {
		var me = this;
		me.config = config;
		me.items = [me.createForm(config),
			me.portListViewer = Ext.create('component.business.PortBugsListViewer',{
				tbar:['->',{
					text:'执行',
					iconCls : 'toolbar-search',
					handler : function() {
						me.doPortPing();
					}
				},'-',{
					text:'结果集',
					iconCls:'toolbar-right',
					handler:function(){
						me.portListViewer.setVisible(false);
						me.commandPanel.setVisible(true);
					}
				}],
				region : 'center'
			}),
			me.commandPanel = Ext.create('Ext.panel.Panel',{
				tbar:[{
						text:'返回',
						iconCls:'toolbar-back',
						handler:function(){
							me.portListViewer.setVisible(true);
							me.commandPanel.setVisible(false);
						}
					},'->',{
						text:'清除',
						iconCls:'toolbar-del',
						handler:function(){
							me.commandView.getStore().removeAll();
						}
					}],
				region : 'center',
				layout:'fit',
				items:me.commandView = Ext.create('component.business.CommandView', {})
			})];
		me.commandPanel.setVisible(false);
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
		me.on('show', function() {
			var forms = me.query('form');
			me.commandView.getStore().removeAll();
			me.portListViewer.setVisible(true);
			me.commandPanel.setVisible(false);
			for (var key in forms) {
				if (forms[key].name == 'baseInfo') {
					forms[key].getForm().findField('agentId').getStore().load({
						callback:function(){
							if(config.autoPing!=false){
								var store = me.portListViewer.getStore();
								store.proxy.extraParams={
									limit: 500,
									page: 1,
									start: 0,
									nodeIdList:config.getNodeId()
								};
								store.load({
									callback:function(){
										me.portListViewer.getStore().sort('PORT_INDEX', 'ASC');
									}
								});
							}
						}
					});
				}
			}
		});
	},
	doPortPing:function(){
		var me = this;
		var forms = me.query('form'), values = {};
		for (var key in forms) {
			Ext.apply(values, forms[key].getForm().getValues());
		}
		if(Ext.isEmpty(values.agentId)){
			setTimeout(function(){
				me.doPortPing();
			},100);
			return;
		}
		var count = me.portListViewer.getSelectionModel().getCount();
		me.fillerIps();
		var store = me.portListViewer.getStore();
		var ips = [];
		me.bindMessageSource();
		var records = me.portListViewer.getSelectionModel().getSelection();
		if(count>0&&records.length==0){
			store.clearFilter();
			return Ext.Msg.alert('提示','所选端口无IP信息或者不在管理中');
		}
		me.portListViewer.setVisible(false);
		me.commandPanel.setVisible(true);
		if(records.length==0){
			return Ext.Msg.alert('提示','请选择有效端口IP');
			for(var i=0,length=store.getCount();i<length;i++){
				var params = Ext.clone(values);
				Ext.apply(params,{
					ip:store.getAt(i).get('PORT_IP'),
					kpi:me.getPortKpiByNodeTypeId(store.getAt(i).get('NODE_TYPE_ID')),
					nodeId:store.getAt(i).get('NODE_ID'),
					portStatus:store.getAt(i).get('PORT_STATE')
				});
				me.doCmd(params);
			}
		}
		else{
			for(var i=0,length=records.length;i<length;i++){
				var params = Ext.clone(values);
				Ext.apply(params,{
					ip:records[i].get('PORT_IP'),
					kpi:me.getPortKpiByNodeTypeId(records[i].get('NODE_TYPE_ID')),
					nodeId:records[i].get('NODE_ID'),
					portStatus:records[i].get('PORT_STATE')
				});
				me.doCmd(params);
			}
		}
	},
	doCmd : function(values) {
		var me = this;
		Ext.Ajax.request({
		    url: webRoot+'ctrlCmdController/ping.do',
		    params: {
				agentId : values.agentId,
				command : Ext.encode(values)
			},
		    success: function(response){
		        var text = response.responseText;
		        var data = Ext.JSON.decode(text);
		        if (data.success) {
					me.commandView.getStore().insert(0, {
							nodeId : data.uuid,
							value : ' ping "' + values.ip + '" ',
							src : webRoot+ 'common/topoImages/16x16/sync.gif',
							success : false,
							result : '',
							ip:values.ip,
							bussinceKey:values.nodeId+','+values.portStatus,
							logger:[]
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
	},
	bindMessageSource:function(){
		var me = this;
		component.socket.Message.bind('mq_net_web', {
			timer : 5000,
			callback : function(data) {
				var l = me.commandView.getStore().getCount(), store = me.commandView.getStore(), result = Ext.decode(data);
				for (var i = 0; i < l && i < 99; i++) {
					var record = store.getAt(i);
					if (result.uuid == record.get('nodeId')) {
						if (Ext.isEmpty(result) || (Ext.isEmpty(result.message)&&Ext.isEmpty(result.logger)))
							return Ext.Msg.alert('提示', '返回数据有问题');
						var times = result.times, timeMsg = '';
						if (!Ext.isEmpty(times)) {
							times = times.split(',');
							var minTime = 900000,maxTime = 0,total = 0,index=0;
							for (var j = 0; j < times.length; j++) {
								if (!Ext.isEmpty(times[j])){
									index++;
									total+=Number(times[j]);
									if(minTime>Number(times[j]))
										minTime = Number(times[j]);
									if(maxTime<Number(times[j]))
										maxTime = Number(times[j]);
								}
							}
							timeMsg+=' 最短 = '+minTime+'ms 最长 = '+maxTime+'ms 平均 = '+Math.round(total/index*100)/100+'ms'+'  丢失率：('+(( 1-result.rate)*100)+')%';
						}
						if(timeMsg!=''){
							if (result.message) {
								record.set('src', webRoot+ 'common/topoImages/16x16/ok.png');
								record.set('success', true);
								record.set('result', timeMsg + ' 连接正常!');
								record.set('successColor','color:green;');
							}
							else{
								record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
								record.set('success', true);
								record.set('result', timeMsg + ' 连接失败');
								record.set('successColor','color:red;');
							}
							var keys = record.get('bussinceKey').split(',');
							if(result.message==true&&keys[1]=='2'){
								me.setPortPing(keys[0],'1');
							}
							else if(result.message==false&&keys[1]=='1'){
								me.setPortPing(keys[0],'2');
							}
						}
						else{
							if(Ext.isEmpty(result.logger)){
								record.get('logger').push({debug:'来自 '+record.get('ip')+' 的回复： 时间<'+(result.time==0?1:result.time)+'ms'});
								record.set('result', '响应时间:<'+result.time+'ms');
							}
							else{
								record.get('logger').push({debug:result.logger});
								record.set('result', result.logger);
							}
						}
					}
				}
			}
		},me);
	},
	fillerIps:function(){
		this.portListViewer.getStore().filter([{filterFn: function(item) { 
			return Number(item.get("ADMIN_STATUS")) ==1&&!Ext.isEmpty(item.get("PORT_IP")); 
		}}]);
	},
	setPortPing:function(nodeId,status){
		var me =this;
		Ext.Ajax.request({
			url : webRoot + '/nodeTypeAttr/updateNodeAttrByPk.do',
			method : 'POST',
			params : {
				nodeId : nodeId,
				nodeTypeAttrId : 61,
				attrValue :status
			},
			success : function(resp, action) {
				me.portListViewer.getStore().load();
			},
			failure : function() {
			}
		});
	},
	createForm : function(obj) {
		var me = this;
		var form = Ext.create('component.public.FormPanel', {
			region : 'north',
			name : 'baseInfo',
			groupName : '基本参数',
			hiddenItems:[{
    		 	xtype:'hidden',
    		 	name:'nodeId'
    		 },{
    		 	xtype:'hidden',
    		 	name:'kpi'
    		 }],
			items : [{
				inputWidth : 150,
				width : 150,
				xtype : 'textButtonField',
				fieldLabel : '任务代理',
				name : 'agentId',
				plugins : [Ext.create('component.agent.ComboPlugin')],
				allowBlank : false,
				blankText : '请选择代理',
				editable : false
			}, {
				inputWidth : 250,
				xtype : 'numberfield',
				fieldLabel : '重复次数',
				name : 'replace',
				value : '1',
				allowBlank : false
			}, {
				inputWidth : 140,
				width : 120,
				xtype : 'numberfield',
				fieldLabel : '超时(ms)',
				name : 'timeOut',
				value : '3000',
				allowBlank : false
			}]
		});
		return form;
	},
	layout : 'border'
});