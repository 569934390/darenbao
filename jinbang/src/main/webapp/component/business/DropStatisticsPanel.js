Ext.define('component.business.DropStatisticsPanel', {
	extend : 'Ext.panel.Panel',
    requires:['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField'],
	constructor : function(config) {
		var me = this;
		config=config||{};
		me.searchForm=me.createForm();
		me.grid=me.createGrid();
		Ext.applyIf(config,{
			title:'数据统计',
			layout:'border',
    		border:false,
    		items:[me.grid,me.searchForm]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.down('[name=searchBtn]').on('click',function(){
			me.grid.store.load();
		});
		me.grid.store.on('beforeload',function(store){
			if(me.searchValidation()===false) return false;
			Ext.apply(me.grid.store.proxy.extraParams,Ext.getFormValues(me.searchForm));
		});
	},
	searchValidation:function(){
		var me=this;
		var startTime=me.searchForm.down('[name=startTime]').getValue();
			var endTime=me.searchForm.down('[name=endTime]').getValue();
			if(startTime.getTime()>endTime.getTime()){
				Ext.Msg.alert('提示','开始时间不能大于结束时间!');
				return false;
			}
		if(!me.searchForm.getForm().isValid()) return false;
	},
	createForm:function(){
    	var me=this;
    	var searchForm = Ext.create('component.public.FormPanel', {
    	    region:'north',
    	    frame:true,
    		border:false,
    	    name:'searchForm',
    	    items:[{
    			xtype:'datefield',
    			fieldLabel:'起始时间',
//    			editable:false,
    			value:new Date(new Date().getTime()-1000*60*60*24*30),
    			format:'Y-m-d',
    			name:'startTime'
    		},{
    			xtype:'datefield',
    			fieldLabel:'结束时间',
//    			editable:false,
    			value:new Date(new Date().getTime()+1000*60*60*24),
    			format:'Y-m-d',
    			name:'endTime'
    		},{
				xtype:'button',
				text:'搜索',
				maxWidth:55,
				style:'margin-left:10px;',
				iconCls:'toolbar-search',
	        	name:'searchBtn'
			},{
				xtype:'button',
				text:'重置',
		        iconCls:'toolbar-delete',
		        style:'margin-left:10px;',
		        name:'reset',
				maxWidth:55,
		        handler: function() {
		            this.up('form').getForm().setValues({
								startTime : new Date(new Date().getTime()
										- 1000 * 60 * 60 * 24 * 30),
								endTime : new Date(new Date().getTime() + 1000
										* 60 * 60 * 24)
							});
		        }
			},{
				xtype:'button',
				text:'导出',
		        iconCls:'toolbar-export',
		        style:'margin-left:10px;',
		        name:'delete',
				maxWidth:55,
		        handler: function() {
		        	if(me.searchValidation()===false) return false;
		        	var params=Ext.getFormValues(me.searchForm);
		            window.open(ctx+'/chartController/export.do?startTime='+params['startTime']+'&endTime='+params['endTime']);
		        }
			}]
    	});
    	return searchForm;
    
	},
	createGrid:function(){
		var store=Ext.create('component.public.AjaxStore',{
				fields:['dropDate','totalCount','in','ccg','p2psms','ismp','timeCount1','timeCount2','timeCount3','errorCount'],
				pageSize:'50',
				url:ctx+'/chartController/getDropStatistics.do',
				autoLoad:true
			});
		return Ext.create('Ext.grid.Panel',{
			region:'center',
			plugins: {
		        ptype: 'bufferedrenderer',
		        trailingBufferZone: 20,  // Keep 20 rows rendered in the table behind scroll
		        leadingBufferZone: 50   // Keep 50 rows rendered in the table ahead of scroll
		    },
			columns:[{
				text:'采集时间',
				dataIndex:'dropDate'
			},{
				text:'消息总量',
				dataIndex:'totalCount',
				flex:1
			},{
				text:'语音业务数据量',
				dataIndex:'in',
				flex:1
			},{
				text:'数据业务数据量',
				dataIndex:'ccg',
				flex:1
			},{
				text:'短信业务数据量',
				dataIndex:'p2psms',
				flex:1
			},{
				text:'增值业务数据量',
				dataIndex:'ismp',
				flex:1
			},{
				text:'&lt;=50ms消息量',
				dataIndex:'timeCount1',
				flex:1
			},{
				text:'&lt;=200ms消息量',
				dataIndex:'timeCount2',
				flex:1
			},{
				text:'&gt;200ms消息量',
				dataIndex:'timeCount3',
				flex:1
			},{
				text:'异常消息量',
				dataIndex:'errorCount',
				flex:1
			}],
			store:store
//    	    dockedItems: [{
//		        xtype: 'pagingtoolbar',
//		        store: store,   // same store GridPanel is using
//		        dock: 'bottom',
//		        displayInfo: true
//		    }]
		});
	}
});