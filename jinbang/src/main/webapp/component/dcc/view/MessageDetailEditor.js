Ext.define('component.dcc.view.MessageDetailEditor', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.messageDetailEditor',
	cls :'messageDetailViewer',
	requires:['component.public.AjaxComboBox'],
	constructor : function(config) {
		var me=this,cfg = config;
		config=config||{};
		var plugins=null;
		if(config.editable){
			plugins=[new Ext.grid.plugin.CellEditing({
	            clicksToEdit: 1
	        }),Ext.create('component.public.GridToolTipPlugin',{cellIndex:'4'})]
		}else{
			plugins=[Ext.create('component.public.GridToolTipPlugin',{cellIndex:'4'})]
		}
		delete config.editable;
		var srcCombo = Ext.widget('ajaxComboBox',{
			queryMode:'local',
			name:'state',
			value:'00A',
			data:[['固定值','固定值'],['表达式','表达式']]
		});
		Ext.applyIf(config,{
			loadMask : true,
			useArrows : true,
			rootVisible : false,
			animate : false,
			plugins : plugins,
			store:Ext.create('component.dcc.store.MessageDetailEditStore',{url:config.url}),
			columns : [{
				xtype : 'treecolumn', // this is so we know which column will
				// show the tree
				text : '名称',
				width : 250,
				sortable : true,
				dataIndex : 'text'
			}, {
				text : 'code',
				width : 50,
				dataIndex : 'code',
				sortable : true
			}, {
				text : '类型',
				width : 120,
				dataIndex : 'type',
				sortable : true
			}, {
				text : '来源',
				width : 60,
				dataIndex : 'src',
				sortable : true,
				editor: srcCombo,
				renderer:function(val){
					return val;
				}
			}, {
				text : '取值',
				dataIndex : 'avpValue',
				width : 170,
				editor: {
                    xtype: 'textfield'
                },
				sortable : true
			}, {
				text : 'flags',
				width : 50,
				dataIndex : 'flags',
				sortable : true
			}, {
				text : 'vendor_id',
				width : 50,
				dataIndex : 'vendorId',
				sortable : true
			}, {
				text : '描述',
				flex : 1,
				minWidth:100,
				dataIndex : 'desc',
				sortable : true
			}]
		});
		me.callParent([config]);
//		me.bindEvent();
	}
//	bindEvent:function(){
//		this.on('afterrender',Ext.bind(this.initQuickTip,this));
//	}
//	initQuickTip:function(){
//		var view=this.getView();
//		var tip = Ext.create('Ext.tip.ToolTip', {
//			// The overall target element.
//			target : view.el,
//			// Each grid row causes its own separate show and hide.
//			delegate : 'td .x-grid-cell',
//			// Moving within the row should not hide the tip.
//			trackMouse : true,
//			// Render immediately so that tip.body can be referenced
//			// prior to the first show.
//			renderTo : Ext.getBody(),
//			listeners : {
//				// Change content dynamically depending on which element
//				// triggered the show.
//				beforeshow : function updateTipBody(tip) {
//					var cellIndex=tip.triggerElement.cellIndex;
//					if(cellIndex==4){
//						tip.update('取值:'+ view.getRecord(tip.triggerElement.parentNode).get('avpValue'));
//					}else{
//						return false;
//					}
//				}
//			}
//		});
//	}
});