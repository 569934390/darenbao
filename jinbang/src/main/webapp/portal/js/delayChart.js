constans.delayChart=function(menu){
	option = {
		animation:!$.isIE8m(),
		addDataAnimation:!$.isIE8m(),
		title : {
	        text: menu.title||'消息时延',
	        subtext: menu.subTitle||'最近24小时',
	        x:'right',
	        y:'top'
	    },
    	backgroundColor:'#fff',
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['<20ms','<50ms','<200ms','>200ms']
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
	    dataZoom : {
	        show : true,
	        realtime : true,
	        start : 20,
	        end : 80
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push('2013-03-' + i);
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
	            name:'<20ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(Math.round(Math.random()* 300));
	                }
	                return list;
	            }()
	        },{
	            name:'<50ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(Math.round(Math.random()* 300));
	                }
	                return list;
	            }()
	        },{
	            name:'<200ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(Math.round(Math.random()* 90));
	                }
	                return list;
	            }()
	        },{
	            name:'>200ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(Math.round(Math.random()* 100));
	                }
	                return list;
	            }()
	        },
	        {
	            name:'消息时延',
	            type:'pie',
	            tooltip : {
	                trigger: 'item',
	                formatter: '{a} <br/>{b} : {c} ({d}%)'
	            },
	            center: [120,80],
	            radius : [0, 30],
	            itemStyle :　{
	                normal : {
	                    labelLine : {
	                        length : 20
	                    }
	                }
	            },
	            data:[
	                {value:1048, name:'最大时延'},
	                {value:251, name:'最小时延'},
	                {value:147, name:'平均时延'},
	                {value:102, name:'成功率'}
	            ]
	        }
	    ]
	};
	return option;
};