<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/public/AnyChart/AnyChart.js"></script>

<!-- 两列多行立体柱状图 -->
<textarea id="xmlArea_doubleBar3D" style="display:none"> 
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical">
				<data_plot_settings default_series_type="Bar" enable_3d_mode="True" z_aspect="0.5">
					<bar_series point_padding="0" group_padding="0.5" style="AquaLight">
						<label_settings enabled="true">
							<background enabled="false"/>
							<position anchor="Center" valign="Center" halign="Center"/>
							<font color="White" bold="true">
								<effects>
									<drop_shadow enabled="true" distance="1" angle="45" blur_x="1.5" blur_y="1.5" strength="2" opacity="0.5"/>
								</effects>
							</font>
							<format>{%Value}{numXD}</format>
						</label_settings>
						<tooltip_settings enabled="True">
						<format>
						{titleName}: {%Name}
						{seriesName}: {%SeriesName}
						{seriesValue}: {%YValue}{numD}{unit}
						</format>
						<font bold="False" size="12"/>
						</tooltip_settings>
					</bar_series>
				</data_plot_settings>
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
					<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Right">
						<format>{%Icon}{%SeriesName}</format>
						<template></template>
						<title enabled="false">
							<text>{dataTitle}</text>
						</title>
						<columns_separator enabled="true"/>
						<background>
							<inside_margin left="10" right="10"/>
						</background>
						<items>
							<item source="series"/>
						</items>
						<font bold="False" size="12"/>
					</legend>
					<axes>
						<y_axis position="Left">
				          	<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<scale mode="Stacked" {dzData}/>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
						<extra_y_axis_1 enabled="false">
							<scale mode="Stacked"/>
						</extra_y_axis_1>
						<y_axis name="extra_y_axis_0" position="Left">
							<title enabled="false">
								<text></text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
						 <x_axis>
				        	<title enabled="false">
								<text></text>
							</title>
				            <labels/>
				        </x_axis>
				        
					</axes>
				</chart_settings>
				<data>
			    	{data}
			    </data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 两层饼图 -->
<textarea id="xmlArea_doublePie" style="display:none"> 
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
	<charts>
	<chart plot_type="Pie">
		<data_plot_settings enable_3d_mode="false">
			<pie_series>
				<tooltip_settings enabled="true">
				<format>
				{titleName}: {%Name}
				{seriesName}: {%SeriesName}
				{seriesValue}: {%Value}{numDecimals:2}{unit}
				{Percent}: {%YPercentOfSeries}{numDecimals: 2}%
				</format>
				<font bold="False" size="12"/>
				</tooltip_settings>
				<label_settings enabled="true">
					<background enabled="false"/>
					<position anchor="Center" valign="Center" halign="Center" padding="0"/>
					<font color="White" size="10">
						<effects>
							<drop_shadow enabled="true" distance="2" opacity="0.5" blur_x="2" blur_y="2"/>
						</effects>
					</font>
					<format>{%Name}:{%Value}{numDecimals:0}</format>
				</label_settings>
			</pie_series>
		</data_plot_settings>
		<data>
			{data}
		</data>
		<chart_settings>
			<title enabled="true" padding="20">
				<text>{title}</text>
			</title>
			<legend enabled="false">
				<title enabled="false"/>
			</legend>
			<font bold="False" size="12"/>
		</chart_settings>
	</chart>
	</charts>
	</anychart>
</textarea>

