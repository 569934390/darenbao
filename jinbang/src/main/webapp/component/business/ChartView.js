Ext.define('component.business.ChartView', {
	extend: 'Ext.chart.Chart',
	style: 'background:#fff',
    animate: false,
	constructor : function(config) {
		var me = this;
		var imagesStore = Ext.create('Ext.data.JsonStore', {
		    fields: ['name', 'data1', 'data2'],
		    data: (function(n, floor){
		        var data = [],
		            p = (Math.random() *  11) + 1,
		            i;
		        for (i = 0; i < 57; i++) {
		            data.push({
		                name: i,
		                data1: 0,
		                data2: 0
		            });
		        }
		        return data;
		    })()
		});
		this.store = imagesStore;
		var conditions = Ext.apply({}, config);
		me.callParent([conditions]);
	},
	axes: [{
        type: 'Numeric',
        position: 'left',
        fields: ['data1', 'data2'],
        title: '消息数',
        minimum: 0,
        maximum: 130,
        grid: true
    }, {
        type: 'Category',
        position: 'bottom',
        fields: ['name'],
        title: '      发包时间（秒）'
    }],
    series: [{
        type: 'column',
        axis: 'left',
        xField: 'name',
        yField: 'data1',
        highlight: true,
        tips: {
          trackMouse: true,
          width: 180,
          height: 28,
          renderer: function(storeItem, item) {
            this.setTitle('发送'+storeItem.get('data1') + '个消息包('+storeItem.get('name') +'/秒)');
          }
        },
        label: {
          display: 'insideEnd',
          'text-anchor': 'middle',
            field: 'data',
            renderer: Ext.util.Format.numberRenderer('0'),
            orientation: 'vertical',
            color: '#333'
        }
    }, {
        type : 'column',
		axis : 'left',
		highlight : true,
		style : {
			color: '#ff0000', 
			size: 20 	
		},
		tips : {
			trackMouse : true,
			width : 180,
			height : 40,
			renderer : function(storeItem, item) {
				this.setTitle('发送'
						+ storeItem.get('data1')
						+ '个消息包<br/>错误消息包个数：'
						+ (storeItem.get('data1') - storeItem
								.get('data2')) + '('
						+ storeItem.get('name') + '/秒)');
			}
		},
        xField: 'name',
        yField: 'data2'
    }]
});




//chartView = Ext.create('Ext.chart.Chart', {
//            style: 'background:#fff',
//            animate: true,
//            store: Ext.create('Ext.data.JsonStore', {
//			    fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
//			    data: (function(n, floor){
//			        var data = [],
//			            p = (Math.random() *  11) + 1,
//			            i;
//			        floor = (!floor && floor !== 0)? 20 : floor;
//			        
//			        for (i = 0; i < (n || 12); i++) {
//			            data.push({
//			                name: Ext.Date.monthNames[i % 12],
//			                data1: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data2: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data3: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data4: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data5: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data6: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data7: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data8: Math.floor(Math.max((Math.random() * 100), floor)),
//			                data9: Math.floor(Math.max((Math.random() * 100), floor))
//			            });
//			        }
//			        return data;
//			    })()
//			}),
//            legend: {
//                position: 'bottom'
//            },
//            axes: [{
//                type: 'Numeric',
//                position: 'left',
//                fields: ['data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7'],
//                title: 'Number of Hits',
//                grid: {
//                    odd: {
//                        opacity: 1,
//                        fill: '#ddd',
//                        stroke: '#bbb',
//                        'stroke-width': 1
//                    }
//                },
//                minimum: 0,
//                adjustMinimumByMajorUnit: 0
//            }, {
//                type: 'Category',
//                position: 'bottom',
//                fields: ['name'],
//                title: 'Month of the Year',
//                grid: true,
//                label: {
//                    rotate: {
//                        degrees: 315
//                    }
//                }
//            }],
//            series: [{
//                type: 'area',
//                highlight: false,
//                axis: 'left',
//                xField: 'name',
//                yField: ['data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7'],
//                style: {
//                    opacity: 0.93
//                }
//            }]
//        });