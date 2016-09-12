/**
 * 添加样式，可以延迟加载，添加dom元素时要重新刷新
 */
 define(['jq','./utils'],function () {
 	function initHelper(){
		constans.helpPanel = $('<div id="mask"></div>')
						.css({display:'none',width:constans.width,height:constans.height});
		$(document.body).append(constans.helpPanel);
		constans.helpPanel.click(function(){
			constans.helpPanel.hide();
			constans.helper.hide();
		}).bind('keyup',hideHelper);
		constans.helper = $('<div id="helper"></div>').appendTo(document.body);
		constans.helper.mouseout(changeTabStyle);
	}
	function changeTabStyle(id){
		constans.helpPanel.html('');
		if(id&&parseInt(id)>0){
			$helpTip = $('<div></div>').css({width:constans.width,height:constans.height,background:'url("'+webRoot+'portal/images/helps/'+id+'.png") no-repeat'})
			constans.helpPanel.append($helpTip);
		}
		constans.helper.find('.helperTask').css({border:'4px solid #d3d3d3'}).eq(constans.helper.openIndex).css({border:'4px solid #fff'});
	}
	function showHelper(e){
		if(constans.helperTimer)
			return;
		if(e.keyCode==16){
			if(!constans.helpPanel){
				initHelper();
			}
			constans.helperTimer = setTimeout(function(){
				constans.helper.html('');
				var width = 0;
				$('iframe').each(function(i,item){
					var index = findTabIndex($(item).attr('id'));
					if($(this).css('display')=='inline'){
						constans.helper.openId = $(item).attr('id');
						constans.helper.openIndex = i;
					}
					if(taskMenus[index]){
						var task = taskMenus[index];
						var $taskDom = $('<div class="helperTask"></div>').css({width:'81px',height:'81px','background-image':'url("'+webRoot+'portal/images/icons/'+taskMenus[i].icon+'_max.png")'});
						$taskDom.data('task',task);
						if(task.id==constans.helper.openId){
							$taskDom.css({border:'4px solid #fff'});
						}
						constans.helper.append($taskDom);
						width +=99;
					}
				});
				constans.helpPanel.show();
				constans.helper.find('.helperTask').hover(function(){
					constans.helper.find('.helperTask').css({border:'4px solid #d3d3d3'});
					$(this).css({border:'4px solid #fff'});
					showTab($(this).data('task').id,$(this).data('task'));
				},function(){
					$(this).css({border:'4px solid #d3d3d3'});
				}).click(function(e){
					showTab($(this).data('task').id,$(this).data('task'));
					hideHelper(e);
				});
				
				constans.helper.css({width:width,left:(constans.width-width)/2,top:constans.height/2-50}).show();
			},200);
		}
	}
	function hideHelper(e){
		clearTimeout(constans.helperTimer);
		constans.helperTimer = null;
		if(!constans.helper||!constans.helpPanel)return;
		constans.helpPanel.hide();
		constans.helper.hide();
		if(constans.helper.openId){
	//		showTab(constans.helper.openId);
		}
	}
    return {
		initHelper : initHelper,
		changeTabStyle:changeTabStyle,
		showHelper:showHelper,
		hideHelper:hideHelper
    }
 });
