/**
 * 图形类
 */
function AnyChartDynClass(){
	/**
	 * 内饼图
	 */
	this.getInPieChart = function(pointsList,chartParams){
		var xml = null;
		xml = document.getElementById('xmlArea_in_pie').value;
		var anychartData = "<series name='"+chartParams.searisName+"'>\n";
		for (var j = 0; j < pointsList.length; j++) {
			anychartData += "<point name='" + pointsList[j].CHARTNAME+ "' y='"
					+ pointsList[j].CHARTNUM + "' color='" + pointsList[j].COLOR+ "'/>\n";
		}
		anychartData += "</series>\n";
		
		var doublePie = new AnyChart(webRoot+'public/AnyChart/swf/AnyChart.swf'); 
		doublePie.width = chartParams.chartWidth;
		doublePie.wMode="transparent";
		doublePie.height = chartParams.chartHeight;
		
		var data = anychartData;
		xml = xml.replace('{title}', chartParams.chartTitle);
		xml = xml.replace('{Y_AXIS}', chartParams.yAxis);
		xml = xml.replace('{tdata}', chartParams.tdata);
		xml = xml.replace('{dtunit}', chartParams.dtunit);
		xml = xml.replace('{pcunit}', chartParams.pcunit);
		xml = xml.replace('{data}', data);
		doublePie.setData(xml);
		
		doublePie.write(chartParams.divId);
	};
	/**
	 * 柱状图
	 */
	this.getBarChart = function(pointsList,chartParams){
		var xml = null;
		xml = document.getElementById('xmlArea_bar').value;
		var anychartData = "<series name='"+chartParams.searisName+"'>\n";
		for (var j = 0; j < pointsList.length; j++) {
			anychartData += "<point name='" + pointsList[j].CHARTNAME+ "' y='"
			+ pointsList[j].CHARTNUM + "' color='" + pointsList[j].COLOR+ "'/>\n";
		}
		anychartData += "</series>\n";
		
		var doublePie = new AnyChart(webRoot+'public/AnyChart/swf/AnyChart.swf'); 
		doublePie.width = chartParams.chartWidth;
		doublePie.wMode="transparent";
		doublePie.height = chartParams.chartHeight;
		
		var data = anychartData;
		xml = xml.replace('{title}', chartParams.chartTitle);
		xml = xml.replace('{Y_AXIS}', chartParams.yAxis);
		xml = xml.replace('{pcunit}', chartParams.pcunit);
		xml = xml.replace('{data}', data);
		doublePie.setData(xml);
		
		doublePie.write(chartParams.divId);
	}
	/**
	 * 折线图
	 */
	this.getLineChart = function(pointsList,chartParams){
		var xml = null;
		xml = document.getElementById('xmlArea_tip_line').value;
		var anychartData = "<series name='"+chartParams.searisName+"'>\n";
		for (var j = 0; j < pointsList.length; j++) {
			anychartData += "<point name='" + pointsList[j].CHARTNAME+ "' y='"
					+ pointsList[j].CHARTNUM + "' color='" + pointsList[j].COLOR+ "'/>\n";
		}
		anychartData += "</series>\n";

		var doublePie = new AnyChart(webRoot+'public/AnyChart/swf/AnyChart.swf'); 
		doublePie.width = chartParams.chartWidth;
		doublePie.wMode="transparent";
		doublePie.height = chartParams.chartHeight;
		
		var data = anychartData;
		xml = xml.replace('{title}', chartParams.chartTitle);
		xml = xml.replace('{Y_AXIS}', chartParams.yAxis);
		xml = xml.replace('{pcunit}', chartParams.pcunit);
		xml = xml.replace('{data}', data);
		doublePie.setData(xml);
		
		doublePie.write(chartParams.divId);
	}
}
