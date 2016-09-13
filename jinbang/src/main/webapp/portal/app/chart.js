/**
 * 报表图表
 * 
 * @吕衍辉  qq:569934930 
 */
 
define(['jq','app/utils','../../public/ECharts/echarts-plain','charts/businessChart','charts/businessTestChart','charts/delayChart','charts/delayPieChart','charts/heartChart','charts/returnCodeChart','jquery.mousewheel','charts/countChart','../../component/chart/generalChart'],function (a,b) {
	if($.isIE8m()){
		alert('检测到用户使用不是现代浏览器，图表需要消耗一定性能，请尽早更换chrome等现代浏览器');
	}
	function initwheel(){
		constans.pageNo = 1;
		$(document.body).unbind('mousewheel');
		function changePage(direction){
			$(document.body).unbind('mousewheel');
			$('.box').stop().each(function(index,box){
				$(box).stop().animate({left:$(box).offset().left+direction*constans.width},{ 
				    easing: 'easeInQuart', 
				    duration: 400, 
				    complete: function(){
						if(index==0){
							$(document.body).bind('mousewheel', mousewheel);
						}
					}
				});
			});
		}
		function mousewheel(event, delta) {
			if(event.target&&event.target.tagName&&event.target.tagName.toUpperCase()=='IFRAME'){
				return;
			}
			$(document.body).unbind('mousewheel');
			constans.pageNo+=(delta>0?-1:1);
			if(constans.pageNo<=0){
				constans.pageNo=1;
				$(document.body).bind('mousewheel', mousewheel);
				return true;
			}
			if(constans.pageNo>constans.pageSize){
				constans.pageNo=constans.pageSize;
				$(document.body).bind('mousewheel', mousewheel);
				return true;
			}
			changePage((delta>0?1:-1));
			return true;  
		}
		$(document.body).bind('mousewheel', mousewheel);
	}
	
	function chartInit(menus){
		var heartTimerTicket;
		$('.chart').dblclick(function(){
////			$('.box').hide();
//			var $box = $('<div class="dropStatistics">asdfasdfs<iframe  style="width:100%;height:100%" src='+ctx+'/portal/dropStatistics.jsp></iframe></div>').css({
//					height:constans.height,
//					width:constans.width,
//					left:5,
//					zIndex:9999
//				});
//				if($.isIE8m()){
//					$box.css({backgroundColor:'#fff'});
//				}
//				$(document.body).append($box);
//			constans.chartPanelbg = $('<img class="chart-panel-bg" /src="'+webRoot+'portal/images/chart_bg.png" />')
//			.click(function(){
//				$(".dropStatistics").remove();
//			}).appendTo(document.body);
//			window.showModalDialog(ctx+'/portal/dropStatistics.jsp','dropStatistics','dialogWidth:'+document.body.clientWidth+'px;dialogHeight:'+document.body.clientHeight+'px');
			window.open(ctx+'/portal/dropStatistics.jsp');
		});
		$('.chart').each(function(index,dom){
			// 基于准备好的dom，初始化echarts图表
			var menu = menus[parseInt(index/4)].children[index%4];
			if(!menu) return true;
			if(menu.type=='generalChart'){
				$(dom).empty();
				var w = $(dom).width(),h=$(dom).height();
				$.post(ctx+'/chartController/load.do',{chartId:menu.chartId},function(chart){
					var names = [];
					chart.ys = chart.chartDetails;
					for(var i=0;i<chart.ys.length;i++){
						names.push(chart.ys[i].title);
						chart.ys[i].data = chart.ys[i].sqlData;
						chart.ys[i].name = chart.ys[i].title;
						chart.ys[i].type = ChartCfg.typeMemu[parseInt(chart.ys[i].type)];
					}
					chart.xs = chart.xs.sqlData;
					constans.initMenu(chart,function(obj){
						var code = '(function(menu,w,h){ var '+chart.adviceConfig+'; return option})(obj,w,h);';
						var option = eval(code);
						if(option.xAxis){
							var temp = option.xAxis[0].data;
							option.xAxis[0].data=[];
							for(var i=0;i<temp.length;i++){
								option.xAxis[0].data.push(temp[i]);
							}
							
							for(var i=0;i<option.series.length;i++){
								var temp = option.series[i].data;
								option.series[i].data = [];
								for(var j=0;j<temp.length;j++){
									option.series[i].data.push(temp[j]);
								}
							}
						}
						var render = echarts.init(dom);
						render.setOption(option); 
					});
				});
			}
			else{
				delete menu['xs'];
				delete menu['chartDetails'];
				delete menu['adviceConfig'];
				delete menu['chartDetails'];
				$.post(webRoot+'chartController/loadChartElementData.do',menu,function(data){
					$(dom).empty();
					var w = $(dom).width(),h=$(dom).height();
					if(!data.success){
						$('<img src="'+webRoot+'/portal/images/error.gif"/>')
							.css({'margin-left':w/2-75+'px','margin-top':h/2-75+'px'})
								.appendTo($(dom));
						return;
					}
					var render = echarts.init(dom);
					if(!constans.chart_renders||constans.chart_renders.length==0)constans.chart_renders = [];
					constans.chart_renders.push(render);
		       		for(var key in constans){
			        	if(key==menu.type){
			        		var option = constans[key].call(render,menu,data.result,w,h);
			        		render.setOption(option); 
			        		render.chartType=key;
			        		if(constans[key+'_click']){
			        			render.on('click',constans[key+'_click']);
			        		}
			        		break;
			        	}
			        }
			        render.autoflush=menu.autoflush;
			        if(!constans.renders){constans.renders = []}
				});
			}
		});
	}
	function init(){
		$.post(webRoot+'chartController/loadChartData.do',{},function(data){
			var menus = data;
			if (!constans.chartPanelbg) {return;};
			constans.chartPanelbg.show();
			constans.pageSize = menus.length;
			for(var i=0;i<constans.pageSize;i++){
				var $box = $('<div class="box"></div>').css({
					height:constans.height-30,
					width:constans.width-10,
					left:(constans.width)*i+5
				});
				if($.isIE8m()){
					$box.css({backgroundColor:'#fff'});
				}
				$(document.body).append($box);
				
				for(var j=0;j<4;j++){
					var $chart = $('<div class="chart" ></div>').css({
						height:(constans.height-45)/2,
						width:(constans.width-25)/2
					});
					if(i*j>=menus.length-1&&menus.length!=1) {
						$('<img src="'+webRoot+'/portal/images/404.png"/>')
							.css({'margin-left':$chart.width()/2-276/2+'px','margin-top':$chart.height()/2-127/2+'px'})
							.appendTo($chart);
					}
					else{
							$('<img src="'+webRoot+'/portal/images/loading1.gif"/>')
								.css({'margin-left':$chart.width()/2-150/2+'px','margin-top':$chart.height()/2-150/2+'px'})
								.appendTo($chart);
					}
					$box.append($chart);
				}
			}
			chartInit(menus);//初始化图表
			initwheel();
		});
	}
	return {
		init:init
	}
});