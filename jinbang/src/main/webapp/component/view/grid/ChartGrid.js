Ext.define('component.view.grid.ChartGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.chartGrid',
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
		     fields:['nodeName','nodeId','chartId','type','title','subTitle','sort','state','sourceCount','adviceConfig'],
		     proxy: {
		         type: 'ajax',
		         url: ctx+'/base/getPage.do',
		         reader: {
		            type: 'json',
		            root: 'result',
	        		totalProperty: 'totalCount'
		         },
		         extraParams:{conditionsStr:Ext.encode({staffId:session.user.id}),sqlKey:'DopChart.selectList'}
		     },
		     pageSize:50,
		     autoLoad: true
		 });
		 var plugin=[Ext.create('component.public.GridToolTipPlugin',{
		 	toolTipRenderer:function(grid,record,value,label,cellIndex,cellElement,dataIndex){
		 		if(cellIndex==15||cellIndex==16){
		 			return value;
		 		}
		 		return false;
		 	}
		 }),new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
        })];
		me.store = store;
		me.plugins = plugin;
		var nodeCombo = Ext.widget({
			xtype:'ajaxComboBox',
			editable : false,
			displayField: 'nodeName',
			valueField: 'nodeId',
			url:ctx+'/base/getList.do?sqlKey=DopNetworkNode.selectList',
			queryMode:'remote'
		});
		me.columns = [
			{ text: '节点名称',  dataIndex: 'nodeId' ,width:140,editor:nodeCombo,renderer:function(val,view,record){
				var store = nodeCombo.getStore();
				for(var i=0;i<store.getCount();i++){
					if(store.getAt(i).get('nodeId')==val){
						record.set('nodeName',store.getAt(i).get('nodeName'));
						return record.get('nodeName');
					}
				}
				return val;
			}},
	        { text: '排序', dataIndex: 'sort' ,width:60,editor: {
                xtype:'ajaxComboBox',
    			queryMode:'local',
				data:[['1','1'],['2','2']
				,['3','3'],['4','4']
				,['5','5'],['6','6']
				,['7','7'],['8','8']
				,['9','9'],['10','10']
				,['11','11'],['12','12']]
            }},
			{ text: '图表名称',  dataIndex: 'title' ,flex:1},
	        { text: '图表类型', dataIndex: 'type' ,width:140},
    		{ text: '副标题', dataIndex: 'subTitle',width:150},
	        { text: '指标个数', dataIndex: 'sourceCount' ,width:80}];
	    if(config.columns){me.columns = config.columns}
    	me.dockedItems = [{
	        xtype: 'pagingtoolbar',
	        store: store,
	        dock: 'bottom',
	        displayInfo: true
	    }];
		me.callParent([config]);
		me.on('edit',function(editor, e){
			var record = e.record;
			console.info(arguments);
			Ext.Persistent.post(ctx+'/chartController/addToNode.do',record.data,function(chart){
				record.commit();
			});
		});
	}
});