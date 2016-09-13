constans.returnCodeChart_click=function(data){
	alert(this.chartType+"->"+data.name+":"+data.value)
}
constans.returnCodeChart=function(menu,data){
	records = data[0];
	var series = [],index=-1,prev = '',types=[],typeSet=[];
	for(var i=0;i<records.length;i++){
		if(index>10) break;
		if(records[i].drop_date!=prev){
			series.push({
                name:int2DateFormat(records[i].drop_date),
                type:'pie',
                itemStyle : {normal : {
                    label : {show : index > 28},
                    labelLine : {show : index > 28, length:20}
                }},
                radius : [index * 8 + 40, index * 8 + 47],
                data:[]
            });
            prev = records[i].drop_date;
            index++;
		}
		types[records[i].drop_statistics_type] = records[i].drop_statistics_type;
		series[index].data.push({value:records[i].drop_statistics_result,name:records[i].drop_statistics_type});
	}
	for(var type in types){
		typeSet.push(type);
	}
	series[0].markPoint = {
        symbol:'emptyCircle',
        symbolSize:series[0].radius[0],
//        effect:{show:!$.isIE8m(),scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
        data:[{x:'50%',y:'50%'}]
    };
	option = {
		animation:!$.isIE8m(),
		addDataAnimation:!$.isIE8m(),
	    title : {
	        text: menu.title||'返回码错误率',
	        subtext: menu.subTitle||'最近30天',
	        x:'right',
	        y:'top'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:typeSet
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : false,
	    series :series
//	    series : (function (){
//				        var series = [];
//				        for (var i = 0; i < 10; i++) {
//				            series.push({
//				                name:'返回码错误率',
//				                type:'pie',
//				                itemStyle : {normal : {
//				                    label : {show : i > 28},
//				                    labelLine : {show : i > 28, length:20}
//				                }},
//				                radius : [i * 8 + 40, i * 8 + 47],
//				                data:[
//				                    {value: i *16  + 680,  name:'信息类'},
//				                    {value: i * Math.random()*100  + 160,  name:'协议错误'},
//				                    {value: i * Math.random()*100  + 320,  name:'短暂失败'},
//				                    {value: i * Math.random()*100  + 10,  name:'永久失败'}
//				                ]
//				            })
//				        }
//				        series[0].markPoint = {
//				            symbol:'emptyCircle',
//				            symbolSize:series[0].radius[0],
//				            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
//				            data:[{x:'50%',y:'50%'}]
//				        };
//				        return series;
//				    })()
	};
	return option;
};
