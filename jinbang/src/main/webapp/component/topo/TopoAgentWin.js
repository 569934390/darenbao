/**
 * 选择Agent
 */
Ext.define('component.topo.TopoAgentWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoAgentWin',
	constructor:function(condition){
		var me = this;
		me.createAgentWin();
		me.curNode = condition.curNode;
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'fit',
			items:[me.agentTreePanel],
			buttons : [ {
				xtype : 'button',
				text : '同步',
				scope : this,
				handler:me.buttonEvent.createDelegate(me,['nodeSync'],0)
			}, {
				xtype : 'button',
				text : '取消',
				scope : this,
				handler : function() {
					me.hide();
				}
			} ]
		});
		me.callParent([config]);
	},
	createAgentWin : function(){
		var me = this;
		me.agentTreePanel = Ext.create('component.public.TreeCommonPanel', {
			region : 'center',
			border : false,
			autoExpand : true,                	//是否全展开
			useArrows : true,
			checkable : false,
			readOnly : true,
			singleSelect : false,               //单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : false,         	//是否只有叶子节点可选(Boolean)
			barAllCheck : false,				//全选按钮(Boolean)
			rootVisible : true,					//根节点可见
			rootId : '13010001',   				//根节点值
			rootText : 'Agent树',				//根节点text
			iconable : true,					//是否显示图标(Boolean)
			rootIcon : '../common/topoImages/16x16/dlnms/agent.gif', //根节点图标URL
			sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadTopoMapList',  //获取数据的SQL名称(String)
			idKey : 'nodeTypeId',	     		//树节点查询子节点值关联KEY
			nodeId : 'nodeId',					//节点id KEY
			nodeName : 'symbolName1',			//节点text KEY
			nodeIcon : 'treeIconPath'			//节点icon KEY
		});
		me.agentTreePanel.on('itemclick', function(view, node, item, index,e,eOpts){
			me.agentId = node.data.id;
		});
		me.agentTreePanel.on('itemdblclick', function(view, node, item, index,e,eOpts){
			me.agentId = node.data.id;
			me.nodeSync();
		});
	},
	nodeSync : function(){
		var me = this;
		var task = {};
		
		task.agentId = me.agentId;
		if(Ext.isEmpty(me.curNode.raw.attributes)){
			task.nodeId= curNode.raw.nodeId;
			task.ipaddress = curNode.raw.ipaddress;
		}else{
			var attributes = Ext.JSON.decode(me.curNode.raw.attributes);
			task.nodeId=attributes.nodeId;
			task.ipaddress=attributes.ipaddress;
		}
		task.attrValue = curNode.data.text;
		
		var mapPanel = document.getElementById('frame_main');
		mapPanel.contentWindow.topoNodeSynchronous(task);
		
		me.close();
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'nodeSync':
				me.nodeSync();
				break;
		}
	}
});