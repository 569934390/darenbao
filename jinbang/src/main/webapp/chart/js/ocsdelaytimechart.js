// 基于准备好的dom，初始化echarts图表
var myChart2 = echarts.init(document.getElementById('chart2')); 

Ext.Ajax.request({     
	url:webRoot+'ocsStatisticsController/getDelayTimeNum.do',   
	success: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);                                                   
		var delayTimeNumLegend=respText.legend;  
		var delayTimeNumSeries=respText.series;  
		creatdelayTimeChart(delayTimeNumLegend,delayTimeNumSeries);
	},   
	failure: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);   
		Ext.Msg.alert('错误', respText.error);   
	}     
         
});
function creatdelayTimeChart(delayTimeNumLegend,delayTimeNumSeries){	
	var delayTime = {
	    title : {
	        text: 'ocs消息延时数统计',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        x : 'center',
		    y : 'bottom',
	        data:delayTimeNumLegend
	    },
	    toolbox: {
	        show : true,
	        x : 'left',
	        orient : 'vertical',
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name:'ocs消息延时数统计',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '50%'],
	            data:delayTimeNumSeries
	        }
	    ]
	};
                    
	myChart2.setOption(delayTime);
}
