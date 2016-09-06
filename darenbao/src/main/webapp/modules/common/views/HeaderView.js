define([
    'text!modules/common/templates/HeaderView.html',
    'Portal'
], function (HeaderViewTpl,Portal) {
	var timer;
	var messageCount = function() {
		$.post(Portal.webRoot+'/message/count.do',{},function(result){
			console.log("message count:"+result);
			$("#noticeCount").html(result);
        }).error(function() { 
        	console.log("error");
        	if(timer)
        		clearInterval(timer);
        });
	}
	timer = setInterval(messageCount, 300000);
	messageCount();
//	var wsUri = "ws://127.0.0.1:8080/shanguoyinyi/websocket";
//	var websocket;
//	var connect = function() {
//		websocket = new WebSocket(wsUri);
//		websocket.onerror = function(event) {
//			console.log(event);
//		}
//		// 打开Socket
//		websocket.onopen = function(event) {
//			console.log("connect active. ");
//		}
//		// 监听消息
//		websocket.onmessage = function(event) {
//			var num = parseInt(event.data);
//			$("#noticeCount").html(num);
//		};
//
//		// 监听Socket的关闭
//		websocket.onclose = function(event) {
//			console.log("disconnect. ");
//		};
//	}
//	connect();
//	$(document).on("click", ".user-menu .user-image", connect);
	
    return club.View.extend({
        template: club.compile(HeaderViewTpl),
        i18nData: club.extend({}),
        screenSizes: {
            xs: 480,
            sm: 768,
            md: 992,
            lg: 1200
        },
        events: {
            "click .sidebar-toggle": "doActivate",
            "click #logout":"logout",
            "click #noticeClick":"noticeList"
        },
        //这里用来进行dom操作
        _render: function () {
            this.$el.html(this.template(this.i18nData));
            return this;
        },

        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {

            this._doInit();
            $.post(Portal.webRoot+"/client/loginName.do",{}, function(data){
            	$(".header_view .login_name_span").html(data.data);
            });
            
            $.ajaxSetup({     
                contentType:"application/x-www-form-urlencoded;charset=utf-8",     
                complete:function(XMLHttpRequest,textStatus){  
                    // 通过XMLHttpRequest取得响应头，sessionstatus，  
                    var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");  
                    if(sessionstatus=="timeout"){     
                        window.location.replace(Portal.webRoot+"/login.html");     
                    }  
                }  
            });  
        },
        logout:function(){
            window.location.href =Portal.webRoot+'/index/logout.do';
        },
        noticeList:function(){
        	$("#FUNC_NOTICE_LIST").click();
        },
        _doInit: function () {
            if (($('body').hasClass('fixed') && $('body').hasClass('sidebar-mini'))) {
                this.expandOnHover();
            }
        },

        doActivate: function () {
            //Enable sidebar push menu
            var screenSizes = this.screenSizes;
            if ($(window).width() > (screenSizes.sm - 1)) {
                if ($("body").hasClass('sidebar-collapse')) {
                    $("body").removeClass('sidebar-collapse').trigger('expanded.pushMenu');
                } else {
                    $("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
                }
            }
            //Handle sidebar push menu for small screens
            else {
                if ($("body").hasClass('sidebar-open')) {
                    $("body").removeClass('sidebar-open').removeClass('sidebar-collapse').trigger('collapsed.pushMenu');
                } else {
                    $("body").addClass('sidebar-open').trigger('expanded.pushMenu');
                }
            }
            //这里有动画
            setTimeout(function () {
                $(window).trigger('debouncedresize');
            }, 400);
        },

        expandOnHover: function () {
            var _this = this;
            var screenWidth = this.screenSizes.sm - 1;
            //Expand sidebar on hover
            $('.main-sidebar').hover(function () {
                if ($('body').hasClass('sidebar-mini') && $("body").hasClass('sidebar-collapse') && $(window).width() > screenWidth) {
                    _this.expand();
                }
            }, function () {
                if ($('body').hasClass('sidebar-mini') && $('body').hasClass('sidebar-expanded-on-hover') && $(window).width() > screenWidth) {
                    _this.collapse();
                }
            });
        },
        expand: function () {
            $("body").removeClass('sidebar-collapse').addClass('sidebar-expanded-on-hover');
        },
        collapse: function () {
            if ($('body').hasClass('sidebar-expanded-on-hover')) {
                $('body').removeClass('sidebar-expanded-on-hover').addClass('sidebar-collapse');
            }
        }
    });
});
