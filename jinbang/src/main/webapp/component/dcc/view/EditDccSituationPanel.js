Ext.define('component.dcc.view.EditDccSituationPanel',{
	extend:'Ext.panel.Panel',
	alias:'widget.editDccSituationPanel',
	requires:['component.public.AjaxComboBox'],
	config:{
		parentId:null
	},
	constructor:function(config){
		var me=this;
		config=config||{};
		me.situationTable=me.createTable();
		me.packageTable = me.createPackageTable();
		Ext.applyIf(config,{
			layout:'border',
			items:[me.situationTable,me.packageTable],
			dockedItems: [{
		        xtype: 'toolbar',
		        dock: 'top',
		        items: [{
		            text: '新增业务',
		            name:'addScene',
		            iconCls:'toolbar-add',
		            handler:function(){
		            	me.editScene('add');
		            }
		        },'-',{
		            text: '修改业务',
		            name:'editScene',
		            iconCls:'toolbar-edit',
		            handler:function(){
		            	me.editScene('edit');
		            }
		        }
		        ,'-',{
		            text: '删除业务',
		            name:'deleteScene',
		            iconCls:'toolbar-del',
		            handler:function(){
		            	var records=me.situationTable.getSelectionModel().getSelection(),idStr = [];
		            	if(records.length==0){
		            		return Ext.Msg.alert('提示','请选择需要删除的场景');
		            	}
		            	for(var i=0;i<records.length;i++){
		            		idStr.push(records[i].get('dccId'));
		            	}
		            	Ext.Ajax.request({
							url:ctx+'/debug/delPackage.do',
							params:{idStr:idStr.join(',')},
							success:function(response){
								if(response.responseText=='0'){
									return Ext.Msg.alert('操作成功','业务删除成功！',function(){
										me.situationTable.getStore().load();
									});
								}else{
									return Ext.Msg.alert('操作失败',response.responseText);
								}
							},
							failure:function(){
								return Ext.Msg.alert('提示','操作失败');
							}
						});
		            }
		        },'->',{
		        	text: '添加包',
		            name:'addPackage',
		            handler:function(){
		            	me.editPackage('add');
		            }
		        },'-',{
		        	text: '删除包',
		            name:'deletePackage',
		            handler:function(){
		            	var records=me.packageTable.getSelectionModel().getSelection(),idStr = [];
		            	if(records.length==0){
		            		return Ext.Msg.alert('提示','请选择需要删除的包');
		            	}
						Ext.MessageBox.confirm('提示', '确定要删除所选消息包？', function(btn) {
							if (btn == 'yes') {
								for(var i=0;i<records.length;i++){
									idStr.push(records[i].get('dccId'));
								}
								Ext.Ajax.request({
									url:ctx+'/debug/delPackage.do',
									params:{idStr:idStr.join(',')},
									success:function(response){
										if(response.responseText=='0'){
											return Ext.Msg.alert('操作成功','删除消息包成功！',function(){
												me.packageTable.getStore().load();
											});
										}else{
											return Ext.Msg.alert('操作失败',response.responseText);
										}
									},
									failure:function(){
										return Ext.Msg.alert('提示','操作失败');
									}
								});
							}
						});
		            }
		        }]
		    }]
		});
		me.callParent([config]);
	},
	createTable:function(){
		var me=this;
		var store = Ext.create('Ext.data.Store', {
		    fields:['dccId','parentId', 'text', 'content','type'],
		    proxy : {
				type : 'ajax',
				reader : 'json',
				url : webRoot + 'debug/selectDccByParentId.do?dccId=-1'
			}
		});
		
		var tableView =  Ext.create('Ext.grid.Panel',{
			selModel: {
   	 			selType:'checkboxmodel'
   	 		},
			region : 'center',border:false,
			title:'业务列表',
			store: store,
		    columns : [{
				text : '系统',
				dataIndex : 'parentId',
				renderer : function(val) {
					if (val == '3')
						return 'OCS';
					else
						return 'ABM';
				}
			}, {
				text : '业务',
				dataIndex : 'text',
				flex : 1
			}]
		});
		tableView.on('itemclick',function(){
			var record=me.situationTable.getSelectionModel().getSelection()[0];
			me.packageTable.getStore().getProxy().extraParams.parentId=record.get('dccId');
			me.parentId=record.get('dccId');
			me.packageTable.getStore().load();
		});
		return tableView;
	},
	createPackageTable:function(){
		var me=this;
		var store = Ext.create('Ext.data.Store', {
		    fields:['dccId','parentId', 'text', 'content','type'],
		    proxy : {
				type : 'ajax',
				reader : 'json',
				url : webRoot + 'debug/selectDccByParentId.do'
			}
		});
		
		var tableView =  Ext.create('Ext.grid.Panel',{
			selModel: {
   	 			selType:'checkboxmodel'
   	 		},
			region : 'east',frame:true,
			width:'50%',
			title:'消息包列表',
			store: store,
		    columns: [
		        { text: '消息包', dataIndex: 'text' ,flex: 1},
		        { text: '类型', dataIndex: 'type' ,width:80}
		    ]
		});
		tableView.on('itemdblclick',function(){
			var record=me.packageTable.getSelectionModel().getSelection()[0];
			me.editPackage('edit',record);
		});
		return tableView;
	},
	editScene:function(option){
		var me = this;
		if(!me.editSceneWin){
			me.editSceneWin = Ext.create('Ext.window.Window', {
			    title: '新增业务',
			    height: 250,
			    width: 300,
			    modal:true,
			    closeAction:'hide',
			    items:[{
			    	frame:true,
			    	xtype:'form',
			    	layout:'form',
			    	style:'margin:10px',
			    	items : [{
						xtype:'ajaxComboBox',
						clear:true,
						fieldLabel:'系统',
						displayField: 'text',
						valueField: 'dcc_id',
						url:ctx+'/base/getList.do?sqlKey=DopTestDcc.selectEditTestDccParent',
						queryMode:'remote',
						labelAlign:'right',
						editable:false,
						width:150,
						allowBlank :false,
						name:'parentId'
					}, {
						xtype : 'textfield',
						labelAlign : 'right',
						fieldLabel : '名称',
						allowBlank : false,
						width : 150,
						name : 'text'
					}, {
						xtype : 'hidden',
						labelAlign : 'right',
						fieldLabel : '标识',
						name : 'dccId'
					}, {
						xtype : 'hidden',
						labelAlign : 'right',
						fieldLabel : '类型',
						name : 'type',
						value:0
					}]
			    }],
			    buttons:[{
					text:'确定',
					handler:function(){
						var form = me.editSceneWin.down('form');
						if(!form.isValid())
							return Ext.Msg.alert('提示','表单不完整');
						var params = form.getForm().getValues();
						Ext.Ajax.request({
							url:ctx+'/debug/savePackage.do',
							params:params,
							success:function(response){
								var result = Ext.JSON.decode(response.responseText);
								if(result.success){
									return Ext.Msg.alert('提示','场景操作成功！',function(){
										me.situationTable.getStore().load();
										me.editSceneWin.close();
									});
								}
								else{
									Ext.Msg.alert('提示',result.msg);
								}
							}
						});
					}
			    },{
			    	text:'重置',
			    	handler:function(){
			    		var form = me.editSceneWin.down('form');
			    		form.getForm().reset();
			    	}
			    }]
			});
		}
		if(option=='edit'){
			var record=Ext.gridSelectCheck(me.situationTable);
			if(!record) return;
			me.editSceneWin.setTitle('编辑业务');
			me.editSceneWin.down('form').getForm().setValues(record.raw);
		}
		else{
			me.editSceneWin.setTitle('新增业务');
		}
		me.editSceneWin.show();
	},
	//editPackage:function(option,record){
	//	var me = this;
	//	var records=me.situationTable.getSelectionModel().getSelection();
	//	if(records.length==0){
	//		return Ext.Msg.alert('提示','请选择一个业务');
	//	}
	//	else if(records.length>1){
	//		return Ext.Msg.alert('提示','只允许选择一个业务');
	//	}
	//	if(!me.editDccWindow){
	//		me.editDccWindow=Ext.create('component.dcc.view.EditSingleDccWindow');
	//		me.editDccWindow.setPackageGrid(me.packageTable);
	//	}
	//	if(option=='edit'){
	//		me.editDccWindow.setTitle('编辑消息包');
	//		me.editDccWindow.setDcc(record.raw);
	//	}
	//	else{
	//		me.editDccWindow.setTitle('添加消息包');
	//		me.editDccWindow.setDcc({parentId:me.parentId,type:0});
	//	}
	//	me.editDccWindow.show();
	//},
	editPackage:function(option,record){
		var me = this;
		var records=me.situationTable.getSelectionModel().getSelection();
		if(records.length==0){
			return Ext.Msg.alert('提示','请选择一个场景');
		}
		else if(records.length>1){
			return Ext.Msg.alert('提示','只允许选择一个场景');
		}
		if(!me.editPackageWin){
			me.editPackageWin=Ext.create('component.dcc.view.EditDccPackageWindow',{
				afterSave:function(){
					Ext.Msg.alert('提示','保存成功!',function(){
						me.editPackageWin.hide();
					});
					me.packageTable.getStore().load();
				}
			});
		}
		me.editPackageWin.action=option;
		me.editPackageWin.show();
		if(option=='edit'){
			me.editPackageWin.setTitle('编辑消息包');
			var record=me.packageTable.getSelectionModel().getSelection()[0];
			me.editPackageWin.setDcc(record.raw);
		}
		else{
			me.editPackageWin.setTitle('添加消息包');
			me.editPackageWin.setDcc({type:'0',parentId:me.parentId});
		}
	}
});