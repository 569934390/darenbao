/**
 * 添加样式，可以延迟加载，添加dom元素时要重新刷新
 */
 define(['jq','./utils'],function () {
    return {
		refushStyle : function(){
			$('.toolbar_level1_item').hover(function(){
				$(this).css({background: 'url("'+webRoot+'portal/images/level1-bg.png")',color:'#fff'});
			},function(){
				$(this).css({background: 'transparent',color:'#000'});
			});
			function nowTime(){
				var time = new Date();  
				$('#staffInfo').text(session.user.realName+'('+session.user.name+'),欢迎登陆! '+time.format("MM月dd日 hh:mm"));
			}
			nowTime();
			setInterval(nowTime,60000);
			$('#logout').click(function(){
				$.post(ctx + '/login/loginOut.do',{},function(){
					window.top.location.href=ctx+'/main/login.jsp';
				});
			}).hover(function(){
				$(this).stop().css({background: 'url("'+webRoot+'portal/images/logout-selected.png") no-repeat'}).animate({width:40});
			},function(){
				$(this).stop().animate({width:30}).css({background: 'url("'+webRoot+'portal/images/logout.png") no-repeat'});
			});
		}
    }
 });
