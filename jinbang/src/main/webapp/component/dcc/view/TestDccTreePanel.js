/**
 * 节点树形控件
 */
Ext.define('component.dcc.view.TestDccTreePanel', {
			extend : 'component.public.TreeCommonPanel',
			alias : 'widget.testDccTreePanel',
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
							searchWidth : '80%', // 查询框的宽度,配合txtSearch使用(String/Long)
							paramMap : {
								type:Ext.isEmpty(condition.type)?'0':condition.type,
								dccFlag:'true',
								parentId : '-1'
							}	
						});
				me.callParent([config]);
			},
			rootId : -1, // 根节点ID
			rootText : 'DCC端口测试', // 根节点text
			rootVisible:true,
//			iconable : true, // 是否显示图标(Boolean)
//			rootIcon : ctx + '/common/topoImages/16x16/physicalsubnet.gif', // 根节点图标URL
			sqlKey : 'DopTestDcc.selectListByParentId', // 获取数据的SQL名称(String)
			idKey : 'parentId', // 树节点查询子节点值关联KEY
			nodeId : 'dcc_id', // 节点id KEY
			nodeName : 'text'// 节点text KEY
		});