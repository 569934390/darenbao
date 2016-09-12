Ext.define('component.tree.SimulatorTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.simulatorTree',
    requires: ['component.public.TreeLoader'],
    constructor: function(cfg) {
    	var parms={};
    	Ext.apply(parms,{
    		border:false,
    		collapsible: true,
			headerPosition : 'left',
			split:true,
			title : '场景选择器',
			plugins : [Ext.create("component.public.TreeCheckPlugin")],
			width : 250,
			rootVisible: false
    	});
    	Ext.apply(parms,cfg);
    	this.store = Ext.create('component.public.TreeLoader', {
    		 root:{text : '所有场景',id:1},
    		 proxy:{
    			 extraParams : {
    				 paramMap:Ext.JSON.encode(Ext.apply({
    					 sqlKey:cfg.sqlKey||'DopTestDcc.selectSimulatorLevel',
    					 rootId:-1,
    					 valueField:'dcc_id',
    					 displayField:'text'
    				 },cfg.paramMap))
	    		 },
 				url : webRoot+"tree/getRecTree.do",
 	            fields: ['id','text','rate']
    		 }
		 });
        this.callParent([parms]);
    }
});