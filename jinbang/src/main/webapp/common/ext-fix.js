Ext.Ajax.on('requestcomplete',function(conn,response,options,eOpts){
	if(Ext.isFunction(response.getAllResponseHeaders)&&response.getAllResponseHeaders().sessionstatus=='timeout'){
		window.top.location.href=ctx+'/main/login.jsp';
	}else if(response.responseText){
		var responseText=Ext.ResponseDecode(response.responseText);
		if(responseText.success===false){
			return Ext.Msg.alert('提示',responseText.message);
		}
	}
});
Ext.Ajax.on('requestexception',function(conn,response,options,eOpts){
	Ext.ExceptionHandler(response);
});
Ext.Privilege = {};

Ext.Privilege.checkBtn = function(btn,btns){
	var me = this;
	for(var k in btns){
		var key = null;
		if(btn.privilegeCode){
			key = btn.privilegeCode;
		}
		else if(btn.text){
			key = btn.text;
		}else if(btn.itemId){
			key = btn.itemId;
		}else if(btn.iconCls){
			key = btn.iconCls;
		}else{
			key = btn.id;
		}
		console.info(key,btns[k].code==key);
		if(key&&btns[k].code==key){
			btn.handler=Ext.emptyFn();
			btn.setDisabled(true);
			break;
		}
	}
};
Ext.Privilege.bindEvent = function(btns){
	if(!Ext.isArray(btns))return;
	var buttons = Ext.ComponentQuery.query('button');
	for(var i = 0;i<buttons.length;i++){
		var btn = buttons[i];
		Ext.Privilege.checkBtn.call(this,btn,btns);
	}
}
Ext.Privilege.onReadyTimer = setInterval(function(){
	if(Ext.ComponentQuery.query('viewport').length==0) return true;
	clearInterval(Ext.Privilege.onReadyTimer);
	var btns = [];
	if (!session||!session.resources) {return;};
	for(var i=0;i<session.resources.length;i++){
		var resource = session.resources[i];
		if(window.location.pathname.indexOf(resource.direction)>0){
			btns.push(resource	);
		}
	}
	var tabs = Ext.ComponentQuery.query('tabpanel');
	for(var i=0;i<tabs.length;i++){
		tabs[i].on('tabchange',function(){
			Ext.Privilege.bindEvent.call(this,btns);
		});
	}
	Ext.Privilege.bindEvent.call(this,btns);
},50);
String.prototype.format = function() {
    var args = arguments;
    if(args==null||args.length<1)return ;
    var str = this.replace(/{(\d{1})}/g, function() {
        return args[arguments[1]];
    });
    str = str.replace(/{(\w{1,20})}/g,function(){
    	var obj=null;
    	for(var i=0;i<args.length;i++){
    		if(typeof args[i]=='object'){
    			obj = args[i];break;
    		}
    	}
    	return obj[arguments[1]]||arguments[1];
    });
    return str;
};
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {   
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {   
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);   
     } else {   
        return this.replace(reallyDo, replaceWith);   
     }   
};
Function.prototype.createDelegate = function(obj, args, appendArgs) {
	var method = this;
	return function() {
		var callArgs = args || arguments;
		if (appendArgs === true) {
			callArgs = Array.prototype.slice.call(arguments, 0);
			callArgs = callArgs.concat(args);
		} else if (Ext.isNumber(appendArgs)) {
			callArgs = Array.prototype.slice.call(arguments, 0);
			var applyArgs = [appendArgs, 0].concat(args);
			Array.prototype.splice.apply(callArgs, applyArgs);
		}
		return method.apply(obj || window, callArgs);
	};
};
/**
 * 
 * @param {} grid 要选择数据的grid
 * @param {} multiRecord 是否可以选择多条数据
 * @param {} showAlarm 不符合要求是是否弹出警告提示
 * @return {Boolean}
 */
Ext.gridSelectCheck=function(grid,multiRecord,showAlarm){
	var records=grid.getSelectionModel().getSelection();
	if(Ext.isEmpty(records)){
		if(showAlarm===undefined||showAlarm===true){
				Ext.Msg.alert('提示','请选择记录!');
		}
		return false;
	}else if(records.length>1&!multiRecord){
		if(showAlarm===undefined||showAlarm===true){
				Ext.Msg.alert('提示','只能选择一条记录!');
		}
		return false;
	}else{
		return multiRecord?records:records[0];
	}
};
/**
 * 驼峰命名法变量转为下划线风格变量
 */
Ext.toUnderline = function(s) {
	if (Ext.isEmpty(s)) {
		return null;
	}
	var sb = "";
	var upperCase = false;
	for (var i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		var nextUpperCase = true;

		if (i < (s.length - 1)) {
			nextUpperCase = /^[A-Z]+$/.test(s.charAt(i + 1));
		}

		if ((i >= 0) && (/^[A-Z]+$/.test(c))) {
			if (!upperCase || !nextUpperCase) {
				if (i > 0)
					sb += "_";
			}
			upperCase = true;
		} else {
			upperCase = false;
		}
		sb += c.toLowerCase();
	}
	return sb;
};
/**
 * 驼峰命名法变量的对象转为下划线风格变量的对象
 */
