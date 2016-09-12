Ext.define('component.tree.GeneralTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.generalTree',
    requires: ['component.public.TreeLoader'],
    constructor: function(cfg) {
    	var parms={};
    	Ext.apply(parms,{
    		border:false,
    		collapsible: true,
			headerPosition : 'left',
			split:true,
			title : '通用树',
			plugins : [Ext.create("component.public.TreeCheckPlugin")],
			width : 250,
			rootVisible: false
    	});
    	Ext.apply(parms,cfg);
    	this.store = Ext.create('component.public.TreeLoader', {
    		 root:{text : '根节点',id:1},
    		 proxy:{
    			 extraParams : {
    				 paramMap:Ext.JSON.encode(Ext.apply({
    					 sqlKey:cfg.sqlKey||'DopMenu.getLevelMenuList',
    					 rootId:-1,
    					 iconStr:',,/common/topoImages/16x16/module.png,/common/topoImages/16x16/packages.gif',
    					 valueField:'menu_id',
    					 displayField:'menu_name'
    				 },cfg.paramMap))
	    		 },
 				url : webRoot+"tree/getRecTree.do",
 	            fields: ['id','text','rate','type','attr1','attr2']
    		 }
		 });
        this.callParent([parms]);
    }
});