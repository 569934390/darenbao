
Ext.define('component.socket.Message', {
	url:webRoot+'base/message.do',
	singleton: true,
	uuid:	Math.uuid(),
	queue:[],
	handlers:[],
	timeOut:0,
	constructor:function(){
		var me = this;
		me.init();
		me.callParent(arguments);
		setTimeout(function(){
			component.socket.Message.open();
		},1000);
	},
	init:function(){
	},
	open:function(){
		var me = component.socket.Message;
		me.connet();
	},
	connet:function(){
		var me = component.socket.Message;
		Ext.Ajax.request({
			url : me.url,
    		method : 'POST',
    		params : {uuid:me.uuid},
			success:function(response){
				me.timeOut=0;
				var result = Ext.decode(response.responseText);
    			if(result.success===true){
    				var cells = result.jsonData;
	    			for(var channel in cells){
		    			me.onmessage(cells[channel].channel,cells[channel].message);
	    			}
    			}
    			else if(result.success===false){
    				Ext.Msg.alert('提示',result.msg);
    			}
    			me.connet();//继续心跳请求
			},
			failure:function(){
				if(me.timeOut++>=10){
					return Ext.Msg.alert('提示','推送服务断开！',function(){
						window.location.reload();
					});
				}
				else{
					me.connet();//继续心跳请求
				}
			},
		    timeout: 60000
		});
	},
	bind:function(channel,callback){
		if(!channel){
			throw "消息队列名称不能为空！";
		}
		else if(!callback||!Ext.isFunction(callback)){
			throw "请指定订阅回调函数！";
		}
		var me = component.socket.Message;
		if(!me.handlers[channel]){
			me.handlers[channel]=[];
		}
		var bindId = Math.uuid();
		me.handlers[channel][bindId] = callback;
		return bindId;
	},
	unbind:function(channel,id){
		if(!channel){
			throw "消息队列名称不能为空！";
		}
		else if(!id){
			throw "退订Id为空！";
		}
		var me = component.socket.Message;
		delete me.handlers[channel][id];
	},
	onmessage:function(channel,message){
//		console.info('消息管理', '消息队列['+channel+']收到消息：'+message);
		for(var key in component.socket.Message.handlers){
			var handlers = component.socket.Message.handlers[key];
			if(key == channel){
				for(var id in handlers){
					var handler = handlers[id];
					handler.call(handler.scope||component.socket.Message,message);
				}
			}
		};
	}
});
window.MessageUtils = component.socket.Message;