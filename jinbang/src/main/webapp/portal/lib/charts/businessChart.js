var businessTimerTicket;
constans.businessChart_click=function(data){
	console.info(arguments,this);
	alert(this.chartType+"->"+data.name+":"+data.value)
}
constans.businessChart=function(menu,data,w,h){
	var series = [],index=-1,prev = '',types=[],typeSet=[],startDate=20300101,endDate=19000101,dates=[];
	for(var i=0;i<data.length;i++){
		var t = data[i][0].drop_date;
		t = parseInt(t);
		if(t<startDate)
			startDate = t;
		t = data[i][data[i].length-1].drop_date;
		t = parseInt(t);
		if(t>endDate)
			endDate = t;
	}
	var start = int2DateFormat(startDate);  
	var end = int2DateFormat(endDate);  
	setRange(dates,start,end);   
	
	var secs = [];
	for(var i=0; i<dates.length; i++){  
		var date = parseInt(dates[i].replace(/-/g,''));
	   	for(var k = 0;k<data.length;k++){
	   		if(!secs[k])
	   			secs[k] = [];
	   		var flag = true;
		   	for(var j=0;j<data[k].length;j++){
		   		if(parseInt(data[k][j].drop_date)==date){
					secs[k].push(parseInt(data[k][j].drop_statistics_result));
					flag = false;
					break;
		   		}
			}
			if(flag){
				secs[k].push(0);
			}
	   }
	}  
	option = {
		animation:!$.isIE8m(),
		addDataAnimation:!$.isIE8m(),
		title : {
	        text: menu.title||'消息时延',
	        subtext: menu.subTitle||'最近24小时',
	        x:'right',
	        y:'top'
	    },
	    grid:{
			x:55,
			x2:60,
			y:30,
			y2:70
		},
		dataZoom : {
			y:h-48,
	        show : true,
	        realtime : true,
	        start : 20,
	        end : 80
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['数据','短信','语音']
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataZoom : {show: true},
	            dataView : {show: true},
	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : dates
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'数据',
	            type:'bar',
	            data:secs[0]||[]
	        },
	        {
	            name:'短信',
	            type:'bar',
	            data:secs[2]||[]
	        },
	        {
	            name:'语音',
	            type:'bar',
	            data:secs[1]||[]
	        }
	    ]
	};
	return option;
};
