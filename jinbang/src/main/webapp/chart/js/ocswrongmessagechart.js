// 基于准备好的dom，初始化echarts图表
var myChart1 = echarts.init(document.getElementById('chart1')); 

Ext.Ajax.request({     
	url:webRoot+'ocsStatisticsController/getWrongMessageNum.do',  
//	params:{  
//		date:dateInt
//	},  
	success: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);                                                   
		var wrongMessageNumLegend=respText.legend;  
		var wrongMessageNumSeries=respText.series;  
		creatwrongMessageChart(wrongMessageNumLegend,wrongMessageNumSeries);
	},   
	failure: function(resp,opts) {   
		var respText = Ext.util.JSON.decode(resp.responseText);   
		Ext.Msg.alert('错误', respText.error);   
	}     
         
});
function creatwrongMessageChart(wrongMessageNumLegend,wrongMessageNumSeries){	
	var wrongMessageNum = {
	    title : {
	        text: 'ocs系统错误消息数统计',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        x : 'center',
	        y : 'bottom',
	        data:wrongMessageNumLegend
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
	            name:'ocs系统错误消息数统计',
	            type:'pie',
	            radius : ['20%', '55%'],
	            center : ['50%', '50%'],
	            roseType : 'area',
	            data:wrongMessageNumSeries
	        }
	    ]
	};

	myChart1.setOption(wrongMessageNum);
}
