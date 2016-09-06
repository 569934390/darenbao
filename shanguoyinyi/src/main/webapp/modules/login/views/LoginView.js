define([
    'text!modules/login/templates/LoginView.html',
    'i18n!modules/login/i18n/login.i18n',
    'Portal'
], function(LoginViewTpl, i18nlogin,Portal) {

    return club.View.extend({
        template: club.compile(LoginViewTpl),
        i18nData: club.extend({}, i18nlogin),

        events: {
            'click #btnLogin': 'loginClick',
            'change #selectLanguage': 'doSwitchLanguage',
            'focus .jstoggleFocus': 'formFocus',
            'blur  .jstoggleFocus': 'formBlur',
            'click #imgVerifyCode': 'refreshVerifyCode'
        },

        //这里用来进行dom操作
        _render: function() {
            this.$el.html(this.template(this.i18nData));
            return this;
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function() {
            this.initUIFunc();
        },

        initUIFunc: function() {
            this.$el.bind("contextmenu", function() { // 禁止右击，复制，粘贴和剪切等行为
                return false;
            }).bind("cut", function() {
                return false;
            }).bind("copy", function() {
                return false;
            }).bind("paste", function() {
                return false;
            });

            this.$("#inputUserName").bind("paste", function() { // 禁止用户名粘贴的行为
                return false;
            }).keydown(function(event) { //IE中keypress不支持功能按键
            });

            this.$("#inputPassword").bind("paste", function() { // 禁止用户密码粘贴的行为
                return false;
            }).keydown(function(event) {
            });

            // 初始化语言下拉框
            $('#selectLanguage').prop('value', club.language);
        },

        // 切换语言选项
        doSwitchLanguage: function(event) {
            var value = $('#selectLanguage').val()
            club.setLanguage(value);
            var moduleURL = "modules/login/i18n/login.i18n" + '.' + value;
            var thisObj = this;
            require([moduleURL], function(i18nlogin) {
                thisObj.i18nData = club.extend({}, i18nlogin);
                var tmp = thisObj.template(thisObj.i18nData);
                thisObj.$el.html(tmp);
                $('#selectLanguage').prop('value', club.language);
            });

        },

        checkLogin: function(event, type) {
            var code = eve.keyCode || eve.which || eve.charCode;
            if (code == 13) {
                try {
                    if (t == 1) {
                        $("#inputPassword").focus();
                    } else if (t == 2) {
                        this.loginClick();
                    } else {
                        this.loginClick();
                    }
                    eve.keyCode ? eve.keyCode = 0 : eve.which = 0;
                    eve.preventDefault();
                } catch (e) {}
                return;
            }
            if (t == 1) { // Check if the Key pressed is digits or valid character in [A..Z,a..z] and TAB key which the code is 9;
                if (!(code >= 48 && code <= 57) && !(code >= 65 && code <= 90) && !(code >= 97 && code <= 122) && code != 45 && code != 95 && code != 8 && code != 9) {
                    eve.keyCode ? eve.keyCode = 0 : eve.which = 0;
                    eve.preventDefault();
                    return;
                }
            }
        },
        // 校验完毕登录
        loginClick: function(event) {
            if ($('#loginForm').isValid()) { //校验
                $.post(Portal.webRoot+'/login.do',{username:$('input[name=loginName]').val(),password:$('input[name=password]').val()},function(result){
                    if (result.code===0) {
                        window.location.href ='./main.html';
                    }
                    else{
                        club.toast('info', result.msg);
                    }
                    
                });
            }
        }
    });
});
