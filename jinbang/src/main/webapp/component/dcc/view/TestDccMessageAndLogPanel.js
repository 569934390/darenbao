Ext.define('component.dcc.view.TestDccMessageAndLogPanel',{
	extend:'Ext.panel.Panel',
	constructor:function(config){
		var me=this;
		config=config||{};
		me.testDccMessagePanel=me.createMessagePanel();
		me.testDccLogPanel=me.createLogPanel();
		me.testDccMessagePanel.setTestDccLogPanel(me.testDccLogPanel);
		Ext.apply(config,{
			layout:'border',
			border:false,
			items:[me.testDccMessagePanel,me.testDccLogPanel]
		});
		me.callParent([config]);
	},
	createMessagePanel:function(){
		return Ext.create('component.dcc.view.TestDccMessageTreePanel',{border:false,region:'center'});
	
	},	
	createLogPanel:function(){
		return Ext.create('component.dcc.view.TestDccLogPanel', {
			region : 'south',
			height : '70%',
			collapsible: true,
			split:true,
			border:false,
			listeners:{
				itemclick:function(tree,record,item,index,e,eOpts){
					
				}
			}
		});
	}
});