Ext.require(['component.view.grid.AutomationTestGrid','component.view.grid.AutomationGrid','component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField']);
Ext.define('component.view.panel.ResourcePanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.resourcePanel',
	requires:[],
	config:{
		btns:[],
		urls:[]
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		config.btns = [];
		config.urls = [];
		Ext.applyIf(config,{
			border:false,
			tbar:[{text:'保存',iconCls:'toolbar-save',handler:Ext.bind(me.saveAction,me)}],
			layout:'border',
			items:[me.menuTree = Ext.create('component.tree.MenuTree',{
			region:'west',
			disableClick:true,
			width:200,
			listeners:{
				load:function(tree,snode, records, successful, eOpts){
					var me = this,view = template.generalGird;
					var record=Ext.gridSelectCheck(view);
					var nodeId = Ext.state.Manager.get('menuId')||'11',ids=[];
					var params = {privilegeId:record.get('id')};
					Ext.Persistent.getPage({sqlKey:'DopPrivilegeResource.selectList',conditionsStr:Ext.encode(params)},function(data){
						console.info(data);
						for(var i=0;i<data.result.length;i++){
							if(data.result[i].type=='URL')
								ids.push(data.result[i].code);
							else if(data.result[i].type=='BUTTON'){
								if(!config.btns[data.result[i].name])config.btns[data.result[i].name]=[];
								config.btns[data.result[i].name][data.result[i].code]=true;
								config.urls[data.result[i].code]=data.result[i].direction;
							}
						}
						tree.getRootNode().cascadeBy(function(node){
							if(node.data.id==nodeId){
								tree.fireEvent('itemclick',tree,node);
							}
							for(var i=0;i<ids.length;i++){
								if(ids[i]==node.data.id){
									node.set('checked',true);
								}
							}
						});
					});
				},
				itemclick:function(tree,record,item,index,e,eOpts){
					if(record.data.depth=='2'){
						if(me.tabPanel.child('#'+record.get('id'))){
							me.tabPanel.setActiveTab(record.get('id'));
							return;
						}
						if(Ext.isEmpty(record.raw.attributeMap.url)){
							return Ext.Msg.alert('提示','资源URL不存在');
						}
						Ext.state.Manager.set('menuId',record.get('id'));
						var resourceView = Ext.create('component.view.panel.ResourceView', {
								region : 'center',
								border:false,
								url:record.raw.attributeMap.url,
								closable:true,
								itemId:record.get('id'),
								title : record.get('text'),
								btns:config.btns[record.get('id')]
							});
						me.tabPanel.add(resourceView);
						me.tabPanel.setActiveTab(resourceView);
					}
				}
			}
		}),me.tabPanel =Ext.create('Ext.tab.Panel',{region:'center',	border:false})]
		});
		me.callParent([config]);
	},
	loadData:function(tree){
		var me =this;
		me.menuTree.getStore().load();
		me.tabPanel.removeAll();
	},
	saveAction:function(){
		var me = this,view = template.generalGird;
		if(!Ext.isArray(me.btns))me.btns = [];
		var record=Ext.gridSelectCheck(view);
		var records = me.menuTree.getChecked();
		var params = {privilegeId:record.get('id')},list = [];
		for(var i=0;i<records.length;i++){
			if(records[i].raw.attributeMap)
				list.push({id:'NULL',direction:records[i].raw.attributeMap.url,privilegeId:record.get('id'),code:records[i].raw.attributeMap.menu_id,name:records[i].raw.attributeMap.menu_name,type:'URL'});
		}
		params['list']=list;
		
		var resourceViews = me.tabPanel.query('resourceView');
		for(var i=0;i<resourceViews.length;i++){
			var view = resourceViews[i];
			delete me.btns[view.itemId];//去掉已存在
			for(var k in view.btns){
				if(view.btns[k])
					list.push({id:'NULL',privilegeId:record.get('id'),code:k,name:view.itemId,type:'BUTTON',direction:view.url});
			}
		}
		//后台加载的数据
		for(var key in me.btns){
			for(var k in me.btns[key])
				if(me.btns[key][k])
					list.push({id:'NULL',privilegeId:record.get('id'),code:k,name:key,type:'BUTTON',direction:me.urls[k]});
		}
		Ext.Persistent.updateItems('DopPrivilegeResource',params,function(data){
			Ext.Msg.alert('提示','保存成功!');
		});
	}
});