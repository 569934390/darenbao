/**
 * 拓扑设置面板
 */
Ext.define('component.topo.TopoLayoutSetWin', {
	extend : 'window.parent.window.Ext.window.Window',
	alias : 'widget.topoLayoutSetWin',
	constructor:function(condition){
		var me = this;
		me.Ext =Ext.isEmpty(window.parent.window.Ext)?Ext:window.parent.window.Ext;
		
		me.createLayoutSetForm();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.layoutSetForm]
		});
		me.callParent([config]);
		
		me.setFormValues();
	},
	createLayoutSetForm : function(){
		var me = this;
		
		var refreshTime = me.Ext.create('Ext.form.ComboBox', {
			emptyText : "请选择",
		    fieldLabel: '刷新频率',
			labelAlign : 'right',
			name : 'refreshTime',
			labelWidth : 80,
			editable : false,
			value : '180000',
		    store: me.Ext.create('Ext.data.Store', {
		        fields: ['abbr', 'name'],
		        data : [
		            {"abbr":"60000", "name":"1分钟"},
		            {"abbr":"180000", "name":"3分钟"},
		            {"abbr":"300000", "name":"5分钟"}
		        ]
		    }),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'abbr'
		});

		var filedSet = new me.Ext.form.FieldSet({
			title : '',
			border : false,
			autoHeight : true,
			labelWidth : 80,
			labelAlign : 'right',
			layout : 'column',
			items : [{
				columnWidth :1,
				border : false,
				layout : 'form',
				items  : [refreshTime]
			},{
				columnWidth :.5,
				layout : 'form',
				border : false,
				items  : [{
					xtype: 'fieldcontainer',
					defaultType: 'checkboxfield',
		            fieldLabel: '面板显示',
		            labelWidth : 80,
		            labelAlign : 'right',
		            items: [{
	                    boxLabel  : '告警面板',
	                    name      : 'alarmPanelState',
	                    inputValue: true,
						checked   : true
	                }, {
	                    boxLabel  : '图形面板',
	                    name      : 'topoChartState',
	                    inputValue: true
	                }]
				}]
			},{
				columnWidth :.5,
				layout : 'form',
				border : false,
				items : [{
					xtype: 'fieldcontainer',
					defaultType: 'checkboxfield',
					fieldLabel: '面板显示',
					hideLabel : true,
					items: [{
						boxLabel  : '属性面板',
						name      : 'propertyPanelState',
						inputValue: true,
	                    checked   : true
					}]
				}]
			}]
		});

		var layoutSetForm = new me.Ext.form.FormPanel({
			region : 'center',
			name : 'layoutSetForm',
			labelAlign : 'right',
			border : true,
			frame : false,
			deferredRender : false,
			style : 'padding:0 0 0 0',
			activeTab : 0,
			width : '100%',
			height : 270,
			defaults : {
				autoScroll : true
			},
			layoutOnTabChange : true,
			items : [filedSet],
			buttons : [ {
				xtype : 'button',
				text : '保存',
				scope : this,
				handler:me.buttonEvent.createDelegate(me,['saveLayoutSet'],0)
//				handler : me.saveTopoSet.createDelegate(me)
			}, {
				xtype : 'button',
				text : '取消',
				scope : this,
				handler : function() {
					me.hide();
				}
			} ]
		});
		me.layoutSetForm = layoutSetForm;
	},
	saveTopoSet:function(){
		var me = this;
		var layoutSetForm = me.layoutSetForm.getForm().getValues();
		
		if(layoutSetForm.topoChartState){
			Ext.getCmp('topoChartPanel').show();
		}else{
			Ext.getCmp('topoChartPanel').hide();
		}
		if(layoutSetForm.alarmPanelState){
			Ext.getCmp('topoBusinessPanel').show();
			if(Ext.isEmpty(Ext.getCmp('topoAlarmGridPanel'))){
				createTopoAlarmGridPanel();
				topoBusinessPanel.add({
					title: '告警',
					id : 'topoAlarmGridPanel',
					border : false,
					frame : false,
					layout :'border',
					items : [topoAlarmGridPanel]
				});
			}
		}else{
			if(!Ext.isEmpty(Ext.getCmp('topoAlarmGridPanel'))){
				Ext.getCmp('topoBusinessPanel').hide();
				Ext.getCmp('topoAlarmGridPanel').close();
			}
		}
		if(layoutSetForm.propertyPanelState){
			Ext.getCmp('topoPropertyPanel').show();
		}else{
			Ext.getCmp('topoPropertyPanel').hide();
		}
		Ext.state.Manager.set("refreshTime",layoutSetForm.refreshTime);
		Ext.state.Manager.set("topoChartState",layoutSetForm.topoChartState);
		Ext.state.Manager.set("alarmPanelState",layoutSetForm.alarmPanelState);
		Ext.state.Manager.set("propertyPanelState",layoutSetForm.propertyPanelState);

		var mapPanel = document.getElementById('frame_main');
		mapPanel.contentWindow.topoRefreshSpeed = layoutSetForm.refreshTime;
		Ext.Msg.alert('提示','保存设置成功！');
		me.close();
		return;
		Ext.Ajax.request({
			url : webRoot + '/topoSymbol/saveTopoLayoutSet.do',
			method : 'POST',
			params : layoutSetForm,
			success : function(form, action) {
//				reLoadMapData(false);
//				//更改刷新频率
//				clearReFresh();
//				reFreshMap();
				Ext.Msg.alert('提示','保存设置成功！');
				me.close();
			},
			failure : function() {
			}
		});
	},
	setFormValues : function(){
		var me = this;
		var providerData={};

		providerData.refreshTime        = Ext.state.Manager.get("refreshTime");
		providerData.topoChartState    = Ext.state.Manager.get("topoChartState");
		providerData.alarmPanelState    = Ext.state.Manager.get("alarmPanelState");
		providerData.propertyPanelState = Ext.state.Manager.get("propertyPanelState");
		
		if(!Ext.isEmpty(providerData.refreshTime)){
			me.layoutSetForm.getForm().setValues(providerData)
		}
		return;
		Ext.Ajax.request({
			url : webRoot + 'topoSymbol/getTopoLayoutSet.do',
			method : 'POST',
			params : {
			},
			success : function(resp, action) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(!Ext.isEmpty(respText.refreshTime)){
					me.layoutSetForm.getForm().setValues(respText)
				}
			},
			failure : function() {
			}
		});
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'saveLayoutSet':
				me.saveTopoSet();
				break;
		}
	}
});