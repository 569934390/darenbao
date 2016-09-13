/**
 * 例子:
   var win=Ext.create('component.alarm.AlarmNotifyTemplateWindow');
   win.show();
 */
/**
 * 告警通知模版控件
 */
Ext.define('component.alarm.AlarmNotifyTemplatePanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.alarmNotifyTemplatePanel',
	constructor : function(config) {
		config=config||{};
		var me = this;
		var customPanel = Ext.create('component.public.CustomPanelComponent', {
			aliasName : 'ALARM_NOTIFY_TEMPLATE',
			init : function(panel, toolPanel, grid) {
				me.grid=grid;
			}
		});
		Ext.applyIf(config, {
			layout : 'fit',
			items : [customPanel]
		})
		me.callParent([config]);
	},
	getGrid:function(){
		return this.grid;
	}
});
Ext.define('component.alarm.AlarmNotifyTemplateWindow', {
	extend : 'Ext.window.Window',
	closeAction:'hide',
	alias : 'widget.alarmNotifyTemplateWindow',
	constructor : function(config) {
		config=config||{};
		var me = this;
		var templatePanel = Ext.create('component.alarm.AlarmNotifyTemplatePanel');
		Ext.applyIf(config, {
			layout : 'fit',
			title : '模版信息',
			width:1000,
			height:500,
			items : [templatePanel]
		})
		me.callParent([config]);
		if(config.showCallBack){
			me.on('show',config.showCallBack);
		}
	},
	getRecords:function(){
		var grid=this.down('alarmNotifyTemplatePanel').getGrid();
		var records=grid.getSelectionModel().getSelection();
		return records;
	},
	buttons : [{
		text : '确定',
		handler : function() {
			var win=this.up('alarmNotifyTemplateWindow');
			var records=win.getRecords();
			if(win.callBack){
				win.callBack(records,win);
			}
			win.close();
		}
	}, {
		text : '取消',
		handler : function() {
			this.up('alarmNotifyTemplateWindow').close();
		}
	}],
	buttonAlign:'center'
});
