/**
 * 节点树形控件
 */
Ext.define('component.business.NodeTreePanel', {
			extend : 'component.public.TreeCommonPanel',
			requires : ['component.public.TreeCommonPanel'],
			alias : 'widget.nodeTreePanel',
			region : 'center',
			constructor : function(condition) {
				var me = this;
				var config = Ext.applyIf(condition, {
							height : 200,
							cascadeCheck : 'downmulti',
							barExpand : true,
							barCollapse:true,
							autoExpand : true, // 标题是否展开
							checkable : false, // 是否显示可选框(Boolean)
							// checkBox : true, //是否显示可选框(Boolean)
							singleSelect : false, // 单选(Boolean)
													// (级别比cascadeCheck高)
							onlyLeafCheckable : false, // 是否只有叶子节点可选(Boolean)
							// itemcontextmenu : true, //添加树右键菜单(Boolean)
							txtSearch : true, // 是否显示查询框(Boolean)
							searchWidth : '80%' // 查询框的宽度,配合txtSearch使用(String/Long)
								
						});
				me.callParent([config]);
			},
			rootId : 1, // 根节点ID
			rootText : '节点树', // 根节点text
			iconable : true, // 是否显示图标(Boolean)
			rootIcon : ctx + '/common/topoImages/16x16/physicalsubnet.gif', // 根节点图标URL
			sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadNodeFamilyList', // 获取数据的SQL名称(String)
			idKey : 'treeParentId', // 树节点查询子节点值关联KEY
			nodeId : 'symbolId', // 节点id KEY
			nodeName : 'symbolName1', // 节点text KEY
			nodeIcon : 'treeIconPath',
			paramMap : {
				symbolStyle : '3',
				isShortcut:'0',
				nodeTypeId:'13010001'
			}
		});
Ext.define('component.business.NodeTreePanelWindow', {
			extend : 'Ext.window.Window',
			title : '节点树',
			alias : 'widget.nodeTreePanelWindow',
			width : this.width || 300,
			height : this.height || 400,
			layout : 'border',
			constructor : function(condition) {
				var me = this;
				Ext.apply(condition, {
							items : [Ext.create(
									'component.business.NodeTreePanel', {
										checkable : condition.checkable
									})]
						});
				me.callParent([condition]);
			},
			afterRender : function() {
				var me = this;
				me.callParent(arguments);
				me.on("resize", me.resize);
				me.getTreePanel().on("itemdblclick",
						function(panel, record, item, index, e, eOpts) {
							me.callbackFun(me, me.getTreePanel());
						});
			},
			resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
				this.getTreePanel().setWidth(width);
				this.getTreePanel().setHeight(height);
			},
			getTreePanel : function() {
				return this.down("nodeTreePanel");
			},
			callbackFun : function(window, treePanel) {
				if (this.callback) {
					if (treePanel.checkable) {// 有checkbox的情况下的选中行数据
						this.callback(treePanel.getChecked(), treePanel);
					} else {
						this.callback(treePanel.getSelectionModel()
										.getSelection(), treePanel);
					}
				}
				window.hide();
				// console.info(window.getTreePanel().getSelectionModel().getSelection());
				// return
				// window.getTreePanel().getSelectionModel().getSelection();
			},
			buttonAlign : 'center',
			closeAction : 'hide',
			buttons : [{
						text : '确定',
						handler : function() {
							var window = this.up("nodeTreePanelWindow");
							return window.callbackFun(window, window.getTreePanel());
						}
					}, {
						text : '取消',
						handler : function() {
							this.up("nodeTreePanelWindow").hide();
						}
					}]

		});