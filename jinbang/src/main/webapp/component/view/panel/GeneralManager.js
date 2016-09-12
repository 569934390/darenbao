Ext.define('component.view.panel.GeneralManager', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.GeneralManager',
	requires:[],
	layout:'border',
	searchHandler:function(){
		var me = this;
		var values = me.searchPanel.getForm().getValues(),params={};
    	for(var key in values){
    		var k = key,v = values[k];
    		if(k.indexOf('%')==0){
    			v = '%'+v;
    		}
    		if(k.lastIndexOf('%')>0){
    			v = v+'%';
    		}
    		k = k.replace(/\%/g,'');
    		params[k]=v;
    	}
		Ext.apply(me.down('grid').getStore().proxy.extraParams,{conditionsStr:Ext.encode(params)});
		me.down('grid').getStore().load();
	},
	config:{
	},
	getEditor:function(config){
		var me =this;
		if(!me.editWin){
			config.template.edit.callback = function(){
				me.editWin.hide();
				me.down('grid').getStore().load();		
				me.down('grid').getSelectionModel().deselectAll();
			}
			me.editWin = Ext.create('component.view.win.GeneralEditWin',config.template.edit);
		}
		return me.editWin;
	},
	constructor : function(config) {
		var me = this;
		me.template = config.template;
		me.config = config;
		config=config||{};
		config.tbar = [];
		if (!config.template.config) {
			config.template.config={};
		};

		if (!config.template.config.hiddenViewBar) {
			config.tbar.push({
				text:'查看',
				iconCls:'toolbar-view',
				handler:function(){
					var records=me.down('grid').getSelectionModel().getSelection();
					if(records.length==0){
						return Ext.Msg.alert('提示','请选择需要查看的记录');
					}
					if(records.length>1){
						return Ext.Msg.alert('提示','只能选择一条');
					}
					me.getEditor(config).action='view';
					me.getEditor(config).show();
					me.getEditor(config).down('form').getForm().setValues(records[0].raw);
				}
			});
		};

		if (!config.template.config.hiddenAddBar) {
			config.tbar.push({
				text:'新增',
				iconCls:'toolbar-add',
				handler:function(){
					me.getEditor(config).action='save';
					me.getEditor(config).show();
					me.getEditor(config).down('form').getForm().reset();
				}
			});
		};

		if (!config.template.config.hiddenEditBar) {
			config.tbar.push({
				text:'修改',
				iconCls:'toolbar-edit',
				handler:function(){
					var records=me.down('grid').getSelectionModel().getSelection();
					if(records.length==0){
						return Ext.Msg.alert('提示','请选择需要修改的记录');
					}
					if(records.length>1){
						return Ext.Msg.alert('提示','只能选择一条');
					}
					me.getEditor(config).action='update';
					me.getEditor(config).show();
					me.getEditor(config).down('form').getForm().setValues(records[0].raw);
				}
			});
		};

		if (!config.template.config.hiddenDelBar) {
			config.tbar.push({
				text:'删除',
				iconCls:'toolbar-del',
				handler:function(){
					var records=me.down('grid').getSelectionModel().getSelection(),idStr = [];

					if(records.length==0){
						return Ext.Msg.alert('提示','请选择需要删除的记录');
					}
					for(var i=0;i<records.length;i++){
						idStr.push(records[i].get(config.template.edit.id));
					}
	            	Ext.Persistent.deletes(config.template.edit.sqlKey,idStr.join(','),function(){
		    			Ext.Msg.alert('提示','删除成功!');
		    			me.down('grid').getStore().load();
		    		},config.template.edit.cascade,config.template.edit.subSqlKey);
				}
			});
		};

		if (!config.template.config.hiddenSearchBar) {
			config.tbar.push('-');
			config.tbar.push({
				text:'查询',
				iconCls:'toolbar-search',
				handler:Ext.bind(me.searchHandler,me)
			});
		};
		
		if(Ext.isArray( me.template.tbar)){
			config.tbar = config.tbar.concat(me.template.tbar);
		}
		me.items=[me.createSearchPanel(config),me.createGeneralGird(config)];
		me.callParent([config]);
		me.bindEvent(config);
	},
	bindEvent:function(config){
		var me = this;
		if (config.template.autoSearch) {
			me.searchHandler();
		};
	},
	createSearchPanel:function(){
		var me = this,searchPanel;
		searchPanel = Ext.create('component.view.form.FormPanel', {
    	    region:'north',border:false,frame:false,
    	    absHeight:-18,
    	    name:'searchForm',
    	    items:me.template.search
    	});
		me.searchPanel = searchPanel;
		return searchPanel;
	},
	createGeneralGird:function(config){
		var me = this,generalGird;
		generalGird = Ext.create('component.view.grid.GeneralGrid',{region:'center',template:this.template});
		generalGird.on('itemdblclick',function(grid, record, item, index, e, eOpts ){
			me.getEditor(config).action='view';
			me.getEditor(config).show();
			me.getEditor(config).down('form').getForm().setValues(record.raw);
		});
		me.generalGird = generalGird;
		me.template.generalGird = generalGird;
		return generalGird;
	}
});