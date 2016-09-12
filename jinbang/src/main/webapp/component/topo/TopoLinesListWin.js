/**
 * 物理链路列表
 */
Ext.define('component.topo.TopoLinesListWin',{
	extend : 'Ext.window.Window',
	alias : 'widget.topoLinesListWin',
	constructor:function(condition){
		var me = this;
		me.createLogicGridPanel();
		me.createPhyLineGridPanel();
		me.createLineCombo();
		me.createLineForm();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.lineForm,me.logicGrid,me.phyLineGrid]
		});
		me.callParent([config]);
	},
	createLineForm : function(){
		var me = this;
		
		var lineSet = new Ext.form.FieldSet({
			title : '',
			border : false,
	        bodyPadding: 0,
			layout : 'column',
			items : [{
				columnWidth :.3,
				border : false,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel:'链路名称',
					name:'linkName',
					labelWidth : 60,
					labelAlign : 'right',
					width : 200
				}]
			}]
		});
		me.lineForm = Ext.create('Ext.form.Panel', {
			region : 'north',
			title: '查询面板',
			border : true,
			deferredRender : false,
	        bodyPadding: 0,
			autoScroll : true,
            collapsible: true,
			split : true,
			height : 85,
			layoutOnTabChange : true,
			items : [lineSet],
		    tbar : new Ext.Toolbar({
				items : [me.lineCombo,{xtype : 'tbfill'},{
					xtype:'button',text:'查询',tooltip:'查询',iconCls:'toolbar-search',
					handler : me.buttonEvent.createDelegate(me,['gridSearch'],0)
//				},{
//					xtype:'button',text:'导出',tooltip:'导出',iconCls:'toolbar-export',
//					handler : me.buttonEvent.createDelegate(me,['gridExport'],0)
				}]
		    }),
		});
	},
	createLogicGridPanel : function(){
		var me = this;
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/linkSymbol/findLogicLinkPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
	        fields: [
	             'linkSymbolId', 'linkName1', 'srcSymbolName', 'desSymbolName','direction','remark'
	        ],
		    pageSize: 20,
	        listeners:{
				beforeload:function() {
					store.proxy.url = webRoot+'/linkSymbol/findLogicLinkPage.do';
				}
			}
		});
		store.load({params:{start:0, limit:20}});

		me.logicGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
		    store: store,
		    forceFit : true,
			multiSelect: true,
			hidden : true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    columns: [{ 
		    	header: "链路ID",dataIndex: 'linkSymbolId',hidden:true,sortable: true
		    },{
		    	header: "链路名称",dataIndex: 'linkName1', sortable: true,
		    	renderer:function(value, meta, record) {
	        		if(Ext.isEmpty(value)) {
	        			return 'N/A';
	        		}else{
	        			return value;
	        		}
	            }
		    },{
		    	header: "源端节点",dataIndex: 'srcSymbolName', sortable: true
		    },{ 
		    	header: "宿端节点",dataIndex: 'desSymbolName', sortable: true
		    },{ 
		    	header: "链路方向",dataIndex: 'direction', sortable: true
	        },{
				header: "链路描述",dataIndex:'remark',hidden:true
			}],
			bbar : new Ext.PagingToolbar({
	            pageSize: 20,
				displayMsg : 'Displaying{0} - {1} of {2}',
	            store: store,
	            displayInfo: true
	        })
		});
	},
	createPhyLineGridPanel : function(){
		var me = this;
		var store = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + '/linkSymbol/findPhyLinkPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        }
		    },
	        fields: [
	             'linkId','friendlyName','srcSymbolName', 'srcIpaddress', 'srcPortIndex','srcMacValue',
	             'desSymbolName','desIpaddress','desPortIndex','desMacValue'
	        ],
		    pageSize: 20,
	        listeners:{
				beforeload:function() {
					store.proxy.url = webRoot+'/linkSymbol/findPhyLinkPage.do';//?result='+resultTypeShow;
				}
			}
		});
		store.load({params:{start:0, limit:20}});

		me.phyLineGrid = Ext.create('Ext.grid.Panel', {
			region : 'center',
			store: store,
		    forceFit : true,
			multiSelect: true,
		    selModel: {
		    	selType:'checkboxmodel'
			},
		    columns: [{ 
		    	header: "链路ID",dataIndex: 'linkId',hidden:true,sortable: true
		    },{
		    	header: "链路名称",dataIndex: 'friendlyName', sortable: true
		    },{
		    	header: "源端网元",dataIndex: 'srcSymbolName', sortable: true
		    },{
		    	header: "源端IP",dataIndex: 'srcIpaddress', sortable: true
		    },{
		    	header: "源端端口索引",dataIndex: 'srcPortIndex', sortable: true
		    },{
		    	header: "源端MAC地址",dataIndex: 'srcMacValue', sortable: true
		    },{ 
		    	header: "宿端网元",dataIndex: 'desSymbolName', sortable: true
		    },{ 
		    	header: "宿端IP",dataIndex: 'desIpaddress', sortable: true
		    },{
		    	header: "宿端端口索引",dataIndex: 'desPortIndex', sortable: true
		    },{ 
		    	header: "宿端MAC地址",dataIndex: 'desMacValue', sortable: true
			}],
			bbar : new Ext.PagingToolbar({
	            pageSize: 20,
				displayMsg : 'Displaying{0} - {1} of {2}',
	            store: store,
	            displayInfo: true
	        })
		});
	},
	createLineCombo : function(){
		var me = this;
		
		var states = Ext.create('Ext.data.Store', {
		    fields: ['abbr', 'name'],
		    data : [{"abbr":"1", "name":"逻辑链路"},
		            {"abbr":"2", "name":"物理链路"}]
		});
		
		me.lineCombo = Ext.create('Ext.form.ComboBox', {
			emptyText : "请选择",name : 'lineStyle',fieldLabel : "链路类型",
			labelWidth : 68,width:160,labelAlign : 'right',value : '2',
		    store: states,
		    queryMode: 'local',
		    displayField: 'name',
		    editable : false,
		    valueField: 'abbr',
			listeners:{
				change:function(combo,val) {
	    	   		if(val==1) {
						if(!Ext.isEmpty(me.phyLineGrid)){
							me.phyLineGrid.hide();
						}
						me.logicGrid.show();
	    	   		}else{
						if(!Ext.isEmpty(me.logicGrid)){
							me.logicGrid.hide();
						}
						me.phyLineGrid.show();
	    	   		}
	    	   		me.doLayout();
	       		}
			}
		});
	},
	gridSearch : function(){
		var me = this;
		var form = me.lineForm.getForm().getValues();
		var params = {};
		params.start=0;
		params.limit=20;
		if(!Ext.isEmpty(form.linkName)){
			params.linkName1 = "%"+form.linkName+"%";
			params.friendlyName = "%"+form.linkName+"%";
		}
		if(form.lineStyle=="1"){
			me.logicGrid.getStore().load({params:params});
		}else{
			me.phyLineGrid.getStore().load({params:params});
		}
	},
	gridExport : function(){
		var me = this;
		var form = me.lineForm.getForm().getValues();
		if(form.lineStyle=="1"){
			me.grid = me.logicGrid;
		}else{
			me.grid = me.phyLineGrid;
		}
		var length = me.grid.getSelectionModel().getCount();
		if(length <1){
			Ext.Msg.confirm('提示','未选择记录，是否导出全部!',function(btn){
				if(btn=="yes"){
					
				}
			});
		}else{
			var records = me.grid.getSelectionModel().getSelection();
			var lineIds = "";
			for(var i = 0;i<records.length;i++) {
				lineIds = lineIds + ","+records[i].data['linkId'];
		    }
//			console.info(lineIds);
//			window.open(webRoot + '/linkSymbol/exportLinkGrid.do?lineIds='+lineIds);
		}
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'gridSearch':
				me.gridSearch();
				break;
			case 'gridExport':
				me.gridExport();
				break;
		}
	}
});