Ext.define("component.public.SnmpComponent", {
	layout : 'fit',
	title : 'SNMP ping',
	width : 500,
	height : 350,
	alias: 'widget.snmpComponent',
	extend : 'Ext.window.Window',
	items : [{
		xtype : 'form',
		frame : true,
		border : false,
		items : [{
			xtype : 'fieldset',
			layout : 'column',
			items : [{
				layout : 'form',
				frame : true,
				border : false,
				columnWidth : 0.5,
				name : 'leftForm',
				defaults : { // defaults are applied to items, not the
					// container
					labelAlign : 'right',
					labelWidth : 60
				},
				defaultType : 'textfield',
				items : [{
					fieldLabel : 'ip地址',
					maskRe : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
					regex : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
					regexText : "请输入合法的ip地址"
				}, {
					fieldLabel : '共同体'
				}, {
					fieldLabel : '超时(秒)'
				}, {
					fieldLabel : '用户名称'
				}, {
					fieldLabel : '认证协议'
				}, {
					fieldLabel : '加密协议'
				}]
			}, {
				layout : 'form',
				columnWidth : 0.5,
				defaults : { // defaults are applied to items, not the
					// container
					labelAlign : 'right',
					labelWidth : 60
				},
				border : false,
				name : 'rightForm',
				frame : true,
				defaultType : 'textfield',
				items : [{
							fieldLabel : 'SNMP端口'
						}, {
							xtype : 'combo',
							fieldLabel : 'SNMP版本',
							displayField : 'label',
							mode : 'local',
							editable : false,
							typeAhead : true,
							triggerAction : 'all',
							store : new Ext.data.ArrayStore({
										fields : ['label', 'value'],
										data : [["SNMPV1", "SNMPV1"],
												["SNMPV2C", "SNMPV2C"],
												["SNMPV3", "SNMPV3"]]
									})

						}, {
							fieldLabel : '重试次数'
						}, {
							fieldLabel : '安全等级'
						}, {
							fieldLabel : '认证密码'
						}, {
							fieldLabel : '私有密码'
						}]
			}]
		}, {
			xtype : 'textarea',
			width : 478
		}]
	}],
	buttons : [{
				text : "执行"
			}, {
				text : "取消"
			}]
});