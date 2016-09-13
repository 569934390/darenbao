constans.generalChart_click=function(data){
	alert(this.chartType+"->"+data.name+":"+data.value)
}
constans.generalChart=function(menu,w,h){
	var names = [];
	for(var i=0;i<menu.ys.length;i++){
		names.push(menu.ys[i].name);
	}
	var option = {
		animation:!$&&$.isIE8m(),
		addDataAnimation:!$&&$.isIE8m(),
		title : {
	        text: menu.title||'图表标题',
	        subtext: menu.subTitle||'副标题',
	        x:'right',
	        y:'top'
	    },
	    grid:{
			x:55,
			x2:60,
			y:30,
			y2:70
		},
		dataZoom : {
			y:h-48,
	        show : true,
	        realtime : true,
	        start : 20,
	        end : 80
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:names
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : true,
	            data : menu.xs
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : menu.ys
	};
	return option;
};
if(!$.Persistent)$.Persistent={};
$.Persistent.getPages = function(entity,fn,hideTip){
	var str = '';
	if(JSON&&JSON.stringify){
		str = JSON.stringify(entity);
	}
	else{
		str = Ext.encode(entity);
	
	}
	$.post(ctx+'/base/getPages.do',{pageStr:str,nativeSql:'true'},function(result){
		fn.call(this,result);
	});
};
constans.initMenu = function(menu,callback){
	var params = [{
		sqlKey:menu.xs,
		limit:50,
		start:0
	}];
	for(var i=0;i<menu.ys.length;i++){
		params.push({
			sqlKey:menu.ys[i].data,
			limit:50,
			start:0
		});
	}
	$.Persistent.getPages(params,function(datas){
		for(var i=0;i<datas.length;i++){
			if(typeof datas[i] =='string'){
				if(datas[i].indexOf('error:')==-1){
					if(i==0){
						menu.xs = eval(datas[i]);
					}
					else{
						menu.ys[i-1].data = eval(datas[i]);
					}
				}
				else console.info('报表报错提示',datas[i]);
			}
			else{
				if(i==0){
					menu.xs = [];
					for(var j = 0;j<datas[i].result.length;j++){
						for(var k in datas[i].result[j]){
							menu.xs.push(datas[i].result[j][k]);break;
						}
					}
				}
				else{
					menu.ys[i-1].data = [];
					for(var j = 0;j<datas[i].result.length;j++){
						for(var k in datas[i].result[j]){
							menu.ys[i-1].data.push(parseInt(datas[i].result[j][k]));break;
						}
					}
					for(var j=menu.ys[i-1].data.length;j<menu.xs.length;j++){
						menu.ys[i-1].data.push(0);
					}
				}
			}
		}
		for(var i=0;i<menu.ys.length;i++){
			delete menu.ys[i].sqlData;
			delete menu.ys[i].title;
			delete menu.ys[i].xName;
			delete menu.ys[i].yName;
			delete menu.ys[i].chartId;
			delete menu.ys[i].detailId;
		}
		var result = {title:menu.title,subTitle:menu.subTitle,xs:menu.xs,ys:menu.ys};
		console.info(result);
		callback(result);
	});
}
constans.generalChartString = "option = {\n"+
"		title : {\n"+
"	        text: menu.title||'图表标题',\n"+
"	        subtext: menu.subTitle||'副标题',\n"+
"	        x:'right',\n"+
"	        y:'top'\n"+
"	    },\n"+
"	    grid:{\n"+
"			x:55,\n"+
"			x2:60,\n"+
"			y:30,\n"+
"			y2:70\n"+
"		},\n"+
"		dataZoom : {\n"+
"			y:h-48,\n"+
"	        show : true,\n"+
"	        realtime : true,\n"+
"	        start : 20,\n"+
"	        end : 80\n"+
"	    },\n"+
"	    tooltip : {\n"+
"	        trigger: 'axis'\n"+
"	    },\n"+
"	    legend: {\n"+
"	        data:names\n"+
"	    },\n"+
"	    calculable : true,\n"+
"	    xAxis : [\n"+
"	        {\n"+
"	            type : 'category',\n"+
"	            boundaryGap : true,\n"+
"	            data : (function(){return menu.xs})()\n"+
"	        }\n"+
"	    ],\n"+
"	    yAxis : [\n"+
"	        {\n"+
"	            type : 'value'\n"+
"	        }\n"+
"	    ],\n"+
"	    series : (function(){return menu.ys})()\n"+
"	}";