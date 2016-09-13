///**
// * jquery 滑动类
// * 
// * @吕衍辉  qq:569934930 
// */
//function chartInit(menus){
//	var heartTimerTicket;
//	$('.chart').each(function(index,dom){
//		// 基于准备好的dom，初始化echarts图表
//		var menu = menus[parseInt(index/4)].children[index%4];
//		if(index>=4)return;
//		if(!menu) return true;
//		$.post(webRoot+'chartController/loadChartElementData.do',menu,function(data){
//			$(dom).empty();
//			if(!data.success){
//				$('<img src="'+webRoot+'/portal/images/error.gif"/>')
//					.css({'margin-left':$(dom).width()/2-75+'px','margin-top':$(dom).height()/2-75+'px'})
//						.appendTo($(dom));
//				return;
//			}
//			var render = echarts.init(dom);
//       		for(var key in constans){
//	        	if(key==menu.type){
//	        		var option = constans[key].call(render,menu,data.result);
//	        		render.setOption(option); 
//	        		render.chartType=key;
//	        		if(constans[key+'_click']){
//	        			render.on('click',constans[key+'_click']);
//	        		}
//	        		break;
//	        	}
//	        }
//	        render.autoflush=menu.autoflush;
//	        if(!constans.renders){constans.renders = []}
//	        if(menu.autoflush){
//	        	clearInterval(heartTimerTicket);
//				heartTimerTicket = setInterval(function (){
//					for(var i=(constans.pageNo-1)*4;i<constans.pageNo*4;i++){
//						var r = constans.renders[i];
//						if(r&&r.autoflush){
//							var option = r.getOption();
//						    option.series[0].data[0].value = (Math.random()*2+60).toFixed(2) - 0;
//						    option.series[1].data[0].value = (Math.random()*7).toFixed(2) - 0;
//						    option.series[2].data[0].value = (Math.random()*2).toFixed(2) - 0;
//						    option.series[3].data[0].value = (Math.random()*2).toFixed(2) - 0;
//						    r.setOption(option,true);
//						}
//					};
//				},2000);
//	        }
//		});
//	});
//}
//
//$(function(){
//	$.post(webRoot+'chartController/loadChartData.do',{},function(data){
//		menus = data;
//		constans.pageSize = menus.length;
//		for(var i=0;i<constans.pageSize;i++){
//			var $box = $('<div class="box"></div>').css({
//				height:constans.height-50,
//				width:constans.width-20,
//				left:(constans.width)*i+10
//			});
//			$(document.body).append($box);
//			
//			for(var j=0;j<4;j++){
//				var $chart = $('<div class="chart" ></div>').css({
//					height:(constans.height-65)/2,
//					width:(constans.width-35)/2
//				});
//				if(i*j>=menus.length-1&&menus.length!=1) {
//					$('<img src="'+webRoot+'/portal/images/404.png"/>')
//						.css({'margin-left':$chart.width()/2-276/2+'px','margin-top':$chart.height()/2-127/2+'px'})
//						.appendTo($chart);
//				}
//				else{
//						$('<img src="'+webRoot+'/portal/images/loading.gif"/>')
//							.css({'margin-left':$chart.width()/2-150/2+'px','margin-top':$chart.height()/2-150/2+'px'})
//							.appendTo($chart);
//				}
//				$box.append($chart);
//			}
//			Portal.dock.insert({title:menus[i].nodeName,icon:menus[i].nodeIcon},i);
//		}
////		$('#jqDock').jqDock(constans.jqDockOpts);
//		chartInit(menus);//初始化图表
//	});
//});