Ext.toUnderlineObject=function(obj){
	if (Ext.isEmpty(obj)) {
		return null;
	}
	var temp=new Object();
	for(var i in obj){
		temp[Ext.toUnderline(i)]=obj[i];
	}
	return temp;
};
/**
 * 下划线风格变量转为驼峰命名法变量
 */
Ext.toCamelCase = function(s) {
	if (Ext.isEmpty(s)) {
		return null;
	}
	s = s.toLowerCase();
	var sb = "";
	var upperCase = false;
	for (var i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (c == "_") {
			upperCase = true;
		} else if (upperCase) {
			sb += c.toUpperCase();
			upperCase = false;
		} else {
			sb+=c;
		}
	}
	return sb;

};
/**
 * 下划线风格变量的对象转为驼峰命名法变量的对象
 */
Ext.toCamelCaseObject=function(obj){
	if (Ext.isEmpty(obj)) {
		return null;
	}
	var temp=new Object();
	for(var i in obj){
		temp[Ext.toCamelCase(i)]=obj[i];
	}
	return temp;
};
/**
 * 下划线风格变量转为首字母大写的驼峰命名法变量
 */
Ext.toCapitalizeCamelCase = function() {
	if (Ext.isEmpty(s)) {
		return null;
	}
	s = Ext.UnderlinetoCamelCase(s);
	return s.substring(0, 1).toUpperCase() + s.substring(1);
};
/**
 * 下划线风格变量的对象转为首字母大写的驼峰命名法变量的对象
 */
Ext.toCapitalizeCamelCaseObject = function(obj) {
	if (Ext.isEmpty(obj)) {
		return null;
	}
	var temp=new Object();
	for(var i in obj){
		temp[Ext.toCapitalizeCamelCase(i)]=obj[i];
	}
	return temp;
};
/**
 * 根据数组中的key得到value
 */
Ext.getArrayValue=function(array,key,keyIndex){
	keyIndex=keyIndex||1;
	var temp=null;
	Ext.each(array,function(obj,index){
		if(obj[keyIndex]==key){
			temp=obj[1-keyIndex];
			return;
		}
	});
	return temp;
};
/**
 * 根据数组的value得到key
 */
Ext.getArrayKey=function(array,value,valueIndex){

	valueIndex=valueIndex||0;
	var temp=[];
	Ext.each(array,function(obj,index){
		if(obj[valueIndex]==value){
			temp.push(obj[1-valueIndex]);
			return;
		}
	});
	return temp;

};

Ext.showFields=function(form,fieldStr,showFlag){
	var fieldArr=fieldStr.split(",");
	for (var index = 0; index < fieldArr.length; index++) {
		if(Ext.isString(form)){
		}else{
			showFlag?form.getForm().findField(fieldArr[index]).show():form.getForm().findField(fieldArr[index]).hide();
		}
	}
	
};
/**
 * disabled的field用form.getValues()获取不到数据，用这个方法可以获取disabled的field的数据
 */
Ext.getFormValues=function(form){
	if(Ext.isFunction(form.getForm)){
		form=form.getForm();
	}
	var values=form.getValues();
	var fields=form.getFields().items;
	Ext.each(fields,function(field,index){
		if(field.isXType('datefield')||field.isXType('datetimefield')){
			values[field.getName()||field.getId()]=Ext.Date.format(field.getValue(),'Y-m-d H:i:s');
		}else if(field.isXType('radiofield')||field.isXType('checkbox')||field.isXType('radio')){
			
		}else{
			values[field.getName()||field.getId()]=field.getValue();
		}
	});
	return values;
};
Ext.setReadOnly=function(form,flag,filterStr){
	form.getForm().getFields().each(function(field,index){
		if(!Ext.isEmpty(filterStr)){
			if(filterStr.indexOf(field.getName()||field.getId())==-1){
				field.setReadOnly(flag);
			}
		}else{
			field.setReadOnly(flag);
		}
	});
//	form.items.each(function(item, index, length){
//		item.setReadOnly(flag);
//	});
};
/**
 * 自定义表单数据校验
 */
