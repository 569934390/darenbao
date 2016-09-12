/**
 * TopoEditSplitterWin
 */
Ext.define('component.topo.TopoEditSplitterWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoEditSplitterWin',
	requires: [
        'component.public.AjaxComboBox'
   	],
	constructor:function(condition){
		var me = this;
		me.nodeId = condition.nodeId;
		me.splitterTreePanel();
		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'fit',
			items:[me.splitterTreePanel],
			buttons : [ {
				xtype : 'button',
				text : '保存',
				scope : this,
				handler:me.buttonEvent.createDelegate(me,['onusSave'],0)
			}, {
				xtype : 'button',
				text : '取消',
				scope : this,
				handler : function() {
					me.close();
				}
			} ]
		});
		me.callParent([config]);
	},
	splitterTreePanel : function(){
		var me = this;
		me.splitterTreePanel = Ext.create('component.public.TreeCommonPanel', {
			region : 'center',
			border : false,
			autoExpand : false,                	//是否全展开
			useArrows : true,
			checkable : false,
			readOnly : true,
			singleSelect : false,               //单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : false,         	//是否只有叶子节点可选(Boolean)
			barAllCheck : false,				//全选按钮(Boolean)
			rootVisible : false,				//根节点可见
			rootId : me.nodeId,   				//根节点值
			rootText : '',						//根节点text
			iconable : true,					//是否显示图标(Boolean)
			rootIcon : '../common/topoImages/16x16/dlnms/splitter.png', //根节点图标URL
			sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadEditOltTree',  //获取数据的SQL名称(String)
			idKey : 'desNodeId',	     		//树节点查询子节点值关联KEY
			nodeId : 'nodeId',					//节点id KEY
			nodeName : 'symbolName1',			//节点text KEY
			nodeIcon : 'treeIconPath' 			//节点icon KEY
		});
		me.splitterTreePanel.on('itemclick', function(view, node, item, index,e,eOpts){
			me.sNodeId = node.data.id;
		});
		me.splitterTreePanel.on('itemdblclick', function(view, node, item, index,e,eOpts){
			me.sNodeId = node.data.id;
			me.onusSave();
		});
		me.splitterTreePanel.getStore().on('beforeload', function(store, operation, eOpts){
    		var extraParams=me.splitterTreePanel.getStore().getProxy().extraParams;
    		if(operation.node.getId()!=me.nodeId){
    			extraParams.sqlKey='com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadEditSplitterTree';  //获取数据的SQL名称(String)
    			extraParams.idKey = 'desNodeId';	     		//树节点查询子节点值关联KEY
    			extraParams.nodeId = 'nodeId';					//节点id KEY
    			extraParams.nodeName = 'symbolName1';			//节点text KEY
    		}else{
    			extraParams.sqlKey = 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadEditOltTree';  //获取数据的SQL名称(String)
    		}
	  	});
		
	},
	onusSave : function(){
		var me = this;
		var lineData ={};
		lineData.sNodeId = me.sNodeId;
		lineData.dNodeId = me.nodeId;
		Ext.Ajax.request({
			url: webRoot+'/linkSymbol/updateOnuSplitter.do',
			method : 'POST',
			params : lineData,
			success : function(resp, action) {
				var mapPanel = document.getElementById('frame_main');
				mapPanel.contentWindow.reLoadMapLayer(true,true);
				me.close();
			},
			failure: function(){
			}
		});
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'onusSave':
				me.onusSave();
				break;
		}
	}
});
	