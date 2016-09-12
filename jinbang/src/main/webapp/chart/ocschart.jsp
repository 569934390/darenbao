<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/head.ext4.inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	html,body{width:100%;height:100%;overflow: auto;}
	.chart{
		width:50%;
		height: 50%;
		float: left;
	}
	.selectSystem{
		width:10;
		height: 5;
		float: left;
	}
</style>
</head>
<body>
	<div class="selectSystem">
		<select id="products" name="products" tabindex="10" onchange="if (this.selectedIndex!=0) window.location.href=this.value">
			    <option value="">=选择系统=</option>
			    <option value="${ctx}/chart/ocschart.jsp" selected="selected">OCS</option>
		</select>
	</div>
	<br>
	<br>
	<br>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div class="chart" id="chart1"></div>
    <div class="chart" id="chart2"></div>
<!--    <div class="chart" id="chart3"></div>-->
    <div class="chart" id="chart4"></div>
    <div class="chart" id="chart5"></div>
<!--    <div class="chart" id="chart6"></div>-->
    <div class="chart" id="chart7"></div>
    <!-- ECharts单文件引入 -->
   <script type="text/javascript" src="${ctx}/public/ECharts/echarts-plain.js"></script>
   <script type="text/javascript" src="js/ocswrongmessagechart.js"></script>
   <script type="text/javascript" src="js/ocsdelaytimechart.js"></script>
<!--   <script type="text/javascript" src="js/ocssecondhandlechart.js"></script> -->
   <script type="text/javascript" src="js/ocsbusinesshandlesuccesschart.js"></script>
   <script type="text/javascript" src="js/ocsmessagelostnumchart.js"></script>
<!--   <script type="text/javascript" src="js/ocssrsystemsendsucesschart.js"></script> --> 
   <script type="text/javascript" src="js/ocsbusinesschangechart.js"></script>
</body>
</html>