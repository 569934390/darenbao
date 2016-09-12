var cp = new Ext.state.CookieProvider();
	Ext.state.Manager.setProvider(cp);
var loginOper={
	enableLogin:function(){
		Ext.get('login').dom.disabled='';
		Ext.get('login').addCls('cursor');
	},
	disableLogin:function(){
		Ext.get('login').dom.disabled='disabled';
		Ext.get('login').removeCls('cursor');
	},
	getCookieValue:function(name){
        var cookies = document.cookie.split('; '),
            i = cookies.length,
            cookie, value=null;

        while(i--) {
           cookie = cookies[i].split('=');
           if (cookie[0] === name) {
               value = cookie[1];
           }
        }

        return value;
    },
	login:function(){
		loginOper.disableLogin();
		var username=Ext.get('username').getValue();
		var password=Ext.get('password').getValue();
		if(Ext.isEmpty(username)&&Ext.isEmpty(password)){
			loginOper.enableLogin();
			return Ext.Msg.alert('提示','请输入用户名和密码!');
		}else if(Ext.isEmpty(username)){
			loginOper.enableLogin();
			return Ext.Msg.alert('提示','请输入用户名!');
		}else if(Ext.isEmpty(password)){
			loginOper.enableLogin();
			return Ext.Msg.alert('提示','请输入密码');
		}
//		console.info(MD5(password));
		Ext.Ajax.request({
			url : ctx + '/login/login.do',
			method : 'POST',
			params : {
				name : username,
				password : MD5(password)
			},
			success : function(response) {
				if(!Ext.ExceptionHandler(response)){
					loginOper.enableLogin();
					return;
				} 
				window.top.location.href=ctx+'/portal/portalView.jsp?theme='+(cp.get('theme')||'classic');
			},
			failure : function(response, action) {
				loginOper.enableLogin();
				Ext.Msg.alert('提示','登录失败');
			}
		});
	}
};