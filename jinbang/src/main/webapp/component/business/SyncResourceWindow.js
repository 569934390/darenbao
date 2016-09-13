Ext.define('component.business.SyncResourceWindow', {
	extend : 'component.public.CustomGridEditorWindowComponent',
	modal : false,
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
			var forms = me.query('form'),agentIdField;
			var nodeCatagory = config.getNodeCategory();
			for (var key in forms) {
				if (forms[key].name == 'baseInfo') {
					forms[key].getForm().findField('ip').setValue(config.getIp());
					forms[key].getForm().findField('viceIp').setValue(config.getPIpaddress());
					forms[key].getForm().findField('nodeId').setValue(config.getNodeId());
					if(nodeCatagory=='104'||nodeCatagory=='105'){
						forms[key].getForm().findField('writeCommunity').setValue('private');
						forms[key].getForm().findField('community').setValue('public');
						forms[key].getForm().findField('telnetLoginPwd').setValue('xmdl@95598');
					}
					else{
						forms[key].getForm().findField('writeCommunity').setValue('pwzdh');
						forms[key].getForm().findField('community').setValue('pwzdh');
						forms[key].getForm().findField('telnetLoginPwd').setValue('123');
					}
					agentIdField = forms[key].getForm().findField('agentId');
				}
				else if(forms[key].name == 'cmdInfo'){
					forms[key].getForm().findField('scriptName').setValue(config.getNodeTypeId());
				}
			}
			agentIdField.getStore().load({
				callback:function(){
					if(config.autoPing!=false){
						me.doCmd();
					}
				}
			});
		});
		me.on('close',function(){
			component.socket.Message.unbind('mq_net_web',me.id);
		});
	},
	createCmd : function() {
		var me = this;
		var cmd = {
			xtype : 'form',
			frame : true,
			layout : 'hbox',
			name : 'cmdInfo',
			items : [{
				xtype : 'ajaxComboBox',
				width : 545,
				name:'scriptName',
				queryMode:'remote',
				readOnly:true,
				sqlKey:sqlmapPrefix+"ComboCommonMapper.syncResourceScriptCombo"
			},{
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
		values.scriptName=forms[key].getForm().findField('scriptName').getRawValue();
		component.socket.Message.bind('mq_net_web', {
			timer : 5000,
			callback : function(data) {
				var l = me.commandView.getStore().getCount(), store = me.commandView.getStore(), result = Ext.decode(data);
				for (var i = 0; i < l; i++) {
					var record = store.getAt(i);
					if (result.uuid == record.get('nodeId')) {
						if (result.message==true) {
							record.set('src', webRoot+ 'common/topoImages/16x16/ok.png');
							record.set('success', true);
							record.set('successColor','color:green;');
							record.set('result', '同步完成!');
						}else {
							record.set('src',webRoot+ 'common/topoImages/16x16/sync.gif');
							record.set('success', true);
							record.set('result', result.message);
							record.set('successColor','color:#blue;');
							record.get('logger').push({debug:result.message});
						}
					}
				}
			}
		},me,me.id);
		Ext.Ajax.request({
		    url: webRoot+ 'ctrlCmdController/callScriptCmd.do',
		    params: {
				agentId : values.agentId,
				command : Ext.encode(values)
			},
		    success: function(response){
		        var text = response.responseText;
		        var data = Ext.JSON.decode(text);
		        me.commandView.getStore().insert(0, {
					nodeId : data.uuid,
					value : '同步任务:"' + values.ip + '->' + values.scriptName+ '"',
					src : webRoot + 'common/topoImages/16x16/sync.gif',
					success : false,
					result : '',
					logger:[]
				});
				(function(record) {
					setTimeout(function() {
						if (record.get('success') == false) {
							record.set('src',webRoot+ 'common/topoImages/16x16/cancel.png');
							record.set('success', true);
							record.set('result', '网元同步执行超时！');
						}
					}, 180000);
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
    		 	name:'viceIp'
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
				name : 'community',
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
				value:'2',
				queryMode : 'local',
				name : 'version',
				allowBlank : false,
				blankText : '请选择发现协议',
				data : [['SNPMv1', '1'], ['SNMPv2c', '2'],['SNMPv3c', '3']]
			}, {
				inputWidth : 200,
				width : 200,
				xtype : 'textfield',
				labelWidth:80,
				fieldLabel : 'telnet用户名',
				name : 'telnetLoginName',
				value : 'admin'
			}, {
				inputWidth : 200,
				width : 200,
				xtype : 'textfield',
				labelWidth:70,
				fieldLabel : 'telnet密码',
				name : 'telnetLoginPwd',
				value : '123'
			}]
		});
		return form;
	},
	layout : 'border'
});