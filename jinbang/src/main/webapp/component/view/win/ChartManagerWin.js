Ext.define('component.view.win.ChartManagerWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.chartManagerWin',
	requires:[],
	closeAction:'hide',
	maximizable:true,
	modal:true,
	width:Ext.getBody().getWidth()*0.9,
	height:Ext.getBody().getHeight()*0.9,
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
		Ext.apply(me.down('grid').getStore().proxy.extraParams,{page:1,start:0,limit:50,conditionsStr:Ext.encode(params)});
		me.down('grid').getStore().load();
	},
	config:{
	},
	constructor : function(config) {
		var me = this;
		me.config = config;
		config=config||{};
		config.tbar = [{
			text:'新增',
			iconCls:'toolbar-add',
			handler:function(){
				me.hide();
			}
		},{
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
				me.hide();
				me.config.editHanlder(records[0]);
			}
		},{
			text:'删除',
			iconCls:'toolbar-del',
			handler:function(){
				var records=me.down('grid').getSelectionModel().getSelection(),idStr = [];
            	for(var i=0;i<records.length;i++){
            		idStr.push(records[i].get('chartId'));
            	}
            	Ext.Ajax.request({
					url:ctx+'/chartController/delete.do',
					params:{idStr:idStr.join(',')},
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(result.success){
							return Ext.Msg.alert('操作成功','成功删除任务！',function(){
								me.down('grid').getStore().load();
							});
						}else{
							return Ext.Msg.alert('操作失败',result.message);
						}
					},
					failure:function(){
						return Ext.Msg.alert('提示','操作失败');
					}
				});
			}
		},'-',{
			text:'查询',
			iconCls:'toolbar-search',
			handler:Ext.bind(me.searchHandler,me)
		}];
		me.items=[me.createSearchPanel(),me.createPerfTestGird()];
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me = this;
	},
	createSearchPanel:function(){
		var me = this,searchPanel;
		searchPanel = Ext.create('component.view.form.FormPanel', {
    	    region:'north',border:false,frame:true,
    	    name:'searchForm',
    	    items:[
			{
				xtype : 'clearTextField', fieldLabel:'图表名称', name:'%title%',
				emptyText : '图表名称'
			},{
				xtype:'ajaxComboBox',
    	    	clear:true,
    			fieldLabel:'图表类型',
    			queryMode:'local',
    			name:'type',
				data:[['generalChart','generalChart'],['delayChart','delayChart']
				,['returnCodeChart','returnCodeChart'],['businessChart','businessChart']
				,['heartChart','heartChart'],['countChart','countChart']]
			}]
    	});
		me.searchPanel = searchPanel;
		return searchPanel;
	},
	createPerfTestGird:function(){
		var perfTestGird;
		perfTestGird = Ext.create('component.view.grid.ChartGrid',{region:'center'});
		return perfTestGird;
	}
});