Ext.define('component.dcc.view.EditSituationPanel',{
	extend:'Ext.panel.Panel',
	alias:'widget.editSituationPanel',
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
		            text: '新增场景',
		            name:'addScene',
		            iconCls:'toolbar-add',
		            handler:function(){
		            	me.editScene('add');
		            }
		        }
//		            ,'-',{
//		            text: '修改场景',
//		            name:'editScene',
//		            iconCls:'toolbar-edit',
//		            handler:function(){
//		            	me.editScene('edit');
//		            }
//		        }
		        ,'-',{
		            text: '删除场景',
		            name:'deleteScene',
		            iconCls:'toolbar-del',
		            handler:function(){
		            	var records=me.situationTable.getSelectionModel().getSelection(),idStr = [];
		            	if(records.length==0){
		            		return Ext.Msg.alert('提示','请选择需要删除的场景');
		            	}
		            	for(var i=0;i<records.length;i++){
		            		idStr.push(records[i].get('sceneId'));
		            	}
		            	Ext.Ajax.request({
							url:ctx+'/debug/delScene.do',
							params:{idStr:idStr.join(',')},
							success:function(response){
								if(response.responseText=='0'){
									return Ext.Msg.alert('操作成功','场景操作成功！',function(){
										me.situationTable.getStore().load();
										me.editSceneWin.hide();
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
		        }]
		    }]
		});
		me.callParent([config]);
	},
	createTable:function(){
		var me=this;
		var store = Ext.create('Ext.data.Store', {
		    fields:['sceneId','sceneSys', 'sceneName', 'createTime', 'state'],
		    autoLoad:true,
		    proxy : {
				type : 'ajax',
				reader : 'json',
				url : webRoot + 'debug/selectScene.do'
			}
		});
		
		var tableView =  Ext.create('Ext.grid.Panel',{
			selModel: {
   	 			selType:'checkboxmodel'
   	 		},
			region : 'center',border:false,
			title:'场景列表',
			store: store,
		    columns: [
		        { text: '系统',  dataIndex: 'sceneSys',renderer:function(val){
		        	if(val=='6')  return 'OCS';
		        	else return 'ABM';
		        } },
		        { text: '场景名称', dataIndex: 'sceneName', flex: 1 },
		        { text: '创建时间', dataIndex: 'createTime' ,width:150},
		        { text: '是否生效', dataIndex: 'state' ,width:150}
		    ]
		});
		tableView.on('itemclick',function(){
			var record=me.situationTable.getSelectionModel().getSelection()[0];
			me.packageTable.getStore().getProxy().extraParams.parentId=record.get('sceneId');
			me.parentId=record.get('sceneId');
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
				url : webRoot + 'debug/selectTestDccByParentId.do'
			}
		});
		
		var tableView =  Ext.create('Ext.grid.Panel',{
			selModel: {
   	 			selType:'checkboxmodel'
   	 		},
			region : 'east',frame:true,
			width:250,
			title:'场景包',
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
			    title: '新增场景',
			    height: 250,
			    width: 300,
			    modal:true,
			    closeAction:'hide',
			    items:[{
			    	frame:true,
			    	xtype:'form',
			    	layout:'form',
			    	style:'margin:10px',
			    	items:[{
						xtype:'ajaxComboBox',
						clear:true,
						fieldLabel:'系统',
						displayField: 'name',
						valueField: 'dccId',
						url:ctx+'/base/getList.do?sqlKey=DopScene.selectSimulator',
						queryMode:'remote',
			    		labelAlign:'right',
			    		editable:false,
			    		width:150,
			    		allowBlank :false,
			    		name:'sceneSys'
			    	},{
			    		xtype:'textfield',
			    		labelAlign:'right',
			    		fieldLabel:'名称',
			    		allowBlank :false,
			    		width:150,
			    		name:'sceneName'
			    	},{
			    		xtype:'datefield',
			    		labelAlign:'right',
			    		fieldLabel:'创建时间',
			    		width:150,
			    		format:'Y-m-d H:i:s',
			    		editable:false,
			    		value:new Date(),
			    		name:'createTime'
			    	},{
			    		xtype:'combo',
			    		store:Ext.create('Ext.data.Store', {
						    fields:['value','label'],
						    data:[{value:'00A',label:'生效'},{value:'00X',label:'失效'}]
						}),
						displayField: 'label',
    					valueField: 'value',
			    		labelAlign:'right',
			    		fieldLabel:'状态',
			    		editable:false,
			    		value:'00A',
			    		allowBlank :false,
			    		width:150,
			    		name:'state'
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
							url:ctx+'/debug/saveScene.do',
							params:params,
							success:function(response){
								if(response.responseText=='0'){
									return Ext.Msg.alert('操作成功','场景操作成功！',function(){
										me.situationTable.getStore().load();
										me.editSceneWin.hide();
									});
								}else{
									return Ext.Msg.alert('操作失败',response.responseText);
								}
								me.editSceneWin.hide();
							},
							failure:function(){
								return Ext.Msg.alert('提示','操作失败');
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
			me.editSceneWin.setTitle('编辑场景');
		}
		else{
			me.editSceneWin.setTitle('新增场景');
		}
		me.editSceneWin.show();
	},
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
			me.editPackageWin.setDcc({type:'3',parentId:me.parentId});
		}
	}
});