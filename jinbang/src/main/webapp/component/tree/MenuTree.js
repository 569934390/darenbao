Ext.define('component.tree.MenuTree', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.menuTree',
    requires: ['component.public.TreeLoader'],
    constructor: function(cfg) {
    	var parms={};
    	Ext.apply(parms,{
    		border:false,
    		collapsible: true,
			headerPosition : 'left',
			split:true,
			title : '系统功能树',
			plugins : [Ext.create("component.public.TreeCheckPlugin")],
			width : 250,
			rootVisible: false
    	});
    	Ext.apply(parms,cfg);
    	this.store = Ext.create('component.public.TreeLoader', {
    		 root:{text : '跟节点',id:-1},
    		 proxy:{
    			 extraParams : {
    				 paramMap:Ext.JSON.encode(Ext.apply({
    					 sqlKey:cfg.sqlKey||'DopMenu.getLevelMenuList',
    					 rootId:-1,
    					 checked:'1,2,3',
    					 iconStr:',,/common/topoImages/16x16/module.png,/common/topoImages/16x16/packages.gif',
    					 valueField:'menu_id',
    					 displayField:'menu_name'
    				 },cfg.paramMap))
	    		 },
 				url : webRoot+"tree/getRecTree.do",
 	            fields: ['id','text','rate']
    		 }
		 });
        this.callParent([parms]);
    }
});