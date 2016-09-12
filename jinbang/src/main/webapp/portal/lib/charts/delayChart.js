constans.delayChart=function(menu,datas,w,h){
	option = {
		animation:!$.isIE8m(),
		addDataAnimation:!$.isIE8m(),
		color:[ 
		    '#32cd32', '#87cefa', '#da70d6', '#ff0000', '#6495ed', 
		    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0', 
		    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700', 
		    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0' 
		],
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
		title : {
	        text: menu.title||'消息时延',
	        subtext: menu.subTitle||'最近24小时',
	        x:'right',
	        y:'top'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['<20ms','<50ms','<200ms','>200ms'],
	        x:'center',
	        y:'top'
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
	            name:'<20ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(2000+Math.round(Math.random()* 300));
	                }
	                return list;
	            }()
	        },{
	            name:'<50ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(600+Math.round(Math.random()* 300));
	                }
	                return list;
	            }()
	        },{
	            name:'<200ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(200+Math.round(Math.random()* 90));
	                }
	                return list;
	            }()
	        },{
	            name:'>200ms',
	            type:'line',
	            data:function (){
	                var list = [];
	                for (var i = 1; i <= 30; i++) {
	                    list.push(20+Math.round(Math.random()* 100));
	                }
	                return list;
	            }()
	        }
	    ]
	};
	return option;
};