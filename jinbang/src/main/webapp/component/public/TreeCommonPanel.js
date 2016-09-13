/**
 * 通用树主键
 * @author 	Damen
 * @date 	2013-09-29
 * @define 	component.public.TreeCommonPanel
 * @alias 	treeCommonPanel
 * 
 * @title 基本设置
 * @param autoExpand 		: 是否全展开(Boolean)
 * @param checkable 		: 是否显示可选框(Boolean)
 * @param cascadeCheck 		: 选择框选中关联（String）
 *        	'down'向下级联
 *         	'downmulti'向下级联(父节点取消选择则子节点也取消选择)
 *         	'up'向上级联
 *         	'upsingle'向上级联一级
 * @param singleSelect 		: 单选(Boolean) (级别比cascadeCheck高)
 * @param onlyLeafCheckable : 是否只有叶子节点可选(Boolean)
 * @param itemcontextmenu 	: 添加树右键菜单(Menu)
 * 
 * @title 请求数据参数
 * @param rootId 		: 根节点ID
 * @param rootText 		: 根节点值
 * @param rootIcon 		: 根节点图标URL
 * @param iconable 		: 是否显示图标(Boolean)
 * @param iconClass 	: 图标样式处理接口
 * @param rootIconCls 	: 根节点图标CSS (与rootIcon二选一设置)
 * @param url 			: 获取数据的url (以一下的必填二选一使用)
 * @param sqlKey 		: 获取数据的SQL名称(String)  (必)
 * @param sqlValue 		: 可直接提供sql供查询，一般树有多层是没有写死(预留)(String)
 * @param idKey 		: 树节点查询子节点值关联KEY (必)
 * @param nodeId 		: 节点id KEY(String) (必)
 * @param nodeName 		: 节点text KEY(String)  (必)
 * @param nodeIcon 		: 节点icon KEY(String)  (iconable : true 时必填,否则图标)
 * @param nodeLeaf 		: 节点icon KEY(String)  (onlyLeafCheckable : true 时必填,否则无法判断为末节点)
 * @param expandLevel	: 默认展开层数(Int)
 * @param paramMap 		: 其他参数
 * 
 * @title toolbar设置
 * @param txtSearch 	: 是否显示检索框(Boolean)
 * @param localFilter 	: 是否检索已展开树节点(Boolean) 默认全展开
 * @param searchWidth 	: 查询框的宽度,配合txtSearch使用(String/Long)
 * @param barExpand 	: 展开按钮(Boolean) 
 * @param barCollapse 	: 展开按钮(Boolean) 
 * @param barAllCheck 	: 全选按钮(Boolean)
 * 
 * @title 方法(methods)
 * @methods selectNode(node) 				: 选中节点
 * @methods removeNode(node) 				: 删除节点
 * @methods setNodeChecked(nodeId,checked) 	: 节点勾选
 * @methods getSelectNode() 				: 获取选择节点
 * @methods getCheckValue 					: 返回选中的节点
 * @methods refreshNodeData(nodeId) 		: 刷新某节点
 * @methods refreshData() 					: 刷新整个树
 * 
 */

