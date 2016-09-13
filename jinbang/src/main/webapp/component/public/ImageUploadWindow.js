Ext.define('component.public.ImageUploadWindow', {
	extend:'component.public.FileUploadWindow',
	alias:'widget.imageUploadWindow',
	requires:['component.public.TextButtonField'],
	constructor:function(config){
		var me=this;
		config=config||{};
		me.uploadForm=me.createUploadForm();
		Ext.applyIf(config,{
			accept:'png,jpeg,jpg',
			url:ctx+'/debug/importXml.do',
			items:[me.uploadForm]
		});
		me.callParent([config]);
	},
	bindEvent:function(){
		var me=this;
		this.callParent();
	},
	createUploadForm:function(){
		return Ext.create('Ext.form.Panel',{
					layout : 'form',
					frame : true,
					style : 'margin:10px',
					defaults:{
						labelAlign : 'right'
					},
					items : [{
								xtype : 'filefield',
								name : 'file',
								fieldLabel : '图片',
								allowBlank : false,
								blankText:'请选择图片',
								buttonText : '选择图片'
							}]
				});
	}
});