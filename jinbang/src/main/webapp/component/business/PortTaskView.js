Ext.define('component.business.PortTaskView', {
	extend: 'Ext.tree.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*'
    ],    
    xtype: 'tree-grid',
    title: ' 端口任务列表',
    height: 300,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    singleExpand: true,
    initComponent: function() {
        Ext.apply(this, {
            store: new Ext.data.TreeStore({
                fields: ['taskId','taskName','stateDate','iconCls'],
                proxy: {
                    type: 'ajax',
                    url: webRoot+'nodeTypeAttr/findPort.do'
                },
                folderSort: true,
                autoLoad:false
            }),
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                text: '任务名称',
                flex: 2,
                sortable: true,
                dataIndex: 'taskName'
            },{
                text: '修改日期',
                flex: 1,
                sortable: true,
                dataIndex: 'stateDate'
            }]
        });
        this.callParent();
    }
});