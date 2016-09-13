Ext.define('component.public.PopWindow', {
	extend:'Ext.window.Window',
	alias:'widget.popWindow',
	constructor:function(config){
		var me=this;
		config=config||{};
		Ext.applyIf(config,{
			closeAction:'hide',
			modal:true,
			title : '弹出窗口',
			width : '70%',
			height:'100%',
			resizable:false,
			bodyPadding : 10,
			frame : true
//			dockedItems: [{
//			    xtype: 'toolbar',
//			    dock: 'bottom',
//			    ui:'footer',
//			    frame:true,
//			    layout:{
//			    	pack:'center'
//			    },
//				items: [{
//					text : '确定',
//					name : 'okBtn'
//			    }, {
//					text : '取消',
//					name : 'canceltBtn'
//				}]
//			}]
		});
		me.callParent([config]);
		me.addDockedItems();
		me.bindEvent();
	},
	addDockedItems:function(){
		var toolbar = Ext.create('Ext.toolbar.Toolbar', {
				dock : 'bottom',
				ui : 'footer',
				frame : true,
				layout : {
					pack : 'center'
				},
				items : [{
							text : '确定',
							name : 'okBtn'
						}, {
							text : '取消',
							name : 'canceltBtn'
						}]
			});
		this.addDocked(toolbar,['bottom']);
	},
	bindEvent:function(){
		var me=this;
		if(Ext.isFunction(me.okHandler)){
			me.down('[name=okBtn]').on('click',Ext.bind(me.okHandler,me));
		}else if(me.targetButton&&me.grid&&me.displayField&&me.valueField){//由textbuttonfield弹出的窗口
			me.down('[name=okBtn]').on('click',function(){
				var record=Ext.gridSelectCheck(me.grid);
				if(!record) return;
				me.targetButton.setValue(record.get(me.displayField)+","+record.get(me.valueField));
				me.close();
			});
		}
		me.down('[name=canceltBtn]').on('click',Ext.bind(me.cancelHandler,me));
	},
	cancelHandler:function(){
		this.close();
	}
});