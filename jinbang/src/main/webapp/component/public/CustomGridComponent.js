//grid
Ext.define('component.public.CustomGridComponent', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.customGrid',
//	forceFit: true,
	columns: [],
    selModel: {
    	selType:'checkboxmodel'
	},
//	selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
//	plugins : [Ext.create("component.public.GridCheckPlugin")],
	bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true,
        layout: 'hbox'
	},
	setGridPage: function(newSize) {
		Ext.apply(this.store, {
			pageSize: newSize
		});
		this.store.loadPage(1);
	},
	setContextMenu: function(menuCfg) {
		this.on("itemcontextmenu",function(gd, record, item, index, e, eOpts ){
   	 		//禁用浏览器的右键相应事件
   	 		e.preventDefault();
   	 		e.stopEvent();
   	 		//禁用浏览器的右键相应事件
   	 		var flag = true;
   	 		if(this.getSelectionModel().getSelection().length > 1) {
   	 			flag = false;
   	 		}
   	 		var menus = Ext.create('Ext.menu.Menu');
   	 		if(menuCfg.view && flag) {
   	 			menus.add({
   	 				text : "查看详情",
   	 				iconCls : 'toolbar-view',
	   	  	    	handler: menuCfg.view
   	 	        });
   	 		}
   	 		if(menuCfg.edit && flag) {
   	 			menus.add({
	   	 			text: '修改',
	   	 			iconCls : 'toolbar-edit',
   	 				handler: menuCfg.edit
   	 	        });
   	 		}  	 		
   	 		if(menuCfg.add && flag) {
   	 			menus.add({
	   	 			text: '新增',
	   	 			iconCls : 'toolbar-add',
	 				handler: menuCfg.add
   	 	        });
   	 		}
   	 		if(menuCfg.del) {
   	 			menus.add({
	   	 			text: '删除',
	   	 			iconCls : 'toolbar-delete',
   	 				handler: menuCfg.del
   	 	        });   	 			
   	 		}
   	 		if(menuCfg.custom) {
//   	 			for(var key in menuCfg.custom){
//   	 				menuCfg.custom[key].handler = Ext.bind(menuCfg.custom[key].handler,gd,[arguments],true)
//   	 			}
 				menus.add(menuCfg.custom);
   	 		}
   	 	    menus.showAt(e.getXY());
   	 	});
	},
	setDisplayInit: function(initAry) {
		var displayCmAry = Ext.ComponentQuery.query('gridcolumn[mark=displaycm]',this);
		for(var j = 0; j < initAry.length; j++) {
			for(var i = 0; i < displayCmAry.length; i++) {
				if(initAry[j]['key'] == displayCmAry[i]['dataIndex']) {
					flag = !initAry[j]['hidden'];
					displayCmAry[i].setVisible(flag);
				}
			}
		}
	},
	customLoad: function() {
		var me = this;
//		var pageSize = me.pageSize;
		var paramsObj = {
//			start: 0,
//			limit: pageSize
		};
		
		//解析页面链接带的参数
		var urlPath = window.location.href;
		if(urlPath.indexOf("?") != -1) {
			var urlParams = Ext.urlDecode(urlPath.substr(urlPath.indexOf("?")+1));
			var urlParamsAry = [];
			for(key in urlParams) {//无需验证字段是否存在表中
				var obj = {key:key.toUpperCase(),opt:"=",value:urlParams[key]};
				urlParamsAry.push(obj);
			}
			if(urlParamsAry.length > 0) {
				if(me.where && me.where.length > 0) {
					for(var i = 0; i < urlParamsAry.length; i++) {
						var exist = false;
						for(var j = 0; j < me.where.length; j++) {
							if(urlParamsAry[i].key == me.where[j].key) {
								exist = true;
							} 
						}
						if(!exist) {
							me.where.push(urlParamsAry[i]);
						}
					}
				} else {
					me.where = urlParamsAry;
				}
			}
		}
		//结束
		
		if(me.where != null) {
			var orgParam = me.where;
			var tempParams = [];
			var tempPostParams = {};
			for(var i = 0; i < orgParam.length; i++) {
				if(typeof(orgParam[i]['key']) != 'undefined') {
					tempParams.push(orgParam[i]);
				}
			}
			for(var i = 0; i < tempParams.length; i++) {
				var name = tempParams[i]['key'];
				var opt = tempParams[i]['opt'];
				var value = tempParams[i]['value'];
				tempPostParams[name + '_' + i] = opt;
				tempPostParams[name + '_VALUE_' + i] = value;
			}
			paramsObj.orgParams = Ext.encode(tempPostParams);
		}
		if(me.order != null) {
			var tempParams = me.order;
			var tempPostParams = '';
			for(var i = 0; i < tempParams.length; i++) {
				tempPostParams += ',' + tempParams[i]['key'] + ' ' + tempParams[i]['dir'];
			}
			paramsObj.orderParams = tempPostParams.substr(1);
		}
		if(me.connector != null) {
			paramsObj.connector = me.connector;
		}
		Ext.apply(me.store.proxy.extraParams,paramsObj);
		me.store.load();
	},
	gridLoad: function(cbFunction) {	
		var me = this;
		var aliasName = me.aliasName;
		var pageSize = me.pageSize;
		var displayFunctions = me.displayFunctions;
		var confStore = new Ext.data.JsonStore({
		    proxy: {
		        type: 'ajax',
		        url: webRoot + 'loadCustomGridDataByAlias.do?alias=' + aliasName,
		        reader: {
		            type: 'json',
		            root: 'colunmsData'//保留字段colunms无法使用
		        }
		    },
		   	fields: [
		   		'COLUMN_ID',
		   		'COLUMN_INDEX',
		   		'COLUMN_HEADER',
		   		'RESOURCE_ID',
		   		'IS_SHOW',
		   		'IS_SHOW_DEF',
		   		'IS_SEARCH',
		   		'IS_SEARCH_DEF',
		   		'FIELD_TYPE',
		   		'SEARCH_TYPE',
		   		'TABLE_SHORT_FORM',
		   		'SORT_ID',
		   		'COLUMN_WIDTH'
		   	]
		});
		var pagingbar = Ext.ComponentQuery.query('pagingtoolbar',me)[0];
		if(!pagingbar.down('combo')) {
			pagingbar.add([{
//	        	xtype: 'textfield',
//	        	mark: 'setPage',
//	        	width: 40
	 	    	xtype: 'combo',
	 	    	mark: 'setPage',
	 	    	width: 50,
	   			store: new Ext.data.ArrayStore({
	   	 			fields: ['value', 'label'],
		   	 		data: [
	   	 		       ['25','25'],
	   	 		       ['50','50'],
	   	 		       ['100','100'],
	   	 		       ['500','500']
		   	 		]
		   	 	}),
	 			valueField: 'value',
	 			displayField: 'label'
	        },{
	        	xtype: 'button',
	        	text: '更新分页记录条数',
	        	handler: function(obj) {
	        		me.setGridPage(obj.up("pagingtoolbar").down("textfield[mark=setPage]").getValue());
	        	}
	        }]);
		}		
		confStore.load({
		    callback: function(records, operation, success) {
				if(success) {
					var fieidsAry = new Array();
					var columnsAry = new Array()//显示字段配置;
					columnsAry.push(Ext.widget('rownumberer',{
						width: 40,
						mark: 'rowheader',
						text: '<列>'
					}));//序号
					var searchAry = new Array();//查询字段配置
					
					var cmInitAry = me.displayInit;//前台显示字段配置传入
					var cmMap = new Ext.util.HashMap();
					if(cmInitAry != null) {
						for(var i = 0; i < cmInitAry.length; i++) {
							cmMap.add(cmInitAry[i].key, cmInitAry[i].hidden);
						}						
					}
					
					var searchInitAry = me.searchInit;//前台查询字段配置传入
					var searchMap = new Ext.util.HashMap();
					if(searchInitAry != null) {
						for(var i = 0; i < searchInitAry.length; i++) {
							var val = '0';
							if(!searchInitAry[i].hidden) {
								val = '1';
							}
							searchMap.add(searchInitAry[i].key, val);
						}						
					}
					for(var i = 0; i < records.length; i++) {
						if(records[i].data['IS_SHOW'] > 0) {
							fieidsAry.push(records[i].data['COLUMN_INDEX']);
							var obj = {
								header: records[i].data['COLUMN_HEADER'], 
								dataIndex: records[i].data['COLUMN_INDEX'],
								hidden: records[i].data['IS_SHOW_DEF'] == 0?true:false,
								mark: 'displaycm'
							};
							if(me.fixedWidth != null && me.fixedWidth.fixed == true) {
								if(me.fixedWidth.width != null) {
									obj.width = me.fixedWidth.width;
								}	
							}
							if(!Ext.isEmpty(records[i].data['COLUMN_WIDTH'])) {
								obj.width = records[i].data['COLUMN_WIDTH'];
							}
							if(cmMap.get(obj.dataIndex) != null) {
								obj.hidden = cmMap.get(obj.dataIndex);//再配置
							}
							if(displayFunctions != null) {
								for(var j = 0; j < displayFunctions.length; j++) {
									if(records[i].data['COLUMN_INDEX'] == displayFunctions[j]['key']) {
										if(Ext.isFunction(displayFunctions[j]['fun'])) obj.renderer = displayFunctions[j]['fun'];
										if(!Ext.isEmpty(displayFunctions[j]['width'])) obj.width = displayFunctions[j]['width'];
									}
								}
							}
//							if(records[i].data['FIELD_TYPE'] == '2') {
//								obj.renderer = Ext.util.Format.dateRenderer('Y-m-d');
//							}
							columnsAry.push(obj);
						}
						if(records[i].data['IS_SEARCH'] > 0) {
							var obj = {
								header: records[i].data['COLUMN_HEADER'], 
								dataIndex: records[i].data['COLUMN_INDEX'],
								searchType: records[i].data['SEARCH_TYPE'], 
								fieldType: records[i].data['FIELD_TYPE'],
								isSearchDef: records[i].data['IS_SEARCH_DEF']
							};
							if(searchMap.get(obj.dataIndex) != null) {
								obj.isSearchDef = searchMap.get(obj.dataIndex);//再配置
							}
							searchAry.push(obj);
						}
					}
					me.reconfigure(
						new Ext.data.JsonStore({
						    proxy: {
						        type: 'ajax',
		        				url: webRoot + 'loadCustomGridDataByAlias.do?alias=' + aliasName,
						        reader: {
						            type: 'json',
						            root: 'results',
						            totalProperty :'total'
						        }
						    },
						    pageSize: pageSize,
						   	fields: fieidsAry
						}),
						columnsAry
					);
					me.columnsShowData = columnsAry;//保存列数据
					me.columnsSearchData = searchAry;
					
					me.down('pagingtoolbar').bindStore(me.store);
					me.customLoad();//传入查询参数
					cbFunction.call();
					if(me.down('combo[mark=setPage]')) {
						me.down('combo[mark=setPage]').setValue(pageSize);
					}
				}
		    }
		});
	}
});

//图标
function loadCssFile(filename){
    var fileref = document.createElement('link');
    fileref.setAttribute("rel","stylesheet");
    fileref.setAttribute("type","text/css");
    fileref.setAttribute("href",filename);
    if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
}
loadCssFile(webRoot+'common/css/toolbarCss.css');