Ext.define('component.public.TreeCommonPanel', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.treeCommonPanel',
//	viewConfig: {
//        plugins: {
//            ptype: 'treeviewdragdrop',         	// 可以拖拽
//            enableDrag:true,              		// 拖拽
//            enableDrop:true,              		// 节点接受拖动
//            appendOnly: true
//        }
//	},
	mixins: {
        treeFilter : 'component.public.TreeCommonFilter',
        treeMethod : 'component.public.TreeCommonMethod'
    },
    loadMask:true,
	initComponent : function(config) {
		var me = this;
		me.isRoot = true;
        
		if(this.autoExpand){
			this.url = this.url||webRoot+"/tree/getTreeAllData.do";
		}else{
			this.url = this.url||webRoot+"/tree/getTreeData.do";
		}
		var root = {
			text : this.rootText,
			id : this.rootId || '0',
			expanded : this.rootExpanded===false?false:true,
			checked : false,
			icon : this.rootIcon,
			iconCls : this.rootIconCls
		};
		if(!this.checkable||this.onlyLeafCheckable){
			delete root.checked;
		}
		this.store = Ext.create('Ext.data.TreeStore', {
		    fields:this.fields||[{name:'id'},{name:'text'}],
		    autoLoad: false,
			proxy : {
				type : 'ajax',
				url : this.url,
				extraParams : {
					sqlKey 	  	: this.sqlKey,
					idKey     	: this.idKey,
					nodeId 		: this.nodeId,
					nodeName 	: this.nodeName,
					nodeIcon 	: this.nodeIcon,
					nodeLeaf 	: this.nodeLeaf,
			    	rootId 		: this.rootId,
			    	iconClass 	: this.iconClass,
			    	iconAble 	: this.iconable,
			    	expandLevel	: this.expandLevel,
			    	paramMap 	: this.paramMap
				},
				reader : {
					checkable : this.checkable,
					onlyLeafCheckable : this.onlyLeafCheckable,
					autoExpand : this.autoExpand,
					type : 'json',
					extractData : function(root) {
						var store = this, records = [], Model = store.model,length = root.length,convertedValues, node, record, i;
						if (!root.length && Ext.isObject(root)) {
							root = [root];
							length = 1;
						}
						for (i = 0; i < length; i++) {
							node = root[i];
							store.proxy.extraParams.rootId=node.id;
	
							/**
							 * checkable:true 添加选择框
							 */
							if(me.checkable){
								node.checked = false;
							}
							if (!this.checkable) {
								delete(node.checked);
								node.checkable = false;
							} else if (this.onlyLeafCheckable && !node.leaf) {
								delete(node.checked);
								node.checkable = false;
							} else {
								node.checkable = true;
							}
							
							if (this.autoExpand && !node.leaf) {
								node.expanded = true;
							}
							record = new Model(undefined, store.getId(node), node,convertedValues = {});
	
							record.phantom = false;
							
							store.convertRecordData(convertedValues, node, record);
	
							records.push(record);
	
							if (store.implicitIncludes) {
								store.readAssociated(record, node);
							}
						}
						return records;
					}
				}
			},
			root : root,
		    loadMask:true,
			loader : true,
			listeners: {
		        'load': function (store, records, success) {
		        	if(!Ext.isEmpty(me.expandLevel)&&me.expandLevel>0&&me.isRoot&&(Ext.isEmpty(me.autoExpand)||!me.autoExpand)){
		        		me.expandLevel = me.expandLevel-1;
		        		me.isRoot = false;
		        		if(me.expandLevel>0){
		        			if(!Ext.isFunction(me.allLoad)){
		        				me.addEvents('allLoad');
		        			}
		        			me.getRootNode().cascadeBy(function(node){
		        				var currNode=this;
		        				if(node.data.id!=me.rootId){
		        					me.expandNode(currNode,null,function(){
		        						me.expandLevelFn();
		        					});
		        				}
		        			});
		        		}
		        	}
		        	if(me.loading){
			        	me.loading.hide();
		        	}
		        },
		        'beforeload': function (store, operation, eOpts) {
		        	if(document.body){
		        		me.loading = new Ext.LoadMask(document.body,{
			        		msg : '数据加载中...'// 完成后移除
			        	});   
			        	me.loading.show();
		        	}
		        	store.proxy.extraParams.paramMap=Ext.encode(me.paramMap);
		        	console.info(store.proxy.extraParams.paramMap);
					store.proxy.extraParams.rootId=operation.id;
		        }
		    }
		});

//		me.on('beforeload', function(store, operation, eOpts ){
//			console.info("_-------------------");
//			console.info(this.body);
//			if(this.body){
//				me.body.mask("正在加载");
//			}
//			store.proxy.extraParams.rootId=operation.id;
//		});
//		me.on('load', function(store, operation, eOpts ){
//			if(this.body){
//				me.body.unmask();
//			}
//		});
		/**
		 * itemcontextmenu : true 添加树右键菜单
		 */
		if(this.itemcontextmenu){
			me.on('itemcontextmenu', function(view,record,item,index,event,eOpts) {
				//禁用浏览器的右键相应事件
				event.preventDefault();
				event.stopEvent();
				//禁用浏览器的右键相应事件
				me.itemcontextmenu.showAt(event.getXY());//让右键菜单跟随鼠标位置
			});
		}
		/**
		 * singleSelect:true 设置单选
		 */
		if (this.singleSelect) {
			this.on('checkchange', function(node, checked) {
				if (checked) {
//					if(me.onlyLeafCheckable){
						this.treeSigleCheck(node, false);
//					}else{
//						this.childSigleCheck(this.getRootNode(), false, [node]);
//					}
				}

			}, this);
		} else if (this.cascadeCheck) {
			switch (this.cascadeCheck) {
				case 'down' :
					this.on('checkchange', function(node, checked) {
						// node.expand();
						node.checked = checked;
						if (checked) {
							this.childCheck(node, checked);
						}
					}, this);
					break;
				case 'downmulti' :
					this.on('checkchange', function(node, checked) {
						// node.expand();
						node.checked = checked;
						this.childCheck(node, checked);
					}, this);
					break;
				case 'up' :
					this.on('checkchange', function(node, checked) {
						// node.expand();
						node.checked = checked;
						if (checked)
							this.parentCheck(node, checked);

					}, this);
					break;
				case 'upsingle' :
					this.on('checkchange', function(node, checked) {
						// node.expand();
						node.checked = checked;
						if (checked)
							this.parentCheck(node, checked, true);

					}, this);
					break;
			}
		}
		/**
		 * tree ToolBar 设计
		 * 
		 */
		if (this.txtSearch || this.barExpand || this.barAllCheck || this.barAllUnCheck) {
			me.toolbar = Ext.create('Ext.toolbar.Toolbar', {
			});
			if(this.txtSearch){
				me.toolbar.add({
					xtype: 'trigger',
					trigger1Cls: 'x-form-clear-trigger',
					trigger2Cls: me.treeAllSearch?'x-form-search-trigger':'',
					emptyText:'快速检索功能',
					width : Ext.isEmpty(this.searchWidth)?'65%':this.searchWidth,
		            enableKeyEvents : true,
		            scope: this,
		            onTrigger2Click:function(){
		            	if(Ext.isFunction(me.onTriggerSearchClick)){
		            		me.onTriggerSearchClick();
		            	}
		            },
		            onTrigger1Click: function () {
		            	this.setValue('');
		            	me.clearFilter();
		            },
					listeners : {
						keyup: function(src,e){
							if (Ext.EventObject.ESC == e.getKey()) {
		                        field.onTriggerClick();
		                    } else {
		                        me.filterByText(src.getValue());
		                    }
						}
					}
				});
			}
			if (this.barExpand) {
				me.toolbar.add({
					xtype : 'button',
					iconCls : 'x-tool-img x-tool-expand',
					style : 'width:21px;margin-right:-3px;',
					handler : function() {
						me.getEl().mask('展开中...');
						
						me.expandAll(function() {
							me.getEl().unmask();
						});
					}
				})
			}
			if (this.barCollapse) {
				me.toolbar.add({
					xtype : 'button',
					iconCls : 'x-tool-img x-tool-collapse',
					style : 'width:21px;',
					handler : function() {
						me.collapseAll();
					}
				})
			}
			if (this.barAllCheck) {
				if(this.checkable){
					me.toolbar.add({
						text : '全选',
						xtype : 'button',
						handler : function(bTxt) {
							var tree = this.findParentByType('treepanel');
							if (tree.checkable) {
								tree.getRootNode().cascadeBy(function(node){
									node.data.checkable=true;

									node.checked = true;
								});
								if(bTxt.text=="全选"){
									bTxt.setText("反选");
									
									tree.childCheck(tree.getRootNode(), true)
								}else{
									bTxt.setText("全选");
									
									tree.childCheck(tree.getRootNode(), false);
								}
							}
						}
					});
				}
			}
			this.dockedItems = [me.toolbar];
		}
		this.callParent(config);
	}
});

