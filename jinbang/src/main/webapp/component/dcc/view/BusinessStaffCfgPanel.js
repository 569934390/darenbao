Ext.define('component.dcc.view.BusinessStaffCfgPanel', {
	extend : 'Ext.panel.Panel',
	requires:['component.public.TextButtonField'],
	layout:'border',
	config:{
	},
	mixins:['component.public.TreeCommonMethod'],
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		me.businessStaffDccTree = me.createBusinessStaffDccTree();
		me.messageDetailViewer = me.createMessageDetailEditor();
		Ext.applyIf(config,{
			title:'营业员消息包配置',
			dockedItems:[{
				xtype: 'toolbar',
		        dock: 'top',
		        items: ['->',{
		            text: '保存修改',
		            iconCls:'toolbar-save',
		            name:'saveBtn'
		        }]
			}],
			items:[me.businessStaffDccTree,me.messageDetailViewer]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	i18n:function(text){
		if(text=='消息包')return 'msg';
		if(text=='请求包')return 'request';
		if(text=='返回包')return 'response';
		return text;
	},
	getPath:function(record){
		var me=this;
		var p = record.parentNode,px='';
		while(p){
			px=me.i18n(p.raw.text)+'/'+px;
			p = p.parentNode;
		}
		px+=record.raw.code;
		return px;
	},
	saveHandler:function(){
		var me=this;
		var records=me.messageDetailViewer.getStore().getModifiedRecords();
		if(Ext.isEmpty(records)){
			return Ext.Msg.alert('提示','请修改数据');
		}
		var businessStaffCfgList=[];
		var flag=true;
		Ext.each(records,function(record,index){
			var path=me.getPath(record);
			var obj={dccId:me.dccId,path:path,avpDesc:record.get('desc')};
			if(record.get('cfg')){
				if(Ext.isEmpty(record.get('desc'))){
					flag=false;
					return false;
				}
				obj['state']='00A';
			}else{
				obj['state']='00X';
			}
			businessStaffCfgList.push(obj);
		});
		if(!flag){
			 return Ext.Msg.alert('提示','请填写描述');
		}
		Ext.Ajax.request({
			url:ctx+'/businessStaff/saveOrUpdateCfg.do',
			params:{
				businessStaffCfgListStr:Ext.encode(businessStaffCfgList)
			},
			success:function(){
				me.messageDetailViewer.getStore().reload();
				Ext.Msg.alert('提示','修改成功');
			}
		});
		
	},
	bindEvent:function(){
		var me = this;
		me.businessStaffDccTree.on('itemclick',Ext.bind(me.itemClickHandler,me));
		me.businessStaffDccTree.on('itemcontextmenu',Ext.bind(me.contextMenuHandler,me));
		me.messageDetailViewer.on('edit',Ext.bind(me.dccEditHandler,me))
		me.messageDetailViewer.getStore().on('load',Ext.bind(me.modifyRecord,me));
		me.down('[name=saveBtn]').on('click',Ext.bind(me.saveHandler,me));
		me.messageDetailViewer.on('beforeedit',function(editor, e){
			if(!e.record.raw.code){
				return false;
			}
			return true;
		});
	},
	contextMenuHandler:function(tree,record,item,index,e,eOpts){
		var me=this;
		if(!me.contextMenu){
			me.contextMenu=Ext.create('Ext.menu.Menu',{
			items: [{
					text : '刷新节点',
					iconCls : 'toolbar-reLoad',
					name :'refreshBtn' 
				},{
			        text: '添加子节点',
			        iconCls:'toolbar-add',
			        name:'addBtn'
			    },{
			        text: '修改节点',
			        iconCls:'toolbar-edit',
			        name:'editBtn'
			    },{
			        text: '删除子节点',
			        iconCls:'toolbar-delete',
			        name:'deleteChildrenBtn' 
			    },{
			        text: '删除节点',
			        iconCls:'toolbar-delete',
			        name:'deleteBtn' 
			    }]
			});
			me.contextMenu.down('[name=refreshBtn]').on('click',function(){me.refreshTree()});
			me.contextMenu.down('[name=addBtn]').on('click',Ext.bind(me.editDccHandler,me,['add'],0));
			me.contextMenu.down('[name=editBtn]').on('click',Ext.bind(me.editDccHandler,me,['update'],0));
			me.contextMenu.down('[name=deleteChildrenBtn]').on('click',Ext.bind(me.deleteDccHandler,me,['deleteChildren'],0));
			me.contextMenu.down('[name=deleteBtn]').on('click',Ext.bind(me.deleteDccHandler,me,['delete'],0));
		}
		me.editRecord=record;
		e.preventDefault();
		me.contextMenu.showAt(e.getXY());
	},
	editDccHandler:function(action){
		var me=this;
		if(!me.dccEditWindow){
			me.dccEditForm=me.createDccEditForm();
			me.dccEditWindow=Ext.create('component.public.PopWindow',{
				layout:'fit',
				items:me.dccEditForm,
				okHandler:function(){
					var window=this;
					var params=Ext.getFormValues(me.dccEditForm);
					if(Ext.isEmpty(params.content.trim())){
						delete params.content;
					}
					Ext.Ajax.request({
						url:ctx+'/debug/savePackage.do',
						params:params,
						success:function(){
							if(window.action=="add"){
								me.refreshTree();
							}else{
								me.refreshTree(me.editRecord.parentNode);
							}
							window.close();
						}
					});
				}
			});
			me.getParentIdField().on('click',function(){
				me.parentIdHandler(this);
			});
		}
		me.dccEditWindow.action=action;
		me.dccEditForm.getForm().reset();
		if(action=="update"){
			me.getParentIdField().enable();
			var params=Ext.apply({},me.editRecord.raw);
			params['dccId']=params['dcc_id'];
			params['parentId']=params['parent_name']+','+params['parent_id'];
			me.dccEditForm.getForm().setValues(params);
		}else{
			me.getParentIdField().enable();
			var dccId=me.editRecord.raw.dcc_id||me.editRecord.raw.id;
			me.dccEditForm.getForm().setValues({parentId:me.editRecord.raw.text+','+dccId});
			me.getParentIdField().disable();
		}
		me.dccEditWindow.show();
	},
	getParentIdField:function(){
		return this.dccEditForm.down('[name=parentId]');
	},
	parentIdHandler:function(targetButton){
		var me=this;
		if(!me.parentIdWindow){
			me.businessStaffTree = me.createBusinessStaffDccTree('DopTestDcc.selectBusinessStaffByParentId');
			me.parentIdWindow=Ext.create('component.public.PopWindow',{
				title:'业务场景',
				layout:'fit',
				items:me.businessStaffTree,
				displayField:'text',
				valueField:'dcc_id',
				grid:me.businessStaffTree,
				targetButton:targetButton
			});
		}
		me.parentIdWindow.show();
	},
	refreshTree:function(node){
		var me=this;
//		var node=me.businessStaffDccTree.getStore().getNodeById(me.editRecord.raw.dcc_id);
		var store=me.businessStaffDccTree.getStore();
		node=node||me.editRecord;
		me.businessStaffDccTree.paramMap.parentId=node.raw.dcc_id;
		store.load({
			node:node,
			callback:function(){
			 me.businessStaffDccTree.getView().refresh();
			}
		});
	},
	deleteDccHandler:function(action){
		var me=this;
		var params={};
		params.dccId=me.editRecord.raw.dcc_id;
		var url="";
		if(action=="deleteChildren"){
			url=ctx+'/debug/deleteChildrenPackage.do';
		}else if(action=="delete"){
			url=ctx+'/debug/deletePackage.do';
		}
		Ext.Ajax.request({
			url:url,
			params:params,
			success:function(){
				if(action=="deleteChildren"){
					me.refreshTree(me.editRecord);
				}else{
					me.refreshTree(me.editRecord.parentNode);
				}
			}
		});
	},
	dccEditHandler:function(editor,e){
		if(Ext.isEmpty(e.value)&&Ext.isEmpty(e.originalValue)){
			e.record.reject();
		}
	},
	itemClickHandler:function(tree,record,item,index,e,eOpts){
		var me=this;
		if(!Ext.isEmpty(record.raw.content)){
			me.dccId = record.raw.dcc_id;
			me.messageDetailViewer.getStore().proxy.extraParams.dccId=record.raw['dcc_id'];
			me.messageDetailViewer.getStore().proxy.extraParams.content=record.raw['content'];
			me.messageDetailViewer.getStore().load();
		}
	},
	selectionChangeHandler:function(){
	},
	modifyRecord:function(){
		var me=this;
		Ext.Ajax.request({
			url:ctx+'/businessStaff/loadCfg.do',
			params:{dccId:me.dccId},
			success:function(response){
				var modifys = Ext.ResponseDecode(response.responseText);
				if(!Ext.isEmpty(modifys)){
					me.messageDetailViewer.getRootNode().cascadeBy(function(node){
						for(var i=0;i<modifys.length;i++){
							if(modifys[i].path==me.getPath(node)){
								node.set('desc',modifys[i].avpDesc);
								node.set('cfg',modifys[i].state=='00A');
							}
						}
					});
				}
			}
		});
	},
	createBusinessStaffDccTree:function(sqlKey){
		var me = this;
		var treePanel = Ext.create('component.dcc.view.BusinessStaffDccTreePanel', {
			region : 'west',
			width : 200,
			collapsible: false,
			height:200,
			title:'',
//			plugins:[],
			frame:true,
			sqlKey:sqlKey,
			listeners:{}
		});
		return treePanel;
	},
	createMessageDetailEditor : function() {
		return Ext.create('component.dcc.view.MessageDetailEditor', {
			region : 'center',
			hideFlag : 'root',
			editable : true,
			columns : [{
						xtype : 'treecolumn', // this is so we know
												// which column will
						// show the tree
						text : '名称',
						width : 250,
						sortable : true,
						dataIndex : 'text'
					}, {
						text : 'code',
						width : 50,
						dataIndex : 'code',
						sortable : true
					}, {
						text : '类型',
						width : 120,
						dataIndex : 'type',
						sortable : true
					}, {
						text : 'flags',
						width : 50,
						dataIndex : 'flags',
						sortable : true
					}, {
						text : 'vendor_id',
						width : 50,
						dataIndex : 'vendorId',
						sortable : true
					}, {
						text : '描述',
						flex : 1,
						minWidth : 100,
						dataIndex : 'desc',
						sortable : true,
						editor: {
		                    xtype: 'textfield'
		                }
					}, {
			            text: '是否配置',
			            dataIndex: 'cfg',
			            width: 70,
			            renderer:function(val,e){
			            	if(!e.record.get('desc'))
			            		return '';
			            	if(val==true){
			            		return '<span style="color:red">是</span>';
			            	}
			            	return '否';
			            },
			            editor: {
			                xtype: 'checkbox',
			                cls: 'x-grid-checkheader-editor'
			            }
			        }]
		});
	},
	createDccEditForm:function(){
		return Ext.create('Ext.form.Panel',{
			frame:true,
			defaults:{
				xtype:'textfield',
				labelAlign:'right',
				labelWidth:70
			},
			layout:'form',
			items:[{
				xtype:'hidden',
				fieldLabel:'标识',
				name:'dccId'
			},{
				xtype:'hidden',
				fieldLabel:'类型',
				name:'type',
				value:4
			},{
				fieldLabel:'名称',
				name:'text'
			},{
				xtype:'textButtonField',
				fieldLabel:'父节点',
				editable:false,
				name:'parentId'
			},{
				xtype:'textarea',
				fieldLabel:'消息包',
				name:'content',
				height:300
			}]
		});
	}
});