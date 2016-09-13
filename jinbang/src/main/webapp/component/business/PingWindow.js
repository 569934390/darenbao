Ext.define('component.business.PingWindow', {
	extend : 'component.public.CustomGridEditorWindowComponent',
	modal : false,
	mixins: {
    	kpiDatas:'component.business.KpiDatas'
    },
	requires:['component.public.TextButtonField'],
	constructor : function(config) {
		var me = this;
		me.config = config;
		me.items = [me.createForm(config),
			me.commandView = Ext.create('component.business.CommandView', {
				region : 'center'
			})];
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
		me.on('show', function() {
			var forms = me.query('form');
			me.commandView.getStore().removeAll();
			for (var key in forms) {
				if (forms[key].name == 'baseInfo') {
					forms[key].getForm().findField('ip').setValue(config.getIp());
					forms[key].getForm().findField('nodeId').setValue(config.getNodeId());
					forms[key].getForm().findField('kpi').setValue(me.getKpiByNodeTypeId(config.getNodeTypeId()));
					forms[key].getForm().findField('agentId').getStore().load({
						callback:function(){
							if(config.autoPing!=false){
								me.doCmd();
							}
						}
					});
				}
			}
		});
	},
	doCmd : function(value) {
		var me = this;
		var forms = me.query('form'), values = {};
		for (var key in forms) {
			Ext.apply(values, forms[key].getForm().getValues());
		}
		if(Ext.isEmpty(values.agentId)){
			setTimeout(function(){
				me.doCmd();
			},100);
			return;
		}
		component.socket.Message.bind('mq_net_web', {
			timer : 5000,
			callback : function(data) {
				var l = me.commandView.getStore().getCount(), store = me.commandView
						.getStore(), result = Ext.decode(data);
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
							me.config.setPing(result.message);
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
				inputWidth : 180,
				width : 180,
				xtype : 'textfield',
				fieldLabel : 'IP地址',
				name : 'ip',
				value : obj.getIp()
			}, {
				inputWidth : 200,
				width : 200,
				xtype : 'textButtonField',
				fieldLabel : '任务代理',
				name : 'agentId',
				plugins : [Ext.create('component.agent.ComboPlugin')],
				allowBlank : false,
				blankText : '请选择代理',
				editable : false
			}, {
				inputWidth : 100,
				width : 100,
				xtype : 'numberfield',
				fieldLabel : '重复次数',
				name : 'replace',
				value : '3',
				allowBlank : false
			}, {
				inputWidth : 140,
				width : 120,
				xtype : 'numberfield',
				fieldLabel : '超时(ms)',
				name : 'timeOut',
				value : '3000',
				allowBlank : false
			}, {
				xtype : 'button',
				width : 24,
				text : ' ',
				iconCls : 'toolbar-search',
				handler : function() {
					me.doCmd();
				}
			}]
		});
		return form;
	},
	layout : 'border'
});