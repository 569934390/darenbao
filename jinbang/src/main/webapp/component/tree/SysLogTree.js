Ext.define('component.tree.SysLogTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.sysLogTree',
    requires: ['component.public.TreeLoader'],
    constructor: function(cfg) {
    	var parms={};
    	Ext.apply(parms,{
    		border:false,
    		collapsible: true,
			headerPosition : 'left',
			split:true,
			title : '模块选择器',
			plugins : [Ext.create("component.public.TreeCheckPlugin")],
			width : 250,
			rootVisible: false
    	});
    	Ext.apply(parms,cfg);
    	this.store = Ext.create('component.public.TreeLoader', {
    		 root:{text : '所有模块',id:-1},
    		 proxy:{
    			 extraParams : {
    				 paramMap:Ext.JSON.encode(Ext.apply({
    					 sqlKey:cfg.sqlKey||'DopTestDcc.selectSimulatorLevel',
    					 rootId:-1,
    					 valueField:'id',
    					 displayField:'model'
    				 },cfg.paramMap))
	    		 },
 				url : webRoot+"tree/getRecTree.do",
 	            fields: ['id','text','rate']
    		 }
		 });
        this.callParent([parms]);
    }
});