// 基于准备好的dom，初始化echarts图表
var myChart5 = echarts.init(document.getElementById('chart5')); 

Ext.Ajax.request({     
	url:webRoot+'ocsStatisticsController/getMessageLostNum.do',   
	success: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);                                                   
		var secondHandlexAxis=respText.xAxis;
		var secondHandleSeries=respText.series;  
		creatMessageLostNumChart(secondHandleSeries,secondHandlexAxis);
	},   
	failure: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);   
		Ext.Msg.alert('错误', respText.error);   
	}     
         
});
function creatMessageLostNumChart(secondHandleSeries,secondHandlexAxis){	
	var messageLostNum = {
	    title : {
	        text: 'ocs系统协议级消息丢包数',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['消息丢包数'],
	        x : 'center',
	        y : 'bottom'
	    },
	    toolbox: {
	        show : true,
	        x : 'left',
	        orient : 'vertical',
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : secondHandlexAxis
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            splitArea : {show : true}
	        }
	    ],
	    series : [
	        {
	            name:'消息丢包数',
	            type:'line',
	            itemStyle: {
	                normal: {
	                    lineStyle: {
	                        shadowColor : 'rgba(0,0,0,0.4)',
	                        shadowBlur: 5,
	                        shadowOffsetX: 3,
	                        shadowOffsetY: 3
	                    }
	                }
	            },
	            data:secondHandleSeries,
	           
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        }
	    ]
	};
               
	myChart5.setOption(messageLostNum);
}
