/**
 * 通用下拉框树主键
 * @author Damen
 * @date 2013-11-25
 * @define component.public.TreeCombo
 * @alias treeCombo
 * 
 * @require : 'component.public.TreeCommonPanel'
 * 
 */

Ext.define('component.public.TreeCombo', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.treeCombo',
	tree : {},
	idValue : '',
	textValue : '',
	rootNode : '',
	selectedClass : '',
	onSelect : Ext.emptyFn,
	emptyText : '---请选择---',
	clearValue : function() {
		this.setValue('');
		this.setRawValue('');
	},
	anchor : '100%',
	setValue:function(){
		return this.textValue;
	},
	getValue:function(){
		return this.idValue;
	},
	onTreeDbClick : function(record) {

	},
    onTreeClick : function(record) {

	},
	initComponent : function() {
		Ext.apply(this, {
			editable : false,
			queryMode : 'local'
		});
		var treeid = Math.random() + '';
		var me = this;
		this.tpl = Ext.String.format('<div id="{0}"></div>', treeid);
		
		this.tree = Ext.create('component.public.TreeCommonPanel', {
			singleSelect : this.singleSelect,
			onlyLeafCheckable : this.onlyLeafCheckable,
			autoExpand : this.autoExpand,
			rootVisible : this.rootVisible,
			rootId : this.rootId,
			iconable : this.iconable,					//是否显示图标(Boolean)
			rootIcon : this.rootIcon, 					//根节点图标URL
			sqlKey : this.sqlKey,  						//获取数据的SQL名称(String)
			idKey : this.idKey,	     					//树节点查询子节点值关联KEY
			nodeId : this.nodeId,
			nodeName : this.nodeName,
			nodeIcon : this.nodeIcon,
			nodeLeaf : this.nodeLeaf,
			paramMap : this.paramMap,
			multiSelect : this.multiSelect,
			cascadeCheck : this.cascadeCheck,
			checkable : this.checkable,
			barExpand : this.barExpand,
			barCollapse : this.barCollapse,
			barAllCheck : this.barAllCheck,
			barAllUnCheck : this.barAllUnCheck,
			minHeight:200
		});
		
//		var store  = this.tree.getStore();
//		store.on("beforeload", function(store, records, options) {
//					store.proxy.extraParams.sqlKey = config.sqlKey;
//					store.proxy.extraParams.paramMap = Ext.JSON
//							.encode(config.paramMap);
//				});
		if (this.rootNode) {
			this.tree.setRootNode(this.rootNode);
		}

		this.tree.on('itemclick', function(view, record) {
			if(record.isLeaf()){ me.collapse(); }
			
	 		me.idValue = record.get('id');
			me.textValue = record.get('text');
        	me.setValue(record.get('id'));  
        	me.setRawValue(record.get('text'));
	            
		});
        
		this.addEvents('treedbclick');
		this.addEvents('treeitemclick');
		this.on('treedbclick', this.onTreeDbClick, this);
		this.on('treeitemclick', this.onTreeClick, this);
		this.tree.on('celldblclick', function(table, td, cellIndex, record, tr, rowIndex, e, eOpts) {
			this.fireEvent('treedbclick', record);
		}, this);
		this.tree.on('itemclick', function( view,  record,  item,  index, e,  eOpts){
			this.fireEvent('treeitemclick', record);
		},this);
		this.on('expand', function() {
			if (!this.tree.rendered) {
				
				this.tree.minWidth=this.getWidth();
				this.tree.render(treeid);
			}
		});

		this.callParent(arguments);
	},
	refreshData : function(rootId, url) {
		this.tree.refreshData(rootId, url,this.tree);

	}
});


/*
 *使用方法
 */
//var nodeTypeCombo = Ext.create('component.public.TreeCombo', {
//	displayField : 'topoTypeDisplayName',
//	queryMode : 'remote',
//	hiddenName : 'topoTypeId',
//	valueField : 'topoTypeDisplayName',
//	typeAhead: true,
//	editable : false,
// 	triggerAction: 'all',
//	autoExpand : true,  
// 	rootVisible : false,
// 	rootId : -1,
// 	iconable : true,
// 	rootIcon : '../common/topoImages/16x16/physicalsubnet.gif',
//	idKey : 'parentId',
//	nodeId : 'topoTypeId',
//	nodeName : 'topoTypeDisplayName',	
//	nodeIcon : 'treeIconPath',
//	fields : ['topoTypeId', 'topoTypeDisplayName'],
//	nodeLeaf : 'childNum',
//	paramMap : {						//其他参数
//		symbolStyle : '1'
//	}
//});
