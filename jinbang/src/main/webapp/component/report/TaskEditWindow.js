Ext.Loader.setConfig({
	enabled : true,
	paths : {
		perf:ctx+'/task/manager'
	}
});
Ext.define('component.report.TaskEditWindow', {
	extend:'Ext.window.Window',
	alias:'widget.taskEditWindow',
	closeAction:'hide',
	mixins:['component.public.EditWindow'],
	requires:['component.public.AjaxComboBox','Ext.ux.form.DateTimeField','component.public.TextButtonField'],
	constructor:function(config){
		var me=this;
		me.taskForm=me.createTaskForm();
		config=config||{};
		Ext.apply(config,{
			width:700,
			height:500,
			layout:'fit',
			title:'报表任务窗口',
			items:[me.taskForm],
			buttonAlign:'center',
			buttons:[{
				text:'确定',
				name:'saveBtn',
				plugins:[Ext.create('component.public.ButtonHandlerPlugin',{handler:me.saveOrUpdateReportTask,operate:common.constant.buttonOperate.saveOrUpdate,scope:me})]
			},{
				text:'取消',
				name:'cancelBtn',
				plugins:[Ext.create('component.public.ButtonHandlerPlugin',{window:me,operate:common.constant.buttonOperate.cancel})]
			}]
		});
		me.callParent([config]);
		me.on('close',function(){
			if(me.callBack){
				me.callBack();
			}
		});
	},
	saveOrUpdateReportTask:function(){
		var me=this;
		var params=Ext.getFormValues(me.taskForm);
		params.creator=session.user.id;
		params.action=me.getEditState();
		Ext.Ajax.request({
			url:ctx+'/report/saveOrUpdateReportTask.do',
			params:params,
			success:function(response,action){
				me.close();
			},
			failure:function(response,action){
				
			}
		});
	},
	createTaskForm:function(){
		var me=this;
		me.taskTimeForm=Ext.create('perf.Builds').getTimePanel();
		return Ext.create('Ext.form.Panel', {
			name:'taskForm',
			frame:true,
			items : [ {
				xtype : 'fieldset',
				title : '任务信息',
				height:180,
				layout:'column',
				defaults:{
					style:'margin-top:3px',
					labelAlign:'right',
					columnWidth:0.5
				},
				items : [{
					xtype:'textfield',
					fieldLabel : '任务名称',
					name : 'taskName'
				},{
					xtype:'ajaxComboBox',
					fieldLabel : '状态',
					plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
					queryMode:'local',
					data:common.constant.state,
					name : 'state'
				},{
					xtype:'datetimefield',
					fieldLabel : '创建时间',
					editable:false,
					format:'Y-m-d',
					value:new Date(),
					name : 'createDate'
				},{
					xtype:'datetimefield',
					format:'Y-m-d',
					fieldLabel : '最后修改时间',
					editable:false,
					value:new Date(),
					name : 'lastModifyDate'
				},{
					xtype:'ajaxComboBox',
					fieldLabel : '报表模板',
					name : 'taskTemplateMapId',
					queryMode:'remote',
					plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
					sqlKey:sqlmapPrefix+"TaskTemplateMapMapper.selectAll",
					displayField:'taskTemplateMapName',
					valueField:'taskTemplateMapId'
				},{
					xtype:'textarea',
					fieldLabel : '任务描述',
					columnWidth:1,
					name : 'taskDesc'
				},{
					xtype:'hidden',
					fieldLabel : '任务描述',
					columnWidth:1,
					name : 'taskId'
				}]
			},{
				xtype : 'fieldset',
				height: 250,
				title : '采集任务',
				items : [ me.taskTimeForm ]
			}  ]
		});
	}
});