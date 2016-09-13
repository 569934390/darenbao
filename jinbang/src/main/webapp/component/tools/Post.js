Ext.define('component.tools.Post', {
	post:function(url,params,success){
		Ext.Ajax.request({
		    url: webRoot+url,
		    params: params,
		    success: function(response){
		        var text = response.responseText;
		        var result = Ext.JSON.decode(text);
		        if(result.success)
		        	success(result);
		        else{
		        	Ext.Msg.alert('提示',result.msg||'执行失败');
		        }
		    },
		 	failure: function(response,opts){
		 		Ext.Msg.alert('提示','执行超时，请检查网络');
		 	}
		});
	}
});