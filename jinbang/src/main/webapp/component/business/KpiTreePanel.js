/**
 * 节点类型树形控件
 */
Ext.define('component.business.KpiTreePanel', {
			extend : 'component.public.TreeCommonPanel',
			requires : ['component.public.TreeCommonPanel'],
			alias : 'widget.kpiTreePanel',
			url : ctx + '/kpiCodeDataController/getKpiTree.do',
			// height:200,
			cascadeCheck : 'downmulti',
			autoExpand : false, // 标题是否展开
			checkable : true, // 是否显示可选框(Boolean)
			// checkBox : true, //是否显示可选框(Boolean)
			singleSelect : false, // 单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : false, // 是否只有叶子节点可选(Boolean)
			// itemcontextmenu : true, //添加树右键菜单(Boolean)
			txtSearch : true, // 是否显示查询框(Boolean)
			searchWidth : '60%', // 查询框的宽度,配合txtSearch使用(String/Long)
			barExpand : true, // 展开按钮(Boolean)
			barAllCheck : true, // 全选按钮(Boolean)
			// viewConfig: {
			// plugins: {
			// ptype: 'treeviewdragdrop', // 可以拖拽
			// enableDrag: true, // 拖拽
			// enableDrop: true, // 节点接受拖动
			// appendOnly: true
			// }
			// },
			rootId : 1, // 根节点ID
			rootText : '告警指标', // 根节点text
			// rootIcon : 'public/topImages/images/16x16/physicalsubnet.gif',
			// //根节点图标URL
			// //获取数据的SQL名称(String) (必)
			idKey : 'parentNodeTypeId', // 树节点查询子节点值关联KEY
			nodeId : 'nodeTypeId', // 节点id KEY (必)
			nodeName : 'nodeTypeName', // 节点text KEY (必)
//			addHandler : function() { // 右键菜单新增动作(Handler)(当itemcontextmenu：true有效)
//				alert(33333);
//			},
//			editHandler : function() { // 右键菜单修改动作(Handler)(当itemcontextmenu：true有效)
//				alert(33333);
//			},
//			delHandler : function() { // 右键菜单删除动作(Handler)(当itemcontextmenu：true有效)
//				alert(33333);
//			},
			paramMap : { // 其他参数
				nodeTypeId : '-1',
				kpiType:20
			}
		});
Ext.define('component.business.KpiTreePanelWindow', {
	extend : 'Ext.window.Window',
	title : 'kpi选择',
	layout : 'border',
	alias : 'widget.kpiTreePanelWindow',
	width : this.width || 300,
	height : this.height || 400,
	closeAction : 'hide',
	resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
		this.getKpiTreePanel().setWidth(width / 2);
		this.getKpiTreePanel().setHeight(height);
	},
	afterRender : function() {
		var me = this;
		me.callParent(arguments);
		me.on("resize", me.resize);
		me.getNodeTreePanel().on("itemclick", me.nodeTreeClick);
		me.getKpiTreePanel().on("itemdblclick",
				function(panel, record, item, index, e, eOpts) {
					me.callbackFun(me, me.getKpiTreePanel());
				});
	},
	getKpiTreePanel : function() {
		return this.down("kpiTreePanel");
	},
	getNodeTreePanel : function() {
		return this.down("nodeTreePanel");
	},
	nodeTreeClick : function(view, record, item, index, e, eOpts) {
		var me = this.up("kpiTreePanelWindow");
		var attrs = Ext.JSON.decode(record.raw.attributes);
		if (!Ext.isEmpty(attrs) && !Ext.isEmpty(attrs.nodeTypeId)) {
			var paramMap=me.getKpiTreePanel().paramMap;
			paramMap.nodeTypeId=attrs.nodeTypeId;
			Ext.apply(me.getKpiTreePanel().getStore().proxy.extraParams, {
						paramMap : Ext.encode({
									nodeTypeId : attrs.nodeTypeId,
									kpiType : 20
								})
					});
//			me.getKpiTreePanel().getStore().proxy.extraParams.paramMap.nodeTypeId=attrs.nodeTypeId;
			me.getKpiTreePanel().refreshData();
			me.getKpiTreePanel().getRootNode().set('checked', false);
		}
	},
	callbackFun : function(window, treePanel) {
		if (this.callback) {
			if (treePanel.checkable) {
				this.callback(treePanel.getChecked(), treePanel);
			} else {
				this.callback(treePanel.getSelectionModel().getSelection(),
						treePanel);
			}
		}
		window.hide();
		// console.info(window.getTreePanel().getSelectionModel().getSelection());
		// return window.getTreePanel().getSelectionModel().getSelection();
	},
	items : [Ext.create('component.business.NodeTreePanel', {
						region : 'center'
					}), Ext.create('component.business.KpiTreePanel', {
						region : 'east',
						width : 200,
						height : 300
					})],
	buttonAlign : 'center',
	buttons : [{
				text : '确定',
				handler : function() {
					var window = this.up("kpiTreePanelWindow");
					return window.callbackFun(window, window.getKpiTreePanel());
				}
			}, {
				text : '取消',
				handler : function() {
					this.up("nodeTypeTreeWindow").hide();
				}
			}]

})