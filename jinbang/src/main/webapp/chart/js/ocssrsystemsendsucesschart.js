// 基于准备好的dom，初始化echarts图表
var myChart6 = echarts.init(document.getElementById('chart6')); 

Ext.Ajax.request({     
	url:webRoot+'ocsStatisticsController/getSRSystemSendSucess.do',   
	success: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);                                                   
		var secondHandlexAxis=respText.xAxis;
		var secondHandleSeries=respText.series;  
		creatSRSystemSendSucessChart(secondHandleSeries,secondHandlexAxis);
	},   
	failure: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);   
		Ext.Msg.alert('错误', respText.error);   
	}     
         
});
function creatSRSystemSendSucessChart(secondHandleSeries,secondHandlexAxis){	
	var SRSystemSendSucess = {
	    title : {
	        text: 'SR转发成功率',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis',
	        formatter : '{b}<br/>{a}:{c}%'
	    },
	    legend: {
	        data:['消息转发成功率'],
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
	            axisLabel : {
	                formatter: '{value} %'
	            },
	            splitArea : {show : true}
	        }
	    ],
	    series : [
	        {
	            name:'消息转发成功率',
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
               
	myChart6.setOption(SRSystemSendSucess);
}
