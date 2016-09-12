Ext.define('component.report.TaskGrid', {
	extend:'component.public.CustomPanelComponent',
	aliasName : 'REPORT_TASK',
	alias:'widget.taskGrid',
	title : '报表任务列表',
	pageSize : 25,
	constructor:function(config){
		var me=this;
		config=config||{};
		Ext.apply(config,{
			searchComponents : [{
				key : 'STATE',
				item : Ext.create('component.public.AjaxComboBox', {
					name : 'STATE',
					clear:true,
					plugins:[Ext.create('component.public.ComboSelectFirstPlugin')],
					data : common.constant.state
				})
			}],
			displayFunctions : [
			{
				key : 'STATE',
				fun : function(value, metadata, record) {
					return Ext.getArrayValue(common.constant.state,
							value, 1);
				}
			}],
			dockedItems:[{//菜单栏
						xtype: 'toolbar',
						dock: 'top',
						items : [ {
							text : '查看',
							iconCls : 'toolbar-view',
							name : 'taskView'
						}, '-', {
							text : '新增',
							iconCls : 'toolbar-add',
							name : 'taskAdd'
						}, '-', {
							text : '修改',
							iconCls : 'toolbar-edit',
							name : 'taskEdit'
						}, '-', {
							text : '删除',
							iconCls : 'toolbar-delete',
							name : 'taskDelete',
							plugins:[Ext.create('component.public.ButtonHandlerPlugin',{handler:me.taskDelete,operate:common.constant.buttonOperate.remove,scope:me})]
						}]
			}],
			gridCfg: {
			 		columnLines: true,
			 		multiSelect: false,
			 		selModel: {
			 			selType:'checkboxmodel'
			 		}
				},
			init : function(panel, toolPanel, grid) {
				var me=this;
				me.down('[name="taskView"]').on('click',Ext.bind(me.taskView,me));
				me.down('[name="taskAdd"]').on('click',Ext.bind(me.taskSaveOrUpdate,me,[common.constant.buttonOperate.add],0));
				me.down('[name="taskEdit"]').on('click',Ext.bind(me.taskSaveOrUpdate,me,[common.constant.buttonOperate.edit],0));
//				me.down('[name="taskDelete"]').on('click',Ext.bind(me.taskDelete,me));
			}
		});
		this.callParent([config]);
	},
	getTaskEditWindow:function(){
		var me=this;
		if(!this.taskEditWindow){
			this.taskEditWindow=Ext.create('component.report.TaskEditWindow',{
				callBack:function(window){
					me.getGrid().getStore().reload();
				}
			});
		}
		return this.taskEditWindow;
	},
	setFormValues:function(){
		var me=this;
		var record=Ext.gridSelectCheck(me.getGrid(), false);
		if(!record) return;
		var params={};
		params.taskId=record.get('TASK_ID');
		Ext.Ajax.request({
			url:ctx+'/report/findReportTaskById.do',
			params:params,
			success:function(response,action){
				var reportTask=Ext.ResponseDecode(response);
				me.getTaskEditWindow().setEditState('view');
				var forms=me.getTaskEditWindow().query('form');
				Ext.each(forms,function(form,index){
					if(form.name=="taskForm"){
						form.getForm().setValues(reportTask);
					}
					if(form.name=="quartzPanel"){
						form.getForm().setValues(reportTask.quartz);
					}
				});
			},
			failure:function(response,action){
				
			},
		});
	
	},
	taskView:function(){
		var me=this;
		me.setFormValues();
		Ext.setReadOnly(me.getTaskForm(),true,'');
		me.getTaskEditWindow().down('button[name="saveBtn"]').hide();
		me.getTaskEditWindow().show();
	},
	getTaskForm:function(){
		return this.getTaskEditWindow().down('form[name="taskForm"]');
	},
	taskSaveOrUpdate:function(operate){
		var me=this;
		me.getTaskEditWindow().setEditState(operate);
		if(operate==common.constant.buttonOperate.edit){
			me.setFormValues();
		}else{
			me.getTaskForm().getForm().setValues({});
		}
		Ext.setReadOnly(me.getTaskForm(),false,'');
		me.getTaskEditWindow().down('button[name="saveBtn"]').show();
		me.getTaskEditWindow().show();
	
	},
	taskDelete:function(){
		var me=this;
		var record=Ext.gridSelectCheck(me.getGrid(), false);
		if(!record) return;
		var params={};
		params.taskId=record.get('TASK_ID');
		Ext.Ajax.request({
			url:ctx+'/report/deleteReportTask.do',
			params:params,
			success:function(response,action){
			},
			failure:function(response,action){
				
			},
		});
	}
});