Ext.apply(Ext.form.field.VTypes, {
    daterange: function(val, field) {
        var date = field.parseDate(val);

        if (!date) {
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = field.up('form').down('#' + field.startDateField)||field.up('form').down('[name=' + field.startDateField+']');
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = field.up('form').down('#' + field.endDateField)||field.up('form').down('[name=' + field.endDateField+']');
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    daterangeText: '开始日期必须小于结束日期',

    password: function(val, field) {
        if (field.initialPassField) {
            var pwd = field.up('form').down('#' + field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },

    passwordText: '两次密码不匹配',
    privilegeCode:function(value,field){
    	return /^[A-Z]{2}[0-9]{4}$/.test(value);
    },
    privilegeCodeText:'权限格式错误(权限格式为两个大写字母加四个数字,如:AA1234)!',
    ip:function(val){
    	var re =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
		return re.test(val); 
    },
    ipText:'请输入正确的IP地址',
    phoneNumber:function(val){
    	var re=/^1[3|4|5|8][0-9]\d{4,8}$/;
    	return re.test(val);
    },
    phoneNumberText:'请输入正确的手机号码'
});

Ext.util.JSON=Ext.JSON;
Ext.Loader.getCssPath=function(className) {
    var path = '',
        paths = Ext.Loader.config.paths,
        prefix = Ext.Loader.getPrefix(className);

    if (prefix.length > 0) {
        if (prefix === className) {
            return paths[prefix];
        }

        path = paths[prefix];
        className = className.substring(prefix.length + 1);
    }

    if (path.length > 0) {
        path += '/';
    }
    return path.replace(/\/\.\//g, '/') + className.replace(/\./g, "/") + '.css';
};
Ext.ResponseDecode=function(response){
	var result=response;
	if(response.responseText){
		try {
			result=Ext.isString(response.responseText)?Ext.decode(response.responseText):response.responseText;
		} catch (e) {
			// TODO: handle exception
			result=response.responseText;
		}
	}
	try {
		result=Ext.isString(result)?Ext.decode(result):result;
	} catch (e) {
		// TODO: handle exception
	}
	return result;
};
Ext.ExceptionHandler=function(response){
	var result=response;
	if(response.responseText){
		try {
			result=Ext.isString(response.responseText)?Ext.decode(response.responseText):response.responseText;
		} catch (e) {
			// TODO: handle exception
			result=response.responseText;
		}
	}
	try {
		result=Ext.isString(result)?Ext.decode(result):result;
	} catch (e) {
		// TODO: handle exception
		return true;
	}
	if(result.success===true||result.success=="true"||Ext.isEmpty(result.success)){
		return result.message||true;
	}else{
		Ext.Msg.alert('提示',result.message);
		return false;
	}
};
Ext.showConfirmWindow=function(scope,fn,operate){
	if(operate.indexOf('scope')>-1&&operate.indexOf('.')>-1){
		operate=eval(operate);
	}
	var args=Array.prototype.slice.call(arguments, 2);
	var msg="确定要执行该操作吗?";
	if(operate==common.constant.buttonOperate.save||operate==common.constant.buttonOperate.add){
		msg="确定要新增记录吗?";
	}
	if(operate==common.constant.buttonOperate.saveOrUpdate){
		msg="确定要修改(新增)记录吗?";
	}
	if(operate==common.constant.buttonOperate.update||operate==common.constant.buttonOperate.edit){
		msg="确定要更新记录吗?";
	}
	if(operate==common.constant.buttonOperate.remove){
		msg="确定要删除记录吗?";
	}

	Ext.Msg.show({
	    title: '提示',
	    msg: msg,
	    width: 300,
	    buttons: Ext.Msg.YESNO,
	    buttonText:{ 
            yes: "确定", 
            no: "取消" 
        },
        icon: Ext.Msg.QUESTION,
	    fn: function(btn,text){
	    	if(btn=='yes'&&fn){
	    		fn.apply(scope,args);
	    	}
	    }
	});
};
Ext.registPrivilege=function(btn,parentPanel){
	if(Ext.isString(btn)){
		if(btn.indexOf(',')>0){
			var btnArr=btn.split(',');
			for (var int = 0; int < btnArr.length; int++) {
				btn=parentPanel.down('[name='+btnArr[index]+']')||parentPanel.down('[id='+btnArr[index]+']');
				Ext.disableBtnByPrivilege(btn);
			}
		}else{
			btn=parentPanel.down('[name='+btn+']')||parentPanel.down('[id='+btn+']');
			Ext.disableBtnByPrivilege(btn);
		}
		
	}else{
		Ext.disableBtnByPrivilege(btn);
	}
	
};
Ext.disableBtnByPrivilege=function(btn){
	var resourcePrivilege=session.resourcePrivilegeMap[btn.resourceId];
	if(Ext.isEmpty(btn)){
		console.error('没有找到按钮'+btn.getName()||btn.getId());
		return;
	}
	if(Ext.isEmpty(resourcePrivilege)){
		btn.enable();
	}else{
		if(Ext.isEmpty(session.staffPrivilegeMap[resourcePrivilege['privId']])){
			btn.disable();
		}else{
			btn.enable();
		}
	}
};
Ext.requireCss = function(classNames){
	classNames = (typeof classNames === 'string') ? [ classNames ] : classNames;
	for (var index = 0; index < classNames.length; index++) {
		var filePath=Ext.Loader.getCssPath(classNames[index]);
		var fileRef = document.createElement('link');
	    fileRef.setAttribute("rel","stylesheet");
	    fileRef.setAttribute("type","text/css");
	    fileRef.setAttribute("href",filePath);
	    var parentNode=document.getElementsByTagName("head")[0];
	    if(typeof fileRef != "undefined"){
	       parentNode.insertBefore(fileRef,parentNode.lastChild);
	    }
	}
};

Ext.isGBK = function(str){
	if(!(/^[\u4e00-\u9fa5]+$/i).test(str)){    
       return false;
    }
    return true;
}
