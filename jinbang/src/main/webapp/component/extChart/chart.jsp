<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/head.ext4.inc.jsp"%>
</head>
<body>
	<button id="aaa">aaa</button>
	<div id="container" style="height: 500px; min-width: 500px"></div>
	<script type="text/javascript" src="${ctx}/component/socket/jq.js"></script>
  	<script type="text/javascript" src="${ctx}/component/extChart/highstock.js"></script>
  	<script type="text/javascript">
  	$(function() {
  		$('#aaa').click(function(){
  			$('#container').width(400);
  		});
  		$.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function(data) {
  			$('#container').highcharts('StockChart', {
  				rangeSelector : {
  					selected : 1
  				},
  				title : {
  					text : 'AAPL Stock Price'
  				},
  				series : [{
  					name : 'AAPL',
  					data : data,
  					tooltip: {
  						valueDecimals: 2
  					}
  				}]
  			});
  		});
  	});
  	</script>
</body>
</html>