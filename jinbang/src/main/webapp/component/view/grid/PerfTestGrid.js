Ext.define('component.view.grid.PerfTestGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.perfTestGrid',
	requires:[],
	config:{
	},
	viewConfig:{
		enableTextSelection:true
	},
	selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
	constructor : function(config) {
		var me = this;
		config=config||{};
		var store = Ext.create('Ext.data.Store', {
		     fields:['id','simulators','total','totalCount','count','missCount','successCount','errorCount','delay','avgDelay','totalDelay','minDelay','maxDelay','createTime','sessionId','userId','uuid','success'],
		     proxy: {
		         type: 'ajax',
		         url: ctx+'/base/getPage.do',
		         reader: {
		            type: 'json',
		            root: 'result',
	        		totalProperty: 'totalCount'
		         },
		         extraParams:{conditionsStr:Ext.encode({staffId:session.user.id}),sqlKey:'DopPerfDatas.selectList'}
		     },
		     pageSize:50,
		     autoLoad: false
		 });
		 var plugin=Ext.create('component.public.GridToolTipPlugin',{
		 	toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
		 		if(cellIndex==15||cellIndex==16){
		 			return value;
		 		}
		 		return false;
		 	}
		 });
		me.store = store;
		me.plugins = plugin;
		me.columns = [
			{ text: '业务类型',  dataIndex: 'simulators' ,flex:1},
    		{ text: '累计场景数', dataIndex: 'count',width:80},
	        { text: '平均时延', dataIndex: 'avgDelay' ,width:60},
	        { text: '最小时延', dataIndex: 'minDelay' ,width:60},
	        { text: '最大时延', dataIndex: 'maxDelay' ,width:60},
	        { text: '成功场景数', dataIndex: 'successCount' ,width:70},
	        { text: '失败场景数', dataIndex: 'errorCount' ,width:70},
	        { text: '丢包数', dataIndex: 'missCount' ,width:60},
	        { text: '采集时间', dataIndex: 'createTime' ,width:125}];
	    if(config.columns){me.columns = config.columns}
    	me.dockedItems = [{
	        xtype: 'pagingtoolbar',
	        store: store,   // same store GridPanel is using
	        dock: 'bottom',
	        displayInfo: true
	    }];
		me.callParent([config]);
	}
});