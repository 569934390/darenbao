Ext.define('component.tree.TestDccTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.testDccTree',
    requires: ['component.public.TreeLoader'],
    constructor: function(config) {
    	config=Ext.applyIf(config||{},{
				border:false,
				collapsible: true,
				headerPosition : 'left',
				split:true,
				title : '消息包选择器',
				plugins : [Ext.create("component.public.TreeCheckPlugin")],
				width : 200,
				rootVisible: false
			});
    	config.store = Ext.create('component.public.TreeLoader', {
    		 root:{text : '所有消息包',id:1},
    		 proxy:{
    			 extraParams : {
    				 paramMap:Ext.JSON.encode({
    					 sqlKey:'DopTestDcc.selectListByDccId',
    					 rootId:-1,
    					 valueField:'dcc_id',
    					 displayField:'text'
    				 })
	    		 },
 				url : webRoot+"tree/getRecTree.do",
 	            fields: ['id','text']
    		 }
		 });
        this.callParent([config]);
    }
});