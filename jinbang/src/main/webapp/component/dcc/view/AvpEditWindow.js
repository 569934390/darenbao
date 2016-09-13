Ext.define('component.dcc.view.AvpEditWindow', {
	extend:'component.public.PopWindow',
	alias:'widget.avpEditWindow',
	config:{
		configTree:null,
		action:null
	},
	requires:['component.public.AjaxComboBox'],
	constructor:function(config){
		var me=this;
		config=config||{};
		me.searchForm=me.createSearchForm();
		me.grid=me.createGrid();
		me.editForm=me.createEditForm();
		Ext.applyIf(config,{
			layout:'border',
//			dockedItems:[{
//			    xtype: 'toolbar',
//			    dock: 'top',
//			    frame:true,
//			    layout:{
//			    	pack:'left'
//			    },
//				items: [{
//					text : '添加avp',
//					name : 'addBtn'
//			    }, {
//					text : '删除avp',
//					name : 'deleteBtn'
//				}]
//			}],
			items:[me.searchForm,me.grid,me.editForm]
		});
		me.callParent([config]);
	},
	bindEvent:function(){
		var me=this;
		me.callParent();
		me.searchForm.down('[name=queryBtn]').on('click',Ext.bind(me.queryHandler,me));
		me.down('[name=saveBtn]').on('click',Ext.bind(me.saveHandler,me));
		me.down('[name=cancelEditBtn]').on('click', Ext.bind(me.resetHandler,me));
		me.down('[name=deleteBtn]').on('click',Ext.bind(Ext.showConfirmWindow,me,[me,me.deleteHandler,common.constant.buttonOperate.remove],0));
		me.grid.on('itemclick',Ext.bind(me.editHandler,me));
	},
	okHandler:function(){
		var me=this;
		var record=Ext.gridSelectCheck(me.grid);
		if(!record) return;
		me.close();
		if(me.callback){
			me.callback(record);
		}
	},
//	addHandler:function(){
//		var me=this;
//		me.rowEditor.cancelEdit();
//		var r = Ext.create('component.dcc.model.AvpTree', {
//                    mFlag:1,
//                    pFlag:0
//                });
//        me.grid.store.insert(0, r);
//        me.rowEditor.startEdit(0, 0);
//	},
	deleteHandler:function(){
		var me=this;
		var formValues=me.editForm.getForm().getValues();
		Ext.Ajax.request({
			params:{avpCode:formValues['avpCode']},
			url:ctx+'/debug/deleteAvp.do',
			success:function(){
				me.resetHandler();
			}
		});
	},
	resetHandler:function(){
		this.editForm.getForm().reset();
		this.editForm.getForm().setValues({
			mFlag:1,
			pFlag:0,
			avpType:'OctetString'
		});
		this.changeState('add');
		this.grid.getStore().reload();
	},
	queryHandler:function(){
		var me=this;
		var formValues=me.searchForm.getForm().getValues();
		Ext.apply(me.grid.getStore().getProxy().extraParams,{
			'conditions[avpName]':'%'+formValues['avpName'].trim()+'%',
			'conditions[avpCode]':'%'+formValues['avpCode'].trim()+'%'
		}),
		me.grid.getStore().load();
	},
	editHandler:function(grid,record){
		var me=this;
		me.editForm.getForm().setValues(record.data);
		me.changeState('update');
	},
	changeState:function(state){
		var me=this;
		me.editState=state;
		var deleteBtn=me.editForm.down('[name=deleteBtn]');
		var editBtn=me.editForm.down('[name=saveBtn]');
		if(state!="add"){
			editBtn.setText('修改');
			deleteBtn.enable();
		}else{
			editBtn.setText('添加');
			deleteBtn.disable();
		}
		
	},
	saveHandler:function(){
		var me=this;
		var basicEditForm=me.editForm.getForm();
		var params=basicEditForm.getValues();
		params.mFlag=params.mFlag=='on'?1:0;
		params.pFlag=params.pFlag=='on'?1:0;
		params.action=me.editState||'add';
		if(!basicEditForm.isValid()){
			return;
		}
		Ext.Ajax.request({
			params:params,
			url:ctx+'/debug/addOrUpdateAvp.do',
			success:function(){
				me.resetHandler();
			}
		});
	},
	createSearchForm:function(){
		return Ext.create('Ext.form.Panel', {
			frame:true,
			region:'north',
			layout:'column',
			defaults:{
				labelAlign:'right',
				labelWidth:50,
				xtype:'textfield',
				style:'margin-right:10px;'
			},
			items:[{
				name:'avpName',
				fieldLabel:'名称'
			},{
				name:'avpCode',
				fieldLabel:'编码'
			},{
				xtype:'button',
				name:'queryBtn',
				text:'查询'
			},{
				xtype:'button',
				name:'resetBtn',
				plugins:Ext.create('component.public.plugin.ButtonResetPlugin'),
				text:'重置'
			}]
		});
	},
	createEditForm:function(){
		return Ext.create('Ext.form.Panel', {
			frame:true,
			region:'east',
			width:300,
			defaults:{
				labelAlign:'right',
				labelWidth:50,
				xtype:'textfield'
			},
			dockedItems:[{
				xtype:'toolbar',
				dock : 'bottom',
				ui : 'footer',
				frame : true,
				layout : {
					pack : 'center'
				},
				items : [{
							text : '添加',
							name : 'saveBtn'
						}, {
							text : '重置',
							name : 'cancelEditBtn'
						}, {
							text : '删除',
							name : 'deleteBtn',
							disabled:true
						}]
			}],
			items:[{
				name:'avpName',
				fieldLabel:'名称',
				allowBlank:false
			},{
				name:'avpCode',
				fieldLabel:'编码',
				allowBlank:false
			},{
				xtype:'ajaxComboBox',
				name:'avpType',
				fieldLabel:'类型',
				plugins:Ext.create('component.public.ComboSelectFirstPlugin'),
				data : common.constant.testDcc.dataType
			},{
				xtype:'checkbox',
				name:'mFlag',
				checked:true,
				fieldLabel:'mFlag'
			},{
				xtype:'checkbox',
				name:'pFlag',
				checked:false,
				fieldLabel:'pFlag'
			}]
		});
	
	},
	createGrid:function(){
		var me=this;
		var store=Ext.create('component.public.AjaxStore', {
				model : 'component.dcc.model.AvpTree',
				pageSize:20,
				root:'result',
				autoLoad:true,
				url : ctx + '/debug/loadAvpList.do'
			});
		return Ext.create('Ext.grid.Panel',{
			region:'center',
			loadMask : true,
			useArrows : true,
			store : store,
			dockedItems: [{
		        xtype: 'pagingtoolbar',
		        store: store,   // same store GridPanel is using
		        dock: 'bottom',
		        displayInfo: true
		    }],
			plugins : [Ext.create('component.public.GridToolTipPlugin',{
				cellIndex : '4'
			})],
			columns : [{
				text : '名称',
				width : 250,
				dataIndex : 'avpName'
			},{
				text : '编码',
				width : 50,
				dataIndex : 'avpCode'
			},{
				text : '类型',
				width : 120,
				dataIndex : 'avpType'
			},{
				text : 'mFlag',
				flex : 1,
				minWidth:100,
				dataIndex : 'mFlag'
			},{
				text : 'pFlag',
				flex : 1,
				minWidth:100,
				dataIndex : 'pFlag'
			}]
		});
	}
	
});