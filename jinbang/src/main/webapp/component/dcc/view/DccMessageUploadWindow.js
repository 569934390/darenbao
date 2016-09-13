Ext.define('component.dcc.view.DccMessageUploadWindow', {
	extend:'component.public.FileUploadWindow',
	alias:'widget.dccMessageUploadWindow',
	requires:['component.public.TextButtonField'],
	constructor:function(config){
		var me=this;
		config=config||{};
		me.uploadForm=me.createUploadForm();
		Ext.applyIf(config,{
			accept:'xml',
			url:ctx+'/debug/importXml.do',
			items:[me.uploadForm]
		});
		me.callParent([config]);
	},
	bindEvent:function(){
		var me=this;
		this.callParent();
		me.down('[name=parentId]').handler=me.parentHander
	},
	parentHander:function(textButton){
		var me=this;
		if(!me.parentIdWindow){
			me.parentIdWindow = Ext.create('component.dcc.view.TestDccTreeWindow', {
				targetField:textButton,
				validation : function(record) {
					if(record.raw.attributeMap.parent_id==-1){
						Ext.Msg.alert('提示','请选择业务!');
						return false;
					}
					return true;
				}
			});
		}
		me.parentIdWindow.show();
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
								xtype : 'hidden',
								fieldLabel : '消息包名',
								name : 'type',
								value:'0'||me.type
							},{
								xtype : 'textfield',
								fieldLabel : '消息包名',
								name : 'text',
								allowBlank : false,
								blankText : '请填写消息包名'
							}, {
								xtype : 'textButtonField',
								fieldLabel : '业务',
								name : 'parentId',
								allowBlank : false,
								blankText : '请选择业务',
								editable : false
							}, {
								xtype : 'filefield',
								name : 'file',
								fieldLabel : '文件',
								allowBlank : false,
								blankText:'请选择文件',
								buttonText : '选择文件'
							}, {
						        xtype: 'filefield',
						        name: 'photo',
						        fieldLabel: 'Photo',
						        labelWidth: 50,
						        msgTarget: 'side',
						        allowBlank: false,
						        anchor: '100%',
						        buttonText: 'Select Photo...'
						    }]
				});
	}
});