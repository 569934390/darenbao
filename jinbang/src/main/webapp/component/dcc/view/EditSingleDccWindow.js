Ext.define('component.dcc.view.EditSingleDccWindow', {
			extend : 'Ext.window.Window',
			alias : 'widget.editSingleDccWindow',
			constructor : function(config) {
				var me = this;
				config=config||{};
				me.tabPanel=me.createTabPanel(config);
				config = Ext.applyIf(config || {}, {
							title : '编辑消息包',
							height : Ext.getBody().getHeight() - 50,
							width : '80%',
							modal : true,
							closeAction : 'hide',
							layout:'fit',
							items:[me.tabPanel]
						});
				me.callParent([config]);
				me.bindEvent();
			},
			setDcc:function(dcc){
				var me=this;
				me.dcc=dcc;
				me.dcc.dccId=me.dcc.dcc_id||me.dcc.dccId;
				me.dcc.parentId=me.dcc.parent_id||me.dcc.parentId;
				me.tabPanel.setDcc(me.dcc);
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
					me.tabPanel.resetHandler();
				})
			},
			createTabPanel:function(config){
				return Ext.create('component.dcc.view.EditSingleDccPanel');
			}
		});