Ext.define('component.view.grid.AutomationTestGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.automationTestGrid',
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
		     fields:['testTime','version','totalCount','successCount','failCount','rate','ipPort'],
		     proxy: {
		         type: 'ajax',
		         url: ctx+'/simulator/selectAutomationTestList.do',
		         reader: {
		             type: 'json',
		            root: 'result',
	        		totalProperty: 'totalCount'
		         },
		         extraParams:{conditionsStr:Ext.encode({staffId:session.user.id}),type:config.gtype||'version'}
		     },
		     pageSize:50,
		     autoLoad: true
		 });
		 var plugin=Ext.create('component.public.GridToolTipPlugin',{
		 	toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
		 		if(cellIndex==0||cellIndex==9){
		 			return value;
		 		}else if(cellIndex==11){
		 			return me.retrunCodeDescRender(value);
		 		}
		 		return false;
		 	}
		 });
		me.store = store;
		me.plugins = plugin;
		me.columns = [
	        { text: '版本号',  dataIndex: 'version' ,width:230},
	        { text: 'ip地址',  dataIndex: 'ipPort' ,width:150},
	        { text: '总场景数', dataIndex: 'totalCount' ,width:100},
	        { text: '通过场景数',  dataIndex: 'successCount' ,width:100},
	        { text: '失败场景数', dataIndex: 'failCount' ,width:100},
	        { text: '通过率', dataIndex: 'rate' ,renderer:function(val,e,record){
	        	var success = parseInt(record.get('successCount'));
	        	var totalCount = parseInt(record.get('totalCount'));
	        	console.info(arguments);
	        	if(success==0)return 0+"%";
	        	return (success/totalCount*100).toFixed(2)+"%";
	        }},
	        { text: '测试时间', dataIndex: 'testTime' ,flex:1}];
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