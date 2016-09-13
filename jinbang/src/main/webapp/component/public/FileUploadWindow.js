Ext.define('component.public.FileUploadWindow', {
	extend:'Ext.window.Window',
	alias:'widget.fileUploadWindow',
	constructor:function(config){
		var me=this;
		config=config||{};
		Ext.applyIf(config,{
			closeAction:'hide',
			modal:true,
			title : '文件导入',
			width : 400,
			height:400,
			resizable:false,
			bodyPadding : 10,
			frame : true,
			dockedItems: [{
			    xtype: 'toolbar',
			    dock: 'bottom',
			    ui:'footer',
			    frame:true,
			    layout:{
			    	pack:'center'
			    },
				items: [{
					text : '确定',
					name : 'submitBtn'
			    }, {
					text : '取消',
					name : 'canceltBtn'
				}]
			}]
		});
		me.callParent([config]);
		me.bindEvent();
	},
	bindEvent:function(){
		var me=this;
		me.down('[name=submitBtn]').on('click',Ext.bind(me.submitHandler,me));
		me.down('[name=canceltBtn]').on('click',Ext.bind(me.cancelHandler,me));
	},
	acceptValid:function(field){
		var me=this;
		var acceptArr = me.accept.split(',');
		var fileName=field.getValue();
		var acceptFlag=false;
		Ext.each(acceptArr, function(accept, index) {
			if (fileName.substring(fileName.length - accept.length) == accept) {
				acceptFlag = true;
				return false;
			}
		});
		return acceptFlag;
	},
	submitHandler:function(){
		var me=this;
		var formPanel=me.down('form');
		if(formPanel){
			var acceptFlag=true
			var form = formPanel.getForm();
			var fields=form.getFields();
			if(me.accept){
				fields.each(function(field){
				if(field.isXType('filefield')){
					acceptFlag=me.acceptValid(field);
					return acceptFlag;
				}
			});
			}
			if(acceptFlag===false){
				return Ext.Msg.alert('文件类型错误','支持以下文件格式:'+me.accept);
			}
			if (form.isValid()) {
				form.submit({
					url : me.url,
					waitMsg : '文件正在上传...',
//					params:me.getParams(),
					success : function(form, action) {
						Ext.Msg.alert('提示', action.result.message||'文件上传成功');
						if(Ext.isFunction(me.callback)){
							me.callback(form,action);
						}else{
							me.close();
						}
					},
					failure:function(form, action){
						var response=Ext.ResponseDecode(action.response);
						if(Ext.isEmpty(action.response)||Ext.isEmpty(response)){
							Ext.Msg.alert('提示', '文件上传失败');
						}else{
							Ext.Msg.alert('提示', response.message);
						}
					}
				});
			}
		}
	},
	cancelHandler:function(){
		this.close();
	}
});