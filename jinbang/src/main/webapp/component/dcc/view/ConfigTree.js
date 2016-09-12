Ext.define('component.dcc.view.ConfigTree', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.configTree',
	cls :'messageDetailViewer',
	requires:['component.dcc.model.AvpTree'],
	constructor : function(config) {
		var me=this;
		config=config||{};
		Ext.applyIf(config,{
			loadMask : true,
			useArrows : true,
			rootVisible : false,
//			dockedItems:[{
//			    xtype: 'toolbar',
//			    dock: 'bottom',
//			    ui:'footer',
//			    frame:true,
//			    layout:{
//			    	pack:'center'
//			    },
//				items: [{
//					text : '确定',
//					name : 'okBtn'
//			    }, {
//					text : '取消',
//					name : 'canceltBtn'
//				}]
//			}],
			plugins : [
				Ext.create('component.public.GridToolTipPlugin', {
					cellIndex : '4'
				}),
				Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit : 1
			})],
			store : Ext.create('component.public.TreeStore', {
				model : 'component.dcc.model.AvpTree',
				url : ctx + '/debug/loadConfigTree.do'
			}),
			columns : [{
				xtype : 'treecolumn', // this is so we know which column will
				// show the tree
				text : '名称',
				width : 250,
				dataIndex : 'avpName'
			}, {
				text : '标识',
				width : 50,
				dataIndex : 'avpId'
			}, {
				text : '编码',
				width : 50,
				dataIndex : 'avpCode'
			}, {
				text : '顺序',
				width : 50,
				dataIndex : 'sort'
			}, {
				text : '类型',
				width : 120,
				dataIndex : 'avpType'
			},{
				text : '取值',
				dataIndex : 'avpValue',
				editor: {
                    xtype: 'textfield'
                },
				width : 170
			},{
				text : '描述',
				flex : 1,
				minWidth:100,
				dataIndex : 'avpDesc'
			}]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		this.on('itemcontextmenu',Ext.bind(this.contextMenuHandler,this));
	},
	contextMenuHandler:function(view, record, item, index, e, eOpts ){
		var me=this;
    	e.preventDefault();
    	if(!me.contextMenu){
    		me.contextMenu = Ext.create('Ext.menu.Menu', {
				items : [{
							text : '添加子节点',
							iconCls : 'toolbar-add',
							handler : Ext.bind(me.configTreeEditHandler,me,['add'],0)
						},{
							text : '修改节点',
							iconCls : 'toolbar-edit',
							handler : Ext.bind(me.configTreeEditHandler,me,['edit'],0)
						},{
							text : '刷新节点',
							iconCls : 'toolbar-reLoad',
							handler : Ext.bind(me.configTreeRefreshHandler,me)
						},{
							text : '删除节点',
							iconCls : 'toolbar-delete',
							handler : Ext.bind(me.configTreeDeleteHandler,me)
						},{
							text : '导出xml',
							iconCls : 'toolbar-export',
							handler : Ext.bind(me.exportHandler,me)
						}]
			});
    	}
    	me.editRecord=record;
    	me.contextMenu.showAt(e.getXY());
	},
	configTreeEditHandler:function(action){
		var me=this;
		if(!me.configTreeEditWindow){
			me.configTreeEditWindow=Ext.create('component.dcc.view.ConfigTreeEditWindow',{width:400,height:400});
			me.configTreeEditWindow.setConfigTree(me);
		}
		var params=null;
		if(action=="add"){
			params={parentAvpId:me.editRecord.get('avpId')}
		}else{
			params=me.editRecord.raw;
			params.avpCode=params.avpName+','+params.avpCode
		}
		me.configTreeEditWindow.init(params);
		me.configTreeEditWindow.show();
	},
	configTreeDeleteHandler:function(){
		var me=this;
		var params=me.editRecord.raw;
		Ext.Ajax.request({
				params:params,
				url:ctx+'/debug/configTreeDelete.do',
				success:function(){
					me.reloadConfigTree(params);
				}
			});
	},
	reloadConfigTree:function(params){
		var me=this;
		var store=me.getStore();
		var avpId=params['parentAvpId'];
		var node=store.getNodeById(avpId);
		store.load({
			params:{
				avpId:avpId
			},
			node:node,
			callback:function(){
				me.getView().refresh();
			}
		});
	},
	configTreeRefreshHandler:function(tree,eOpts){
		var node=this.getSelectionModel().getSelection();
		var params={};
		params['parentAvpId']=node[0].get('avpId');
		this.reloadConfigTree(params);
	},
	exportHandler:function(){
		var dcc=this.getDcc();
		Ext.get('dcc').set({value:Ext.encode(dcc)});
		Ext.get('exportForm').dom.submit();
	},
	getDcc:function(){
		var me=this;
		var requestNode=me.getRootNode().childNodes[0];
		var dcc=new Object();
//		var dccMessageType=me.down('ajaxComboBox[name=dccMessageType]');
//		dcc.dccId=dccMessageType.dccId;
		dcc.name="测试";
		var requestDiameterNode=requestNode.childNodes[0];
		var diameter=new Object();
		Ext.each(requestDiameterNode.childNodes,function(childNode,index){
			if(childNode.raw.checked){
				diameter[childNode.raw['avpName']]=childNode.raw['avpValue'];
			}
		});
		var avps=[];
		var requestAvpNode=requestNode.childNodes[1];
		var requestAvpList=me.getAvpList(requestAvpNode,avps,dcc.name);
		var request={diameter:diameter,avps:avps};
		Ext.apply(dcc,{
			request:request
		});
		return dcc;
//		var responseNode=dccNode.childNodes[1];
//		var responseDiameterNode=responseNode.childNodes[0];
//		var responseAvpNode=responseNode.childNodes[1];
//		var responseAvpList=me.getAvpList(responseAvpNode);
	},
	getAvpList:function(avpListNode,avps,name){
		var me=this;
		var checkedNodeIdList=me.getCheckedNodeIdList();
		Ext.each(avpListNode.childNodes,function(childNode,index){
			if(checkedNodeIdList[childNode.id]){
				var data=childNode.data;
				var avp=Ext.apply({},data);
//				var avp={
//						name:data['avpName'],
//						code:data['avpCode'],
//						type:data['avpType'],
//						vendorId:data['vendorId'],
//						avpValue:data['avpValue'],
//						desc:data['avpDesc']
//					};
				avps.push(avp);
				if(!Ext.isEmpty(childNode.childNodes)){
					avp['avp']=[];
					me.getAvpList(childNode,avp['avp']);
				}
			}
		});
		return avps;
	},
	getCheckedNodeIdList:function(){
		var me = this,
            nodesAndParents = {};
        // Find the nodes which match the search term, expand them.
        // Then add them and their parents to nodesAndParents.
        this.getRootNode().cascadeBy(function(node, view){
            var currNode = this;
            if(currNode.get('checked')) {
                while(currNode.parentNode) {
                	nodesAndParents[currNode.id]=1;
                    currNode = currNode.parentNode;
                }
            }
        }, null, [me]);
        return nodesAndParents;
	}
});