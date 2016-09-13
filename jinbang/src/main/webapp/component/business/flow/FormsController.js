Ext.define('component.business.flow.FormsController', {
	submitData:function(data){
		var me = this,win = this.win;
		var form = win.down('form');
		var values = Ext.getFormValues(form);
		if(!Ext.isEmpty(win.submitData)&&Ext.isFunction(win.submitData)){
			values = win.submitData(values);
		}
		if(me.data.action=='START_BY_KEY'){
			if(me&&me.data&&Ext.isEmpty(me.data.key))
				return Ext.Msg.alert('提示','不允许发起空流程!');
			Ext.Ajax.request({
	    		url : webRoot+'taskController/startByKey.do',
	    		method : 'POST',
	    		params : {processDefinitionKey:me.data.key,variableStr:Ext.encode(values)},
	    		success : function(resp, action) {
	    			var result = Ext.decode(resp.responseText);
	    			window.top.Ext.Msg.alert('提示',result.message,function(){
	    				me.win.hide();
	    			});
	    		},
	    		failure : function() {
	    			window.top.Ext.Msg.alert('提示','网络连接异常');
	    		}
	    	});
		}
		else if(me.data.action=='TASK_BY_ID'){
			Ext.Ajax.request({
	    		url : webRoot+'taskController/completeTask.do',
	    		method : 'POST',
	    		params : {taskId:me.data.taskId,variableStr:Ext.encode(values)},
	    		success : function(resp, action) {
	    			var result = Ext.decode(resp.responseText);
	    			window.top.Ext.Msg.alert('提示',result.message,function(){
	    				me.win.hide();
	    			});
	    		},
	    		failure : function() {
	    			window.top.Ext.Msg.alert('提示','网络连接异常');
	    		}
	    	});
		}
	},
	getTaskForms:function(config){
		Ext.Ajax.request({
    		url : webRoot+'processController/process/getTaskFormKey.do',
    		method : 'POST',
    		params :config.params,
    		success : function(resp, action) {
    			var result = Ext.decode(resp.responseText);
    			if(result.success){
    				config.success.call(config.scop||config,result);
    			}
    			else
    				Ext.Msg.alert('提示',result.message);
    		},
    		failure : function() {
    			Ext.Msg.alert('提示','网络连接异常');
    		}
    	});
	}
});