Ext.define('component.business.NetToolsMenu', {
	extend : 'Ext.menu.Menu',
	requires : ['component.socket.Message'],
	constructor : function(config) {
		var me = this;
		if(config.initJuery!=false){
			Ext.create('component.socket.jq');
		}
		me.items = [{
			text : 'SNMP测试连接',
			iconCls : 'toolbar-command',
			handler : function() {
				if (!me.netToolsWin || me.netToolsWin.isDestroyed) {
					me.netToolsWin = Ext.create(
						'component.business.NetToolsWindow', {
							title : 'SNMP工具',
							width : 600,
							height : 400,
							getIp : function() {
								return config.getIp()
										|| '172.12.0.12';
							},
							getNodeCategory:function(){
								return config.getNodeCategory();
							},
							setPing : function(val) {
								config.setPing(val);
							},
							getNodeTypeId:function(){
								return config.getNodeTypeId();
							},
							getNodeId:function(){
								return config.getNodeId();
							}
						});
				}
				me.netToolsWin.show();
			}
		}, {
			text : 'PING测试连接',
			iconCls : 'toolbar-command',
			handler : function() {
				if (!me.pingWin || me.pingWin.isDestroyed) {
					me.pingWin = Ext.create(
						'component.business.PingWindow', {
							title : 'ping工具',
							width : 710,
							height : 400,
							getIp : function() {
								return config.getIp();
							},
							setPing : function(val) {
								config.setPing(val);
							},
							getNodeTypeId:function(){
								return config.getNodeTypeId();
							},
							getNodeId:function(){
								return config.getNodeId();
							}
						});
				}
				me.pingWin.show();
			}
		}, {
			text : 'PING接口连接',
			iconCls : 'toolbar-command',
			handler : function() {
				if (!me.pingPortWin || me.pingPortWin.isDestroyed) {
					me.pingPortWin = Ext.create(
						'component.business.PingPortWindow', {
							title : 'ping工具',
							width : 710,
							height : Ext.getBody().getHeight()-20,
							getNodeTypeId:function(){
								return config.getNodeTypeId();
							},
							getNodeId:function(){
								return config.getNodeId();
							}
						});
				}
				me.pingPortWin.show();
			}
		}];
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
	}
});