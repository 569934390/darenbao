Ext.define('component.business.PortBugsListViewer', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.portBugsListViewer',
//    selModel:Ext.create('Ext.selection.CheckboxModel',{ checkOnly :true }),
    selModel: {
    	selType:'checkboxmodel'
	},
	plugins : [Ext.create("component.public.GridCheckPlugin")],
    renderState:function(val){
    	if(val=='1'){
    		return '<span style="color:green;">运行</span>';
    	}
    	else{
    		return '<span style="color:red;">异常</span>';
    	}
    },
    renderAdminState:function(val){
    	if(val=='1'){
    		return '<span style="color:green;">管理中</span>';
    	}
    	else{
    		return '<span style="color:red;">非管理</span>';
    	}
    },
    initComponent: function() {
    	var me = this;
    	me.columns= [
            { text: '所属网元', dataIndex: 'PARENT_NODE_ID',width:80,hidden:true},
            { text: '网元名称', dataIndex: 'PARENT_NODE_NAME',width:80},
	        { text: '端口索引', dataIndex: 'PORT_INDEX',width:80},
	        { text: '端口IP',  dataIndex: 'PORT_IP',width:120},
	        { text: '子网掩码', dataIndex: 'PORT_MASK',width:120},
	        { text: '运行状态', dataIndex: 'PORT_STATE',renderer:me.renderState,width:60},
	        { text: '端口MAC', dataIndex: 'PORT_MACADDS',width:120},
	        { text: '端口名称', dataIndex: 'PORT_NAME',width:120},
	        { text: '端口名称', dataIndex: 'NODE_NAME',width:120},
	        { text: '网元类型', dataIndex: 'NODE_TYPE_NAME',width:120},
	        { text: '端口说明', dataIndex: 'PORT_DESC',width:120},
	        { text: '管理状态', dataIndex: 'ADMIN_STATUS',renderer:me.renderAdminState,width:80}
	    ];
	    me.store=Ext.create('Ext.data.Store',{
	    	fields:[{name:'PORT_INDEX',type:'int'},'NODE_ID','PARENT_NODE_ID','PARENT_NODE_NAME','NODE_TYPE_ID','PORT_IP','PORT_MASK', 'PORT_MACADDS', 'PORT_NAME','NODE_NAME','NODE_TYPE_NAME','PORT_DESC','PORT_SPEED','PORT_STATE','ADMIN_STATUS'
		    ],
		    proxy : {
				type : 'ajax',
				url : webRoot + '/nodeTypeAttr/loadPortDetailInfoPage.do',
				reader : {
					type : 'json',
					root: 'result'
				},
				actionMethods: 'GET'
			}
	    });
//	    store.proxy.extraParams={
//				limit: 500,
//				page: 1,
//				start: 0,
//				nodeIdList:config.getNodeId()
//			};
//	    me.store.load({start: 0,limit: 500,nodeIdList:config.getNodeId()});
        this.callParent(arguments);
    }
});