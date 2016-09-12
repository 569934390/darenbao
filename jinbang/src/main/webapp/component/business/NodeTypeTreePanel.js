/**
 * 节点类型树形控件
 */
Ext.define('component.business.NodeTypeTreePanel', {
			extend : 'component.public.TreeCommonPanel',
			requires : ['component.public.TreeCommonPanel'],
			alias : 'widget.nodeTypeTreePanel',
			border : false,
			autoExpand : true, 		// 标题是否展开
			checkable : true, 		// 是否显示可选框(Boolean)
			singleSelect : true, 	// 单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : true, // 是否只有叶子节点可选(Boolean)
			txtSearch : true, // 是否显示查询框(Boolean)
			searchWidth : '80%', // 查询框的宽度,配合txtSearch使用(String/Long)
			barExpand : true, // 展开按钮(Boolean)
			barAllCheck : true, // 全选按钮(Boolean)
			rootId : -1, // 根节点ID
			rootText : '节点类型树', // 根节点text
			sqlKey : 'com.compses.dao.sqlmap.NodeTypeMapper.selectNodeTree', // 获取数据的SQL名称(String)
			idKey : 'parentNodeTypeId', // 树节点查询子节点值关联KEY
			nodeId : 'nodeTypeId', // 节点id KEY
			nodeName : 'nodeTypeName', // 节点text KEY
			nodeLeaf : 'CHILDNUM'
		});
Ext.define('component.business.NodeTypeTreePanelWindow', {
	extend : 'Ext.window.Window',
	title : '节点类型树',
	alias : 'widget.nodeTypeTreePanelWindow',
	constrain : true,
	plain : true,
	modal : true,
	width : this.width || 300,
	height : this.height || 400,
	layout : 'border',
	constructor : function(condition) {
		var me = this;
		var nodeTypeTreePanel = Ext.create('component.public.TreeCommonPanel',{
			region : 'center',
			border : false,
			autoExpand : true, // 标题是否展开
			checkable : true, // 是否显示可选框(Boolean)
			singleSelect : true, // 单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : true, // 是否只有叶子节点可选(Boolean)
			txtSearch : true, // 是否显示查询框(Boolean)
			searchWidth : '60%', // 查询框的宽度,配合txtSearch使用(String/Long)
			barExpand : true,                  	//展开按钮(Boolean)
			barCollapse : true,                 //收起按钮(Boolean)
			barAllCheck : true, 				// 全选按钮(Boolean)
			rootId : -1, // 根节点ID
			rootText : '节点类型树', // 根节点text
			sqlKey : 'com.compses.dao.sqlmap.NodeTypeMapper.selectNodeTree', // 获取数据的SQL名称(String)
			idKey : 'parentNodeTypeId', // 树节点查询子节点值关联KEY
			nodeId : 'nodeTypeId', // 节点id KEY
			nodeName : 'nodeTypeName', // 节点text KEY
			nodeLeaf : 'CHILDNUM',
			paramMap : { // 其他参数
				nodeTypeId : condition.nodeTypeId || ''
			}
		});

		nodeTypeTreePanel.on("itemdblclick", function(panel, record, item,
						index, e, eOpts) {
					me.callbackFun(me, nodeTypeTreePanel);
				});
		me.nodeTypeTreePanel = nodeTypeTreePanel;

		me.items = [nodeTypeTreePanel];
		me.callParent(arguments);
	},
	callbackFun : function(window, treePanel) {
		if (this.callback) {
			if (treePanel.checkable) {
				this.callback(treePanel.getChecked(), treePanel);
			} else {
				this.callback(treePanel.getSelectionModel().getSelection().raw.attributes,treePanel);
			}
		}
		window.close();
	},
	buttonAlign : 'center',
	buttons : [{
				text : '确定',
				handler : function() {
					var treeWin = this.up("nodeTypeTreePanelWindow");
					return treeWin.callbackFun(treeWin,treeWin.nodeTypeTreePanel);
				}
			}, {
				text : '取消',
				handler : function() {
					var treeWin = this.up("nodeTypeTreePanelWindow");
					treeWin.close();
				}
			}]

});