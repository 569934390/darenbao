/**
 * 拓扑类型列表
 */
Ext.define('component.topo.TopoTypeListWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoTypeListWin',
	constructor:function(condition){
		var me = this;
		/**
		 * 拓扑类型列表
		 */
		me.createTopoTypeGrid();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.topoTypeGrid]
		});
		
		me.callParent([config]);
	},
	createTopoTypeGrid : function(){
		var me = this;
		var store=Ext.create('Ext.data.TreeStore',{
			model:'function',
			nodeParam: 'parentId',//查询参数
			proxy:{
				type:'ajax',
				text : '22',
				url: webRoot + '/tree/getTreeGridList.do',
				extraParams : {
					sqlKey : 'com.compses.dao.sqlmap.NmsTopoTypeMapper.selectTypeList',
					parentId : -1
				},
				reader:{
		            type: 'json',
		            root: 'result'
				}
			},
		    fields: ['topoTypeId','parentId','topoTypeDisplayName','nodeTypeId','typeCategory','treeIconPath','mapIconPath'],
			folderSort:false,
			listeners:{'beforeload':function(store, operation, eOpts ){
				if(!Ext.isEmpty(operation.node.raw)&&!Ext.isEmpty(operation.node.raw.topoTypeId)){
					store.proxy.extraParams.parentId=operation.node.raw.topoTypeId;
				}
			}}
		});
		store.load();
		
		var nodeTypeCombo = Ext.create('component.public.TreeCombo', {
			displayField : 'topoTypeDisplayName',
			queryMode : 'remote',
			hiddenName : 'topoTypeId',
			valueField : 'topoTypeDisplayName',
			typeAhead: true,
			editable : false,
		 	triggerAction: 'all',
			autoExpand : true,  
		 	rootVisible : false,
		 	rootId : -1,                 		//根节点ID
			rootText : '网元类型',				//根节点text
			sqlKey : 'com.compses.dao.sqlmap.NodeTypeMapper.selectNodeTree',  //获取数据的SQL名称(String)
			idKey : 'parentNodeTypeId',	     	//树节点查询子节点值关联KEY
			nodeId : 'nodeTypeId',				//节点id KEY
			nodeName : 'nodeTypeName',			//节点text KEY
			nodeLeaf : 'CHILDNUM',
			paramMap : {
				ifQuery : '0BF'					//网元类型树
			},
			fields : ['topoTypeId', 'topoTypeDisplayName']
		});
		
		topoTypeGrid =Ext.create('component.public.TreeGridCommon',{
			region : 'center',
			collapsible:false,
			rootVisible:false,
			layout:'fit',
			fieldkey:'topoTypeId',
			fieldparentkey:'parentId',
			newUrl : webRoot+'/topoSymbol/addTopoType.do',			//新增
			updateUrl:'FnItem!updateFnItem',	//修改
			delUrl:'FnItem!delFnItem',			//删除action
			store : store,
		    columns: [{
		    	text: '', dataIndex: 'topoTypeId',hidden: true
		    },{
		    	text: 'ID', dataIndex: 'parentId',hidden: true
		    },{
		    	xtype: 'treecolumn',text: '类型名称',dataIndex: 'topoTypeDisplayName'
//		    		,field:{
//				 allowBlank:true
//		    	}
//	        },{
//	        	text: '节点类型名称', dataIndex: 'nodeTypeId',hidden: true
	        },{
	        	text: '节点类型名称', dataIndex: 'nodeTypeId'//,field:nodeTypeCombo
	        },{
	        	text: '所属分类', dataIndex: 'typeCategory',
//	        	field:{
//	        		allowBlank:true
//	        	},
	        	renderer:function(value, meta, record) {
	        		var txt;
	        		switch(value){
	        			case '1':txt="子网";break;
	        			case '2':txt="符号";break;
	        			default :txt="网元";break;
	        		}
        			return txt;
        		}
	        },{
	        	text: '树图标', dataIndex: 'treeIconPath',align : "center",
				renderer : function(value, meta, record) {
					var resultStr = "<font color='green'>无</font>";
					if (!Ext.isEmpty(value)) {
						resultStr = "<div><img src='"
							+ webRoot + value
							+ "' style='width:16px;height:16px'></div>";
					}
					return resultStr;
				}
//	        field:{
//				 allowBlank:true
//	        	}
	        },{
	        	text: '图图片', dataIndex: 'mapIconPath',draggable:false,align : "center",
				renderer : function(value, meta, record) {
					var resultStr = "<font color='green'>无</font>";
					if (!Ext.isEmpty(value)) {
						resultStr = "<div><img src='"
							+ webRoot + value
							+ "' style='width:32px;height:32px'></div>";
					}
					return resultStr;
				}
	        }],
		    forceFit : true,
		    tbar:[{
		    	xtype:'splitbutton',
				text:'新增',
				tooltip: '新增数据！',
				disabled : false,
				iconCls:'toolbar-add',
				menu:[{
					text:'新增子层',
					iconCls:'x-tree-icon-leaf',
					handler:function(){this.ownerCt.ownerButton.ownerCt.ownerCt.insertnode(true);}
				},{
					text:'新增父层',
				 	iconCls:'x-tree-icon-parent',
				  	 handler:function(){this.ownerCt.ownerButton.ownerCt.ownerCt.insertnode(false);}
				}],
				handler:function(){this.ownerCt.ownerCt.insertnode(true)}
		    },'-',{
	    		xtype:'button',
	    		text:'删除',
	    		iconCls:'toolbar-del',
	    		disabled : false,
	    		tooltip: '将数据从数据库中删除！',
	    		handler:function(){this.ownerCt.ownerCt.deletedb();}
		    },{
		    	xtype:'button',
		    	text:'保存',
		    	disabled : false,
		    	iconCls:'toolbar-filesave',
		    	tooltip: '将修改后的数据保存入数据库！',
		    	handler:function(){this.ownerCt.ownerCt.savedb();}
		    },{
		    	xtype:'button',
		    	text:'刷新',
				disabled : false,
		    	iconCls:'toolbar-reLoad',
		    	tooltip: '刷新数据库数据！',
		    	handler:function(){store.load();}
		    }]
		});
		me.topoTypeGrid =topoTypeGrid;
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'saveLayoutSet':
				me.saveTopoSet();
				break;
		}
	}
});