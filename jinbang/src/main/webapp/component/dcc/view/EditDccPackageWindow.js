Ext.define('component.dcc.view.EditDccPackageWindow', {
	extend : 'Ext.window.Window',
	alias : 'widget.editDccPackageWindow',
	constructor : function(config) {
		var me = this;
		config=config||{};
		me.tabPanel=me.createTabPanel(config);
		me.baseForm=me.createBaseForm(config);
		config = Ext.applyIf(config || {}, {
			title : '编辑消息包',
			frame:true,
			height : Ext.getBody().getHeight() - 50,
			width : Ext.getBody().getWidth()*0.95,
			modal : true,
			closeAction : 'hide',
			layout:'border',
			items:[me.baseForm,me.tabPanel]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.on('show',function(){
			me.tabPanel.setActiveTab(0);
		});
	},
	setDcc:function(dcc){
		var me=this;
		me.dcc=dcc;
		me.dcc.dccId=me.dcc.dcc_id||me.dcc.dccId;
		me.dcc.parentId=me.dcc.parent_id||me.dcc.parentId;
		me.baseForm.getForm().setValues(me.dcc);
		me.tabPanel.setDcc(me.dcc);
	},
	createBaseForm:function(){
		var me=this;
		var baseForm  = Ext.create('component.view.form.FormPanel', {
			region:'north',
			items : [{
					xtype : 'textfield',
					width:250,
					fieldLabel : '消息包名',
					name : 'text',
					allowBlank:false,
					blankText:'请填写消息包名'
				},{
					xtype : 'numberfield',
					fieldLabel : '排序',
					name : 'seq',
					value:0
				},{
					xtype : 'hidden',
					name : 'dccId'
				},{
					xtype : 'hidden',
					name : 'type'
				},{
					xtype : 'hidden',
					name : 'parentId'
				},{
					xtype:'button',
					iconCls:'toolbar-save',
					style:'margin-left:50px;',
					text:'保存',
					handler:Ext.bind(me.saveDccHandler,me)
				}]
		});
		return baseForm;
	},
	createTabPanel:function(config){
		return Ext.create('component.dcc.view.EditDccPackagePanel',{
			border:false,
			region:'center'
		});
	},
	saveDccHandler:function(){
		var me=this;
		if(!me.baseForm.getForm().isValid()) return Ext.Msg.alert('提示','请将表单数据填写完整!');
		var params = me.baseForm.getForm().getValues();
		params.content = me.tabPanel.editor.getValue();

		Ext.Persistent.post(ctx+'/debug/savePackage.do',params,function(result){
			if(result.success) {
				me.afterSave();
			}else{
				Ext.Msg.alert('提示','报文格式错误');
				console.info(result.msg);
			}
		},true);
	}
});