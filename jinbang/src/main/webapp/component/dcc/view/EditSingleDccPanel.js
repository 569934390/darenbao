Ext.define('component.dcc.view.EditSingleDccPanel', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.editSingleDccPanel',
			requires:['component.public.TextButtonField'],
			config:{
				dcc:null,
				packageGrid:null,
				comBo:null
			},
			constructor : function(config) {
				var me = this;
				config=config||{};
				me.xmlView=me.createXmlView(config.parentFlag);
				delete config.parentFlag;
				me.gridView=me.createGridView();
				Ext.applyIf(config, {
					items : [me.xmlView, me.gridView]
				});
				me.callParent([config]);
				me.bindEvent();
			},
			bindEvent:function(){
				var me=this;
				me.on('tabchange',Ext.bind(me.tabChangeHandler,me));
				me.xmlView.down('[name=saveBtn]').on('click',Ext.bind(me.saveDccHandler,me));
				me.xmlView.down('[name=resetBtn]').on('click',Ext.bind(me.resetHandler,me));
				if(me.xmlView.down('[name=parentId]').isXType('textButtonField')){
					me.xmlView.down('[name=parentId]').handler=me.parentHander;
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
				if(!me.xmlView.isValid()) return Ext.Msg.alert('提示','请将表单数据填写完整!');
				Ext.Ajax.request({
						url:ctx+'/debug/savePackage.do',
						params:me.xmlView.getForm().getValues(),
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
												me.up('window').close();
											});
								} else {
									return Ext.Msg.alert('操作失败',response.responseText);
								}
							}

						}
					});
			},
			resetHandler:function(){
				var me=this;
				me.xmlView.getForm().reset();
				me.xmlView.getForm().setValues(me.dcc);
			},
			tabChangeHandler:function(tabPanel, newCard, oldCard, eOpts){
				var me=this;
				if(newCard.isXType('form')){
				}else{
					if(!oldCard.getForm().isValid()){
						return Ext.Msg.alert('提示','请先填写完整表单数据!');
					}
					Ext.apply(me.gridView.getStore().proxy.extraParams,oldCard.getForm().getValues());
					me.gridView.getStore().load();
				}
			},
			createXmlView : function(parentFlag) {
				var me=this;
				var parentConfig=null;
				if(parentFlag){
					parentConfig={
									xtype : 'textButtonField',
									fieldLabel : '业务',
									name : 'parentId',
									allowBlank:false,
									blankText:'请填写消息包名',
									labelAlign : 'right',
									editable:false,
									columnWidth : 0.5
								};
				}else{
					parentConfig={
									xtype : 'hidden',
									name : 'parentId'
								}
				}
				return Ext.create('Ext.form.Panel', {
							title : '消息包编辑',
							frame : true,
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
							items : [{
											layout : 'column',
											frame : true,
											style : 'margin:10px',
											items : [{
														xtype : 'textfield',
														fieldLabel : '消息包名',
														name : 'text',
														allowBlank:false,
														blankText:'请填写消息包名',
														labelAlign : 'right',
														columnWidth : 0.5
													},{
														xtype : 'hidden',
														name : 'parentId'
													}]
										}, {
											height : Ext.getBody().getHeight()- 150,
											xtype : 'textarea',
											anchor : '100%',
											allowBlank:false,
											blankText:'请填写消息包内容',
											name : 'content'
										}, {
											xtype : 'hidden',
											name : 'dccId'
										},{
											xtype : 'hidden',
											name : 'type'
										}]
						});
			},
			createGridView:function(){
				return Ext.create('component.dcc.view.MessageDetailEditor',{title:'消息包预览'});
			}
		});