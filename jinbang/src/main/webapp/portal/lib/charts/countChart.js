var businessTimerTicket;
constans.countChart_click=function(data){
	console.info(arguments,this);
	alert(this.chartType+"->"+data.name+":"+data.value)
}
constans.countChart=function(menu,data){
	option = {
	    title : {
	        text: menu.title||'消息总量',
	        subtext: menu.subTitle||'最近30天',
	        x:'right',
	        y:'top'
	    },
	    grid:{
			x:55,
			x2:60,
			y:30,
			y2:60
		},
		dataZoom : {
	        show : true,
	        realtime : true,
	        start : 20,
	        end : 80
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['消息总量']
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : true,
	            data : function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push('2014-07-' + i);
	                }
	                return list;
	            }()
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'消息总量',
	            type:'line',
	            smooth:true,
	            itemStyle: {normal: {areaStyle: {type: 'default'}}},
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(100+Math.random()*10);
	                }
	                return list;
	            }()
	        }
	    ]
	};
	return option;
};
