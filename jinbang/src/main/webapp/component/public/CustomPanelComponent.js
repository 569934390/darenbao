//panel容器
Ext.define('component.public.CustomPanelComponent', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.customPanel',
    require: ['component.public.CustomGridComponent','component.public.CustomGridEditorWindowComponent'],
    layout: 'border',
    init: function(){},
    config:{
    	grid:null
    },
    doSearch: function(){
    	var orgGrid = this.grid,values = this.searchPanel.getValues(),orgParams={};
    	/**
    	 * 修复where 与查询框共用一个条件时冲突bug  by吕衍辉 qq:569934930
    	 */
    	for(var key in orgGrid.store.proxy.extraParams.orgParams){
    		var _key = key.substring(0,key.length-2);
    		var flag = true;
    		for(var wkey in values){
    			if(wkey.indexOf(_key)!=-1){
	    			_wkey = wkey.substring(0,wkey.length-2);
	    			if(_key==_wkey){
	    				flag = false;
	    				break;
	    			}
    			}
    		}
    		if(flag)
    			orgParams.key = orgGrid.store.proxy.extraParams.orgParams.key;
    	}
    	orgGrid.store.proxy.extraParams.orgParams = orgParams;
    	Ext.apply(orgGrid.store.proxy.extraParams,{
			whereParams:Ext.encode(values)
		});
		orgGrid.store.load({
			params: {
				page: 1,
				start: 0,
				limit: this.pageSize
			}
		});
		orgGrid.store.loadPage(1);//需要翻到第一页
    },
    searchPanelReset: function() {
    	this.searchPanel.form.reset();
    },
	initSearchItems: function(searchCompAry, searchItemsAry) {
		var me = this;
		var factorStore = Ext.create('Ext.data.Store', {
		    fields: ['id', 'text'],
		    data: [
		        {'text':'='},
		        {'text':'>'},
		        {'text':'<'},
		        {'text':'>='},
		        {'text':'<='},
		        {'text':'<>'},
		        {'text':'like'},
		        {'text':'in'}
		    ]
		});		
		
		var searchComponents = me.searchComponents;
		searchComponents = me.searchComponents;//自定义查询组件
		for(var i = 0; i < searchItemsAry.length; i++) {
			var item = searchItemsAry[i];
			var obj = {};
			var searchStore = null;//下拉项
			var defineItem = null;
			var defOpt = "=";
			if(searchComponents != null) {
				for(var j = 0; j < searchComponents.length; j++) {
					if(searchComponents[j]['key'] == item['dataIndex']) {
						if(searchComponents[j]['store'] != null) {
							searchStore = searchComponents[j]['store'];
						} else if(searchComponents[j]['item'] != null) {
							defineItem = searchComponents[j]['item'];
						} else if(searchComponents[j]['opt'] != null) {
							defOpt = searchComponents[j]['opt'];
						}
					}
				}
			}
//			if(item['isSearchDef']) {
				var headerHtml = '<div style="line-height:22px;text-align:right;padding-right:4px;">'+item['header']+'</div>';
				var containerWidth = 250;
				var optWidth = 45;
				var textWidth = 100;
				var cw_1 = 0.35;
				var cw_2 = 0.65;
				var cw_3 = 0.2;
				var cw_4 = 0.45;
				var factorObj = {
					xtype: 'combo',
			    	mark: 'itemName',
			    	name: item['dataIndex'] + '_' + i,
				    store: factorStore,
				    queryMode: 'local',
				    displayField: 'text',
				    valueField: 'text',
				    value: defOpt,
				    width: optWidth
				};
				switch(parseInt(item['searchType'])) {
					case 0:
						if(searchStore != null) {
							obj = Ext.widget('container',{
								width: containerWidth,
								padding: '0 0 0 5',
								mark: 'searchItem',
//								id: 'item_' + item['dataIndex'],
							    layout: 'column',
							    defaults: {
									border: false
							    },
							    items: [{
							    	columnWidth: cw_1,
							    	html: headerHtml
							    },{
							    	hidden: true,
							    	items: [factorObj]
							    },{					    	
									columnWidth: cw_2,
							    	items: [{
								    	xtype: 'combo',
										name: item['dataIndex'] + '_VALUE_' + i,
									    store: searchStore,
									    queryMode: 'local',
									    displayField: 'text',
									    valueField: 'value'
									}]
							    }]
							});
						} else if(defineItem != null) {
							defineItem.name = item['dataIndex'] + '_VALUE_' + i;
							defineItem.width = textWidth + 49;
							obj = Ext.widget('container',{
								width: containerWidth,
								padding: '0 0 0 5',
								mark: 'searchItem',
//								id: 'item_' + item['dataIndex'],
							    layout: 'column',
//							    width: searchPanel.width,
							    defaults: {
									border: false
							    },
							    items: [{
							    	columnWidth: cw_1,
							    	html: headerHtml
							    },{
							    	hidden: true,
							    	items: [factorObj]
							    },{					    	
									columnWidth: cw_2,
							    	items: [defineItem]
							    }]
							});							
						} else {
							factorObj.value = "like";//默认公用
							obj = Ext.widget('container',{
								width: containerWidth,
								padding: '0 0 0 5',
								mark: 'searchItem',
//								id: 'item_' + item['dataIndex'],
							    layout: 'column',
		//					    width: searchPanel.width,
							    defaults: {
									border: false
							    },
							    items: [{
							    	columnWidth: cw_1,
							    	html: headerHtml
							    },{
									columnWidth: cw_3,
							    	items: [factorObj]
							    },{					    	
									columnWidth: cw_4,
							    	items: [{
			    						xtype: 'textfield',
										name: item['dataIndex'] + '_VALUE_' + i,
										mark: 'enterKey',
										width: textWidth,
										enableKeyEvents: true,
										listeners: {
								            specialkey: function(field,e){
								                if (e.getKey()==Ext.EventObject.ENTER){    
								                	me.doSearch();
								                }  
								            }
										}
									}]
							    }]
							});							
						}
						if(item['isSearchDef'] == "1") {
							obj.setVisible(true);
						} else {
							obj.setVisible(false);
						}
						searchCompAry.push(obj);
						break;
					case 1:
						obj = Ext.widget('container',{
							width: containerWidth,
							padding: '0 0 0 5',
							mark: 'searchItem',
//							id: 'item_' + item['dataIndex'],
						    layout: 'column',
	//					    width: searchPanel.width,
						    defaults: {
								border: false
						    },
						    items: [{
						    	columnWidth: 0.35,
						    	html: headerHtml
						    },{
								columnWidth: 0.2,
						    	items: [factorObj]
						    },{					    	
								columnWidth: 0.45,
						    	items: [{
		    						xtype: 'datefield',
		    						format: 'Y-m-d',
									name: item['dataIndex'] + '_VALUE_' + i,
									width: textWidth
								}]
						    }]
						});
						if(item['isSearchDef'] == "1") {
							obj.setVisible(true);
						} else {
							obj.setVisible(false);
						}
						searchCompAry.push(obj);
						break;
				}
//			}
		}		
		return searchCompAry;
	},
	setSearchInit: function(initAry) {
		var searchCmpsList = Ext.ComponentQuery.query('container[mark=searchItem]', this);
		var searchSelectData = this.grid.columnsSearchData;
		for(var j = 0; j < initAry.length; j++) {
			for(var i = 0; i < searchCmpsList.length; i++) {
				var nameStr = searchCmpsList[i].down('combo[mark=itemName]').name;
				var nameStr = nameStr.substr(0, nameStr.lastIndexOf('_'));
				if(initAry[j]['key'] == nameStr) {
					flag = !initAry[j]['hidden'];
					searchCmpsList[i].setVisible(flag);
				}
			}
			
			for(var i = 0; i < searchSelectData.length; i++) {
				if(initAry[j]['key'] == searchSelectData[i]['dataIndex']) {
					var val = '1';
					if(initAry[j]['hidden']) {
						val = '0';
					}
					searchSelectData[i]['isSearchDef'] = val;
				}				
			}
		}		
	},
	initSearchConfigItems: function(searchConfigCompAry, searchItemsAry) {
		for(var i = 0; i < searchItemsAry.length; i++) {
			var item = searchItemsAry[i];
			var obj = {
				name: item['dataIndex'],
				text: item['header']
			};
			searchConfigCompAry.push(obj);
		}
		return searchConfigCompAry;
	},
	initDisplayConfigItems: function(displayConfigAry, displayItemsAry) {
		for(var i = 1; i < displayItemsAry.length; i++) {//忽略序数列
			var item = displayItemsAry[i];
			var obj = {
				name: item['dataIndex'],
				text: item['header']
			};
			displayConfigAry.push(obj);
		}
		return displayConfigAry;
	},
    initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'panel',
			pos: 'tool',
			border: false,
			bodyPadding: 10,
			region: 'north',
	  		layout: { 
	 			type: 'hbox', 
	 	  	    align: 'middle', 
	  		    pack: 'start'
	  		},
	  		defaults:{
				width: 80,
				margins: '0 10 0 0'
			}
		},{
//			xtype: 'panel',
//			border: false,
//			region: 'center',
//			layout: 'border',
//			items: [{
//				xtype: 'panel',
//				region: 'north',
//				layout: 'border',
//				title: '查询面板',
//				collapsible: true,
//				defaults: {
//					border: false,
//					bodyPadding: 10
//				},
//				height: 95,
//				border: false,
//				items: [{
//					xtype: 'panel',
//					region: 'center',
//					layout: 'fit',
////					overflowY : 'auto',
//					items: [{
//						xtype: 'form',
//						border: false,
//						pos: 'search',
//						layout: 'column'
//					}]
//				},{
//					xtype: 'panel',
//					region: 'east',
//					layout: 'fit',
//					width: 147,
//					items: [{
//						xtype: 'form',
//						border: false,
//						pos: 'searchBtns'
//					}]
//				}]
//			},{
//				xtype: 'panel',
//				border: false,
//				pos: 'grid',
//				region: 'center',
//				layout: 'fit'
//			}]
			xtype: 'panel',
			border: false,
			region: 'center',
			layout: 'border',
			items: [{
				xtype: 'panel',
				region: 'north',
				layout: 'column',
				title: '查询面板',
				collapsible: true,
				defaults: {
					border: false,
					bodyPadding: 10
				},
				border: false,
				items: [{
					xtype: 'form',
					border: false,
					pos: 'search',
					layout: 'column',
					columnWidth: 3/4
				},{
					xtype: 'form',
					border: false,
					pos: 'searchBtns',
					columnWidth: 1/4
				}]
			},{
				xtype: 'panel',
				border: false,
				pos: 'grid',
				region: 'center',
				layout: 'fit'
			}]
		}];		
		
		var orgGridCfg = {
			aliasName: me.aliasName,
			pageSize: me.pageSize,
			displayFunctions: me.displayFunctions,
			where: me.where,
			order: me.order,
			displayInit: me.displayInit,
			searchInit: me.searchInit,
			connector: me.connector,
			fixedWidth: me.fixedWidth
		};
		
		//是否固定列宽
		if(me.fixedWidth == null || me.fixedWidth.fixed != true) {
			orgGridCfg.forceFit = true;
		}
		
		for(key in me.gridCfg) {
			eval('orgGridCfg.'+key+'=me.gridCfg.'+key+";");
		}
		
		var orgGrid = Ext.create('component.public.CustomGridComponent',orgGridCfg);
		orgGrid.gridLoad(function(){//回调
			//基础panel
			var gridPanel = Ext.ComponentQuery.query('panel[pos=grid]',me)[0];
			var searchPanel = Ext.ComponentQuery.query('form[pos=search]',me)[0];
			var searchBtnsPanel = Ext.ComponentQuery.query('form[pos=searchBtns]',me)[0];
			
			//查询form构成
			var searchItemsAry = orgGrid.columnsSearchData;
			var searchCompAry = new Array();
			var searchConfigCompAry = new Array();
			searchCompAry = me.initSearchItems(searchCompAry, searchItemsAry);
			searchConfigCompAry = me.initSearchConfigItems(searchConfigCompAry, searchItemsAry);
			
			//显示列配置
			var displayItemsAry = orgGrid.columnsShowData;
			var displayConfigAry = new Array();
			displayConfigAry = me.initDisplayConfigItems(displayConfigAry, displayItemsAry);
			
			//配置查询配置列grid
			var searchConfigStore = Ext.create('Ext.data.Store', {
			    fields:['name', 'text'],
			    data:{'items': searchConfigCompAry},
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
			
			var searchConfigGrid = Ext.create('Ext.grid.Panel', {
				multiSelect: true,
			    selModel: {
			    	selType:'checkboxmodel'
				},
			    store: searchConfigStore,
			    columns: [
			        { text: '键值',  dataIndex: 'name', hidden: true },
			        { text: '字段名', dataIndex: 'text' ,width: '100%'}
			    ]
			});
			
			//查询配置弹窗
			var win = Ext.create('component.public.CustomGridEditorWindowComponent',{
				width: 600,
				height: 400,
				listeners: {
					afterrender: function() {
						for(var i = 0; i < searchItemsAry.length; i++) {
							var item = searchItemsAry[i];
							if(item['isSearchDef'] == '1') {
								searchConfigGrid.getSelectionModel().select(i, true, false);
							} else {
								searchConfigGrid.getSelectionModel().deselect(i);
							}
						}
					},
					beforehide: function() {
						var store = searchConfigGrid.store;
						store.each(function(record) {//不够简洁
							for(var i = 0; i < searchItemsAry.length; i++) {
								var item = searchItemsAry[i];
								if(item['dataIndex'] == record.data['name']) {
									if(item['isSearchDef'] == '1') {
										searchConfigGrid.getSelectionModel().select(record, true, false);
									} else {
										searchConfigGrid.getSelectionModel().deselect(record);
									}
									break;
								}
							}
						});
					}
				}
			});
			win.add({
				xtype: 'form',
				pos: 'searchConfig',
				overflowY: 'auto',
				border: false,
				title: '查询项配置',
				bodyPadding: 20,
				bbar : {
					xtype: 'container',
					pos: 'bottom',
					height : 40,
					defaults: {
						margins: '0 5 0 0'
					},
			  		layout: { 
			 			type: 'hbox', 
			 	  	    align: 'middle', 
			  		    pack: 'end'
			  		}
				}
			});
			var searchConfigPanel = win.down('form');

			
			//配置显示列grid
			var displayCmStore = Ext.create('Ext.data.Store', {
			    fields:['name', 'text'],
			    data:{'items': displayConfigAry},
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
			
			var displayCmGrid = Ext.create('Ext.grid.Panel', {
				multiSelect: true,
			    selModel: {
			    	selType:'checkboxmodel'
				},
			    store: displayCmStore,
			    columns: [
			        { text: '键值',  dataIndex: 'name', hidden: true },
			        { text: '字段名', dataIndex: 'text' ,width: '100%'}
			    ]
			});
			
			//显示配置弹窗
			var displayWin = Ext.create('component.public.CustomGridEditorWindowComponent',{
				width: 600,
				height: 400,
				listeners: {
					afterrender: function() {
						var displayCmAry = Ext.ComponentQuery.query('gridcolumn[mark=displaycm]',orgGrid);
						for(var i = 0; i < displayCmAry.length; i++) {
							var item = displayCmAry[i];
							if(!item.hidden) {
								displayCmGrid.getSelectionModel().select(i, true, false);
							} else {
								displayCmGrid.getSelectionModel().deselect(i);
							}
						}
					},
					beforehide: function() {
						var displayCmAry = Ext.ComponentQuery.query('gridcolumn[mark=displaycm]',orgGrid);
						var store = displayCmGrid.store;
						store.each(function(record) {//不够简洁
							for(var i = 0; i < displayCmAry.length; i++) {
								var item = displayCmAry[i];
								if(item['dataIndex'] == record.data['name']) {
									if(!item.hidden) {
										displayCmGrid.getSelectionModel().select(record, true, false);
									} else {
										displayCmGrid.getSelectionModel().deselect(record);
									}
									break;
								}
							}
						});
					}
				}
			});
			displayWin.add({
				xtype: 'form',
				pos: 'displayConfig',
				overflowY: 'auto',
				border: false,
				title: '显示项配置',
				bodyPadding: 20,
				bbar : {
					xtype: 'container',
					pos: 'bottom',
					height : 40,
					defaults: {
						margins: '0 5 0 0'
					},
			  		layout: { 
			 			type: 'hbox', 
			 	  	    align: 'middle', 
			  		    pack: 'end'
			  		}
				}
			});
			var displayPanel = displayWin.down('form');
			
			//组件添加
			gridPanel.add(orgGrid);
			//查询
			searchPanel.add(searchCompAry);
			var searchBtnsPanelItems = Ext.widget('panel',{
				border: false,
				defaults: {
//					border: false,
				},
				items: [{
					xtype: 'container',
				    layout: {
				        type: 'hbox',
				        pack: 'end',//水平
				        align: 'middle'//垂直
				    },
				    defaults: {
				    	margins: '0 0 0 5',
				    	width: 60
				    },
					items: [{
						xtype: 'button',
						text: '查询',
						iconCls: 'toolbar-search',
						handler: function(){
							me.doSearch();
						}
					},{
						xtype: 'button',
						text: '重置',
						iconCls: 'toolbar-reLoad',
						handler: me.searchPanelReset.createDelegate(me,[],0)
					}]
				},{
					xtype: 'container',
					padding: '5 0 0 0',
				    layout: {
				        type: 'hbox',
				        pack: 'end',//水平
				        align: 'middle'//垂直
				    },
					items: [{
						xtype: 'button',
						text: '查询项配置',
						iconCls: 'toolbar-module',
						width: 125,
						handler: function(){
							win.show();
						}
					}]
				}]
			});
			searchBtnsPanel.add(searchBtnsPanelItems);
			searchConfigPanel.add(searchConfigGrid);
			searchConfigPanel.down('container[pos=bottom]').add([{
				xtype: 'button',
				text: '应用',
				handler: function(){
					var selectedAry = searchConfigGrid.getSelectionModel().getSelection();
					var searchCmpsList = Ext.ComponentQuery.query('container[mark=searchItem]', searchPanel);
					for(var i = 0; i < searchItemsAry.length; i++) {
						var check = '0';
						for(var j = 0; j < selectedAry.length; j++) {
							if(selectedAry[j].data['name'] == searchItemsAry[i]['dataIndex']) {
								check = '1';
							}
							//break;
						}
						searchItemsAry[i]['isSearchDef'] = check;
					}
					for(var i = 0; i < searchCmpsList.length; i++) {
						var flag = false;
						for(var j = 0; j < selectedAry.length; j++) {
							var nameStr = searchCmpsList[i].down('combo[mark=itemName]').name;
							var nameStr = nameStr.substr(0, nameStr.lastIndexOf('_'));
							if(selectedAry[j].data['name'] == nameStr) {
								flag = true;
							}
						}
						searchCmpsList[i].setVisible(flag);
					}
					//排序
					var newCmpsAry = [];
					var newItemsAry = [];
					var store = searchConfigGrid.store;
					store.each(function(record) {
						for(var i = 0; i < searchItemsAry.length; i++) {
							if(record.data['name'] == searchItemsAry[i]['dataIndex']) {
								newItemsAry.push(searchItemsAry[i]);
								break;
							}
						}
					});
					newCmpsAry = me.initSearchItems(newCmpsAry, newItemsAry);
					orgGrid.columnsSearchData = newItemsAry;
					searchPanel.removeAll(true);
					searchPanel.add(newCmpsAry);
					Ext.Msg.alert('消息', '应用成功');
					win.hide();
				}
			},{
				xtype: 'button',
				text: '全选',
				handler: function(){
					searchConfigGrid.getSelectionModel().selectAll();
				}
			},{
				xtype: 'button',
				text: '反选',
				handler: function(){
					var store = searchConfigGrid.store;
					var model = searchConfigGrid.getSelectionModel();
					store.each(function(record) {
						var index = store.indexOf(record);
						if(model.isSelected(record)) {
							model.deselect(index);
						} else {
							model.select(index, true, false);
						}
					});
				}
			},{
				xtype: 'button',
				text: '上移',
				handler: function() {
					moveGridColumn(searchConfigGrid, '+');
				}
			},{
				xtype: 'button',
				text: '下移',
				handler: function() {
					moveGridColumn(searchConfigGrid, '-');
				}
			},{
				xtype: 'button',
				text: '关闭',
				handler: function() {
					win.hide();
				}
			}]);
			
			//显示
			displayPanel.add(displayCmGrid);
			displayPanel.down('container[pos=bottom]').add([{
				xtype: 'button',
				text: '应用',
				handler: function() {
					//排序
					var newDisplayCmAry = new Array();
					newDisplayCmAry.push({
						xtype: 'rownumberer',
						width: 40,
						mark: 'rowheader',
						text: '<列>'
					});
					var store = displayCmGrid.store;
					
					store.each(function(record) {
						for(var i = 0; i < displayItemsAry.length; i++) {//使用原始数据
							if(record.data['name'] == displayItemsAry[i]['dataIndex']) {
								newDisplayCmAry.push(displayItemsAry[i]);
								break;
							}
						}
					});
					orgGrid.reconfigure(orgGrid.store, newDisplayCmAry);

					//显示(需要先排序，并且显示会有异常)
					var displayCmAry = Ext.ComponentQuery.query('gridcolumn[mark=displaycm]',orgGrid);
					var selectedAry = displayCmGrid.getSelectionModel().getSelection();
					for(var i = 0; i < displayCmAry.length; i++) {
						var flag = false;
						for(var j = 0; j < selectedAry.length; j++) {
							if(selectedAry[j].data['name'] == displayCmAry[i]['dataIndex']) {
								flag = true;
							}
						}
						displayCmAry[i].setVisible(flag);
					}
					
					Ext.Msg.alert('消息', '应用成功');
					displayWin.hide();
				}
			},{
				xtype: 'button',
				text: '全选',
				handler: function() {
					displayCmGrid.getSelectionModel().selectAll();
				}
			},{
				xtype: 'button',
				text: '反选',
				handler: function() {
					var store = displayCmGrid.store;
					var model = displayCmGrid.getSelectionModel();
					store.each(function(record) {
						var index = store.indexOf(record);
						if(model.isSelected(record)) {
							model.deselect(index);
						} else {
							model.select(index, true, false);
						}
					});
				}
			},{
				xtype: 'button',
				text: '上移',
				handler: function() {
					moveGridColumn(displayCmGrid, '+');
				}
			},{
				xtype: 'button',
				text: '下移',
				handler: function() {
					moveGridColumn(displayCmGrid, '-');
				}
			},{
				xtype: 'button',
				text: '关闭',
				handler: function() {
					displayWin.hide();
				}
			}]);
			
			//绑定第一列header点击事件
			orgGrid.headerCt.on("headerclick", function(ct,column,e,t,opts) {
 				if(column.mark == "rowheader") {
 					displayWin.show();
 				}
   	        });
			
			//附加panel
			var toolPanel = Ext.ComponentQuery.query('panel[pos=tool]',me)[0];
			var searchContainerPanel = searchPanel.up('panel');
//			var searchContainerPanel = searchPanel.up('panel').up('panel');
			var centerContainerPanel = searchContainerPanel.up('panel');
			
			if(me.searchPanel == false) {
				centerContainerPanel.remove(searchContainerPanel);
			}
			
			//布局完成后调用
			me.grid = orgGrid;
			me.searchPanel = searchPanel;
			me.init(me, toolPanel, orgGrid);
			if(toolPanel.items.length == 0) {
				me.remove(toolPanel);
			}
		});
		me.setGrid(me.grid);
		me.callParent();
    }
});


function moveGridColumn(grid, opt) {
	var model = grid.getSelectionModel();
	if(model.hasSelection()) {
		var store = grid.getStore();
		var rows = model.getSelection();
		var rowIndex;
		var indexAry = new Array();
		var selectAry = new Array();
		var moveFlag = true;		
		for(var i=0;i<rows.length;i++) {
			rowIndex = store.indexOf(rows[i]);
			indexAry.push(rowIndex);
		}
		indexAry.sort(function(a,b){return a>b?1:-1});		
		
		if(opt == '+'){
			for(var i=0;i<indexAry.length;i++) {
				rowIndex = indexAry[i];
				if(rowIndex == 0) {
					moveFlag = false;
					selectAry.push(rowIndex);
				}
				if(!moveFlag && rowIndex != 0) {
					if(rowIndex != indexAry[i-1]+1) {
						moveFlag = true;
					} else {
						selectAry.push(rowIndex);
					}
				}
				if(moveFlag) {
					store.insert(rowIndex - 1, store.getAt(rowIndex));   
					selectAry.push(rowIndex - 1);
				}
			}
		}
		if(opt == '-'){
			for(var i=indexAry.length-1;i>=0;i--) {
				rowIndex = indexAry[i];
				if(rowIndex == store.getCount()-1) {
					moveFlag = false;
					selectAry.push(rowIndex);
				}
				if(!moveFlag && rowIndex != store.getCount()-1) {
					if(rowIndex != indexAry[i+1]-1) {
						moveFlag = true;
					} else {
						selectAry.push(rowIndex);
					}
				}
				if(moveFlag) {
					store.insert(rowIndex + 2, store.getAt(rowIndex));//为什么+2？
					selectAry.push(rowIndex + 2);
				}
			}
		}
		grid.getView().refresh();
	}
}