<!-- 两列立体柱状图 -->
<textarea id="xmlArea_logarithmic3D" style="display:none">
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical">
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
					<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Bottom">
						<format>{%Icon}{%SeriesName}</format>
						<template></template>
						<title enabled="false">
							<text>{dataTitle}</text>
						</title>
						<columns_separator enabled="true"/>
						<background>
							<inside_margin left="10" right="10"/>
						</background>
						<items>
							<item source="series"/>
						</items>
						<font bold="False" size="12"/>
					</legend>
					<axes>
						<x_axis>
							<title enabled="true">
								<text></text>
							</title>
							  <zoom enabled="true" start="南昌" visible_range="6" />
						</x_axis>
						<y_axis>
							<scale minimum="0"/>
							<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
					</axes>
  					<chart_background enabled="false"/>
				</chart_settings>
				<data_plot_settings default_series_type="Bar" enable_3d_mode="True" z_aspect="1.2">
					<bar_series point_padding="0.2" group_padding="1">
						<label_settings enabled="True" rotation="90">
						   <position anchor="Center" halign="Center" valign="Center"/>
						   <format>{%Value}{numDecimals:0}</format>
						   <font bold="False" color="White">
						   		<effects>
									<drop_shadow enabled="True" opacity="0.5" distance="2" blur_x="1" blur_y="1"/>
								</effects>
						   </font>
						   <background enabled="False"/>
						</label_settings>
						<tooltip_settings enabled="True">
							<format>
								{pointName}: {%Name}
								{seriesName}: {%SeriesName}
								{pointValue}: {%YValue}{numDecimals:0}
							</format>
							<font bold="False" size="12"/>
							<background>
								<border color="DarkColor(%Color)"/>
								<font>
									<effects enabled="true">
										<drop_shadow enabled="true" color="Black" opacity="1"/>
									</effects>
								</font>
							</background>
							<font color="DarkColor(%Color)"/>
						</tooltip_settings>
						<bar_style>
							<states>
								<normal>
									<border color="DarkColor(%Color)" thickness="1"/>
								</normal>
								<hover>
									<border thickness="2"/>
								</hover>
							</states>
						</bar_style>
					</bar_series>
				</data_plot_settings>
				<data>
					{data}
				</data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 两列立体柱状图 -->
<textarea id="xmlArea_bar3D" style="display:none">
	<anychart>
	  <margin all="0" left="0" right="0" bottom="0"/>
	  <settings>
	    <animation enabled="True"/>
	  </settings>
	  <charts>
	    <chart plot_type="CategorizedVertical">
	      <data_plot_settings default_series_type="Bar">
	        <bar_series group_padding="0.3" style="AquaLight">
	          <label_settings enabled="true">
	            <background enabled="false"/>
	            <position anchor="Center" valign="Center" halign="Center"/>
	            <font color="White" bold="true">
	              <effects>
	                <drop_shadow enabled="true" distance="1" angle="45" blur_x="1.5" blur_y="1.5" strength="2" opacity="0.5"/>
	              </effects>
	            </font>
	            <format>{%Value}{numDecimals:0}</format>
	          </label_settings>
	          <bar_style>
	            <fill opacity="1"/>
	            <states>
	              <hover color="White"/>
	            </states>
	          </bar_style>
	        </bar_series>
	      </data_plot_settings>
	      <chart_settings>
	        <title enabled="true">
	          <text>{title}</text>
	        </title>
	        <legend enabled="True" position="Right" align="Center" elements_align="Right">
				<title enabled="false"/>
				<format>{%Icon}{%Name}:{%Value}{numDecimals: 0}{dtunit}</format>
				<template></template>
				<columns_separator enabled="true"/>
				<background>
					<inside_margin left="10" right="10"/>
				</background>
				<items>
					<item source="Points"/>
				</items>
			</legend>
	        <axes>
	          <y_axis position="Left">
	          	<title enabled="true">
					<text>{Y_AXIS}</text>
				</title>
				<labels>
					<format>{%Value}{numDecimals:0}</format>
				</labels>
	            <scale minimum="1" type="Logarithmic" log_base="5"/>
	          </y_axis>
	          <x_axis>
	         	 <title enabled="false">
					<text></text>
				</title>
	            <labels/>
	          </x_axis>
	        </axes>
			<chart_background enabled="true"/>
	      </chart_settings>
	      <data>
	        {data}
	      </data>
	    </chart>
	  </charts>
	</anychart>
</textarea>

