/**
 * ExtAnyChart.js  ---  封装AnyChart的Ext.Panel
 */
Ext.ns('Ext.ux.chart');

Ext.ux.chart.ExtAnyChart = function(config){
	Ext.ux.chart.ExtAnyChart.superclass.constructor.call(this,config);
};

Ext.ux.chart.ExtAnyChart.CHART_URL = ctx + '/static/widgets/AnyChart/swf/AnyChart.swf';

Ext.extend(Ext.ux.chart.ExtAnyChart, Ext.Panel, {
	height : 250,
	series : [],
	ctype : 'Bar',             //目前比较完整提供Bar、Line、Pie的支持 -- 决定图例和PlotType、SeriesType
	mode : 'Normal',
	format : 'flat',           //源数据格式 ---决定了数据解析格式
	unit : '元',               //数值单位---会在Y轴顶部显示
	legendEnabled : true,      //是否使用图例
	legendPosition : 'Bottom', //图例位置
	labelRotation : 0,    //标签文字方向
	labelValign : 'Top',  //数据标签位置
	tooltipsFormat : '{%SeriesName}\n{%Name}: {%YValue}{numDecimals:2,trailingZeros:false,thousandsSeparator:}',
	thresholdValue : 0,   //阀值线，常用于与标杆值、平均值做比较，当为0时，不显示
	thresholdTitle : '',
	thresholdRange : [],	//阀值区间
	legendItems : {Pie:'Points', Bar:"Series", HBar:"Series", Line:'Series'},
	labelFormats : {Pie:'{%Name} {%Value}{numDecimals:2,trailingZeros:false,thousandsSeparator:}\n占比：{%YPercentOfSeries}%', 
		Bar:"{%Value}{numDecimals:2,trailingZeros:false,thousandsSeparator:}", 
		HBar:"{%Value}{numDecimals:2,trailingZeros:false,thousandsSeparator:}", 
		Line:'{%Value}{numDecimals:2,trailingZeros:false,thousandsSeparator:}'},
	tooltipsFormats : {Pie:'{%Name} {%Value}{scale:(1000)(10)(1000)|(千)(万)(亿),thousandsSeparator:},{%YPercentOfSeries}%', Bar:"{%Value}{thousandsSeparator:}", Line:'{%Value}{thousandsSeparator:}'},
	plotTypes : {Pie:'Pie',Bar:'CategorizedVertical',HBar:'CategorizedHorizontal',Line:'CategorizedVertical'},
	initComponent : function(){
		//一些默认的不可更改的配置
		var ths = this;
		this.layout = 'fit';
		
		Ext.ux.chart.ExtAnyChart.superclass.initComponent.call(this);

		if(this.store){
			this.store.addListener('load',function(store){
				if(ths.setDataFn){
					var data = ths.setDataFn(store);
					ths.loadData(data);
				}
				else{
					ths.loadData(store.reader.jsonData['data']);
				}
			});
//			this.store.on('load',function(store){
//				ths.loadData(store.reader.jsonData['data']);
//			});
		}
		
		this.addEvents(
			/**
			 * @event select
			 * @param data 参见AnyChart的文档--List of Values Available in Point Events
			 */
			'select');
		
		this.addEvents(
			/**
			 * @event click
			 * @param data 参见AnyChart的文档--List of Values Available in Point Events
			 */
			'click');
		this.createChart();
	},
	getStore : function(){
		return this.store;
	},
	createChart : function(){
		var ths = this;
		var internalChart = new AnyChart(Ext.ux.chart.ExtAnyChart.CHART_URL);
		try{
			internalChart.addEventListener('pointSelect', function(e){
				try{
					ths.fireEvent('select', e.data);
				}catch(ex){
					alert(ex);
				}
			});
			internalChart.addEventListener('pointClick', function(e){
				try{
					ths.fireEvent('click', e.data);
				}catch(ex){
					alert(ex);
				}
			});
		}catch(ex){
			alert(ex);
		}
		
		internalChart.width = '100%';
		internalChart.height = '100%';
		this._chart = internalChart;
	},
	onRender : function(ct, position){
		Ext.ux.chart.ExtAnyChart.superclass.onRender.call(this, ct, position);
		this._chart.write(this.body.id);
	},
	loadData : function(data, format){
		var ct = Ext.ux.chart.ExtAnyChart.xml,
		    dt = Ext.ux.chart.ExtAnyChart.dataXml,
		    st = Ext.ux.chart.ExtAnyChart.seriesXml;
		format = format||this.format;
		
		switch(format){
			case 'flat'   : this.processFlatData(data);
			case 'series' : this.processSeriesData(data);
			case 'point'  : this.processPointData(data);
		}
		if(this.thresholdRangeFn)
			this.thresholdRangeFn(this,data);
		var dataStr = ct.apply({
			data : dt.apply({series:st.apply(this.series)}), 
			title : this.ctitle,
			type : this.ctype,
			minimum:this.minimum==true?'minimum="0"':'',
			mode : this.mode,
			plotType : this.plotType||this.plotTypes[this.ctype],
			unit : this.unit,
			labelRotation : this.labelRotation,
			labelFormat : this.labelFormat||this.labelFormats[this.ctype],
			tooltipsFormat : (this.tooltipsFormat||this.tooltipsFormats[this.ctype]) + this.unit,
			labelValign : this.labelValign,
			thresholdValue : this.thresholdValue,
			thresholdTitle : this.thresholdTitle,
			thresholdRange : this.thresholdRange,
			legendEnabled : this.legendEnabled,
			legendPosition : this.legendPosition,
			legendPosition : this.legendPosition,
			xAxisTime:this.xAxisTime||1,
			yNum : this.yNum||'',
			legendItem : this.legendItem||this.legendItems[this.ctype]});
		
		this._chart.setData(dataStr);
	},
	/**
	 * 目前只允许接受两种格式的数据<br/>
	 * 1、Series List eg. [series1, series2, ...]    ---  该格式与AnyChart的data xml格式天然匹配
	 *    series1 = {SeriesNameField:'分拣量', PointField:[point1, point2]}   
	 *    point1  = {PointNameField:'厦门', PointYField:'100', OtherAttrField:...}
	 *    配置指定SeriesNameField、PointField、PointNameField、PointYField  
	 *    TODO 这些配置项可以交给一个函数，只要函数返回满足条件格式的数据即可
	 *    
	 * 2、Series Point List/Flat eg. [series1, series2, ...] 
	 *    series1 = {SeriesNameField:'分拣量',PointNameField:'厦门', PointYField:'100', OtherAttrField:...}
	 *    需要处理分组，生成格式1的数据
	 * 
	 * 3、Point List eg. [point1, point2, ...]
	 *    point1  = {PointNameField:'厦门', PointYFieldForSeries1:'100', PointYFieldForSeries2:'120'}
	 *    配置指定PointNameField、Series=[{name:'分拣量',valueField:PointYFieldForSeries1},{name:'已拣量',valueField:PointYFieldForSeries2},...]
	 *    需要根据Series配置生成格式1的数据
	 *    
	 * @param {Array} data
	 */
	processSeriesData : function(data){
		delete this.series;
		this.series = [];
		
		if(!Ext.isArray(data)){
			data = [].concat(data);
		}
		
		//格式一 注意city/subject的出现顺序，决定Point/Series显示的顺序
		//data = [{subject:'总分拣量',lists:[{city:'福州', amt:'140'},{city:'厦门', amt:'140'}]},
		//        {subject:'已分拣量',lists:[{city:'福州', amt:'130'},{city:'厦门', amt:'100'}]}];
		//this.SeriesNameField = "subject";
		//this.PointField = "lists";
		//this.PointNameField = "city";
		//this.PointYField = "amt";
		
		Ext.each(data,function(s){
			var so = {};
			so.name = s[this.SeriesNameField];
			so.points = [];
			Ext.each(s[this.PointField],function(p){
				var point = {name:p[this.PointNameField], y:p[this.PointYField], attrs:[]};
				
				if(this.AttributesList){
					Ext.each(this.AttributesList, function(attr){
						point.attrs.push({name:attr, value:item[attr]});
					}, this);
				}
				so.points.push(point);
				
			},this);
			this.series.push(so);
		},this);
	},
	processFlatData : function(data){
		delete this.series;
		this.series = [];
		
		if(!Ext.isArray(data)){
			data = [].concat(data);
		}
		
		//格式二   注意city/subject的出现顺序，决定Point/Series显示的顺序
		//data = [{city:'福州', subject:'总分拣量', amt:'120'}, {city:'厦门', subject:'总分拣量', amt:'90'}];
		//this.SeriesNameField = "subject";
		//this.PointNameField = "city";
		//this.PointYField = "amt";
		
		var seriesObj = {};  //利用Obj即是Map的特性来临时存储Series分组
		var seriesNameArr = []; //有序存储序列
		Ext.each(data, function(item){
			if(!seriesObj[item[this.SeriesNameField]]) seriesNameArr.push(item[this.SeriesNameField]);
			
			var so = seriesObj[item[this.SeriesNameField]]||{};
			so.name = item[this.SeriesNameField];
			so.points = so.points||[];

			var point = {name:item[this.PointNameField],y:item[this.PointYField], attrs:[]};
			if(this.AttributesList){
				Ext.each(this.AttributesList, function(attr){
					point.attrs.push({name:attr, value:item[attr]});
				}, this);
			}
			so.points.push(point);

			seriesObj[item[this.SeriesNameField]] = so;
		},this);
		
		Ext.each(seriesNameArr,function(name){
			this.series.push(seriesObj[name]);
		},this);
	},
	processPointData : function(data){
		delete this.series;
		this.series = [];
		if(!Ext.isArray(data)){
			data = [].concat(data);
		}
		
		//格式三 注意Point的出现顺序即可
		//data = [{city:'福州', amt:'100', amtP:'120'}, {city:'厦门', amt:'140', amtP:'130'}];
		//this.PointNameField = "city";
		//this.SeriesList = [{name:'分拣量',valueField:'amt'},{name:'已拣量',valueField:'amtP'}];  //格式固定
		this.SeriesList = [].concat(this.SeriesList);

		Ext.each(this.SeriesList, function(s){
			var so = {};
			so.name = s.name;
			so.points = [];
			Ext.each(data,function(p){
				var po = {};
				if(p[this.PointNameField]!=null||p[this.PointNameField]!=undefined){
					po.name = p[this.PointNameField].trim();   //TODO 从数据库取出来时，末尾产生了空格？？
					po.y = p[s.valueField];
					po.attrs = [];
					if(this.AttributesList){
						po.attrs = [];
						Ext.each(this.AttributesList, function(attr){
							po.attrs.push({name:attr, value:p[attr]});
						}, this);
					}
					so.points.push(po);
				}
			},this);
			this.series.push(so);
		},this);
	}
});

