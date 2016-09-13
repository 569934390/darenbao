Ext.define('component.dcc.view.ConfigTreeWindow',{
	extend:'component.public.PopWindow',
	alias:'widget.configTreeWindow',
	constructor:function(config){
		var me=this;
		config=config||{};
		me.configTree=me.createTree();
		Ext.applyIf(config,{
			title:'消息配置',
			closeAction:'hide',
			layout:'fit',
			width:'70%',
			height:'100%',
			items:[me.configTree]
		});
		me.callParent([config]);
	},
	createTree:function(){
		return Ext.create('component.dcc.view.ConfigTree');
	},
	okHandler : function() {
		var me = this;
		var records = me.configTree.getStore().getModifiedRecords();
		if (!Ext.isEmpty(records)) {
			var params = [];
			Ext.each(records, function(record, index) {
						var temp=Ext.apply({},record.data);
						temp.checked=temp.checked?1:0;
						params.push(temp);
					});
			Ext.Ajax.request({
				url : ctx + '/debug/batchUpdateConfigTree.do',
				params : {avpTreeListStr : Ext.encode(params)},
				success : function() {
					me.close();
				}
			});
		} else {
			me.close();
		}
	}
});