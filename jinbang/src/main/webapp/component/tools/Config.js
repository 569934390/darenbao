Ext.define('component.tools.Config', {
	mixins: {
        post : 'component.tools.Post'
    },
	singleton: true,
	properties:false,
	constructor:function(){
		var me = this;
		me.callParent(arguments);
		me.init();
	},
	init:function(){
		var me = this;
		me.post('ctrlCmdController/constants.do',{},function(result){
			me.properties=result.data;
			component.tools.Config.callback();
		});
	},
	status:function(){
		return component.tools.Config.properties;
	},
	get:function(key){
		return component.tools.Config.properties[key];
	}
});