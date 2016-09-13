// 基于准备好的dom，初始化echarts图表
var myChart3 = echarts.init(document.getElementById('chart3')); 

Ext.Ajax.request({     
	url:webRoot+'ocsStatisticsController/getSecondHandleNum.do',   
	success: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);                                                   
		var secondHandlexAxis=respText.xAxis;
		var secondHandleSeries=respText.series;  
		creatSecondHandleChart(secondHandleSeries,secondHandlexAxis);
	},   
	failure: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);   
		Ext.Msg.alert('错误', respText.error);   
	}     
         
});
function creatSecondHandleChart(secondHandleSeries,secondHandlexAxis){	
	var secondHandle = {
	    title : {
	        text: 'ocs系统每秒处理请求数',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	x : 'center',
        	y : 'bottom',
	        data:['ocs系统每秒返回CCA数']
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
	            name:'ocs系统每秒返回CCA数',
	            type:'bar',
	            data:secondHandleSeries
	           
	        }
	    ]
	};
               
	myChart3.setOption(secondHandle);
}
