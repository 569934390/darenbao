/**
 * 门户设置
 * 
 * @吕衍辉  qq:569934930 
 */
 
define(['jq','app/utils','../../public/ECharts/echarts-plain','../../component/chart/generalChart'],function (a,b) {
	var render;
	function chartInit($dom,menu){
		$dom.empty();
		if(render!=null){
			render.dispose();
			render = null;
		}
		var w = $dom.width(),h=$dom.height();
		render = echarts.init($dom[0]);
		var option = constans['generalChart'].call(render,menu,menu.data,w,h);
	    render.setOption(option);
	}
	
	function init(){
		var panelBg = $('<img class="chart-panel-bg panel-bg" /src="'+webRoot+'portal/images/chart_bg.png" />')
				.click(function(){
					$('.panel-bg').remove();
					$('.panel').remove();
					$('.menu-task:last').removeClass('menu-task-selected');
		}).appendTo(document.body);
		
		var $box = $('<div class="panel"></div>').css({
			height:constans.height-60,
			width:constans.width-60,
			left:30
		}).appendTo(document.body);
		
		var $chart = $('<div class="chart" ></div>').css({
			height:(constans.height-45)/2,
			width:(constans.width-25)/2
		});
		
		$box.append($chart);
		var sql = 'select * from test_table where 1=1';
		var $sqlArea = $('<textarea>'+sql+'</textarea>');
		
		$sqlPanel = $('<div></div>').css({
			position: 'absolute',
			top:(constans.height-25)/2,
			left:5,
			width:($box.width()-10),
			height:(constans.height-125)/2
		});
		$sqlPanel.append($sqlArea);
		$box.append($sqlPanel);
		var editor = CodeMirror.fromTextArea($sqlArea[0], {
	        mode: "text/x-plsql",
	        lineNumbers: true
	    });
		chartInit($chart,{
			title:'图表标题',
			subTitle:'副标题',
			data:{xs:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]}
		});//初始化图表
	}
	return {
		init:init
	}
});