/*
 *使用方法
 */
//treePanel =  Ext.create('component.TreeCommonPanel', {
//	height : screen.availHeight-252,
//	autoExpand : true,                	//是否全展开
//  cascadeCheck:'downmulti',
//	checkable : true,					//是否显示可选框(Boolean)
//	singleSelect : true,               	//单选(Boolean) (级别比cascadeCheck高)
//	onlyLeafCheckable : true,         	//是否只有叶子节点可选(Boolean)
//	localFilter : true,         		//是否搜索展开树节点
//	txtSearch : true,                  	//是否显示查询框(Boolean)
//	searchWidth : '80%',               	//查询框的宽度,配合txtSearch使用(String/Long)
//	barExpand : true,                  	//展开按钮(Boolean)
//	barCollapse : true,                 //收起按钮(Boolean)
//	barAllCheck : false,				//全选按钮(Boolean)
//	viewConfig: {
//		plugins: {
//			ptype: 'treeviewdragdrop',	// 可以拖拽
//			enableDrag: true,			// 拖拽
//			enableDrop: true,       	// 节点接受拖动
//			appendOnly: true
//		}
//	},
//	itemcontextmenu : new Ext.menu.Menu({
//		float:true,
//		items:[{
//			text:"添加",
//			iconCls:'toolbar-add',
//			handler:function(){
//				alert(1);
//			}
//		},{
//			text:"修改",
//			iconCls:'toolbar-edit',
//			handler:function(){
//				alert(11);
//			}
//		},{
//			text:"删除",
//			iconCls:'toolbar-delete',
//			handler:function(){
//				alert(12);
//			}
//		}]
//	}),
//	rootVisible : true,					//根节点可见
//	rootId : 1,                 		//根节点值
//	rootText : '拓扑管理',				//根节点text
//	iconable : true,					//是否显示图标(Boolean)
//	rootIcon : '../common/topoImages/16x16/physicalsubnet.gif', //根节点图标URL
//	sqlKey : 'com.compses.dao.sqlmap.NmsTopoSymbolMapper.loadNodeMapList',  //获取数据的SQL名称(String)
//	idKey : 'treeParentId',	     		//树节点查询子节点值关联KEY
//	nodeId : 'symbolId',				//节点id KEY
//	nodeName : 'symbolName1',			//节点text KEY
//	nodeIcon : 'treeIconPath',			//节点icon KEY
//	expandLevel : 3,					//默认展开层数(Int)
//	nodeLeaf : 'childNum',				//节点icon KEY(String)  (onlyLeafCheckable : true 时必填,否则无法判断为末节点)
//	paramMap : {						//其他参数
//		symbolStyle : '1'
//	}
//});
//});