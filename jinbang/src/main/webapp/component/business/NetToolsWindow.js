Ext.define('component.business.NetToolsWindow', {
	extend : 'component.public.CustomGridEditorWindowComponent',
	modal : false,
	mixins: {
    	kpiDatas:'component.business.KpiDatas'
    },
	requires:['component.public.TextButtonField','component.public.AjaxComboBox'],
	constructor : function(config) {
		var me = this;
		me.config = config;
		me.items = [me.createForm(config),
		me.commandView = Ext.create('component.business.CommandView', {
				region : 'center'
			}), Ext.apply(me.createCmd(), {
				region : 'south'
			})];
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
		me.on('show', function() {
			me.commandView.getStore().removeAll();
			var forms = me.query('form');
			var nodeCatagory = config.getNodeCategory();
			for (var key in forms) {
				if (forms[key].name == 'baseInfo') {
					forms[key].getForm().findField('ip').setValue(config.getIp());
					forms[key].getForm().findField('nodeId').setValue(config.getNodeId());
					if(nodeCatagory=='104'||nodeCatagory=='105'){
						forms[key].getForm().findField('writeCommunity').setValue('private');
						forms[key].getForm().findField('readCommunity').setValue('public');
					}
					else{
						forms[key].getForm().findField('writeCommunity').setValue('pwzdh');
						forms[key].getForm().findField('readCommunity').setValue('pwzdh');
					}
					forms[key].getForm().findField('kpi').setValue(me.getKpiByNodeTypeId(config.getNodeTypeId(),'snmp'));
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
	createCmd : function() {
		var me = this;
		var cmd = {
			xtype : 'form',
			frame : true,
			layout : 'hbox',
			items : [{
				xtype : 'ajaxComboBox',
				width : 545,
				plugins : [Ext.create('component.public.ComboSelectFirstPlugin')],
				queryMode : 'local',
				name : 'oid',
				editable : true,
				data : [['系统类型', '1.3.6.1.2.1.1.2'],
						['系统名称', '1.3.6.1.2.1.1.5'],
						['系统描述', '1.3.6.1.2.1.1.1']],
				enableKeyEvents : true,
				listeners : {
					keypress : function(combo, e, eOpts) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							me.doCmd(this.getValue());
						}
					}
				}
			}, {
				xtype : 'button',
				iconCls : 'toolbar-search',
				handler : function() {
					me.doCmd();
				}
			}]
		};
		return cmd;
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
				for (var i = 0; i < l; i++) {
					var record = store.getAt(i);
					if (result.uuid == record.get('nodeId')) {
						if(Ext.isEmpty(result.message)){
							record.get('logger').push({debug:result.logger});
							record.set('result', result.logger);
						}
						else{
							var message = Ext.decode(result.message);
							var flag = false;
							for (var key in message) {
								if (!Ext.isEmpty(key)) {
									flag = true;
									break;
								}
							}
							if (flag) {
								record.set('src', webRoot+ 'common/topoImages/16x16/ok.png');
								record.set('success', true);
								record.set('result', result.message);
								me.config.setPing(flag);
								record.set('successColor','color:green;');
							} else {
								record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
								record.set('success', true);
								record.set('result', '无返回值,请用ping操作确认该设备是否在线');
								record.set('successColor','color:red;');
							}
						}
					}
				}
			}
		},me);
		Ext.Ajax.request({
		    url: webRoot+ 'ctrlCmdController/justDoIt.do',
		    params: {
				agentId : values.agentId,
				command : Ext.encode(values)
			},
		    success: function(response){
		        var text = response.responseText;
		        var data = Ext.JSON.decode(text);
		        me.commandView.getStore().insert(0, {
					nodeId : data.uuid,
					value : 'SNMP OID:"' + values.ip + '->' + values.oid
							+ '"',
					src : webRoot + 'common/topoImages/16x16/sync.gif',
					success : false,
					result : 'asdfdsa',
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
		    },
		 	failure: function(response,opts){
		 		Ext.Msg.alert('提示','执行超时，请检查网络');
		 	}
		});
	},
	createForm : function(obj) {
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
						inputWidth : 200,
						width : 200,
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
						inputWidth : 140,
						width : 140,
						xtype : 'numberfield',
						fieldLabel : '端口',
						name : 'port',
						value : '161',
						allowBlank : false
					}, {
						inputWidth : 200,
						width : 200,
						xtype : 'textfield',
						fieldLabel : '读共同体',
						name : 'readCommunity',
						value : 'pwzdh'
					}, {
						inputWidth : 200,
						width : 200,
						xtype : 'textfield',
						fieldLabel : '写共同体',
						name : 'writeCommunity',
						value : 'pwzdh'
					}, {
						fieldLabel : '发现协议',
						inputWidth : 140,
						xtype : 'ajaxComboBox',
						width : 140,
						plugins : [Ext.create('component.public.ComboSelectFirstPlugin')],
						queryMode : 'local',
						name : 'version',
						allowBlank : false,
						blankText : '请选择发现协议',
						data : [['SNPMv1', '1'], ['SNMPv2c', '2'],['SNMPv3c', '3']]
					}]
		});
		return form;
	},
	layout : 'border'
});