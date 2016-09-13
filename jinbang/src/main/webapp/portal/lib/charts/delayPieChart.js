constans.delayPieChart_click=function(data){
	console.info(arguments,this);
	alert(this.chartType+"->"+data.name+":"+data.value)
}
constans.delayPieChart=function(menu,data){
	option = {
		color:[ 
		    '#32cd32', '#87cefa', '#da70d6', '#7b68ee', '#6495ed', 
		    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0', 
		    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700', 
		    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' 
		],
	    title : {
	         text: menu.title||'消息时延',
	        subtext: menu.subTitle||'最近24小时',
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
	        data:['<20ms','<50ms','<200ms','>200ms']
	    },
	    calculable : true,
	    series : [
	        {
	            name:'消息时延',
	            type:'pie',
	            radius : '65%',
	            center: ['50%', '50%'],
	            data:[
	                {value:335, name:'<20ms'},
	                {value:310, name:'<50ms'},
	                {value:234, name:'<200ms'},
	                {value:135, name:'>200ms'}
	            ],
                itemStyle : {
	                normal : {
	                    label : {
	                        position : 'inner',
	                        formatter : function (a,b,c,d) {return (d - 0).toFixed(0) + '%'}
	                    },
	                    labelLine : {
	                        show : false
	                    }
	                },
	                emphasis : {
	                    label : {
	                        show : true,
	                        formatter : "{b}\n{d}%"
	                    }
	                }
	            }
	        }
	    ]
	};
	return option;
};
