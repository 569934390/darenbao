Ext.define('component.extChart.highChartInit', {
	init : function(){
		var me = this;
	}
});
Highcharts.setOptions({
	global: {
		useUTC: false
	},
	lang:{
		loading: '加载中...',
		rangeSelectorZoom: '缩放',
		rangeSelectorFrom: '从',
		rangeSelectorTo: '到',
		months: ['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月'],
		weekdays: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
		shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月']
	}
});
Highcharts.theme = {
   colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
   chart: {
      plotBackgroundColor: 'rgba(255, 255, 255, .9)',
      backgroundColor:'#fcfefc',
      plotShadow: true
   },
   title: {
      style: {
         color: '#000',
         font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   subtitle: {
      style: {
         color: '#666666',
         font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
      }
   },
   xAxis: {
      gridLineWidth: 1,
      lineColor: '#000',
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'

         }
      }
   },
   yAxis: {
      minorTickInterval: 'auto',
      lineColor: '#000',
      lineWidth: 1,
      tickWidth: 1,
      tickColor: '#000',
      labels: {
         style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
         }
      },
      title: {
         style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
         }
      }
   },
   legend: {
      itemStyle: {
         font: '9pt Trebuchet MS, Verdana, sans-serif',
         color: 'black'

      },
      itemHoverStyle: {
         color: '#039'
      },
      itemHiddenStyle: {
         color: 'gray'
      }
   },
   labels: {
      style: {
         color: '#99b'
      }
   },

   navigation: {
      buttonOptions: {
         theme: {
            stroke: '#CCCCCC'
         }
      }
   }
};
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);