<!-- 单列多行柱状立体图 -->
<textarea id="xmlArea_threeBar3D" style="display:none">
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical">
				<data_plot_settings default_series_type="Bar" enable_3d_mode="True" z_aspect="0.5">
					<bar_series group_padding="0.3">
						<label_settings enabled="true">
							<background enabled="false"/>
							<position anchor="Center" valign="Center" halign="Center"/>
							<font color="White" bold="true">
								<effects>
									<drop_shadow enabled="true" distance="1" angle="45" blur_x="1.5" blur_y="1.5" strength="2" opacity="0.5"/>
								</effects>
							</font>
							<format>{%YValue}{numDecimals:0}</format>
						</label_settings>
						<marker_settings enabled="true">
							<marker type="None" size="10"/>
							<states>
								<hover>
									<marker type="Circle"/>
									<border thickness="2"/>
								</hover>
								<pushed>
									<marker type="Circle" size="6"/>
									<border thickness="2"/>
								</pushed>
								<selected_normal>
									<marker type="Star5" size="16"/>
									<border thickness="1"/>
								</selected_normal>
								<selected_hover>
									<marker type="Star5" size="12"/>
									<border thickness="1"/>
								</selected_hover>
							</states>
						</marker_settings>
						<tooltip_settings enabled="True">
							<position anchor="CenterTop" valign="Top" halign="Center" padding="10"/>
							<background>
								<border thickness="2" type="Solid" color="DarkColor(%Color)"/>
							</background>
							<format>
							{titleName}: {%Name}
							{seriesName}: {%SeriesName}
							{seriesValue}: {%YValue}{numD}{unit}
							</format>
							<font bold="False" size="12"/>
						</tooltip_settings>
					</bar_series>
				</data_plot_settings>
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
					
					<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Right">
						<format>{%Icon}{%SeriesName}</format>
						<template></template>
						<title enabled="false">
							<text>{dataTitle}</text>
						</title>
						<columns_separator enabled="true"/>
						<background>
							<inside_margin left="10" right="10"/>
						</background>
						<items>
							<item source="series"/>
						</items>
						<font bold="False" size="12"/>
					</legend>
					
					<axes>
						<y_axis position="Left">
				          	<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<scale mode="Stacked" minimum="1" type="Logarithmic" log_base="5"/>
							<labels show_last_label="false" align="Inside">
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
						
						 <x_axis>
				        	<title enabled="false">
								<text></text>
							</title>
				            <labels/>
				        </x_axis>
					</axes>
				</chart_settings>
				<data>
				{data}
			    </data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 单行多列立体柱状图 -->
<textarea id="xmlArea_categorized" style="display:none">
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedBySeriesVertical">
				<data_plot_settings default_series_type="Bar" enable_3d_mode="true" z_aspect="0.5">
					<bar_series group_padding="0.5">
						<tooltip_settings enabled="true">
							<format>
							{seriesName}: {%SeriesName}
							{titleName}: {%Name}
							{seriesValue}: {%YValue}{numXD}{unit}
							</format>
							<font bold="False" size="12"/>
						</tooltip_settings>
						<label_settings enabled="true" rotation="60">
							<position anchor="CenterBottom"/>
							<format>{%Name}</format>
							<background enabled="true">
								<border enabled="false"/>
								<fill type="Solid" color="DarkColor(%Color)"/>
								<effects enabled="false"/>
								<inside_margin all="0"/>
							</background>
							<font size="13" color="White" bold="false"/>
						</label_settings>
						
					</bar_series>
				</data_plot_settings>
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
					<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Right">
						<format>{%Icon}{%SeriesName}</format>
						<template></template>
						<title enabled="false">
							<text>{dataTitle}</text>
						</title>
						<columns_separator enabled="true"/>
						<background>
							<inside_margin left="10" right="10"/>
						</background>
						<items>
							<item source="series"/>
						</items>
						<font bold="False" size="12"/>
					</legend>
					<axes>
						<y_axis>
				          	<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
						
						 <x_axis>
				        	<title enabled="false">
								<text></text>
							</title>
				            <labels/>
				        </x_axis>
					</axes>
				</chart_settings>
				<data>
					{data}
				</data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 多行折线图 -->
