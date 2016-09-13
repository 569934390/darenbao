Ext.define('component.dcc.view.EditBatchDccPanel', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.editBatchDccPanel',
			requires:['component.public.TextButtonField'],
			config:{
				packageGrid:null,
				comBo:null,
				params:null,
				xmlView:null,
				gridView:null
			},
			constructor : function(config) {
				var me = this;
				config=config||{};
				me.xmlView=me.createXmlView();
				delete config.parentFlag;
				me.gridView=me.createGridView();
				Ext.applyIf(config, {
					items : [me.xmlView, me.gridView]
				});
				me.callParent([config]);
			},
			createXmlView : function() {
				var me=this;
				return Ext.create('Ext.form.Panel', {
							title : '消息包编辑',
							frame : true,
							items : [{
											height : Ext.getBody().getHeight()- 150,
											xtype : 'textarea',
											anchor : '100%',
											name : 'content'
										}]
						});
			},
			createGridView:function(){
				return Ext.create('component.dcc.view.MessageDetailEditor',{title:'消息包预览'});
			}
		});