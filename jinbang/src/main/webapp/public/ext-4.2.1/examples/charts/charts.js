Ext.require([
]);

Ext.onReady(function() {
	window.generateData = function(n, floor){
        var data = [],
            p = (Math.random() *  11) + 1,
            i;
            
        floor = (!floor && floor !== 0)? 20 : floor;
        data.push({
        	name: Ext.Date.parse('2012-04-01','Y-m-d'),
             data1: Math.floor(Math.max((Math.random() * 100), floor))
        });
        data.push({
        });
        data.push({
        });
        for (i = 0; i < (n || 12); i++) {
        	if(i==2||i==3)continue;
            data.push({
                name: Ext.Date.parse('2013-0'+(i+1)+'-01','Y-m-d'),
                data1: Math.floor(Math.max((Math.random() * 100), floor))
            });
        }
        return data;
    };

    window.store1 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData(8)
    });
    window.loadTask = new Ext.util.DelayedTask();
    store1.loadData(generateData(8));
    console.info(generateData(8))
    var chart = Ext.create('Ext.chart.Chart', {
            style: 'background:#fff',
            animate: true,
            store: store1,
            shadow: true,
            theme: 'Category1',
            legend: {
                position: 'right'
            },
            axes: [{
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: ['data1'],
                title: '中文字体',
                grid: {
		            odd: {
		                opacity: 1,
		                fill: '#ddd',
		                stroke: '#bbb',
		                'stroke-width': 1
		            }
		        },
		        labelTitle: {
		            font: '30px 微软雅黑',
		            orientation:'horizontal',
		            fill: 'red'
		        },
		        label: {
		            font: '12px 宋体'
		        }
            }, {
                type: 'Time',
                position: 'bottom',
                step:[Ext.Date.MONTH, 2],
                fields: ['name'],
                dateFormat: 'Y/m',
                title: 'Month of the Year'
            }],
            series: [{
                type: 'line',
	            highlight: {
	                size: 7,
	                radius: 7
	            },
	            axis: 'left',
	            xField: 'name',
	            yField: 'data1',
	            smooth:true,
	            markerConfig: {
		            type: 'circle',
		            radius: 0,
		            'fill': '#f00'
		        }
            }]
        });
    var win = Ext.create('Ext.Window', {
        width: 800,
        height: 400,
        minHeight: 400,
        minWidth: 550,
        hidden: false,
        maximizable: true,
        title: 'Line Chart',
        renderTo: Ext.getBody(),
        layout: 'fit',
        items: chart
    });
});
