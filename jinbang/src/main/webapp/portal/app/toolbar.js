define(['jq','app/utils','app/tabpanel','app/dock','app/style'],function (q,u,tab,dock,s) {
    return {
        init: function () {
			$.post(webRoot+'menu/getMenuList.do',{},function(toolbars){
				for(var i=0;i<toolbars.length;i++){
					var $level1 = $('<div class="toolbar_level1"></div>');
					for(var j=0;j<toolbars[i].children.length;j++){
						var $item = $('<div class="toolbar_level1_item">'+toolbars[i].children[j].menuName+'</div>')
		//					.css({'background':'url('+webRoot+toolbars[i].children[j].iconCls+')'})
							.data('url',toolbars[i].children[j].url)
							.data('id',toolbars[i].children[j].menuId)
							.data('icon',toolbars[i].children[j].iconCls)
							.data('name',toolbars[i].children[j].menuName);
						$level1.append($item);
					}
					var $level0 = $('<div class="toolbar_level0">'+toolbars[i].menuName+'</div>')
						.click(function(){
							$('.toolbar_level0').css({background: 'transparent'});
							$(this).css({background: '#2c66f1'});
							$('.toolbar_level1').hide();
							$(this).next().show();
							$(document.body).bind('mouseover',function (e){
								if(e.target.className&&(e.target.className.indexOf('toolbar_level0')==-1&&
								e.target.className.indexOf('toolbar_level1')==-1&&e.target.className.indexOf('toolbar_level1_item')==-1)){
									$('.toolbar_level1').hide();
									$('.toolbar_level0').css({background: 'transparent'});
									$(document.body).unbind('mouseover');
								}
								if(!e.target.className){
									$('.toolbar_level1').hide();
									$('.toolbar_level0').css({background: 'transparent'});
									$(document.body).unbind('mouseover');
								}
							});
					})
					.appendTo($('#toolbar'));
					$level1.css({top:'25px',left:$level0.offset().left,'z-index':99999,display:'none'});
					$level1.appendTo($('#toolbar'));
				}
				$('#toolbar').find('.toolbar_level1_item').click(function(e){
					e.stopPropagation();
					var url = $(this).data('url'),id = $(this).data('id');
					if(url){
							dock.insert({id:$(this).data('id'),title:$(this).data('name'),url:$(this).data('url'),icon:$(this).data('icon')||'messageSearch'},null,function(){
								tab.openUrl(id,url,function(){
							});
						});
					}else{
						Portal.dock.insert({id:$(this).data('id'),title:$(this).data('name'),url:$(this).data('url'),icon:$(this).data('icon')||'messageSearch'});
					}
					$(this).parent().hide();
					return false;
				});
				s.refushStyle();
			});
        }
    }
});