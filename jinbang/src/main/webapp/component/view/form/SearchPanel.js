Ext.define('component.view.form.SearchPanel', {
	extend : 'component.public.FormPanel',
	alias : 'widget.searchPanel',
	requires:['component.public.AjaxComboBox','component.public.FormButton','component.public.ClearTextField','Ext.ux.form.DateTimeField'],
	frame : false,
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    ui:'footer',
	    frame:false,
	    layout:{
	    	pack:'end'
	    },
		items: [{
			text : '确定',
			name : 'saveBtn'
	    }, {
			text : '重置',
			name : 'resetBtn'
		}]
	}],
	config:{
	},
	constructor : function(config) {
		var me = this;
		config=config||{};
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.on('tabchange',Ext.bind(me.tabChangeHandler,me));
//		me.xmlView.down('[name=saveBtn]').on('click',Ext.bind(me.saveDccHandler,me));
	},
	resetHandler:function(){
		var me=this;
	}
});