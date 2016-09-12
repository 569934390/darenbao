Ext.onReady(function() {
	var chart1 = new AnyChart(webRoot + 'public/AnyChart/swf/AnyChart.swf');
	chart1.width = '100%';
	chart1.height = '99%';
	function onAlarmLevelStaticDraw() {
		chart1.removeEventListener("draw", onAlarmLevelStaticDraw);
		chart1.updatePointData("sysBuy","pointer",{value: Math.random()*25+12.5});
	}
	var detailWin;
	function onPointClick(e) {
		if(!detailWin){
			Ext.create('Ext.data.Store', {
			    storeId:'simpsonsStore',
			    fields:['name', 'email', 'phone'],
			    data:{'items':[
			        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
			        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
			        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
			        { 'name': 'Marge', "email":"marge@simpsons.com", "phone":"555-222-1254"  }
			    ]},
			    proxy: {
			        type: 'memory',
			        reader: {
			            type: 'json',
			            root: 'items'
			        }
			    }
			});
			
			var grid = Ext.create('Ext.grid.Panel', {
			    title: 'Simpsons',
			    store: Ext.data.StoreManager.lookup('simpsonsStore'),
			    columns: [
			        { text: 'Name',  dataIndex: 'name' },
			        { text: 'Email', dataIndex: 'email', flex: 1 },
			        { text: 'Phone', dataIndex: 'phone' }
			    ]
			});
			detailWin = Ext.create('Ext.window.Window',{
				title:'详细数据',
				width:600,
				height:400,
			    closeAction:'hide',
				layout:'fit',
				items:[grid]
			});
		}
		detailWin.show();
	}
	chart1.addEventListener("draw", onAlarmLevelStaticDraw);
	chart1.setXMLFile('gauge.xml');
	chart1.write('chart1');
	
	var chart2 = new AnyChart(webRoot + 'public/AnyChart/swf/AnyChart.swf');
	chart2.width = '100%';
	chart2.height = '99%';
	chart2.addEventListener('pointClick', onPointClick);
	chart2.setXMLFile('line.xml');
	chart2.write('chart2');
	
	var chart3 = new AnyChart(webRoot + 'public/AnyChart/swf/AnyChart.swf');
	chart3.width = '100%';
	chart3.height = '99%';
	chart3.addEventListener('pointClick', onPointClick);
	chart3.setXMLFile('pie.xml');
	chart3.write('chart3');
	
	var chart4 = new AnyChart(webRoot + 'public/AnyChart/swf/AnyChart.swf');
	chart4.width = '100%';
	chart4.height = '99%';
	chart4.addEventListener('pointClick', onPointClick);
	chart4.setXMLFile('bar.xml');
	chart4.write('chart4');
});
