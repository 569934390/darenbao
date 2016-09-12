Ext.define('component.view.win.AutomationCfgWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.automationCfgWin',
	requires:[],
	closeAction:'hide',
	maximizable:true,
	modal:true,
	draggable:false,
	resizable:false,
	width:Ext.getBody().getWidth()*0.9,
	height:Ext.getBody().getHeight()*0.9,
	layout:'border',
	config:{
	},
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		me.scanTree = me.createScanTree();
		me.config.parent.scanTree = me.scanTree;
		me.messageDetailViewer = me.createMessageDetailEditor();
		me.items=[me.scanTree,me.messageDetailViewer];
		me.callParent([config]);
		me.bindEvent();
	},
	i18n:function(text){
		if(text=='消息包')return 'msg';
		if(text=='请求包')return 'request';
		if(text=='返回包')return 'response';
		return text;
	},
	bindEvent:function(){
		var me = this;
		me.messageDetailViewer.on('edit', function(editor, e) {
			var record = e.record,p = e.record.parentNode,px='';
			while(p){
				px=me.i18n(p.raw.text)+'/'+px;
				p = p.parentNode;
			}
			px+=e.record.raw.code;
			var value,status;
			if(e.field=='active'){
				status = e.value;
				value = record.get('avpValue');
			}
			else{
				status = record.get('active');
				value = e.value;
			}
			Ext.Ajax.request({
				url:ctx+'/simulator/persistenceCfg.do',
				params:{dccId:me.dccId,code:e.record.raw.code,value:value,path:px,status:status?'00A':'00X'},
				success:function(response){
					if(!Ext.isEmpty(response.responseText)){
						var result = Ext.decode(response.responseText);
						console.info(result);
						if(!result.success){
							e.record.reject();
							Ext.Msg.alert('提示',result.message);
						}
					}
				},
				failure:function(){
					return Ext.Msg.alert('提示','操作失败');
				}
			});
		});
		me.messageDetailViewer.on('beforeedit',function(editor, e){
			console.info(e.record);
			if(!e.record.raw.code){
				return false;
			}
			return true;
		});
		me.messageDetailViewer.getStore().on('load',function(store,root,records){
			Ext.Ajax.request({
				url:ctx+'/simulator/selectCfgList.do',
				params:{dccId:me.dccId},
				success:function(response){
					if(!Ext.isEmpty(response.responseText)){
						var result = Ext.decode(response.responseText);
						if(!result.success){
							return Ext.Msg.alert('提示',result.message);
						}
						var modifys = result.cfgs;
						me.messageDetailViewer.getRootNode().cascadeBy(function(node){
							var p = node.parentNode,px='';
							while(p){
								px=me.i18n(p.raw.text)+'/'+px;
								p = p.parentNode;
							}
							px+=node.raw.code;
							for(var i=0;i<modifys.length;i++){
								console.info(px,modifys[i].path);
								if(modifys[i].path==px){
									node.set('avpValue',modifys[i].value);
									node.set('active',modifys[i].status=='00A'?true:false);
								}
							}
						});
					}
				},
				failure:function(){
					return Ext.Msg.alert('提示','操作失败');
				}
			});
		});
	},
	createScanTree:function(){
		var me = this;
		var treePanel = Ext.create('component.tree.SimulatorTree', {
			region : 'west',
			width : 200,
			collapsible: false,
			height:200,
			headerPosition : 'top',
			title:'',
//			plugins:[],
			paramMap:{
				checked:'1,2,3'
			},
			frame:true,
			listeners:{
				load:function(tree,snode, records, successful, eOpts){
//					var nodeId = Ext.state.Manager.get('nodeId')||'84';
//					tree.getRootNode().cascadeBy(function(node){
//						if(node.data.id==nodeId){
//							treePanel.getSelectionModel().select(node);
//							treePanel.fireEvent('itemclick',tree,node);
//						}
//					});
				},
				itemclick:function(tree,record,item,index,e,eOpts){
					if(record.data.depth=='3'){
						me.dccId = record.raw.id;
						Ext.Ajax.request({
							url:ctx+'/debug/loadTestDccContext.do',
							params:{dccId:record.raw.id},
							success:function(response){
								if(!Ext.isEmpty(response.responseText)){
									var result = Ext.decode(response.responseText);
									me.messageDetailViewer.getStore().proxy.extraParams.dccId=result['dcc_id'];
									me.messageDetailViewer.getStore().proxy.extraParams.content=result['content'];
									me.dcc=result;
									me.messageDetailViewer.getStore().load();
								}
							},
							failure:function(){
								return Ext.Msg.alert('提示','操作失败');
							}
						});
					}
				}
			}
		});
		return treePanel;
	},
	createMessageDetailEditor:function(){
		return Ext.create('component.dcc.view.MessageDetailEditor',{region:'center',hideFlag:'root',editable:true,columns : [{
				xtype : 'treecolumn', // this is so we know which column will
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
	            header: '对比',
	            dataIndex: 'active',
	            width: 25,
	            renderer:function(val,e){
	            	if(!e.record.get('code'))
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
	        }, {
				text : '类型',
				width : 120,
				dataIndex : 'type',
				sortable : true
			}, {
				text : '来源',
				width : 60,
				dataIndex : 'src',
				sortable : true
			}, {
				text : '取值',
				dataIndex : 'avpValue',
				width : 170,
				editor: {
                    xtype: 'textfield'
                },
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
				minWidth:100,
				dataIndex : 'desc',
				sortable : true
			}]});
	}
});