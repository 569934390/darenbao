/**
 * 图形类
 */
function ExtChartClass(){
	/**
	 * 柱状图
	 */
	this.getBarChart = function(config){
		var barStore = Ext.create('Ext.data.JsonStore', {
	        fields: ['name', 'value'],
	        data: []
	    });
		
		var barChart =  Ext.create('Ext.chart.Chart', {
	        animate: true,
	        shadow: true,
	        store: barStore,
	        axes: [{
	            type: 'Numeric',
	            position: 'left',
	            fields: ['value'],
//	            title: 'Hits',
	            grid: true
//	            minimum: 0,
//	            maximum: 100
	        }, {
	            type: 'Category',
	            position: 'bottom',
	            fields: ['name'],
//	            title: 'Months',
	            label: {
	                rotate: {
	                    degrees: 0
	                }
	            }
	        }],
	        series: [{
	            type: 'column',
	            axis: 'left',
	            gutter: 80,
	            xField: 'name',
	            yField: ['value'],
	            tips: {
	                trackMouse: true,
	                width: 74,
	                height: 38,
	                renderer: function(storeItem, item) {
	                    this.setTitle(storeItem.get('name'));
	                    this.update(storeItem.get('value'));
	                }
	            },
	            style: {
	                fill: '#38B8BF'
	            }
	        }]
	    });
		
		return barChart;
	};
}
