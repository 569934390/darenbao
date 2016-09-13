Ext.define("component.public.ButtonHandlerPlugin", {
	extend : 'Ext.AbstractPlugin',
	alias : 'widget.buttonHandlerPlugin',
	init : function(button) {
		var me=this;
		if(this.operate==common.constant.buttonOperate.save||this.operate==common.constant.buttonOperate.update||this.operate==common.constant.buttonOperate.saveOrUpdate){
			button.on('click',Ext.bind(Ext.showConfirmWindow,null,[this.scope,this.handler,this.operate],0));
		}else if(this.operate==common.constant.buttonOperate.remove){
			button.on('click',Ext.bind(Ext.showConfirmWindow,null,[this.scope,this.handler,common.constant.buttonOperate.remove],0));
		}else if(this.operate==common.constant.buttonOperate.cancel){
			button.on('click',function(){
				me.window.close();
			});
		}
	}
});