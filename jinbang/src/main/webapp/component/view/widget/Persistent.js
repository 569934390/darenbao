Ext.define('component.view.widget.Persistent', {
	singleton: true,
	save:function(sqlKey,entity,fn){
		Ext.Persistent.post(ctx+'/base/save.do',{entityStr:Ext.JSON.encode(entity),tableName:sqlKey},fn);
	},
	update:function(sqlKey,entity,fn){
		Ext.Persistent.post(ctx+'/base/update.do',{entityStr:Ext.JSON.encode(entity),tableName:sqlKey},fn);
	},
	updateItems:function(sqlKey,entity,fn){
		Ext.Persistent.post(ctx+'/base/updateItems.do',{entityStr:Ext.JSON.encode(entity),tableName:sqlKey},fn);
	},
	deletes:function(sqlKey,ids,fn,cascade,subSqlKey){
		Ext.Persistent.post(ctx+'/base/delete.do',{ids:ids,tableName:sqlKey,cascade:cascade||false,subSqlKey:subSqlKey},fn);
	},
	load:function(sqlKey,id,fn){
		Ext.Persistent.post(ctx+'/base/load.do',{id:id,tableName:sqlKey},fn);
	},
	getPage:function(entity,fn){
		Ext.Persistent.post(ctx+'/base/getPage.do',entity,fn,true);
	},
	getPages:function(entity,fn){
		Ext.Persistent.post(ctx+'/base/getPages.do',{pageStr:Ext.encode(entity)},fn,true);
	},
	callCommand:function(entity,fn){
		Ext.applyIf(entity,{conditionStr:Ext.encode({})});
		Ext.Persistent.post(ctx+'/base/sendCommand.do',entity,fn,true);
	},
	post:function(url,params,success,hideTip){
		Ext.Ajax.request({
			url:url,
			params:params,
			success:function(response){
				if(!Ext.isEmpty(response.responseText)){
					var result = Ext.decode(response.responseText);
					if(!hideTip&&!result.success){
						return Ext.Msg.alert('提示','操作失败:【'+result.message+'】');
					}
					if(!Ext.isEmpty(success)&&Ext.isFunction(success))
						success.call(this,result);
				}
			},
			failure:function(){
				return Ext.Msg.alert('提示','操作失败');
			}
		});
	}
});
Ext.Persistent = component.view.widget.Persistent;