Ext.define('component.dcc.view.ConfigTreeEditWindow', {
	extend:'component.public.PopWindow',
	alias:'widget.configTreeEditWindow',
	requires:['component.public.TextButtonField'],
	config:{
		configTree:null,
		action:null
	},
	constructor:function(config){
		var me=this;
		config=config||{};
		me.form=me.createForm();
		Ext.applyIf(config,{
			layout:'fit',
			items:[me.form]
		});
		me.callParent([config]);
	},
	bindEvent:function(){
		var me=this;
		me.callParent();
		me.form.down('[name=avpCode]').handler=me.avpCodeHandler;
	},
	init:function(avp){
		var me=this;
		var basicForm=me.form.getForm();
		basicForm.reset();
		basicForm.setValues(avp);
	},
	avpCodeHandler:function(textButton){
		var me=this;
		if(!me.avpCodeWindow){
			me.avpCodeWindow=Ext.create('component.dcc.view.AvpEditWindow',{
				callback:function(record){
					if(record){
						textButton.setValue(record.get('avpName')+','+record.get('avpCode'))
					}
				}
			});
		}
		me.avpCodeWindow.show();
	},
	okHandler:function(){
		var me=this;
		var form=this.down('form');
		var params=form.getForm().getValues();
		if(!form.getForm().isValid()){
			return Ext.Msg.alert('提示','请将数据填写完整!');
		}else{
			Ext.Ajax.request({
				params:params,
				url:ctx+'/debug/configTreeEdit.do',
				success:function(){
					me.close();
					me.configTree.reloadConfigTree(params);
				}
			});
		}
	},
	createForm:function(){
		return Ext.create('Ext.form.Panel', {
			frame:true,
			defaults:{
				labelAlign:'right',
				labelWidth:100,
				xtype:'textfield'
			},
			items:[{
				readOnly:true,
				name:'parentAvpId',
				fieldLabel:'父节点标识'
			},{
				name:'avpId',
				readOnly:true,
				fieldLabel:'标识'
			},{
				name:'avpValue',
//				readOnly:true,
				fieldLabel:'值'
			},{
				xtype:'textButtonField',
				name:'avpCode',
				fieldLabel:'编码'
			},{
				name:'sort',
				fieldLabel:'顺序'
			},{
				name:'checked',
				fieldLabel:'是否选中'
			},{
				xtype:'textarea',
				name:'avpDesc',
				fieldLabel:'描述'
			}]
		});
	}
	
});