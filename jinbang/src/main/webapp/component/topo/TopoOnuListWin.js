/**
 * ONU列表
 */
Ext.define('component.topo.TopoOnuListWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoOnuListWin',
	requires: [
        'component.public.AjaxComboBox'
   	],
	constructor:function(condition){
		var me = this;
		me.oltData = condition.oltData;
		
		me.createOnuGrid();
		
		var config = Ext.applyIf(condition,{
			border : false,
		    layout: 'fit',
			items:[me.onuGrid]
		});
		me.callParent([config]);
	},
	createOnuGrid : function(){
		var me = this;
		var cellEditing = new Ext.grid.plugin.CellEditing({
	        clicksToEdit: 1
	    });
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/onusOfOlt/findOnusOfOltPage.do',
		        method : 'POST',
				params : {
				},
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
	        fields: [
	             'onuId', 'onuName','nodeId','nodeName','dIndexNode','ipaddress','symbolId', 'onuPonMac', 'onuIp',
	             'oltId','oltIndex','oltIndexName','onuState','findState','onuDesc','lineState'
	        ],
		    pageSize: 20,
	        listeners:{
	        	//勾选全部，超时，已存在，新发现选项时候改变store的url
				beforeload:function(){
					store.proxy.extraParams.oltId=me.oltData.nodeId;
					if(Ext.isEmpty(me.findState)){
						delete store.proxy.extraParams.findState;
					}else{
						store.proxy.extraParams.findState=me.findState;
					}
//					store.proxy.url = webRoot+'/onusOfOlt/findOnusOfOltPage.do?'+findState;
				}
			}
		});
		store.load({params:{start:0, limit:200}});
		
		me.onuGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
		    store: store,
			plugins: cellEditing,
		    forceFit : true,
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    columns: [{ 
		    	header: "OLT接口索引",dataIndex: 'oltIndex',sortable: true
		    },{ 
		    	header: "OLT接口名称",dataIndex: 'oltIndexName',sortable: true
		    },{
		    	header: "ONU名称",dataIndex: 'nodeName',
	            editor: {
	                allowBlank: false,
	                maxLength : 50
	            }
		    },{ 
		    	header: "ONU IP",dataIndex: 'ipaddress',sortable: true
		    },{ 
		    	header: "PON接口Mac",dataIndex: 'onuPonMac', sortable: true
//		    },{ 
//		    	header: "对应OLT名称",dataIndex: 'oltId',sortable: true
//	        },{
//		    	header: "ONU状态",dataIndex: 'onuState',sortable:true,align:'center',
//	            width:60,renderer:function(value, meta, record) {
//	        		if(value=='0') {
//	        			return "<font color='gray'>未知</font>";
//	        		}else if(value=='1') {
//	        			return "<font color='green'>连接</font>";
//	        		}else if(value=='2') {
//	        			return "<font color='red'>断开</font>";
//	        		}
//	        	}
		    },{
		    	header: "ONU状态",dataIndex: 'lineState',sortable:true,align:'center',
		    	width:60,renderer:function(value, meta, record) {
		    		if(value=='-1') {
		    			return "<font color='red'>ONU不存在</font>";
		    		}else if(value=='0') {
		    			return "<font color='green'>ONU存在</font>";
		    		}else if(value>0) {
		    			return "<font color='yellow'>链路已添加</font>";
		    		}
		    	}
		    },{
	            header: "接口描述",dataIndex: 'onuDesc',align:'center',format:'Y-m-d'  
			}],
		    tbar : new Ext.Toolbar({
				items : [{
//					xtype : 'radio',
//					labelSeparator : '',
//					boxLabel:'全部',
//					hiddenLabel : true,
//					name:'onusRadio',
//					checked : true,
//					listeners:{change:function(o,checked){
//						if(checked) {
//		    	   			me.findState = "";
//							store.load({params:{start:0, limit:20}});
//						}
//					}}
//				},{
//					xtype : 'radio',
//					labelSeparator : '',
//					boxLabel:'新发现',
//					hiddenLabel : true,
//					name:'onusRadio',
//					listeners:{change:function(o,checked){
//						if(checked) {
//							me.findState = 1;
//							store.load({params:{start:0, limit:20}});
//						}
//					}}
//				},{
//					xtype : 'radio',
//					labelSeparator : '',
//					boxLabel:'已存在',
//					hiddenLabel : true,
//					name:'onusRadio',
//					listeners:{change:function(o,checked){
//						if(checked) {
//							me.findState = 2;
//							store.load({params:{start:0, limit:20}});
//						}
//					}}
//				},{
//					xtype : 'radio',
//					labelSeparator : '',
//					boxLabel:'超时',
//					hiddenLabel : true,
//					name:'onusRadio',
//					listeners:{change:function(o,checked){
//						if(checked) {
//							me.findState = 0;
//							store.load({params:{start:0, limit:20}});
//						}
//					}}
//				},{
					xtype : 'tbfill'
				},{
					text : '添加到分光器',
					iconCls : 'toolbar-add',
					handler : me.buttonEvent.createDelegate(me,['addSplitter'],0)
			    }]
//		    }),
//			bbar : new Ext.PagingToolbar({
//	            pageSize: 20,
//				displayMsg : 'Displaying{0} - {1} of {2}',
//	            store: store,
//	            displayInfo: true
	        })
		});
	},
	addSplitter : function(){
		var me = this;
        var onuChecks = me.onuGrid.getSelectionModel().getSelection();
        if(onuChecks.length<1){
        	Ext.Msg.alert('提示','请选择一条记录');
        	return;
        }
        var symbolIds='';
        var oltIds='';
        var nodeIds='';
        var oltIndexs='';
        var dIndexNodes='';
        var onuPonMacs='';
    	for(var checkNum=0;checkNum<onuChecks.length;checkNum++){
    		var onu = onuChecks[checkNum];
    		if(!Ext.isEmpty(onu.raw.lineState)&&onu.raw.lineState!=0){
    			Ext.Msg.alert('提示','请选择已存在ONU!');
    			return;
    		}
    		if(Ext.isEmpty(onu.raw.nodeId)){
    			Ext.Msg.alert('提示','请选择已存在ONU!');
    			return;
    		}
    		
    		symbolIds = symbolIds + onu.raw.symbolId+",";
    		oltIds = oltIds + onu.raw.oltId+",";
    		oltIndexs = oltIndexs + onu.raw.oltIndex+",";
    		nodeIds = nodeIds + onu.raw.nodeId+",";
    		if(Ext.isEmpty(onu.raw.dIndexNode)){
    			onu.raw.dIndexNode='-1';
    		}
    		dIndexNodes = dIndexNodes + onu.raw.dIndexNode+",";
    		onuPonMacs = onuPonMacs + onu.raw.onuPonMac+",";
    	}
    	var onuOlts = {};
    	onuOlts.oltIds =oltIds;
    	onuOlts.oltIndexs =oltIndexs;
    	onuOlts.nodeIds =nodeIds;
    	onuOlts.dIndexNodes =dIndexNodes;
    	onuOlts.onuPonMacs =onuPonMacs;
    	onuOlts.mapParentId =me.oltData.mapParentId;
    	console.info(onuOlts);
    	Ext.create('component.topo.OnusToTopoWin',{
    		title : '添加到分光器',
    		iconCls : 'win-net',
    		resizable : false,
    		draggable : false,
    		width : 260,
    		height : 300,
    		modal : true,
    		oltData : me.oltData,
    		onuOlts : onuOlts,
    		callback : function(){
    			me.onuGrid.getStore().load({params:{start:0, limit:20}});
    		}
    	}).show();
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'addSplitter':
				me.addSplitter();
				break;
		}
	}
});
Ext.define('component.topo.OnusToTopoWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.onusToTopoWin',
	requires: [
        'component.public.AjaxComboBox'
   	],
	constructor:function(condition){
		var me = this;
		me.oltData = condition.oltData;
		me.onuOlts = condition.onuOlts;
		me.splitterTreePanel(me.onuOlts);
		
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
			autoExpand : true,                	//是否全展开
			useArrows : true,
			checkable : false,
			readOnly : true,
			singleSelect : false,               //单选(Boolean) (级别比cascadeCheck高)
			onlyLeafCheckable : false,         	//是否只有叶子节点可选(Boolean)
			barAllCheck : false,				//全选按钮(Boolean)
			rootVisible : true,					//根节点可见
			rootId : me.oltData.symbolId,   	//根节点值
			rootText : me.oltData.symbolName1,	//根节点text
			iconable : true,					//是否显示图标(Boolean)
			rootIcon : '../common/topoImages/16x16/dlnms/splitter.png', //根节点图标URL
			sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadSplitterTree',  //获取数据的SQL名称(String)
			idKey : 'srcSymbolId',	     		//树节点查询子节点值关联KEY
			nodeId : 'symbolId',				//节点id KEY
			nodeName : 'symbolName1',			//节点text KEY
			nodeIcon : 'treeIconPath',			//节点icon KEY
			paramMap : {						//其他参数
				symbolId : me.oltData.symbolId
			}
		});
		me.splitterTreePanel.on('itemclick', function(view, node, item, index,e,eOpts){
			me.treeParentId = node.data.id;
			if(Ext.isEmpty(node.raw.attributes)){
				me.nodeId = me.oltData.nodeId;
			}else{
				var attributes = Ext.JSON.decode(node.raw.attributes);
				me.nodeId = attributes.nodeId;
			}
		});
		me.splitterTreePanel.on('itemdblclick', function(view, node, item, index,e,eOpts){
			me.treeParentId = node.data.id;
			if(Ext.isEmpty(node.raw.attributes)){
				me.nodeId = me.oltData.nodeId;
			}else{
				
				var attributes = Ext.JSON.decode(node.raw.attributes);
				me.nodeId = attributes.nodeId;
			}
			me.onusSave();
		});
	},
	onusSave : function(){
		var me = this;
		var lineData ={};
		lineData.srcSymbolId = me.nodeId;
		lineData.oltIds = me.onuOlts.oltIds;
		lineData.oltIndexs = me.onuOlts.oltIndexs;
		lineData.nodeIds = me.onuOlts.nodeIds;
		lineData.dIndexNodes =me.onuOlts.dIndexNodes;
		lineData.onuPonMacs = me.onuOlts.onuPonMacs;
		lineData.mapParentId = me.onuOlts.mapParentId;
		
		Ext.Ajax.request({
			url: webRoot+'/linkSymbol/addOnusOfOltLink.do',
			method : 'POST',
			params : lineData,
			success : function(resp, action) {
				var mapPanel = document.getElementById('frame_main');
				mapPanel.contentWindow.reLoadMapLayer(true,true);
				me.close();
				me.callback();
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
	