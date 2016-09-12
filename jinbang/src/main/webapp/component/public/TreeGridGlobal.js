//Ajax action
Ext.define('component.public.TreeGridGlobal', {
    doAjax : function (url,jsonData,successfn,failurefn){
    	return (Ext.Ajax.request({
		    url:url,
		    jsonData:jsonData,
		    method:'POST',
		    success: function(response){
		    	var result=jsondecode(response.responseText);
		    	if(result)
		    	{
			        if(result.success)
			        {
			        	successfn(result.info);
			        }
			        else
			        { 
			        	MsgBoxErr(result.info);
			        	failurefn();
			        }
		    	}
		    },
		    failure:function(response){
		      var result =jsondecode(response.responseText);
		      if(result)
		      {
			      MsgBoxErr(result.info);
			      failurefn();
		      }
		    }
	   }));
	},
	jsondecode:function (jsontext,errmsg) {
		var result;
		try
		{
			result=Ext.JSON.decode(jsontext);
		}
		catch(e)
		{
			result=false;
			if(!errmsg)
			   MsgBoxErr("JSON字符串解析失败！");
			else MsgBoxErr(errmsg);
		}
		return result;
	},
	//获取record被修改的属性字段名[]
	getModifiedFieldNames : function (record) {
	    var modified = [];
	    for(var i=0;i<record.fields.keys.length;i++)
	    {
	    	if(record.isModified(record.fields.keys[i]))
	    	{
	    		modified.push(record.fields.keys[i]);
	    	}
	    }
	    return modified; 
	},
	//返回参数json对象集合
	getFilterList : function (keylist,record) {
	   var parameters=[];
	   for(i=0;i<keylist.length;i++)
	   {
	   	  parameters.push(this.getFilter(keylist[i],'=',record.get(keylist[i])));
	   }
	   return parameters;
	},
	getFilter : function (sname,soperate,svalue) {
		return {name:sname,operate:soperate,value:svalue};
	},
	//返回字段json对象集合
	getFieldList : function (fiedlist,record) {
		var me = this;
		var fields=[];
		for(i=0;i<fiedlist.length;i++) {
	   	  	fields.push(me.getField(fiedlist[i],record.get(fiedlist[i])));
		}
		return fields;  
	},
	//返回字段json对象
	getField : function (sname,svalue) {
		return {name:sname,value:svalue};
	},
	//警告提示框
	MsgBoxWar : function (MsgText,callback) {   
		Ext.Msg.show({
			title : '警告',
			msg : MsgText,
			model:true,
			fn:callback,
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	},
	//错误提示框
	MsgBoxErr : function (ErrText,callback) {   
		Ext.Msg.show({
			title : '错误',
			msg : ErrText,
			model:true,
			fn:callback,
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.ERROR
		});
	},
	//提示提示框
	MsgBoxInfo : function (InfoText,callback) {   
		Ext.Msg.show({
			title : '提示',
			msg : InfoText,
			model:true,
			fn:callback,
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.INFO
		});
	},
	//询问提示框
	MsgBoxQst : function (MsgText,callback) {   
		Ext.Msg.show({
			title : '询问',
			msg : MsgText,
			model:true,
			buttons : Ext.Msg.YESNO,
			fn:callback,
			icon : Ext.Msg.QUESTION
		});
	},
	//输入提示框 callback(id,msg)
	MsgBoxInput : function (MsgText,callback) {   
		Ext.Msg.show({
			title : '输入',
			msg : InfoText,
			model:true,
			prompt:true,
			buttons : Ext.Msg.YESNO,
			fn:callback,
			icon : Ext.Msg.INFO
		});
	},
	//等待进度条
	ProgressBarWait : function () {
		Ext.Msg.show({
		  title:'请等待',  
		  msg:'正在工作中.....',  
		  width:240,  
		  progress:true,  
		  closable:false,
		  animate:true
		});
	}
});