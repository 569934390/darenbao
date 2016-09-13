/**
 * @extends Ext.tree.Panel
 */
Ext.define('component.topo.TopoTreePanel', {
    extend: 'component.public.TreeCommonPanel',
	alias : 'widget.topoTreePanel',
	mixins: {
	},
    requires: [
    ],
    region 			: 'center',
	border 			: false,
	autoExpand 		: false,                //是否全展开
	useArrows 		: true,
	treeAllSearch   : true,					//树全量查询
	txtSearch 		: true,        			//是否显示检索框(Boolean)
	localFilter 	: true,         		//是否检索已展开树节点(Boolean) 默认全展开
	searchWidth 	: '100%',               //查询框的宽度,配合txtSearch使用(String/Long)
	rootVisible 	: false,				//根节点可见
	rootId 			: -1,             		//根节点值
	rootText 		: '拓扑管理',			//根节点text
	iconable 		: true,					//是否显示图标(Boolean)
	iconClass 		: "com.compses.filter.tree.TopoTreeNodeFilter.doo",					//图标样式处理类
	rootIcon 		: '../common/topoImages/16x16/physicalsubnet.gif', 					//根节点图标URL
	sqlKey 			: 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadTopoTreeList',  	//获取数据的SQL名称(String)
	idKey 			: 'treeParentId',	  	//树节点查询子节点值关联KEY
	nodeId 			: 'symbolId',			//节点id KEY
	nodeName 		: 'symbolName1',		//节点text KEY
	nodeIcon 		: 'treeIconPath',		//节点icon KEY
	nodeLeaf 		: 'childNum',
	expandLevel 	: 3,
    initComponent : function() {
        var me = this;
        me.onTriggerSearchClick=function(){
    		if(!me.topoMapLocateWin){
    			me.topoMapLocateWin = Ext.create('component.topo.TopoMapLocateWin', {
    				title: '拓扑搜索',
    				iconCls : 'toolbar-search',
    				resizable : false,
    				width : 500,
    				height : 360,
    				closeAction : 'hidden',
    				modal : true
    			});
    		}
    		me.topoMapLocateWin.show();
        }
        me.callParent(arguments);
    }
});