<textarea id="xmlArea_spline" style="display:none">
	<anychart>
		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
	  <charts>
	    <chart plot_type="CategorizedVertical">
	    	<chart_settings>
	    		<title>
	    			<text>{title}</text>
	    			<background enabled="false"/>
	    		</title>
	    		<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Right">
					<format>{%Icon}{%SeriesName}</format>
					<font bold="False" size="12"/>
					<template></template>
					<title enabled="false">
						<text>{dataTitle}</text>
					</title>
					<columns_separator enabled="true"/>
					<background>
						<inside_margin left="10" right="10"/>
					</background>
					<items>
						<item source="series"/>
					</items>
					<font bold="False" size="12"/>
				</legend>
				<axes>
					<y_axis>
			          	<title enabled="true">
							<text>{Y_AXIS}</text>
						</title>
						<labels>
							<format>{%Value}{numDecimals:0}</format>
						</labels>
					</y_axis>
					
					 <x_axis>
			        	<title enabled="false">
							<text></text>
						</title>
			            <labels/>
			        </x_axis>
				</axes>
    		</chart_settings>
	    	<data_plot_settings default_series_type="Spline">
	    		<line_series>
	    			<marker_settings>
	    					<marker size="8"/>
	    					<states>
	    						<hover>
	    							<marker size="12"/>
	    						</hover>
	    					</states>
	    			</marker_settings>
	    			<tooltip_settings enabled="True">
						<format>
						{titleName}: {%Name}
						{seriesName}: {%SeriesName}
						{seriesValue}: {%YValue}{numDecimals:2}{unit}
						</format>
						<font bold="False" size="12"/>
					</tooltip_settings>
	    		</line_series>
	    	</data_plot_settings>
		    <data>
		    	{data}
		    </data>
	    </chart>
	  </charts>
	</anychart>
</textarea>

<!-- 多行折线图 -->
<textarea id="xmlArea_line" style="display:none">  
	<anychart>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical">
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
			        <legend enabled="True" position="Bottom" align="Center" elements_align="Center">
						<title enabled="false"/>
			          <icon>
			            <marker enabled="true" type="%MarkerType" size="8"/>
			          </icon>
					</legend>
					<axes>
						<x_axis tickmarks_placement="Center">
							<labels display_mode="Normal" rotation="60" />
							<title enabled="false">
								<text>X轴</text>
							</title>
						</x_axis>
						<y_axis name="extra_y_axis_0" position="Left">
							<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
					</axes>
  					<chart_background enabled="false"/>
				</chart_settings>
				<data_plot_settings default_series_type="Line">
					<line_series>
						<label_settings enabled="false" >
							<background enabled="false"/>
							<font color="Rgb(45,45,45)" bold="true" size="9">
								<effects enabled="true">
									<glow enabled="true" color="White" opacity="1" blur_x="1.5" blur_y="1.5" strength="3"/>
								</effects>
							</font>
							<format>{%YValue}{numDecimals: 0}</format>
						</label_settings>
						<tooltip_settings enabled="true">
							<format>
								{%Name}: {%YValue}{numDecimals:0}
							</format>
							<background>
								<border type="Solid" color="DarkColor(%Color)"/>
							</background>
							<font color="DarkColor(%Color)"/>
						</tooltip_settings>
						<marker_settings enabled="true"/>
						<line_style>
							<line thickness="3"/>
						</line_style>
					</line_series>
				</data_plot_settings>
				<data>
					{data}
				</data>
			</chart>
	
		</charts>
	</anychart>
</textarea>

