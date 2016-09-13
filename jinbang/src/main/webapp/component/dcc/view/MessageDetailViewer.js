Ext.define('component.dcc.view.MessageDetailViewer', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.messageDetailViewer',
    loadMask: true,
    useArrows: true,
    rootVisible: false,
    animate: false,
    viewConfig:{
		enableTextSelection:true
	},
    cls :'messageDetailViewer',
    plugins: [{
        ptype: 'bufferedrenderer'
    }],
    constructor: function(config) {
	    this.store = Ext.create('component.dcc.store.MessageDetailStore',{
            rowKey:config.rowKey
        });
        this.callParent([config]);
    },
    hideNode:function(flag){
    	var me=this;
    	if(hideFlag='root'){
    		me.getRootNode().cascadeBy(function(tree,view){
	            var uiNode = view.getNodeByRecord(this);
	            if(uiNode && this.parentNode.getId()==tree.getRootNode().getId()) {
	                Ext.get(uiNode).setDisplayed('none');
	            }
	        }, null, [me,me.getView()]);
    	}
    	if(hideFlag='msg'){
    		me.getRootNode().cascadeBy(function(tree,view){
	            var uiNode = view.getNodeByRecord(this);
	            if(uiNode &&this.parentNode.parentNode&&this.parentNode.parentNode.getId()==tree.getRootNode().getId()) {
	                Ext.get(uiNode).setDisplayed('none');
	            }
	        }, null, [me,me.getView()]);
    	}
    },
    columns: [{
        xtype: 'treecolumn', //this is so we know which column will show the tree
        text: '名称',
        width:300,
        sortable: true,
        locked: true,
        dataIndex: 'text'
    },{
        text: 'code',
        width: 120,
        dataIndex: 'code',
        sortable: true
    },{
        text: '类型',
        width: 120,
        dataIndex: 'type',
        sortable: true
    },{
        text: '来源',
        width: 120,
        dataIndex: 'src',
        sortable: true
    },{
        text: '取值',
        dataIndex: 'avpValue',
        flex: 1,
        sortable: true
    },{
        text: 'flags',
        width: 80,
        dataIndex: 'flags',
        sortable: true
    },{
        text: 'vendor_id',
        width: 80,
        dataIndex: 'vendorId',
        sortable: true
    },{
        text: '描述',
        width:200,
        dataIndex: 'desc',
        sortable: true
    }]
});