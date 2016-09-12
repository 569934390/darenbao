Ext.define('component.dcc.view.EditBatchDccWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.editBatchDccWindow',
			config:{
				gridPanel:null,
				dcc:null
			},
			constructor : function(config) {
				var me = this;
				config=config||{};
				me.tabPanel=me.createTabPanel(config);
				me.gridPanel=me.createGridPanel();
				me.formPanel=me.createForm();
				config = Ext.applyIf(config || {}, {
							title : '编辑消息包',
							height : '100%',
							width : '100%',
							modal : true,
							closeAction : 'hide',
							layout:'border',
							dockedItems: [{
							    xtype: 'toolbar',
							    dock: 'bottom',
							    ui:'footer',
							    frame:true,
							    layout:{
							    	pack:'center'
							    },
								items: [{
									text : '确定',
									name : 'saveBtn'
							    }, {
									text : '重置',
									name : 'resetBtn'
								}]
							}],
							items : [me.gridPanel, {
								layout : 'border',
								region :'center',
								items : [me.formPanel, me.tabPanel]
							}]
						});
				me.callParent([config]);
				me.bindEvent();
			},
			setPackageGrid:function(packageGrid){
				this.tabPanel.setPackageGrid(packageGrid);
			},
			setComBo:function(comBo){
				this.tabPanel.setComBo(comBo);
			},
			bindEvent:function(){
				var me=this;
				me.on('show',function(){
					me.resetHandler();
				});
				me.down('[name=saveBtn]').on('click',Ext.bind(me.saveDccHandler,me));
				me.down('[name=resetBtn]').on('click',Ext.bind(me.resetHandler,me));
				me.formPanel.down('[name=parentId]').handler=me.parentHander;
				me.gridPanel.on('itemclick',Ext.bind(me.loadTestDccByRowKey,me));
				me.gridPanel.on('beforeitemclick',Ext.bind(me.xmlContentHandler,me));
			},
			loadTestDccByRowKey:function(grid,record){
				var me=this;
				Ext.Ajax.request({
					url : ctx + '/debug/loadTestDccByRowKey.do',
					params : {
						rowKey : record.get('id')
					},
					success : function(response) {
						var responseText = Ext.ResponseDecode(response);
						me.tabPanel.getXmlView().down('[name=content]').setValue(responseText.content);
						record.set('content',responseText.content);
						me.tabPanel.getGridView().getStore().load({
									params : {
										content : responseText.content
									}
								});
					}
				});
			},
			//将修改过的内容重新赋值
			xmlContentHandler:function(grid,record,item,index,e,eOpts){
				var me=this;
				var selectRecord=Ext.gridSelectCheck(grid,false,false);
				if(selectRecord){
					selectRecord.set('content',me.tabPanel.getXmlView().down('[name=content]').getValue());
				}
			},
			parentHander:function(textButton){
				var me=this;
				if(!me.parentIdWindow){
					me.parentIdWindow = Ext.create('component.dcc.view.TestDccTreeWindow', {
						targetField:textButton,
						validation : function(record) {
							if(record.raw.attributeMap.parent_id==-1){
								Ext.Msg.alert('提示','请选择业务!');
								return false;
							}
							return true;
						}
					});
				}
				me.parentIdWindow.show();
			},
			saveDccHandler:function(){
				var me=this;
				if(!me.formPanel.isValid()) return Ext.Msg.alert('提示','请将表单数据填写完整!');
				me.xmlContentHandler(me.gridPanel);
				var dccList=[];
				var params=me.formPanel.getForm().getValues();
				me.gridPanel.getStore().each(function(record){
					var obj=Ext.apply({},params);
					obj.content=record.get('content');
					obj.type=me.dcc.type;
					obj.rowKey=record.get('id');
					dccList.push(obj);
				});
				Ext.Ajax.request({
						url:ctx+'/debug/saveBatchPackage.do',
						params:{dccList:Ext.encode(dccList)},
						success : function(response) {
							if (me.callback) {
								me.callback(me.xmlView,response);
							} else {
								if (response.responseText == '0') {
									return Ext.Msg.alert('提示', '保存成功！',function() {
												if (me.packageGrid) {
													me.packageGrid.getStore().reload();
												}
												if(me.comBo){
													me.comBo.getStore().reload({
														callback:function(){
															me.comBo.fireEvent('change',me.comBo,me.comBo.getValue(),me.comBo.getValue());
														}
													});
												}
												me.close();
											});
								} else {
									return Ext.Msg.alert('操作失败',response.responseText);
								}
							}

						}
					});
			},
			resetHandler:function(){
				
			},
			createGridPanel:function(){
				return Ext.create('component.dcc.view.MessageGrid',{width:500,region:'west'})
			},
			createTabPanel:function(config){
				return Ext.create('component.dcc.view.EditBatchDccPanel',{region:'center'});
			},
			createForm:function(){
				return Ext.create('Ext.form.Panel', {
					layout : 'column',
					region:'north',
					width : 200,
					frame : true,
					style : 'margin:10px',
					items : [{
								xtype : 'textfield',
								fieldLabel : '消息包名',
								name : 'text',
								allowBlank : false,
								blankText : '请填写消息包名',
								labelAlign : 'right',
								columnWidth : 0.5
							}, {
								xtype : 'textButtonField',
								fieldLabel : '业务',
								name : 'parentId',
								allowBlank : false,
								blankText : '请选择业务',
								labelAlign : 'right',
								editable : false,
								columnWidth : 0.5
							}]
				});
			}
		});