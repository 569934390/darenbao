define(['jq','app/utils','app/helper'],function (a,b,helper) {
 	function findTabIndex(id){
		for(var i=0;i<constans.taskMenus.length;i++){
			if(constans.taskMenus[i].id==id){
				return i;
			}
		}
		return -1;
	}
	function showTab(id,task){
		if(!constans.helper){
			helper.initHelper();
		}
		constans.helper.openId = id;
		$('iframe').each(function(i,item){
			if($(item).attr('id')==id){
				constans.helper.openIndex = i;
			}
		});
		helper.changeTabStyle(id);
		constans.mainPanel.find('iframe').hide();
		constans.mainPanel.find('#'+id).show();
		$.state.set('lastOpenId',id);
	}
	function closeChart(e){
		for(var k in constans.chart_renders){
			constans.chart_renders[k].clear();
			constans.chart_renders[k].dispose();
			delete constans.chart_renders[k];
		}
		constans.chart_renders = [];
		$('.box').remove();
		function rb(){
			constans.chartPanelbg.remove();
			constans.chartPanelbg = null;
		}
		if($.isIE8m()){
			rb();
		}
		else{
			constans.chartPanelbg.fadeOut(rb);
		}
		$('.menu-task:eq(0)').removeClass('menu-task-selected');
		return false;
	}
	function openUrl(id,url,fn,scope){
		var index = findTabIndex(id);
		if(index!=-1){
			$('.menu-task').eq(index).addClass('menu-task-selected');
			var dockItem = $('#dock_container').find('img').eq(index);
			var item = dockItem.data('item');
			item.status = true;
			dockItem.data('item',item);
		}
		if(constans.chartPanelbg) {  closeChart(); if(url==='charts')return;}
		if($('.panel-bg').length>0){
			$('.panel-bg').remove();
			$('.panel').remove();
			$('.menu-task:last').removeClass('menu-task-selected');
			if(url==='setting')return;
		}
		if(url==='charts'){
			constans.chartPanelbg = $('<img class="chart-panel-bg" /src="'+webRoot+'portal/images/chart_bg.png" />')
				.click(closeChart).appendTo(document.body);
			if($.isIE8m()){
				constans.chartPanelbg.show(function(){
					require(['app/chart'],function(chart){
						chart.init();
					});
				});
			}
			else{
				constans.chartPanelbg.fadeIn(100,function(){
					require(['app/chart'],function(chart){
						chart.init();
					});
				});
			}
			return;
		}
		else if(id==='setting'){
			require(['app/setting'],function(setting){
				setting.init();
			});
			return ;
		}
		if(!constans.mainPanel){
			constans.mainPanel = $('<div class="mainPanel"></div>').css({
				height:constans.height-45,
				width:constans.width-20
			});
			constans.pageLoading = $('<div class="pageLoading"><div class="loadingTips">长按shift切换任务</div></div>').css({top:constans.height/2-100,left:constans.width/2-70});
			constans.mainPanel.append(constans.pageLoading);
			constans.mainPanel.appendTo(document.body);
		}
		constans.mainPanel.show();
		var offset = constans.mainPanel.offset();
		if(constans.mainPanel.find('#'+id).length==0){
			constans.mainPanel.find('.pageLoading').show();
			var $iframe = $('<iframe class="main_frame" src=""  id="'+id+'" frameborder="no"   border="no"></iframe>');
			constans.mainPanel.append($iframe);
			$iframe.attr('src',webRoot+url);
			$iframe.load(function(){
				$(this).css({width: '100%',height: '100%'});
				constans.pageLoading.hide();
				this.contentWindow.constans = constans;
				$(this.contentWindow.document.body).bind('keydown',helper.showHelper).bind('keyup',helper.hideHelper);
				if(fn)
					fn.call(scope||this);
			});
		}
		showTab(id);
	}
    return {
		showTab : showTab,
		openUrl:openUrl
    }
});
