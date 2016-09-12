/**
 * 拓扑定位
 */
Ext.define('component.topo.TopoMapLocateWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.topoMapLocateWin',
	requires:[
	     'component.public.TreeCombo',
	     'component.public.IpField'
	],
	constructor:function(condition){
		var me = this;

		me.createTopoMapLocateForm();
		me.createTopoMapLocateGrid();
		
		var config = Ext.applyIf(condition,{
			border : false,
			layout : 'border',
			items:[me.topoMapLocateForm,me.topoMapLocateGrid],
			listeners : {
				show: function(src,arg){
					me.query('textfield[name=symbolName1]')[0].focus();
				}
			}
		});
		me.callParent([config]);
	},
	createTopoMapLocateForm : function() {
		var me = this;
		var searchSet = new Ext.form.FieldSet({
			title : '查找条件',
			border : true,
			frame : false,
			layout : 'column',
			items : [{
				columnWidth : .85,
			    bodyPadding: '0 20 0 0',
				layout : 'form',
				border : false,
				frame : false,
				items : [{
					xtype : 'textfield',
					emptyText : "请输入...",
					fieldLabel:'节点名称',
					name:'symbolName1',
					labelWidth : 60,
					labelAlign : 'right',
		            enableKeyEvents : true,
					listeners : {
						keyup: function(src,e){
							if (e.getKey() == Ext.EventObject.ENTER) {
								me.topoSearch();
							}
						}
					}
				},{
					xtype : 'textfield',
					emptyText : "请输入...",
					fieldLabel:'IP地址',
					name:'ipaddress',
					labelWidth : 60,
					labelAlign : 'right',
					enableKeyEvents : true,
					listeners : {
						keyup: function(src,e){
							if (e.getKey() == Ext.EventObject.ENTER) {
								me.topoSearch();
							}
						}
					}
//				},{
//					xtype:'ipField',
//					fieldLabel:'IP地址',
//					id:'ipaddress',
//					name:'ipaddress',
//					labelWidth : 60,
//					labelAlign : 'right',
//					width : 380
				}]
			},{
				columnWidth : .15,
				border : false,
				frame : false,
				layout : 'form',
				items : [{
					xtype : "button",
					text : '查找',
					buttonAlgin : 'right',
					width : 70,
					handler:me.buttonEvent.createDelegate(me,['topoSearch'],0)
				},{
					xtype : "button",
					text : '取消',
					buttonAlgin : 'right',
					width : 70,
					handler : function(){
						topoMapLocateWin.close();
					}
				}]
			}]
		});

		var searchConSet = new Ext.form.FieldSet({
			title : '查找内容',
			border : true,
			frame : false,
			autoHeight : true,
			layout : 'column',
			items : [{
				columnWidth :.4,
				layout : 'form',
				border : false,
				frame : false,
			    bodyPadding: '0 0 0 100',
				items : [{
					xtype : 'radio',
					labelWidth : 100,
					boxLabel:'全匹配',
					width : 160,
					id:'searchTypeA',
					name:'searchType',
					inputValue : '1'
				}]
			},{
				columnWidth :.45,
				layout : 'form',
				border : false,
				frame : false,
			    bodyPadding: '0 0 0 50',
				items : [{
					xtype : 'radio',
					boxLabel:'包含',
					width : 200,
					id:'searchTypeC',
					name:'searchType',
					checked : true,
					inputValue : '2'
				}]
			}]
		});

		topoMapLocateForm = new Ext.form.FormPanel({
	        region: 'north',
			labelAlign : 'right',
			border : false,
			frame : false,
			height : 130,
			activeTab : 0,
			defaults : {
				autoScroll : true
			},
			layoutOnTabChange : true,
			items : [searchSet,searchConSet]
		});
		me.topoMapLocateForm = topoMapLocateForm;
	},
	createTopoMapLocateGrid : function(){
		var me = this;
		var symbolStore = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
				url : webRoot + '/topoSymbol/findTopoSymbolPage.do',
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty : 'totalCount'
		        },
				actionMethods: 'POST'
		    },
			fields : ['symbolId','treeParentId','mapParentId','symbolName1','ipaddress','topoTypeDisplayName','mapParentName','parentPath','remark'],
		    pageSize: 20
		});
		symbolStore.load({params:{start : 0,limit : 20}});

		topoMapLocateGrid = new Ext.grid.GridPanel({
	        region: 'center',
			store : symbolStore,
			columns : [ {
				header : "节点名称",dataIndex : 'symbolName1'
			}, {
				header : "IP地址",dataIndex : 'ipaddress'
			}, {
				header : "节点类型",dataIndex : 'topoTypeDisplayName'
			}, {
				header : "父节点树",dataIndex : 'mapParentName'
			}, {
				header : "备注",dataIndex : 'remark'
			} ],
			forceFit : true,
			bbar : new Ext.PagingToolbar({
				pageSize : 20,
				store : symbolStore,
				displayInfo : true,
				displayMsg : 'Displaying{0} - {1} of {2}',
				emptyMsg : "No topics to display"
			})
		});
		me.topoMapLocateGrid = topoMapLocateGrid;

		// 给控件添加右键菜单触发事件(rowcontextmenu)
		topoMapLocateGrid.on("itemcontextmenu",function locate_itemcontextmenu(gd, record, item, index, e, eOpts ){
			//禁用浏览器的右键相应事件
			e.preventDefault();
			e.stopEvent();
			//禁用浏览器的右键相应事件
			
			var menus = Ext.create('Ext.menu.Menu', {
				items: [{
					text:"拓扑定位",iconCls:"toolbar-location",
					handler:me.buttonEvent.createDelegate(me,['topoMapLocate'],0)
				}]
			});
			menus.showAt(e.getXY());
		});
		topoMapLocateGrid.on("itemdblclick",function itemdblclick(gd, record, item, index, e, eOpts){
			me.topoMapLocate();
		});
	},
	topoMapLocate : function(){
		var me = this;
		var selected = me.topoMapLocateGrid.getSelectionModel().getSelection();
		var symbolId = selected[0].data['symbolId'];
		var parentPath = selected[0].data['parentPath'];
		var mapParentId = selected[0].data['mapParentId'];
		var mapParentName = selected[0].data['mapParentName'];
		var params ={};
		params.nodeId = symbolId;
		params.parentPath = parentPath;
		topoTreePanel.expandParentPath(params,function(node){
			/**
			 * topoMap.js
			 */
			topoClickLocate(node);
			me.hide();
		});
	},
	topoSearch:function(){
		var me = this;
		var form = me.topoMapLocateForm.getForm().getValues();
		
		var params ={};
		params.limit = 25;
		if(form.searchType==2){
			if(!Ext.isEmpty(form.symbolName1)){
				params.symbolName1 = '%'+form.symbolName1+'%';
			}
			if(!Ext.isEmpty(form.ipaddress)){
				params.ipaddress = '%'+form.ipaddress+'%';
			}
		}else{
			if(!Ext.isEmpty(form.symbolName1)){
				params.symbolName1 = form.symbolName1;
			}
			if(!Ext.isEmpty(form.ipaddress)){
				params.ipaddress = form.ipaddress;
			}
		}
		me.topoMapLocateGrid.getStore().load({params:params});
	},
	buttonEvent:function(oper){
		var me = this;
		switch(oper){
			case 'topoSearch':
				me.topoSearch();
				break;
			case 'topoMapLocate':
				me.topoMapLocate();
				break;
			case 'locateDetail':
				if(me.topoMapLocateGrid.hidden){
					me.topoMapLocateGrid.show();
				}else{
					me.topoMapLocateGrid.hide();
				}
				break;
		}
	}
});