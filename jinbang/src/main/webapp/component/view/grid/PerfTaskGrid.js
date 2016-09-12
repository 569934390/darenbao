Ext.define('component.view.grid.PerfTaskGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.perfTaskGrid',
	requires:[],
	config:{
	},
	viewConfig:{
		enableTextSelection:true
	},
	name:'messageGrid',
	selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
	constructor : function(config) {
		var me = this;
		config=config||{};
		var store = Ext.create('Ext.data.Store', {
		     fields:['id','version','simulators','ipAndPort','totalCount','avgDelay','maxDelay','minDelay','misCount','delayCount','startTimer','endTimer','status','startNum','loopNum','avgLess','rateLess','maxLess','cycleCount'],
		     proxy: {
		         type: 'ajax',
		         url: ctx+'/base/getPage.do',
		         reader: {
		            type: 'json',
		            root: 'result',
	        		totalProperty: 'totalCount'
		         },
		         extraParams:{conditionsStr:Ext.encode({staffId:session.user.id}),sqlKey:'DopPerfConfig.selectList'}
		     },
		     pageSize:50,
		     autoLoad: true
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
			{ text: '主键',  dataIndex: 'id' ,width:40},
	        { text: '对端ip端口', dataIndex: 'ipAndPort' ,width:120},
//	        {
//	        	text:'时延统计(毫秒)',
//	        	columns:[
//	        		{ text: '平均', dataIndex: 'avgDelay' ,width:38},
//			        { text: '最大', dataIndex: 'maxDelay' ,width:38},
//			        { text: '最小', dataIndex: 'minDelay' ,width:38}]
//	        },
//	        {
//	        	text:'消息(个)',
//	        	columns:[
//	        		{ text: '总数', dataIndex: 'totalCount' ,width:38},
//	        		{ text: '未返回', dataIndex: 'misCount' ,width:48},
//			        { text: '超时', dataIndex: 'delayCount' ,width:38}]
//	        },
	        { text: '执行时间', dataIndex: 'startTimer' ,width:125},
	        { text: '结束时间', dataIndex: 'endTimer' ,width:85,renderer:function(val){
	        	if(val)
	        		return val.substring(val.lastIndexOf('-')+1,val.length);
	        	return '未结束';
	        }},
	        { text: '状态', dataIndex: 'status' ,width:50,renderer:function(val){
	        	if(val=='00A')
	        		return '运行中';
	        	return '<span style="color:blue;">已完成</span>';
	        }},
	        {
	        	text:'配置',
	        	columns:[
	        		{ text: '周期(毫秒)',  dataIndex: 'startNum' ,width:70},
			        { text: '周期内发送包数', dataIndex: 'loopNum' ,width:95},
					{ text: '1s并发数', dataIndex: 'loopNum' ,width:60,
					renderer:function(val,cell,record){
						return 1000/record.get('startNum')*record.get('loopNum');
					}},
	       			{ text: '场景配比', dataIndex: 'simulators' ,width:240}]
	        },
			{ text: '版本号',  dataIndex: 'version' ,width:150}];
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