Ext.reg('anychart',Ext.ux.chart.ExtAnyChart);

Ext.ux.chart.ExtAnyChart.xml = new Ext.XTemplate(
'<?xml version="1.0" encoding="UTF-8"?>\n' +   //<!--根元素-->
'<anychart>   \n' +   //<!--根元素-->
'	<settings>   \n' + 
'		<animation enabled="True" />   \n' +   //<!--初始化的时候是否显示动画效果-->
'       <image_export use_title_as_file_name="True"/>  \n' +
'       <context_menu version_info="False" about_anychart="False">  \n' +
'       	<save_as_image_item_text>保存图片...</save_as_image_item_text>\n' + 
'       	<print_chart_item_text>打印图片...</print_chart_item_text>\n' + 
'       </context_menu>  \n' +
'	</settings>   \n' + 
'   <margin all="0"/>\n' +
'	<charts>   \n' +   //当使用<dashboard>做仪表盘的时候，charts包含的多个chart才有用，否则只显示第一个chart
'		<chart plot_type="{plotType}">   \n' +  //Plot类型(Plot有点像layout的概念)与序列类型是相关的，参见【AnyChart--处理数据.docx】
'			<data_plot_settings default_series_type="{type}" enable_3d_mode="false" z_aspect="1">   \n' +  //为该chart内显示的各种序列类型进行设置
'				<bar_series>   \n' +      //直方图
'					<label_settings enabled="True" rotation="{labelRotation}">   \n' + 
'						<position  anchor="CenterTop" halign="Center" valign="{labelValign}"/>   \n' + 
'						<format>{labelFormat}</format>   \n' + 
'						<font bold="False" size="9"/>   \n' + 
'						<background enabled="False"/>   \n' + 
'					</label_settings>   \n' + 
'					<tooltip_settings enabled="True">   \n' + 
'						<position anchor="CenterTop" halign="Center" valign="{labelValign}"/>   \n' + 
'						<format>{tooltipsFormat}</format>   \n' + 
'						<font size="14"/>   \n' + 
'					</tooltip_settings>   \n' + 
'				</bar_series>   \n' + 
'				<line_series>   \n' +      //曲线图
'                             <tooltip_settings enabled="true"> \n'+
'                                     <format> 时间: {%Name}     {%Value}</format>\n'+
'                             </tooltip_settings> \n'+
'					<label_settings enabled="True" rotation="{labelRotation}">   \n' + 
'						<position  anchor="Center" halign="Center" valign="{labelValign}"/>   \n' + 
//'						<format>{labelFormat}</format>   \n' + 
'						<format></format>   \n' + 
'						<font bold="False" size="9"/>   \n' + 
'						<background enabled="False"/>   \n' + 
'					</label_settings>   \n' + 
//'					<tooltip_settings enabled="true">   \n' + 
//'					 <format>   \n' + 
//'					 <![CDATA[   \n' + 
//'					 Name: {%Name}{enabled:false}   \n' + 
//'					 Value: {%Value}{numDecimals:0}   \n' + 
//'					 ]]>   \n' + 
//'					 </format>   \n' + 
//'					 <background>   \n' + 
//'					   <corners type="Rounded" all="3"/>   \n' + 
//'					   <effects enabled="false"/>   \n' + 
//'					   <border type="Solid" color="#494949" opacity="0.5"/>   \n' + 
//'					  </background>   \n' + 
//'					</tooltip_settings>   \n' + 
'				</line_series>   \n' + 
'				<pie_series>   \n' +      //饼图
'					<label_settings enabled="True" mode="Outside">   \n' + 
'						<position anchor="Center" halign="Center" valign="{labelValign}"/>   \n' + 
'						<format>{labelFormat}</format>\n' +
'						<font bold="False" size="11"/>   \n' + 
'						<background enabled="False"/>   \n' + 
'					</label_settings>   \n' + 
'				</pie_series>   \n' + 
'			</data_plot_settings>   \n' + 
'			<chart_settings>   \n' +      //图表设置信息
'				<chart_background enabled="False"/>   \n' +  //图表的背景，就是一个框框 
'				<title enabled="True">   \n' + 
'					<text>{title}</text>   \n' + 
'				</title>   \n' + 
'				<legend enabled="{legendEnabled}" position="{legendPosition}" align="Center" ignore_auto_item="True">   \n' +   //图例显示设置
'					<format>{%Icon} {%Name}</format>   \n' + 
'					<title enabled="false"/>   \n' + 
'					<columns_separator enabled="false"/>   \n' + 
'					<background>   \n' + 
'						<inside_margin left="10" right="10"/>   \n' + 
'					</background>   \n' + 
'					<items>   \n' + 
'						<item source="{legendItem}"/>   \n' + 
'					</items>   \n' + 
'				</legend>   \n' + 
'				<axes>   \n' + 
'					<x_axis tickmarks_placement="Center">   \n' + 
'						<title enabled="false"/>   \n' + 
'						<labels rotation="{labelRotation}"/>   \n' + 
'						<scale major_interval="{xAxisTime}"/>' + 
'					</x_axis>   \n' + 
'					<y_axis>   \n' + 
'						<title enabled="false"/>   \n' + 
'						<minor_grid enabled="false"/>   \n' + 
'						<major_grid interlaced="false"/>   \n' + 
'						<labels>   \n' + 
'							<format>{%Value}</format>   \n' + 
'						</labels>   \n' + 
'						{yNum}   \n' + 
'  						<axis_markers> \n' + 
'    						<tpl if="thresholdValue>0"> \n' + 
'    						<lines> \n' + 
'      							<line value="{thresholdValue}" color="Red"> \n' + 
'      								<label enabled="true"><format>{thresholdTitle} {%Value}</format></label> \n' + 
'      							</line> \n' + 
'    						</lines> \n' + 
'    						</tpl> \n' + 
'    						<tpl if="thresholdRange.length>0"> \n' + 
' 							<ranges>\n' +
'    							<tpl for="thresholdRange"> \n' + 
' 								<range minimum ="{min}" maximum="{max}" display_under_data="False">\n' +
' 									<fill color="{fillColor}" opacity="0.5"/>\n' +
' 									<minimum_line color="{lineColor}" opacity="1"/>\n' +
' 									<maximum_line color="{lineColor}" opacity="1"/>\n' +
' 									<label enabled="True" position="Far">\n' +
' 										<format>{title}</format>\n' +
' 									</label>\n' +
' 								</range>\n' +
'    							</tpl>\n' + 
' 							</ranges>\n' +
'    						</tpl> \n' + 
' 			 			</axis_markers> \n' + 
'						<scale inverted="False" {minimum} mode="{mode}"/>   \n' + 
'					</y_axis>   \n' + 
'				</axes>   \n' + 
'			</chart_settings>   \n' + 
'			{data}   \n' + 
'		</chart>   \n' + 
'	</charts>   \n' + 
'</anychart> ',{
	isPie :function(type){
		return type=="Pie";
	},
	isLine :function(type){
		return type=="Line";
	},
	isBar :function(type){
		return type=="Bar";
	}});

Ext.ux.chart.ExtAnyChart.dataXml = new Ext.XTemplate('<data>\n{series}</data>\n');

Ext.ux.chart.ExtAnyChart.seriesXml = new Ext.XTemplate(
'<tpl for=".">',
	'<series name="{name}">\n',
		'<tpl for="points">',
	  		'<point name="{name}" y="{y}">\n',
	  			'<attributes>\n',
	  				'<tpl for="attrs">',
	  				'<attribute name="{name}"><![CDATA[{value}]]></attribute>\n',
	  				'</tpl>',
	  			'</attributes>\n',
	  		'</point>\n',
		'</tpl>',
	'</series>\n',
'</tpl>');

Ext.ux.chart.ExtAnyChart.pointXml = new Ext.XTemplate('<point name="{name}" y="{y}"/>');