<!-- 多行折线图 -->
<textarea id="xmlArea_lines" style="display:none">  
<anychart>
	<settings>
		<animation enabled="True"/>
	</settings>
	<charts>
		<chart plot_type="CategorizedVertical">
			<data_plot_settings default_series_type="Line">
				<line_series>
					<marker_settings>
						<marker type="None"/>
						<states>
							<hover>
								<marker type="Diamond"/>
							</hover>
						</states>
					</marker_settings>
					<tooltip_settings enabled="True">
					<format>
					{%Name}
					{%SeriesName} - {%Value}{numDecimals:0}
					</format>
					</tooltip_settings>
					<effects enabled="True">
						<drop_shadow enabled="False"/>
						<bevel enabled="true" distance="1" blur_x="2" blur_y="2"/>
					</effects>
					<line_style>
						<line thickness="3"/>
					</line_style>
				</line_series>
			</data_plot_settings>
			<chart_settings>
				<title enabled="true">
					<text>{title}</text>
				</title>
				<axes>
					<y_axis>
						<scale minimum="0" maximum="100" major_interval="5"/>
						<labels>
							<format>{%Value}{numDecimals:0}</format>
						</labels>
						<title>
							<text>{leftText}</text>
						</title>
						<axis_markers>
							<ranges>
								<range minimum ="30" maximum="75">
									<fill color="#D9CDFF" opacity="0.7"/>
									<minimum_line color="#AEA4CC" opacity="1"/>
									<maximum_line color="#AEA4CC" opacity="1"/>
									<label enabled="True" position="Near">
										<format>湿度合格区间</format>
										<font bold="True" color="#393939"/>
									</label>
								</range>
								<range minimum ="2" maximum="8">
									<fill color="#CBFFCF" opacity="0.7"/>
									<minimum_line color="#96BC99" opacity="1"/>
									<maximum_line color="#96BC99" opacity="1"/>
									<label enabled="True" position="Far" multi_line_align="Center">
										<font bold="true" color="#393939"/>
										<format>
										温度合理区间
										</format>
									</label>
								</range>
							</ranges>
						</axis_markers>
					</y_axis>
					<x_axis tickmarks_placement="Center">
						<title enabled="False"/>
					</x_axis>
				</axes>
				<legend enabled="True" position="Bottom" align="Center" elements_align="Center">
					<title enabled="False"/>
					<columns_separator enabled="False"/>
				</legend>
			</chart_settings>
			<data>
			    <series name="湿度">
			    	<point name='01:58' y='36' />
			    	<point name='02:58' y='34' />
			    	<point name='03:58' y='47' />
			    	<point name='04:58' y='52' />
			    	<point name='05:58' y='49' />
			    	<point name='06:58' y='47' />
			    	<point name='07:58' y='39' />
			    	<point name='08:58' y='36' />
			    	<point name='09:58' y='31' />
			    	<point name='10:58' y='35' />
			    	<point name='11:58' y='28' />
			    	<point name='12:58' y='32' />
			    	<point name='13:58' y='33' />
			    	<point name='14:58' y='32' />
			    	<point name='15:58' y='40' />
			    	<point name='16:58' y='40' />
					<point name='17:58' y='48' />
					<point name='18:58' y='47' />
					<point name='19:58' y='47' />
					<point name='20:58' y='50' />
					<point name='21:58' y='56' />
			    </series>
			    <series name="温度">
					<point name='01:58' y='6' />
					<point name='02:58' y='7' />
					<point name='03:58' y='8' />
					<point name='04:58' y='3' />
					<point name='05:58' y='2' />
					<point name='06:58' y='4' />
					<point name='07:58' y='4' />
					<point name='08:58' y='5' />
					<point name='09:58' y='6' />
					<point name='10:58' y='7' />
					<point name='11:58' y='4' />
					<point name='12:58' y='8' />
					<point name='13:58' y='2' />
					<point name='14:58' y='8' />
					<point name='15:58' y='8' />
					<point name='16:58' y='8' />
					<point name='17:58' y='6' />
					<point name='18:58' y='6' />
					<point name='19:58' y='6' />
					<point name='20:58' y='2' />
					<point name='21:58' y='6' />
			    </series>
		    </data>
		</chart>
	</charts>
</anychart>
</textarea>

<!-- 内饼图 -->
<textarea id="xmlArea_in_pie" style="display:none"> 
	<anychart>
  		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="Pie">
				<data_plot_settings enable_3d_mode="true">
					<pie_series group_padding="0.5">
						<tooltip_settings enabled="true">
							<format>{%Name}:{%Value}{numDecimals: 0}{pcunit}[{%YPercentOfSeries}{numDecimals: 2}%]</format>
							<font bold="True" size="13"/>
						</tooltip_settings>
						<label_settings enabled="true">
						<background enabled="false"/>
						<position anchor="Center" valign="Center" halign="Center" padding="10"/>
						<font color="White">
							<effects>
								<drop_shadow enabled="true" distance="2" opacity="0.5" blur_x="2" blur_y="2"/>
							</effects>
						</font>
						<format>{%YPercentOfSeries}{numDecimals:1}%</format>
						<font bold="False" size="12"/>
					</label_settings>
					<connector color="Black" opacity="0.4"/>
					</pie_series>
				</data_plot_settings>
				<chart_settings>
				<title enabled="true" padding="10">
					<text>{title}</text>
				</title>
				<chart_background enabled="false">					 	
					<inside_margin all="0"/>
				 	<corners type="Square"/>
				 	<effects enabled="false"/>
				</chart_background>
				<legend enabled="true" align="Spread" ignore_auto_item="true"  position="Bottom">
					<format>{%Icon}{%Name}:{%Value}{numDecimals: 0}{dtunit}</format>
					<template></template>
					<title enabled="true">
						<text>{tdata}</text>
						<font bold="False" size="12"/>
					</title>
					<font bold="False" size="12"/>
					<columns_separator enabled="true"/>
					<background>
						<inside_margin left="10" right="10"/>
					</background>
					<items>
						<item source="Points"/>
					</items>
				</legend>
			</chart_settings>
				<data>{data}</data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 柱状图 -->
