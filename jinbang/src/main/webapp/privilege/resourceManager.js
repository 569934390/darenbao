Ext.require(['component.view.grid.AutomationTestGrid','component.view.grid.AutomationGrid','component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	var menuTree,tabPanel=Ext.create('Ext.tab.Panel',{region:'center',	border:false});
	Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[menuTree = Ext.create('component.tree.MenuTree',{
			region:'west',
			width:200,
			listeners:{
				load:function(tree,snode, records, successful, eOpts){
					var nodeId = Ext.state.Manager.get('menuId')||'11';
					tree.getRootNode().cascadeBy(function(node){
						if(node.data.id==nodeId){
							menuTree.getSelectionModel().select(node);
							menuTree.fireEvent('itemclick',tree,node);
						}
					});
				},
				itemclick:function(tree,record,item,index,e,eOpts){
					console.info(record)
					if(record.data.depth=='2'){
						if(tabPanel.child('#'+record.get('id'))){
							tabPanel.setActiveTab(record.get('id'));
							return;
						}
						if(Ext.isEmpty(record.raw.attributeMap.url)){
							return Ext.Msg.alert('提示','资源URL不存在');
						}
						Ext.state.Manager.set('menuId',record.get('id'));
						var resourceView = Ext.create('component.view.panel.ResourceView', {
								region : 'center',
								border:false,
								url:webRoot+record.raw.attributeMap.url,
								closable:true,
								itemId:record.get('id'),
								title : record.get('text')
							});
						tabPanel.add(resourceView);
						tabPanel.setActiveTab(resourceView);
					}
				}
			}
		}),Ext.create('component.view.form.FormPanel', {
			    region:'north',border:true,frame:true,
			    name:'mainForm',
			    items:[
				{xtype:'hidden',name:'state',value:'00A'},{
					width:Ext.getBody().getWidth()/2-50,
					xtype : 'clearTextField', fieldLabel:'资源名称', name:'title',allowBlank :false,
					emptyText  : '资源名称',blankText :"提示：资源名称必填"
				},{
					width:Ext.getBody().getWidth()/4-45,
					xtype:'ajaxComboBox',
					editable : true,
					queryMode:'local',
					fieldLabel : '资源类型',
					name:'sourceCount',
					displayField: 'name',
					valueField: 'value',
					data:[['一组数据',1],['二组数据',2],['三组数据',3],['四组数据',4],['五组数据',5],['六组数据',6]],
					value:1,
					listeners:{
						select:function(combo){
							// 图表清空-------------------
							render.clear();
							// 图表释放-------------------
							render.dispose();
							render = null;
							drawChart(combo.getValue());
						}
					}
				}]
		  }),tabPanel]
	});
});
