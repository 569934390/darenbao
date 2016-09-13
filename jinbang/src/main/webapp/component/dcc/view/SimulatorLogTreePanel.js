/**
 * 节点树形控件
 */
Ext.define('component.dcc.view.SimulatorLogTreePanel', {
			extend : 'component.public.TreeCommonPanel',
			alias : 'widget.testDccLogTreePanel',
			constructor : function(condition) {
				var me = this;
				var config = Ext.applyIf(condition, {
							width:600,
							cascadeCheck : 'downmulti',
							barExpand : false,
							barCollapse:false,
							autoExpand : true, // 标题是否展开
							checkable : false, // 是否显示可选框(Boolean)
							// checkBox : true, //是否显示可选框(Boolean)
							singleSelect : false, // 单选(Boolean)
													// (级别比cascadeCheck高)
							onlyLeafCheckable : false, // 是否只有叶子节点可选(Boolean)
							// itemcontextmenu : true, //添加树右键菜单(Boolean)
//							txtSearch : true, // 是否显示查询框(Boolean)
							searchWidth : '80%', // 查询框的宽度,配合txtSearch使用(String/Long)
							fields : [{
								name : 'id'
							}, {
								name : 'text'
							}, {
								name : 'size'
							},{
								name:'create_time'
							},{
								name:'ip_and_port'
							},{
								name:'log_id'
							}],
							columns: [{
						        xtype: 'treecolumn', //this is so we know which column will show the tree
						        text: '名称',
						        width:200,
						        sortable: true,
						        dataIndex: 'text'
						    },{
						        text: '大小',
						        sortable: true,
						        width:40,
						        dataIndex: 'size'
						    },{
						        text: '时间',
						        sortable: true,
						        flex:1,
						        dataIndex: 'create_time'
						    },{
						        text: 'IP和端口',
						        sortable: true,
						        flex:1,
						        dataIndex: 'ip_and_port'
						    }]
						});
				me.callParent([config]);
			},
			rootId : -1, // 根节点ID
			rootText : 'DCC端口测试', // 根节点text
			rootVisible:false,
//			iconable : true, // 是否显示图标(Boolean)
//			rootIcon : ctx + '/common/topoImages/16x16/physicalsubnet.gif', // 根节点图标URL
			sqlKey : 'DopSimulatorDccLog.selectListByStaffId', // 获取数据的SQL名称(String)
			idKey : 'staffId', // 树节点查询子节点值关联KEY
			nodeId : 'id', // 节点id KEY
			nodeName : 'text', // 节点text KEY
			url:ctx+'/tree/getDopTestDccLogTreeAllData.do',
			paramMap : {
				groupField:'name',
				groupFlag:'true',
				staffId : session.user.id,
				logType:'0'
			}
		});