<textarea id="xmlArea_bar" style="visibility:hidden"> 
	<anychart>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical" >
				<data_plot_settings default_series_type="Bar" enable_3d_mode="false" z_aspect="2">
					<bar_series point_padding="0.2" group_padding="1">
						<label_settings enabled="True"  rotation="270"><!-- 字体方向 -->
							   <position  anchor="Center" halign="Center" valign="Center"/>
							   <format>{%Value}{numDecimals: 0}</format>
							   <font bold="False" color="White">
							   		<effects>
										<drop_shadow enabled="True" opacity="0.5" distance="2" blur_x="1" blur_y="1"/>
									</effects>
							   </font>
							   <background enabled="False"/>
						</label_settings>
						<tooltip_settings enabled="True">
							<format>{%Name}: {%YValue}{numDecimals:0}</format><!--{numDecimals:0}
							--><background>
								<border color="DarkColor(%Color)"/>
							</background>
							<font color="DarkColor(%Color)"/>
						</tooltip_settings>
					</bar_series>
				</data_plot_settings>
				<chart_settings >
					<title enabled="true">
						<text>{title}</text>
					</title>
					<legend enabled="false" position="Bottom" align="Center" elements_align="Center">
						<title enabled="false"/>
					</legend>
					<axes>
						<x_axis>
						<labels display_mode="Normal" rotation="0" />
							<title enabled="true">
								<text></text>
							</title>
						</x_axis>
						<y_axis>
							<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
					</axes>
  					<chart_background enabled="false"/>
				</chart_settings>
				<data>
					{data}
				</data>
			</chart>
		</charts>
	</anychart>
</textarea>

<!-- 折线图 -->
<textarea id="xmlArea_tip_line" style="display:none">
	<anychart>
  		<margin all="0" left="0" right="0" bottom="0"/>
		<settings>
			<animation enabled="True"/>
		</settings>
		<charts>
			<chart plot_type="CategorizedVertical">
				<chart_settings>
					<title enabled="true">
						<text>{title}</text>
					</title>
					<axes>
						<x_axis tickmarks_placement="Center" size="3">
							<!--<zoom enabled="true" start="1995/02/02" visible_range="6" visible_range_unit="Month"/>
							-->
							<labels enabled="True" rotation="0"/>
							<title enabled="false">
								<text>X轴</text>
							</title>
						</x_axis>
						<y_axis name="extra_y_axis_0" position="Left">
							<title enabled="true">
								<text>{Y_AXIS}</text>
							</title>
							<labels>
								<format>{%Value}{numDecimals:0}</format>
							</labels>
						</y_axis>
					</axes>
  					<chart_background enabled="false"/>
				</chart_settings>
				<data_plot_settings default_series_type="Line">
					<line_series point_padding="0.2" group_padding="1">
						<label_settings enabled="true">
							<background enabled="false"/>
							<font color="Rgb(45,45,45)" bold="true" size="9">
	
								<effects enabled="true">
									<glow enabled="true" color="White" opacity="1" blur_x="1.5" blur_y="1.5" strength="3"/>
								</effects>
							</font>
							<format>{%YValue}{numDecimals:0}</format>
						</label_settings>
						<tooltip_settings enabled="true">
	<format>
	{%Name}: {%YValue}{numDecimals:{pcunit}}
	</format>
							<background>
								<border type="Solid" color="DarkColor(%Color)"/>
							</background>
							<font color="DarkColor(%Color)"/>
						</tooltip_settings>
						<marker_settings enabled="true"/>
						<line_style>
							<line thickness="3"/>
						</line_style>
					</line_series>
	
				</data_plot_settings>
				<data>
					{data}
				</data>
			</chart>
		</charts>
	</anychart>
</textarea>
